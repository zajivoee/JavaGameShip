package gameship;

import java.awt.*;
import java.util.Random;

public class Enemy {
    private final int x;
    private int y;
    private final int speed;
    private static final Random random = new Random();

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 2 + random.nextInt(3);
    }

    public void update() {
        y += speed;
    }

    public boolean isOffScreen() {
        return y > 600;
    }

    public Rectangle getBounds() {
        int width = 40;
        int height = 20;
        return new Rectangle(x, y, width, height);
    }

}
