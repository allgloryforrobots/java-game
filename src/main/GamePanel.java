package main;

import javax.swing.*;
import java.awt.*;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // НАСТРОЙКИ ЭКРАНА
    final int originalTileSize = 16; // 16х16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48 * 48 tile
    public final int halfTileSize = tileSize / 2;

    // ratio 4 : 3
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 576 pixels

    /** Середина окна игры */
    public final int screenXCenter = screenWidth / 2 - halfTileSize;
    public final int screenYCenter = screenHeight / 2 - halfTileSize;

    // НАСТРОЙКИ КАРТЫ МИРА
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameTread;
    public Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        // better rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Переводит расстояние в тайлах в px
     * @param n tileNumber (x or y)
     * @return offset in px
     */
    public int tileCountToPx(int n) {
        return n * this.tileSize;
    }


    public void startGameThread() {
        gameTread = new Thread(this);
        gameTread.start();
    }

    // sleep game loop method
    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS; // 0.0166s
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameTread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1_000_000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        // save memory best practise
        g2.dispose();
    }

}
