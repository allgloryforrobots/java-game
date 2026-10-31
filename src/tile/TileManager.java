package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = bufferedReader.readLine();
                String[] numbers = line.split(" ");

                while(col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/grass.png")));
            tile[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/wall.png")));
            tile[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/water.png")));
            tile[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/earth.png")));
            tile[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/tree.png")));
            tile[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/ground/sand.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = gp.tileCountToPx(worldCol);
            int worldY = gp.tileCountToPx(worldRow);
            int screenX = worldX
                    // сколько px игрок прошел от точки 0;0
                    - gp.player.worldX
                    // если игрок стоит в точке 0;0, эта точка должна находиться в середине экрана
                    + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // отрисовываем только то, что на экране
            if (
                worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
            ) {
                g2.drawImage(tile[tileNum].getImage(), screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
