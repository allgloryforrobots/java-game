package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    /*
     * Середина текущего окна, игрок всегда находиться по центру окна
     */
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenXCenter;
        screenY = gp.screenYCenter;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileCountToPx(23);
        worldY = gp.tileCountToPx(21);
        speed = 4;
        direction = Direction.DOWN;
    }

    /** Создает изображения с кадрами анимации */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (isWASDKeyPressed()) {

            setDirection();
            checkCollision();

            // игрок может двигаться в выбранном направлении?
            // collision-box игрока не упирается в непроходимый tile?
            if (!collisionOn) {
                movePlayerToDirection();
            }

            updateMoveSprite();
        }
    }

    /* Устанавливает флаг collisionOn: столкнется ли collision-box игрока с непроходимым тайлом */
    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
    }

    /* Двигает игрока в выбранном направлении */
    public void movePlayerToDirection() {
        switch (direction) {
            case UP:
                worldY -= speed;
                break;
            case DOWN:
                worldY += speed;
                break;
            case LEFT:
                worldX -= speed;
                break;
            case RIGHT:
                worldX += speed;
                break;
        }
    }


    /* Нажата клавиша движения */
    private boolean isWASDKeyPressed() {
        return keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;
    }

    /* По нажатию клавиши движения повернуть игрока и передвинуть в выбранном направлении */
    private void setDirection() {
        if (keyH.upPressed) {
            direction = Direction.UP;
        }
        else if (keyH.downPressed) {
            direction = Direction.DOWN;
        }
        else if (keyH.leftPressed) {
            direction = Direction.LEFT;
        }
        else if (keyH.rightPressed) {
            direction = Direction.RIGHT;
        }
    }

    /* Обновляет кадры анимации движения */
    private void updateMoveSprite() {
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case UP:
                if (spriteNum == 1) {
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case DOWN:
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case LEFT:
                if (spriteNum == 1) {
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case RIGHT:
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
