package ru.ifmo.nikita;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;


public class Snake extends JFrame {

    public Snake() {

        add(new Board());


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Snake");

        setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Snake();

    }
}
