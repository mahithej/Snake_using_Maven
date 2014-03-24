package ru.ifmo.snake;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    public void mouseClicked(MouseEvent event) {
      System.exit(0);
        new Board();
    }
}
