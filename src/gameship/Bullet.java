package gameship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bullet {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private Image image;

    public Bullet(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        loadImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    private void loadImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/bullet2.png")));
        image = originalImage.getScaledInstance(10, 20, Image.SCALE_SMOOTH);
    }

    public void update() {
        int speed = 7;
        y -= speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, x, y, null);
        g2d.dispose();
    }

    public boolean isOffScreen() {
        return y < -height;
    }
}
