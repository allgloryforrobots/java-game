package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    protected void loadImage(String path) {
       try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (
                worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
