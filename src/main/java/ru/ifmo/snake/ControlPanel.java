package ru.ifmo.snake;

import javax.swing.*;
import java.awt.*;


/**
 * The bottom panel with 2 buttons on it.
 */
public class ControlPanel extends JPanel {
    JButton butRestart = new JButton("Restart");
    JButton butExit = new JButton("Exit");
    JLabel TFObj = new JLabel();

    /**
     * Setting buttons on Panel.
     */
    ControlPanel() {
        setBounds(50, 550, 500, 70);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        butRestart.setBounds(100, 10, 100, 50);
        add(butRestart);
        butExit.setBounds(350, 10, 100, 50);
        add(butExit);
        butExit.addMouseListener(new MouseListener());
        butRestart.addMouseListener(new MouseListener());
    }

    /**
     * Trying to set a Score on panel.
     */
    public void setLabel() {
        TFObj.setText("sssssssssssssssssssssS");
        TFObj.setBounds(0, 0, 50, 50);
        add(TFObj);
    }

}
