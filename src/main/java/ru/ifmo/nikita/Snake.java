package ru.ifmo.nikita;

import javax.swing.*;
import javax.swing.JFrame;

/**
 * GUI
 */
public class Snake extends JFrame {
    private int WINDOWS_HEIGHT = 600;
    private int WINDOWS_WIDTH = 700;

    public Snake() {
        Board boardObj = new Board();
        add(boardObj);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WINDOWS_HEIGHT, WINDOWS_WIDTH);
        setLocationRelativeTo(null);
        setTitle("Snake");
        setResizable(false);
        setVisible(true);

    }

    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        new Snake();
    }
}
