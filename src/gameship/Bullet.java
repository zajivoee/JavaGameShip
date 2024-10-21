package gameship;

import java.awt.*;

public class Bullet {
    private final int x;
    private int y;
    private final int height = 10;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        int speed = 7;
        y -= speed;
    }

    public boolean checkPosition() {
        return y < -height;
    }

    public Rectangle getBounds() {
        int width = 4;
        return new Rectangle(x, y, width, height);
    }
}
