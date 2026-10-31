package main;

import javax.swing.*;
import java.awt.*;

import entity.Player;


public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16х16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 * 48 tile
    // ratio 4 : 3
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * tileSize; // 768 pixels
    final int screenHeight = maxScreenRow * tileSize; // 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameTread;
    Player player = new Player(this, keyH);


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        // better rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
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

    // delta game loop method
//    @Override
//    public void run() {
//
//        double drawInterval = 1_000_000_000 / FPS; // 0.0166s
//        double delta = 0;
//        long lastTime = System.nanoTime();
//        long currentTime;
//
//        while (gameTread != null) {
//            currentTime = System.nanoTime();
//            delta += (currentTime - lastTime) / drawInterval;
//            lastTime = currentTime;
//
//            if(delta >= 1) {
//                update();
//                repaint();
//                delta--;
//            }
//        }
//    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        // save memory best practise
        g2.dispose();
    }

}
