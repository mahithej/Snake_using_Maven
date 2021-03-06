package ru.ifmo.snake;

import javax.swing.*;
import javax.swing.JFrame;

/**
 * GUI
 */
public class Snake extends JFrame {
    /**
     * {@value}WINDOWS_HEIGHT.
     */
    private int WINDOWS_HEIGHT = 600;
    /**
     * {@value}WINDOWS_WIDTH.
     */
    private int WINDOWS_WIDTH = 670;

    /**
     *  Snake
     */
    public Snake() {

        Board boardObj =  new Board();
        ControlPanel contrObj = new ControlPanel();
        add(boardObj);
        add(contrObj);
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
