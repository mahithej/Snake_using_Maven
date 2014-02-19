package ru.ifmo.nikita;

import javax.swing.*;
import javax.swing.JFrame;

/**
 * Window creating
 */
public class Snake extends JFrame {
    private int windowSize = 500;
    public Snake() {
        add(new Board());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(windowSize, windowSize);
        setLocationRelativeTo(null);
        setTitle("Snake");
        setResizable(false);
        setVisible(true);

    }

    /**
     * Main method.
     * @param args  args
     */
    public static void main(String[] args) {
        new Snake();
    }
}
