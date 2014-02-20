/**
 * Provides the classes necessary to create an applet.
 * ru.ifmo.nikita package.
 * @see java.awt.
 * @deprecated s.
 * @since w.
 */
package ru.ifmo.nikita;

import sun.font.TextLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * I've no idea why CheckStyle indicates javadoc error on values description.
 * {@value } goos.
 */
public class Board extends JPanel implements ActionListener {
    private int snakePanelsize = 500;
    private int dotsSize = 10;
    private int dots = 3;
    private boolean needNewAppleFirst = true;
    private boolean needNewAppleSecond = true;
    protected boolean isNeedSearchAppleZone = true;
    protected int[] x = new int[snakePanelsize];
    protected int[] y = new int[snakePanelsize];
    private int appleOneCoordX;
    private int appleOneCoordY;
    private int appleTwoCoordX;
    private int appleTwoCoordY;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Image body, appleImg;
    // private ArrayList<Integer> listOfCoordX = new ArrayList<Integer>();
    //  private ArrayList<Integer> listOfCoordY = new ArrayList<Integer>();

    /**
     * Constructor, running only once, at the beginning.
     */
    ControlPanel contrObjj = new ControlPanel();

    public Board() {
        addKeyListener(new ActionListener());
        ImageIcon _dot = new ImageIcon(this.getClass().getResource("/dot.png"));
        body = _dot.getImage();
        ImageIcon _apple = new ImageIcon(this.getClass().getResource("/apple.png"));
        appleImg = _apple.getImage();
        start();
        setFocusable(true);
        setBackground(Color.LIGHT_GRAY);
        setBounds(50, 20, snakePanelsize, snakePanelsize);
        setLayout(null);
        // new ControlPanel().setLabel();
    }

    /**
     * The start method. Initializing Timer and snake.
     */
    private void start() {

        for (int z = 0; z < dots; z++) {
            int startPosOfSnake = 50;
            x[z] = startPosOfSnake - z * dotsSize;
            y[z] = startPosOfSnake;
            /*if (z != 0) {
                listOfCoordX.add(x[z]);
                listOfCoordY.add(y[z]);
            }  */
        }
        int delay = 150;
        Timer timer = new Timer(delay, this);
        timer.start();
    }


    /**
     * Method for 2 apples drawing.
     *
     * @param g Grahpics obj.
     */
    public void drawApple(Graphics g) {
        if (needNewAppleSecond) {
            int limitVariable = 50;
            appleTwoCoordY = dotsSize * ((int) (Math.random() * limitVariable));
            appleTwoCoordX = dotsSize * ((int) (Math.random() * limitVariable));
            needNewAppleSecond = false;
        }
        g.drawImage(appleImg, appleOneCoordX, appleOneCoordY, this);

        if (needNewAppleFirst) {
            int limitVariable = 50;
            appleOneCoordX = dotsSize * ((int) (Math.random() * limitVariable));
            appleOneCoordY = dotsSize * ((int) (Math.random() * limitVariable));
            needNewAppleFirst = false;
        }
        g.drawImage(appleImg, appleTwoCoordX, appleTwoCoordY, this);

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
     * Checking for escaping apple. If snake is around apple, need to escaping apple.
     * values 10 and 20 - is coordinates for neighbor dots.
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

    /**
     * Checking ate apple. If ate, create new, using the flag.
     */
    private void checkApple() {
        checkApple(appleOneCoordX, appleOneCoordY);
        checkApple(appleTwoCoordX, appleTwoCoordY);

        if ((x[0] == appleOneCoordX) & (y[0] == appleOneCoordY)) {
            dots++;
            needNewAppleFirst = true;
            isNeedSearchAppleZone = true;
        }
        if ((x[0] == appleTwoCoordX) & (y[0] == appleTwoCoordY)) {
            dots++;
            needNewAppleSecond = true;
            isNeedSearchAppleZone = true;
        }
    }

    /**
     * Escaping apple if snake nearby.
     */

    public void appleEscaping() {
        int position = ((int) (Math.random() * 9));
        switch (position) {
            case 0:
                repaint();
                break;
            case 1:
                appleOneCoordX -= 10;
                appleOneCoordY += 10;
                repaint();
                break;
            case 2:
                appleOneCoordX += 10;
                appleOneCoordY += 10;
                repaint();
                break;
            case 3:
                appleOneCoordX -= 10;
                appleOneCoordY += 10;
                repaint();
                break;
            case 4:
                appleOneCoordX += 10;
                appleOneCoordY -= 10;
                repaint();
                break;
            case 5:
                appleOneCoordX += 10;
                appleOneCoordY += 10;
                repaint();
                break;
            case 6:
                appleOneCoordX += 10;
                appleOneCoordY -= 10;
                repaint();
                break;
            case 7:
                appleOneCoordX -= 10;
                appleOneCoordY -= 10;
                repaint();
                break;
            case 8:
                appleOneCoordX -= 10;
                appleOneCoordY -= 10;
                repaint();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unbelievable, but exception occured.");
                System.exit(0);
        }

    }

    /**
     * Moving the snake in order of pressed button.
     */
    void move() {
        if (x[0] == -10 | x[0] == snakePanelsize | y[0] == -10 | y[0] == snakePanelsize) {
            JOptionPane.showMessageDialog(null, "Meeting with the wall");
            System.exit(0);
        }
     /*   if (listOfCoordX.contains(x[0]) & listOfCoordY.contains(y[0])) {
            JOptionPane.showMessageDialog(null, "Selfmeeting");
        }       */
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
     * @param e object for event
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

    private class ActionListener extends KeyAdapter {
        /**
         * Getting the Key event.
         *
         * @param e event object.
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
        System.exit(0);
    }
}

class ControlPanel extends JPanel {
    JButton butRestart = new JButton("Restart");
    JButton butExit = new JButton("Exit");

    ControlPanel() {
        setBounds(50, 550, 500, 70);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        butRestart.setBounds(100, 10, 100, 50);
        add(butRestart);
        butExit.setBounds(350, 10, 100, 50);
        add(butExit);
        butExit.addMouseListener(new MouseListener());
    }
}
