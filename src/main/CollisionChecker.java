package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        /*
         * Левая граница зоны коллизии
         * Расстояние по х объекта от левой границы карты + расстояние по х от левой границы объекта до зоны коллизии
         */
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;

        /*
         * Правая граница зоны коллизии
         * Левая граница зоны коллизии + ширина зоны коллизии
         */
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;

        /*
         * Верхняя граница зоны коллизии
         * Расстояние объекта от верхней границу карты по y + расстояние по y от верхней границы объекта до зоны коллизии
         */
        int entityTopWorldY = entity.worldY + entity.solidArea.y;

        /*
         * Нижняя граница зоны коллизии
         * Верхняя граница зоны коллизии + высота зоны коллизии
         */
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                // верхний левый угол игрока заходит на тайл при следующем шаге
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                // верхний правый угол игрока заходит на тайл при следующем шаге
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                // ищем тайл по его индексу в коллекции, проверяем, что он не проходимый
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

}
