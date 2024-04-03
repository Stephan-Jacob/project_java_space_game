package res.object;

import main.GamePanel;

import entity.entity;

public class OBJ_DOOR_CASTLE extends entity{
    public OBJ_DOOR_CASTLE(GamePanel gp)
    {
        super(gp);
        name="Castle";
        down1=setup("/res/objects/door/door", gp.tileSize, gp.tileSize);
        collision=true;
    }
}