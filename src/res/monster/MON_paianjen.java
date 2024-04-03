package monster;

import entity.entity;
import main.GamePanel;

import java.util.Random;

public class MON_paianjen extends entity{
    GamePanel gp;
    public MON_paianjen(GamePanel gp)
    {
        super(gp);

        this.gp=gp;

        type=3;
        name = "Paianjen";
        speed = 4;
        maxLife = 6;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage()
    {
        up1=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        up2=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        down1=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        down2=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        left1=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        left2=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        right1=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
        right2=setup("/res/monsters/spider", gp.tileSize, gp.tileSize);
    }

    public void setAction()
    {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; //ia un numar de la 1 la 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter=0;
        }
    }
}