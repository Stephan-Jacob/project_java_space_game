package res.object;

import main.GamePanel;

import entity.entity;

public class OBJ_Chest extends entity{
    public OBJ_Chest(GamePanel gp)
    {
        super(gp);

        name="Chest";
        down1=setup("/res/objects/chest/chest_closed", gp.tileSize, gp.tileSize);
    }
}