/**
 * Provides the classes necessary to create an applet.
 * ru.ifmo.nikita package.
 * @see java.awt.
 * @deprecated s.
 * @since Java SE 1.7
 * @author Ivanov Nikita | tazg@ya.ru | SPb ITMO 5159
 */
package ru.ifmo.snake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * I've no idea why CheckStyle indicates javadoc error on values description.
 * {@value } goos.
 */
class Board extends JPanel implements ActionListener, Runnable {
    private static final int SNAKE_PANELSIZE = 500;
    private static final int DOTS_SIZE = 10;
    private int dots = 3;
    private boolean isNeedFirstAppleDraw = true;
    private boolean isNeedSecondAppleDraw = true;
    protected boolean isNeedSearchAppleZone = true;
    protected int[] x = new int[SNAKE_PANELSIZE];
    protected int[] y = new int[SNAKE_PANELSIZE];
    private int firstAppleY;
    private int firstAppleX;
    private int secondAppleX;
    private int secondAppleY;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Image body, appleImg;
    private List appleSearchZoneXYList = new ArrayList();
    private ControlPanel contrObjj = new ControlPanel();
    private Thread escapingThreadFirstApple;
    private static Logger logger = LoggerFactory.getLogger(Board.class);

    public Board() {
        addKeyListener(new ActionListener());
        ImageIcon dot = new ImageIcon(this.getClass().getResource("/dot.png"));
        body = dot.getImage();
        ImageIcon apple = new ImageIcon(this.getClass().getResource("/apple.png"));
        appleImg = apple.getImage();
        initGame();
        setFocusable(true);
        setBackground(Color.LIGHT_GRAY);
        setBounds(50, 20, SNAKE_PANELSIZE, SNAKE_PANELSIZE);
        setLayout(null);
        contrObjj.setLabel();

    }

    /**
     * Game initializing  method. Initializing Timer and snake.
     */
    private void initGame() {

        for (int z = 0; z < dots; z++) {
            int startPosOfSnake = 50;
            x[z] = startPosOfSnake - z * DOTS_SIZE;
            y[z] = startPosOfSnake;
        }
        final int delay = 150;
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
            secondAppleY = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            secondAppleX = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            isNeedSecondAppleDraw = false;
        }
        g.drawImage(appleImg, firstAppleY, firstAppleX, this);

        if (isNeedFirstAppleDraw) {
            int limitVariable = 50;
            firstAppleY = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            firstAppleX = DOTS_SIZE * ((int) (Math.random() * limitVariable));
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

    public ArrayList getPairAsObj(int x, int y) {
        ArrayList<Integer> pair = new ArrayList<Integer>();
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

        for (localX = appleX - appleEscapingZone; localX < appleX + appleEscapingZone; localX += DOTS_SIZE) {
            for (localY = appleY - appleEscapingZone; localY < appleY + appleEscapingZone; localY += DOTS_SIZE) {
                appleSearchZoneXYList.add(getPairAsObj(localX, localY));
            }
        }

        if (appleSearchZoneXYList.contains(getPairAsObj(x[0], y[0]))) {
            isNeedSearchAppleZone = false;
            escapingThreadFirstApple = new Thread(this);
            escapingThreadFirstApple.start();
            logger.info("Thread {}", escapingThreadFirstApple);
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
        setBounds(30, 20, SNAKE_PANELSIZE, SNAKE_PANELSIZE);
        setLayout(null);
        System.exit(0);
    }

    /**
     * Moving the snake in order of pressed button.
     */
    void move() {
        if (x[0] == -10 | x[0] == SNAKE_PANELSIZE | y[0] == -10 | y[0] == SNAKE_PANELSIZE) {
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
            x[0] -= DOTS_SIZE;
        }
        if (right) {
            x[0] += DOTS_SIZE;

        }
        if (up) {
            y[0] -= DOTS_SIZE;
        }
        if (down) {
            y[0] += DOTS_SIZE;
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
            logger.info("KeyPressed {}", e.getKeyText(e.getKeyCode()));
        }

    }

}


