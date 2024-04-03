package res.object;
import main.GamePanel;
import entity.entity;

public class OBJ_Key extends entity{
    public OBJ_Key(GamePanel gp)
    {
        super(gp);
        name="Key";
        down1=setup("/res/objects/key/cheie", gp.tileSize/3, gp.tileSize/3);
        collision=true;
    }
}
