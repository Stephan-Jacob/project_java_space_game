package res.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

import entity.entity;

public class OBJ_Inima extends entity{
    public OBJ_Inima(GamePanel gp)
    {
        super(gp);
        name="Heart";
        image = setup("/res/objects/inima/inima_full", gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/inima/inima_jumatate", gp.tileSize, gp.tileSize);
        image3 = setup("/res/objects/inima/inima_goala", gp.tileSize, gp.tileSize);
    }
}
