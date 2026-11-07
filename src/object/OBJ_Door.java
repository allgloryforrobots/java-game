package object;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {
        name = "Door";
        type = ObjTypes.DOOR;
        loadImage("/objects/door.png");
        collision = true;
    }

}
