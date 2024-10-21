package gameship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Ship {
    private int x;
    private final int y;
    private int width = 50;
    private final int shootCooldown = 500;
    int height = 20;
    private final int speed = 5;
    private long lastShotTime;
    private Image image;

    public Ship(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        this.lastShotTime = 0;
        loadImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    private void loadImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/Ship.png")));
        image = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) {
            x = 0;
        }
    }

    public void moveRight() {
        x += speed;
        if (x > 800 - width) {
            x = 800 - width;
        }
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
        return shootCooldown;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, x, y, null);
        g2d.dispose();
    }
}
