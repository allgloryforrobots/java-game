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

        int tileNum1 = -1, tileNum2 = -1;

        switch (entity.direction) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                // верхний левый угол игрока заходит на тайл при следующем шаге
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                // верхний правый угол игрока заходит на тайл при следующем шаге
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
        }

        // ищем тайл по его индексу в коллекции, проверяем, что он не проходимый
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }

    }

    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // get entity solid area position
                // нижний край = верхняя границы + высота
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                // правый край = левая граница + ширина
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get object solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= entity.speed;
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        break;
                }

                // нужно проверить 2 тайла по направлению движения
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

}
