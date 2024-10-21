package gameship;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GameShip extends JPanel implements ActionListener, KeyListener {

    private final Timer timer;
    private final Ship ship;
    private final List<Bullet> bullets;
    private final List<Enemy> enemies;
    private int score;
    private boolean leftPressed, rightPressed, spacePressed;
    private long lastEnemySpawnTime;
    private final Random random;
    private Image background;

    public GameShip() throws IOException {
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);
        
        loadBackgroundImage();

        ship = new Ship(375, 500);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        score = 0;
        random = new Random();
        lastEnemySpawnTime = System.currentTimeMillis();

        timer = new Timer(16, this);
        timer.start();
    }

    private void loadBackgroundImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/background2.png")));
        BufferedImage resizedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, 800, 600, null);
        background = resizedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, null);
        } else {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        ship.draw(g);

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Счёт: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            updateGame();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        repaint();
    }

    private void updateGame() throws IOException {
        if (leftPressed) {
            ship.moveLeft();
        }

        if (rightPressed) {
            ship.moveRight();
        }

        if (spacePressed) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - ship.getLastShotTime() > ship.getShootCooldown()) {
                bullets.add(new Bullet(ship.getX() + ship.getWidth() / 2 - 5, ship.getY()));
                ship.setLastShotTime(currentTime);
            }
        }

        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update();
            if (bullet.isOffScreen()) {
                bulletIter.remove();
            }
        }

        if (System.currentTimeMillis() - lastEnemySpawnTime > 1000) {
            enemies.add(new Enemy(random.nextInt(800 - 40), -40));
            lastEnemySpawnTime = System.currentTimeMillis();
        }

        Iterator<Enemy> enemyIter = enemies.iterator();
        while (enemyIter.hasNext()) {
            Enemy enemy = enemyIter.next();
            enemy.update();
            if (enemy.isOffScreen()) {
                enemyIter.remove();
            }
        }

        bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            Iterator<Enemy> eIter = enemies.iterator();
            while (eIter.hasNext()) {
                Enemy enemy = eIter.next();
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bulletIter.remove();
                    eIter.remove();
                    score += 10;
                    break;
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (ship.getBounds().intersects(enemy.getBounds())) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game over :(\nYour Score: " + score);
                System.exit(0);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
