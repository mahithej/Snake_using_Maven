/**
 * Provides the classes necessary to create an applet.
 * ru.ifmo.nikita package.
 * @see java.awt.
 * @deprecated s.
 * @since Java SE 1.7
 * @author Ivanov Nikita | tazg@ya.ru | SPb ITMO 5159
 */
package ru.ifmo.snake;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * I've no idea why CheckStyle indicates javadoc error on values description.
 * {@value } goos.
 */
class Board extends JPanel implements ActionListener, Runnable {
    private int snakePanelsize = 500;
    private int dotsSize = 10;
    private int dots = 3;
    private boolean isNeedFirstAppleDraw = true;
    private boolean isNeedSecondAppleDraw = true;
    protected boolean isNeedSearchAppleZone = true;
    protected int[] x = new int[snakePanelsize];
    protected int[] y = new int[snakePanelsize];
    private int firstAppleY;
    private int firstAppleX;
    private int secondAppleX;
    private int secondAppleY;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Image body, appleImg;
    List<Object> appleSearchZoneXYList = new ArrayList<Object>();
    ControlPanel contrObjj = new ControlPanel();
    Thread escapingThreadFirstApple, d;

    public Board() {
        addKeyListener(new ActionListener());
        ImageIcon _dot = new ImageIcon(this.getClass().getResource("/dot.png"));
        body = _dot.getImage();
        ImageIcon _apple = new ImageIcon(this.getClass().getResource("/apple.png"));
        appleImg = _apple.getImage();
        initGame();
        setFocusable(true);
        setBackground(Color.LIGHT_GRAY);
        setBounds(50, 20, snakePanelsize, snakePanelsize);
        setLayout(null);
        contrObjj.setLabel();
    }

    /**
     * Game initializing  method. Initializing Timer and snake.
     */
    private void initGame() {

        for (int z = 0; z < dots; z++) {
            int startPosOfSnake = 50;
            x[z] = startPosOfSnake - z * dotsSize;
            y[z] = startPosOfSnake;
        }
        int delay = 150;
        Timer timer = new Timer(delay, this);
        timer.start();
    }

    /**
     * Painting method.
     *
     * @param g graphics object.
     */
    public void paint(Graphics g) {
        super.paint(g);
        for (int z = 0; z < dots; z++) {
            g.drawImage(body, x[z], y[z], this);
        }
        drawApple(g);
    }

    /**
     * Method for 2 apples drawing.
     *
     * @param g Grahpics obj.
     */
    public void drawApple(Graphics g) {
        if (isNeedSecondAppleDraw) {
            int limitVariable = 50;
            secondAppleY = dotsSize * ((int) (Math.random() * limitVariable));
            secondAppleX = dotsSize * ((int) (Math.random() * limitVariable));
            isNeedSecondAppleDraw = false;
        }
        g.drawImage(appleImg, firstAppleY, firstAppleX, this);

        if (isNeedFirstAppleDraw) {
            int limitVariable = 50;
            firstAppleY = dotsSize * ((int) (Math.random() * limitVariable));
            firstAppleX = dotsSize * ((int) (Math.random() * limitVariable));
            isNeedFirstAppleDraw = false;
        }
        g.drawImage(appleImg, secondAppleX, secondAppleY, this);

    }


    /**
     * Checking ate apple. If ate, create new, using the flag.
     */
    private void checkApple() {

        if (isNeedSearchAppleZone) {
            checkSnakeIsNearByApple(firstAppleY, firstAppleX);
            checkSnakeIsNearByApple(secondAppleX, secondAppleY);
        }

        if (!isNeedSearchAppleZone) {
            if ((x[0] == firstAppleY) & (y[0] == firstAppleX)) {
                dots++;
                isNeedFirstAppleDraw = true;
                isNeedSearchAppleZone = true;
            }
            if ((x[0] == secondAppleX) & (y[0] == secondAppleY)) {
                dots++;
                isNeedSecondAppleDraw = true;
                isNeedSearchAppleZone = true;
            }
        }
    }

    /**
     * To get pair of X and Y coordinates as object.
     *
     * @param x
     * @param y
     * @return object consists of X and Y int values as coordinates
     */
    public Object getPairAsObj(int x, int y) {

        ArrayList pair = new ArrayList();
        pair.add(x);
        pair.add(y);
        return pair;

    }

    /**
     * Checking for escaping apple. If snake is around apple, need to escaping apple.
     * values 10 and 20 - is neighbor dots coordinates.
     *
     * @param appleX X coordinates for apple.
     * @param appleY Y coordinates for apple.
     * @return apppleSearchZoneXYList object for the UnitTest.
     */
    public void checkSnakeIsNearByApple(int appleX, int appleY) {
        int localX, localY;

        int appleEscapingZone = 20;

        appleSearchZoneXYList.clear();
        for (localX = appleX - appleEscapingZone; localX < appleX + appleEscapingZone; localX += dotsSize) {
            for (localY = appleY - appleEscapingZone; localY < appleY + appleEscapingZone; localY += dotsSize) {
                appleSearchZoneXYList.add(getPairAsObj(localX, localY));
            }
        }

        if (appleSearchZoneXYList.contains(getPairAsObj(x[0], y[0]))) {
            isNeedSearchAppleZone = false;

            escapingThreadFirstApple = new Thread(this);
            escapingThreadFirstApple.start();
        }
    }

    /**
     * Thread for the first apple escaping. Going to improve.
     */
    public void run() {
        int[] mas = new int[]{-10, 0, +10};

        while (!isNeedSearchAppleZone) {

            firstAppleX += mas[(int) (Math.random() * 3)];
            firstAppleY += mas[(int) (Math.random() * 3)];

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
    }


    /**
     * gameOverShow method shows game ending.
     */
    void gameOverShow() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setBounds(30, 20, snakePanelsize, snakePanelsize);
        setLayout(null);
        System.exit(0);
    }

    /**
     * Moving the snake in order of pressed button.
     */
    void move() {
        if (x[0] == -10 | x[0] == snakePanelsize | y[0] == -10 | y[0] == snakePanelsize) {
            new Board().gameOverShow();
        }

        for (int z = dots; z > 0; z--) {
            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                new Board().gameOverShow();
            }
        }

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (left) {
            x[0] -= dotsSize;
        }
        if (right) {
            x[0] += dotsSize;

        }
        if (up) {
            y[0] -= dotsSize;
        }
        if (down) {
            y[0] += dotsSize;
        }
    }

    /**
     * Listener method for buttons.
     *
     * @param e argument as object
     */
    public void actionPerformed(ActionEvent e) {
        move();
        checkApple();
        repaint();

    }

    private class ActionListener extends KeyAdapter {
        /**
         * Getting the Key event.
         *
         * @param e argument as object.
         */
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }

    }

}


class MouseListener extends MouseAdapter {
    public void mouseClicked(MouseEvent event) {
        // System.exit(0);
        new Board();
    }
}

/**
 * The bottom panel with 2 buttons on it.
 */
class ControlPanel extends JPanel {
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