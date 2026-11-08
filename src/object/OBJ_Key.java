package object;

import main.GamePanel;

public class OBJ_Key extends SuperObject {

    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        type = ObjTypes.KEY;
        loadImage("/objects/key.png");
    }

}
