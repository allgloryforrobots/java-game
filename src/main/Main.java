package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Rise of Underdog");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // resize window to contain subcomponents
        window.pack();

        // отображать окно по центру экрана
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}