package time_panel;

import javax.swing.*;

public class TimeFrame extends JDialog {
    private TimePicker timePicker;
    private JButton confirmButton;
    private String returnedTime;

    public TimeFrame(JFrame parent) {
        super(parent, "Time Selection", true);
        this.setSize(250, 70);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        timePicker = new TimePicker();
        this.add(timePicker);
        confirmButton = timePicker.getConfirm();

        confirmButton.addActionListener(e -> {
            returnedTime = timePicker.getTime();
            this.dispose();
        });

        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    public String getReturnedTime() {
        return returnedTime;
    }
}

