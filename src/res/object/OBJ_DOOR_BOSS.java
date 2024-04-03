package res.object;

import main.GamePanel;

import entity.entity;

public class OBJ_DOOR_BOSS extends entity{
    public OBJ_DOOR_BOSS(GamePanel gp)
    {
        super(gp);
        name="Boss";
        down1=setup("/res/objects/door/door", gp.tileSize, gp.tileSize);
        collision=true;
    }
}