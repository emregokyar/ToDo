package main_classes;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel{
    private JButton addTask;
    private JButton clear;

    public ButtonPanel() {
        this.setPreferredSize(new Dimension(500,60));

        addTask= new JButton("+");
        addTask.setFont(new Font("Helvetica", Font.PLAIN, 20));
        this.add(addTask, BorderLayout.WEST);

        this.add(Box.createHorizontalStrut(200));

        clear= new JButton("Clear");
        clear.setFont(new Font("Helvetica", Font.PLAIN, 15));
        this.add(clear, BorderLayout.EAST);
    }

    public JButton getNewTask(){
        return addTask;
    }

    public JButton getClear(){
        return clear;
    }
}
