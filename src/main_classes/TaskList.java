package main_classes;

import com.mysql.cj.jdbc.MysqlDataSource;
import database_class.DataBase;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TaskList extends JPanel {
    private Connection connection;
    private DataBase dataBase;

    TaskList(){
        Properties props= new Properties();
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
            connection= dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dataBase= new DataBase(connection);

        GridLayout layout= new GridLayout(11,1);
        layout.setVgap(1);

        this.setLayout(layout);
    }

    public void updateNumbers(){
        Component[] listItems= this.getComponents();
            for (int i = 0; i < listItems.length; i++) {
                if (listItems[i] instanceof Task) {
                    ((Task) listItems[i]).changeIndex(i + 1);
                }
            }

    }

    public void removeCompletedTasks(){
        for (Component component: getComponents()){
            if (component instanceof Task){
                if (((Task) component).getState()){
                    remove(component);
                    try {
                        dataBase.deleteTask(((Task) component).getTaskId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    updateNumbers();
                }
            }
        }
    }
}
