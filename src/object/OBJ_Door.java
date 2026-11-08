package object;

import main.GamePanel;

public class OBJ_Door extends SuperObject {

    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        type = ObjTypes.DOOR;
        loadImage("/objects/door.png");
        collision = true;
    }

}
