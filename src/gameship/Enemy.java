package gameship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy {
    private final int x;
    private int y;
    private final int speed;
    private int width = 40;
    private int height = 20;
    private Image image;
    private static final Random random = new Random();

    public Enemy(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        this.speed = 2 + random.nextInt(3);
        loadImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    private void loadImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/enemy2.png")));
        image = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }

    public void update() {
        y += speed;
    }

    public boolean isOffScreen() {
        return y > 600;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
