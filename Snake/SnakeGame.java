 import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements KeyListener, Runnable {
    
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int SCALE = 10;
    private static final int FPS = 10;

    private static final Random RANDOM = new Random();
    
    private LinkedList<Point> snake;
    private Point food;
    private int direction;
    private boolean isRunning;

    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new Point(0, 0));
        food = new Point(RANDOM.nextInt(WIDTH / SCALE) * SCALE, RANDOM.nextInt(HEIGHT / SCALE) * SCALE);
        direction = KeyEvent.VK_RIGHT;
        isRunning = true;
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, SCALE, SCALE);
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x, p.y, SCALE, SCALE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            if (Math.abs(direction - key) != 2) {
                direction = key;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000 / FPS);
                Point head = snake.getFirst();
                int x = head.x;
                int y = head.y;
                switch (direction) {
                    case KeyEvent.VK_LEFT:
                        x -= SCALE;
                        break;
                    case KeyEvent.VK_RIGHT:
                        x += SCALE;
                        break;
                    case KeyEvent.VK_UP:
                        y -= SCALE;
                        break;
                    case KeyEvent.VK_DOWN:
                        y += SCALE;
                        break;
                }
                Point nextHead = new Point(x, y);
                if (nextHead.equals(food)) {
                    food = new Point(RANDOM.nextInt(WIDTH / SCALE) * SCALE, RANDOM.nextInt(HEIGHT / SCALE) * SCALE);
                } else {
                    snake.removeLast();
                }
                if (snake.contains(nextHead) || x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                    isRunning = false;
                } else {
                    snake.addFirst(nextHead);
                    repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("


