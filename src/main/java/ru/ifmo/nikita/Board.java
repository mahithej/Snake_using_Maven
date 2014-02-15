/**
 * Provides the classes necessary to create an applet.
 * ru.ifmo.nikita package.
 * @see java.awt.
 * @deprecated s.
 * @since w.
 */
package ru.ifmo.nikita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * {@value } goos.
 */
public class Board extends JPanel implements ActionListener {
    private int windowSize = 600;
    private int DOTS_SIZE = 10;
    private int DOTS = 3;
    private boolean neednewAppleFirst = true;
    private boolean neednewAppleSecond = true;
    public boolean isNeedSearchAppleZone = true;
    int[] x = new int[windowSize];
    int[] y = new int[windowSize];
    private int applex;
    private int appley;
    private int applexx;
    private int appleyy;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Image body, appleImg;
    private int position;
    private int startPosOfSnake = 50;
    private int delay = 150;
    private int limitVariable = 40;

    public Board() {
        addKeyListener(new TAdapter());
        ImageIcon iid = new ImageIcon(this.getClass().getResource("/dot.png"));
        body = iid.getImage();
        ImageIcon iidd = new ImageIcon(this.getClass().getResource("/apple.png"));
        appleImg = iidd.getImage();
        start();
        setFocusable(true);
        setBackground(Color.BLACK);

    }

    /**
     * The start method. Initializing Timer and snake.
     */
    final void start() {

        for (int z = 0; z < DOTS; z++) {
            x[z] = startPosOfSnake - z * DOTS_SIZE;
            y[z] = startPosOfSnake;
        }
        Timer timer = new Timer(delay, this);
        timer.start();
    }

    /**
     * Method for 2 apples drawing.
     *
     * @param g
     */
    public void drawApple(Graphics g) {
        if (neednewAppleFirst) {
            appley = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            applex = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            neednewAppleFirst = false;
        }
        g.drawImage(appleImg, applex, appley, this);

        if (neednewAppleSecond) {
            applexx = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            appleyy = DOTS_SIZE * ((int) (Math.random() * limitVariable));
            neednewAppleSecond = false;
        }
        g.drawImage(appleImg, applexx, appleyy, this);
    }

    /**
     * Painting method
     *
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);
        for (int z = 0; z < DOTS; z++) {
            g.drawImage(body, x[z], y[z], this);
        }
        drawApple(g);
    }

    /**
     * Checking for escaping apple. If snake is around apple, need to escaping apple.
     *
     * @param xx X coordinates for apple.
     * @param yy Y coordinates for apple.
     */
    public void checkApple(int xx, int yy) {
        if (isNeedSearchAppleZone) {
            if (((x[0] + 20 == xx) & (y[0] + 10 == yy))
                    | ((x[0] + 20 == xx) & (y[0] + 20 == yy))
                    | ((x[0] + 20 == xx) & (y[0] == yy))
                    | ((x[0] + 20 == xx) & (y[0] - 10 == yy))
                    | ((x[0] + 20 == xx) & (y[0] - 20 == yy))
                    | ((x[0] + 10 == xx) & (y[0] + 20 == yy))
                    | ((x[0] + 10 == xx) & (y[0] - 20 == yy))
                    | ((x[0] == xx) & (y[0] + 20 == yy))
                    | ((x[0] == xx) & (y[0] - 20 == yy))
                    | ((x[0] - 10 == xx) & (y[0] + 20 == yy))
                    | ((x[0] - 10 == xx) & (y[0] - 20 == yy))
                    | ((x[0] - 20 == xx) & (y[0] + 20 == yy))
                    | ((x[0] - 20 == xx) & (y[0] + 10 == yy))
                    | ((x[0] - 20 == xx) & (y[0] == yy))
                    | ((x[0] - 20 == xx) & (y[0] - 10 == yy))
                    | ((x[0] - 20 == xx) & (y[0] - 20 == yy))
                    ) {
                isNeedSearchAppleZone = false;

                appleEscaping();
            }
        }
    }


    void checkApple() {
        checkApple(applex, appley);
        checkApple(applexx, appleyy);

        if ((x[0] == applex) & (y[0] == appley)) {
            DOTS++;
            neednewAppleFirst = true;
            isNeedSearchAppleZone = true;
        }
        if ((x[0] == applexx) & (y[0] == appleyy)) {
            DOTS++;
            neednewAppleSecond = true;
            isNeedSearchAppleZone = true;
        }
    }

    public void appleEscaping() {
        position = ((int) (Math.random() * 9));
        switch (position) {
            case 0:
                repaint();
                break;
            case 1:
                applex -= 10;
                appley += 10;
                repaint();
                break;
            case 2:
                applex += 10;
                appley += 10;
                repaint();
                break;
            case 3:
                applex -= 10;
                appley += 10;
                repaint();
                break;
            case 4:
                applex += 10;
                appley -= 10;
                repaint();
                break;
            case 5:
                applex += 10;
                appley += 10;
                repaint();
                break;
            case 6:
                applex += 10;
                appley -= 10;
                repaint();
                break;
            case 7:
                applex -= 10;
                appley -= 10;
                repaint();
                break;
            case 8:
                applex -= 10;
                appley -= 10;
                repaint();
                break;
        }

    }

    /**
     *
     */
    void move() {
        if (((x[0] == -10 || x[0] == windowSize) || (y[0] == -10 || y[0] == windowSize))) {
            setBackground(Color.black);
            JOptionPane.showMessageDialog(null, "Meeting with the wall");
            System.exit(0);
        }
        for (int z = DOTS; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (left) {
            int size = DOTS_SIZE;
            x[0] -= size;
        }
        if (right) {
            int size = DOTS_SIZE;
            x[0] += size;
        }
        if (up) {
            int size = DOTS_SIZE;
            y[0] -= size;
        }
        if (down) {
            int size = DOTS_SIZE;
            y[0] += size;
        }
    }

    /**
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        move();
        checkApple();
        repaint();
        if (!isNeedSearchAppleZone) {
            appleEscaping();
            checkApple();
        }
    }


    private class TAdapter extends KeyAdapter {
        /**
         * @param e
         */
        public void keyPressed(KeyEvent e) {
            e = e;
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
