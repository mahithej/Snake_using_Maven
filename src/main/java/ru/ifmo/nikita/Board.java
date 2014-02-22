/**
 * Provides the classes necessary to create an applet.
 * ru.ifmo.nikita package.
 * @see java.awt.
 * @deprecated s.
 * @since Java SE 1.7
 * @author Ivanov Nikita | tazg@ya.ru | SPb ITMO 5159
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
    private boolean isNeedFirstAppleDraw = true;
    private boolean isNeedSecondAppleDraw = true;
    protected boolean isNeedSearchAppleZone = true;
    protected int[] x = new int[snakePanelsize];
    protected int[] y = new int[snakePanelsize];
    private int firstAppleCoordY;
    private int firstAppleCoordX;
    private int secondAppleCoordX;
    private int secondAppleCoordY;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Image body, appleImg;

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
        contrObjj.setLabel();
    }

    /**
     * The start method. Initializing Timer and snake.
     */
    private void start() {

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
     * Method for 2 apples drawing.
     *
     * @param g Grahpics obj.
     */
    public void drawApple(Graphics g) {
        if (isNeedSecondAppleDraw) {
            int limitVariable = 50;
            secondAppleCoordY = dotsSize * ((int) (Math.random() * limitVariable));
            secondAppleCoordX = dotsSize * ((int) (Math.random() * limitVariable));
            isNeedSecondAppleDraw = false;
        }
        g.drawImage(appleImg, firstAppleCoordY, firstAppleCoordX, this);

        if (isNeedFirstAppleDraw) {
            int limitVariable = 50;
            firstAppleCoordY = dotsSize * ((int) (Math.random() * limitVariable));
            firstAppleCoordX = dotsSize * ((int) (Math.random() * limitVariable));
            isNeedFirstAppleDraw = false;
        }
        g.drawImage(appleImg, secondAppleCoordX, secondAppleCoordY, this);

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
        checkApple(firstAppleCoordY, firstAppleCoordX);
        checkApple(secondAppleCoordX, secondAppleCoordY);

        if ((x[0] == firstAppleCoordY) & (y[0] == firstAppleCoordX)) {
            dots++;
            isNeedFirstAppleDraw = true;
            isNeedSearchAppleZone = true;
        }
        if ((x[0] == secondAppleCoordX) & (y[0] == secondAppleCoordY)) {
            dots++;
            isNeedSecondAppleDraw = true;
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
                firstAppleCoordY -= 10;
                firstAppleCoordX += 10;
                repaint();
                break;
            case 2:
                firstAppleCoordY += 10;
                firstAppleCoordX += 10;
                repaint();
                break;
            case 3:
                firstAppleCoordY -= 10;
                firstAppleCoordX += 10;
                repaint();
                break;
            case 4:
                firstAppleCoordY += 10;
                firstAppleCoordX -= 10;
                repaint();
                break;
            case 5:
                firstAppleCoordY += 10;
                firstAppleCoordX += 10;
                repaint();
                break;
            case 6:
                firstAppleCoordY += 10;
                firstAppleCoordX -= 10;
                repaint();
                break;
            case 7:
                firstAppleCoordY -= 10;
                firstAppleCoordX -= 10;
                repaint();
                break;
            case 8:
                firstAppleCoordY -= 10;
                firstAppleCoordX -= 10;
                repaint();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unbelievable, but exception occured.");
                System.exit(0);
        }

    }

    void gameOverShow() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setBounds(30, 20, snakePanelsize, snakePanelsize);
        // setLayout(null);
        // System.exit(0);
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
    JLabel TFObj = new JLabel();

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

    public void setLabel() {
        TFObj.setText("sssssssssssssssssssssS");
        TFObj.setBounds(0, 0, 50, 50);
        add(TFObj);
    }

}
