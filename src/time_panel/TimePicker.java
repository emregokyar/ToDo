package time_panel;

import javax.swing.*;

public class TimePicker extends JPanel {
    private JComboBox<String> hourList;
    private JComboBox<String> minuteList;
    private JButton confirm;

    public TimePicker() {
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }
        hourList = new JComboBox<>(hours);
        hourList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(hourList);

        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        minuteList = new JComboBox<>(minutes);
        minuteList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(minuteList);

        confirm= new JButton("OK");
        this.add(confirm);
    }

    public String getTime() {
        var hour = hourList.getSelectedItem();
        var minute = minuteList.getSelectedItem();
        return (String) hour + ":" + minute;
    }

    public JButton getConfirm() {
        return confirm;
    }
}
