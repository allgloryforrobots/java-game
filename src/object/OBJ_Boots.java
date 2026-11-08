package object;

import main.GamePanel;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        type = ObjTypes.BOOTS;
        loadImage("/objects/boots.png");
    }


}
