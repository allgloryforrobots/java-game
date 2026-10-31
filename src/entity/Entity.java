package entity;

import java.awt.image.BufferedImage;

/**
 * Класс-родитель для игрока, монстра, NPC
 */
public class Entity {
    /** Точка, где находится объект на игровой карте */
    public int worldX, worldY;
    /** Количество px, на которое перемещается объект за 1 шаг */
    public int speed;

    /** Спрайты движения персонажа */
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    /** Направление движения персонажа */
    public Direction direction;
    /** Число кадров между сменами спрайта анимации движения */
    public int spriteCounter = 0;
    /** Спрайт анимации, который показывается в текущем кадре */
    public int spriteNum = 1;
}
