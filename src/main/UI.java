package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    Font textFont;
    Font bigFont;
    Font textSmall;

    public boolean messageOn = false;
    public String message = "";
    long messageEndTime = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        textSmall = new Font("Arial", Font.PLAIN, 30);
        textFont = new Font("Arial", Font.PLAIN, 40);
        bigFont = new Font("Arial", Font.BOLD, 60);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
        messageEndTime = System.nanoTime() + 2_000_000_000L;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        setDefaultTextAntialiasing();
        setDefaultTextSettings();

        if (gp.isGameRuns()) {

        }

        if (gp.isGamePaused()) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        String text  = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }

    public void setDefaultTextAntialiasing() {
        this.g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
    }

    public void setDefaultTextSettings() {
        this.g2.setFont(textFont);
        this.g2.setColor(Color.WHITE);
    }

    public void drawMessage() {
        gp.setFont(textSmall);
        this.g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

        if (System.nanoTime() >= messageEndTime) {
            messageOn = false;
        }
    }

}
