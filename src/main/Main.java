package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Rise of Underdog");

        GamePanel gp = new GamePanel();
        window.add(gp);

        // resize window to contain subcomponents
        window.pack();

        // отображать окно по центру экрана
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.setupGame();
        gp.startGameThread();

    }
}