package object;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        type = ObjTypes.CHEST;
        loadImage("/objects/chest.png");
    }

}
