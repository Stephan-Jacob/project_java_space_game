package res.object;

import main.GamePanel;

import entity.entity;

public class OBJ_Door extends entity{

    public OBJ_Door(GamePanel gp)
    {
        super(gp);
        name="Door";
        down1=setup("/res/objects/door/door", gp.tileSize, gp.tileSize);
        collision=true;
    }
}