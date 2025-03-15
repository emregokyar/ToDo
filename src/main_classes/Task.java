package main_classes;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Task extends JPanel {
    private JLabel index;
    private JTextField taskName;
    private JButton statue;
    private JButton deleteTask;
    private JButton submit;
    private JButton timeButton;

    private String selectedTime= "00:00";
    private int taskId;
    private boolean checked;

    Task() {
        this.taskId= -1;

        checked = false;
        this.setPreferredSize(new Dimension(400, 58));
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLoweredBevelBorder());

        statue = new JButton("✘");
        statue.setPreferredSize(new Dimension(20, 30));
        this.add(statue);

        this.add(Box.createHorizontalStrut(5));

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 30));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index);

        this.add(Box.createHorizontalStrut(5));

        timeButton= new JButton("00:00");
        timeButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(timeButton);

        this.add(Box.createHorizontalStrut(5));

        taskName = new JTextField("new task");
        taskName.setPreferredSize(new Dimension(150, 30));
        taskName.setBackground(Color.lightGray);
        taskName.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(taskName);

        this.add(Box.createHorizontalStrut(5));

        submit = new JButton("OK");
        submit.setPreferredSize(new Dimension(20, 30));
        submit.setBorder(BorderFactory.createEmptyBorder());
        this.add(submit);

        this.add(Box.createHorizontalStrut(5));

        deleteTask = new JButton("\uD83D\uDDD1");
        deleteTask.setPreferredSize(new Dimension(20, 20));
        deleteTask.setBorder(BorderFactory.createEmptyBorder());
        this.add(deleteTask, BorderLayout.EAST);
    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getStatue() {
        return statue;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        if (Objects.equals(getStatue().getText(), "✘")){
            statue.setText("✔");
            checked = true;
        }else {
            statue.setText("✘");
            checked=false;
        }
    }

    public void changeTimeState(String newTime){
        timeButton.setText(newTime);
    }

    public JButton getDeleteTask() {
        return deleteTask;
    }

    public JButton getSubmit() {
        return submit;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public String returnText(){
        return taskName.getText();
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName.setText(taskName);
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public JButton getTimeButton() {
        return timeButton;
    }
}