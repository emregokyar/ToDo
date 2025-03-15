package main_classes;

import javax.swing.*;
import java.awt.*;

public class TitleBar extends JPanel {
    public TitleBar() {
        this.setPreferredSize(new Dimension(400, 60));

        JLabel titleText = new JLabel("Task List");
        titleText.setPreferredSize(new Dimension(350, 30));
        titleText.setFont(new Font("Helvetica", Font.PLAIN, 20));
        titleText.setHorizontalAlignment(JLabel.LEFT);

        JLabel infoText = new JLabel("%-18s %-12s %s".formatted("Status", "Time", "Task"));
        infoText.setPreferredSize(new Dimension(350, 15));
        infoText.setFont(new Font("Helvetica", Font.PLAIN, 12));
        infoText.setHorizontalAlignment(JLabel.LEFT);

        this.add(titleText);
        this.add(infoText);
    }
}
