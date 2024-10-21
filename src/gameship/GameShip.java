package gameship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameShip extends JPanel implements ActionListener, KeyListener {

    private final Timer timer;
    private final Ship ship;
    private final List<Bullet> bullets;
    private final List<Enemy> enemies;
    private int score;
    private boolean moveLeft, moveRight, spacePressed;
    private long lastEnemySpawnTime;
    private final Random random;

    public GameShip() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);

        ship = new Ship(375, 550);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        score = 0;
        random = new Random();
        lastEnemySpawnTime = System.currentTimeMillis();

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        updateGame();
        repaint();
    }

    private void updateGame() {
        if (moveLeft) {
            ship.moveLeft();
        }
        if (moveRight) {
            ship.moveRight();
        }
        
        if (spacePressed) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - ship.getLastShotTime() > ship.getShootCooldown()) {
                bullets.add(new Bullet(ship.getX() + ship.getWidth() / 2 - 2, ship.getY()));
                ship.setLastShotTime(currentTime);
            }
        }
        
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update();
            if (bullet.checkPosition()) {
                bulletIter.remove();
            }
        }

        if (System.currentTimeMillis() - lastEnemySpawnTime > 1000) {
            enemies.add(new Enemy(random.nextInt(760), -30));
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
                JOptionPane.showMessageDialog(this, "Game over :(\nYour score: " + score);
                System.exit(0);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            moveLeft = true;
        }
        
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            moveRight = true;
        }
        
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            moveLeft = false;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            moveRight = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {}
}
