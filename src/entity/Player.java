package entity;

import main.GamePanel;
import main.KeyHandler;
import object.ObjTypes;
import tile.ImageOptimizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    ImageOptimizer imgOptimizer = new ImageOptimizer();;

    /*
     * Середина текущего окна, игрок всегда находиться по центру окна
     */
    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    /*
     * Задержка в кадрах после нажатия клавиши движения, чтобы вернуть персонажа в idle позу
     */
    public int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenXCenter;
        screenY = gp.screenYCenter;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tilesToPx(23);
        worldY = gp.tilesToPx(21);
        speed = 4;
        direction = Direction.DOWN;
    }

    /** Создает изображения с кадрами анимации */
    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/walking_sprites/" + imageName + ".png"));
            image = imgOptimizer.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {
        if (isWASDKeyPressed()) {

            setDirection();
            checkTileCollision();
            int objIndex = checkObjCollision();
            if (objIndex != -1) {
                pickUpObject(objIndex);
            }

            // игрок может двигаться в выбранном направлении?
            // collision-box игрока не упирается в непроходимый tile?
            if (!collisionOn) {
                movePlayerToDirection();
            }

            updateMoveSprite();
        } else {
            stopMove();
        }
    }

    public void stopMove() {
        standCounter++;
        if (standCounter == 20) {
            idleSprite();
            standCounter = 0;
        }
    }

    public void idleSprite() {
        spriteNum = 1;
    }

    public int checkObjCollision() {
        return gp.cChecker.checkObject(this, true);
    }

    /* Устанавливает флаг collisionOn: столкнется ли collision-box игрока с непроходимым тайлом */
    public void checkTileCollision() {
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

    public void removeObjFromMap(int i) {
        gp.obj[i] = null;
    }

    public void pickUpObject(int i) {
        ObjTypes type = gp.obj[i].type;

        switch (type) {
            case KEY:
                gp.playSE(1);
                hasKey++;
                removeObjFromMap(i);
                gp.ui.showMessage("You got a key");
                break;
            case DOOR:
                if (hasKey > 0) {
                    gp.playSE(3);
                    removeObjFromMap(i);
                    hasKey--;
                    gp.ui.showMessage("You use a key");
                } else {
                    gp.ui.showMessage("You need a key");
                }
                break;
            case BOOTS:
                speed += 1;
                removeObjFromMap(i);
                gp.ui.showMessage("Speed up");
                break;
            case CHEST:
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(4);
                break;
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

        g2.drawImage(image, screenX, screenY, null);
    }

}
