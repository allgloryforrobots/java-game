package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

/**
 * Класс создает объекты и размещает их на карте
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tilesToPx(23);
        gp.obj[0].worldY = gp.tilesToPx(7);

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = gp.tilesToPx(23);
        gp.obj[1].worldY = gp.tilesToPx(40);

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = gp.tilesToPx(38);
        gp.obj[2].worldY = gp.tilesToPx(8);

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = gp.tilesToPx(10);
        gp.obj[3].worldY = gp.tilesToPx(11);

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = gp.tilesToPx(8);
        gp.obj[4].worldY = gp.tilesToPx(28);

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = gp.tilesToPx(12);
        gp.obj[5].worldY = gp.tilesToPx(22);

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = gp.tilesToPx(10);
        gp.obj[6].worldY = gp.tilesToPx(7);

        gp.obj[7] = new OBJ_Boots(gp);
        gp.obj[7].worldX = gp.tilesToPx(37);
        gp.obj[7].worldY = gp.tilesToPx(42);
    }

}
