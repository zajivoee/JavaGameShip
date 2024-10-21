package gameship;

import java.awt.*;

public class Ship {
    private int x;
    private final int y;
    private final int width = 50;
    private final int speed = 5;
    private long lastShotTime;
    private int cooldown = 400;

    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
        this.lastShotTime = 0;
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }

    public void moveRight() {
        x += speed;
        if (x > 800 - width) x = 800 - width;
    }

    public Rectangle getBounds() {
        int height = 20;
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(long time) {
        this.lastShotTime = time;
    }

    public int getShootCooldown() {
        return cooldown;
    }
}
