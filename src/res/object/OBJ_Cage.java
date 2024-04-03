package res.object;

import main.GamePanel;

import entity.entity;

public class OBJ_Cage extends entity{
    public OBJ_Cage(GamePanel gp)
    {
        super(gp);
        name="Cage";
        down1=setup("/res/objects/cage/cage_closed", gp.tileSize, gp.tileSize);
        collision=true;
    }
}