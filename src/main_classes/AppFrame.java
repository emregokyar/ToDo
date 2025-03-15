package main_classes;

import com.mysql.cj.jdbc.MysqlDataSource;
import database_class.DataBase;
import time_panel.TimeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AppFrame extends JFrame {
    private DataBase dataBase;
    private TitleBar titleBar;
    private TaskList taskList;
    private ButtonPanel btnPanel;
    private JButton addTask;
    private JButton clear;
    private Connection connection;

    public AppFrame() {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("todo.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        try {
            connection = dataSource.getConnection();
            this.dataBase = new DataBase(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setSize(400, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        titleBar = new TitleBar();
        taskList = new TaskList();
        btnPanel = new ButtonPanel();

        this.add(titleBar, BorderLayout.NORTH);
        this.add(btnPanel, BorderLayout.SOUTH);
        this.add(taskList, BorderLayout.CENTER);

        addTask = btnPanel.getNewTask();
        clear = btnPanel.getClear();

        addListeners();
        this.validate();
    }

    public void addListeners() {
        getFromDataBase();

        addTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                taskList.add(task);
                taskList.updateNumbers();
                buttonActions(task, dataBase);
                revalidate();
                repaint();
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                taskList.removeCompletedTasks();
                revalidate();
                repaint();
            }
        });
    }

    public void getFromDataBase() {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM todo.tasks");

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_info"));

                String hour= resultSet.getString("task_hour");
                String normalHour = hour.substring(0,5);
                task.setSelectedTime(normalHour);
                task.changeTimeState(normalHour);

                buttonActions(task, dataBase);
                taskList.add(task);
                taskList.updateNumbers();
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    private void buttonActions(Task task, DataBase dataBase){
        task.getStatue().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                task.changeState();
                revalidate();
            }
        });

        var delete = task.getDeleteTask();
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    if (task.getTaskId() != -1) {
                        dataBase.deleteTask(task.getTaskId());
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                taskList.remove(task);
                taskList.updateNumbers();
                revalidate();
            }
        });

        var timeButton= task.getTimeButton();
        timeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TimeFrame timeFrame = new TimeFrame(AppFrame.this);
                var time = timeFrame.getReturnedTime();
                task.setSelectedTime(time);
                task.changeTimeState(time);
                revalidate();
                repaint();
            }
        });

        var submit = task.getSubmit();
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String taskTime = task.getSelectedTime();
                try {
                    if (task.getTaskId() != -1) {
                        dataBase.updateTask(task.returnText(), taskTime, task.getTaskId());
                    } else {
                        var id = dataBase.addTask(task.returnText(), taskTime);
                        task.setTaskId(id);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(taskTime);
                revalidate();
            }
        });
    }
}