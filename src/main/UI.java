package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;

    Font textFont;
    Font bigFont;
    Font textSmall;

    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    long messageEndTime = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        textSmall = new Font("Arial", Font.PLAIN, 30);
        textFont = new Font("Arial", Font.PLAIN, 40);
        bigFont = new Font("Arial", Font.BOLD, 60);

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
        messageEndTime = System.nanoTime() + 2_000_000_000L;
    }

    public void draw(Graphics2D g2) {
        setDefaultTextAntialiasing(g2);
        setDefaultTextSettings(g2);

        if (gameFinished) {
            drawGameTitleScreen(g2);
            return;
        }

        drawKeys(g2);

        if (messageOn) {
            drawMessage(g2);
        }
    }

    public void drawGameTitleScreen(Graphics2D g2) {
        String text;
        int textLength;
        int x;
        int y;

        text = "You found a treasure";
        g2.setFont(bigFont);
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - gp.tilesToPx(3);
        g2.drawString(text, x, y);

        text = "Congratulations!";
        g2.setFont(bigFont);
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + gp.tilesToPx(3);
        g2.drawString(text, x, y);

        gp.stopGame();
    }

    public void setDefaultTextAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
    }

    public void setDefaultTextSettings(Graphics2D g2) {
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
    }

    public void drawKeys(Graphics2D g2) {
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x = " + gp.player.hasKey, 74, 65);
    }

    public void drawMessage(Graphics2D g2) {
        gp.setFont(textSmall);
        g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

        if (System.nanoTime() >= messageEndTime) {
            messageOn = false;
        }
    }

}
