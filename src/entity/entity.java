package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    public String direction="down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 96, 96);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter;

    public boolean invincible = false;
    public int invincibleCounter=0;

    boolean attacking = false;

    String dialogues[] = new String[20];
    public int dialogueIndex=0;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public int type; // 0-player, 1-npc, 2-monster

    //Character Status
    public int maxLife;
    public int life;

    public entity(GamePanel gp)
    {
        this.gp = gp;
    }

    public void speak()
    {

    }
    public void setAction()
    {

    }

    public void update()
    {
        setAction();

        collisionOn=false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true)
        {
            if(gp.player.invincible == false)
            {
                //putem sa ii dam damage
                gp.player.life -= 1;
                gp.player.invincible=true;
            }
        }

        if(collisionOn == false)
        {
            switch(direction)
            {
                case "up":
                    worldY= worldY - speed;
                    break;
                case "down":
                    worldY= worldY + speed;
                    break;
                case "left":
                    worldX = worldX - speed;
                    break;
                case "right":
                    worldX = worldX + speed;
                    break;
            }
        }
        spriteCounter++; // apelandu-se update ul de 60 de ori pe secunda, functia de sprite va face la fel
        if(spriteCounter > 20)// cand se ajunge la 20 se schimba "piciorul"
        {
            if(spriteNum == 1)
            {
                spriteNum = 2;
            }
            else if(spriteNum == 2)
            {
                spriteNum = 1;
            }
            spriteCounter = 0;//aici se reseteaza counterul pentru a pleca iar din 0 si a se relua faza cu piciorul
        }

        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter > 40)
            {
                invincible=false;
                invincibleCounter=0;
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize/*expandeaza cu un tile ca sa nu apara margini aiurea*/ > gp.player.worldX - gp.player.screenX && //ca sa nu
                worldX - gp.tileSize/*expandeaza la fel*/ < gp.player.worldX + gp.player.screenX && //randeze toata mapa,
                worldY + gp.tileSize/*expandeaza la fel*/ > gp.player.worldY - gp.player.screenY && //ci doar ce poate fi vazut de player
                worldY - gp.tileSize/*expandeaza la fel*/ < gp.player.worldY + gp.player.screenY)
        {
            BufferedImage image = null;
            switch(direction) {
                case "up":
                    if(spriteNum==1)
                    {
                        image = up1;
                    }
                    if(spriteNum==2)
                    {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNum==1)
                    {
                        image = down1;
                    }
                    if(spriteNum==2)
                    {
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum==1)
                    {
                        image = left1;
                    }
                    if(spriteNum==2)
                    {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum==1)
                    {
                        image = right1;
                    }
                    if(spriteNum==2)
                    {
                        image = right2;
                    }
                    break;
            }
            if(invincible == true)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }

    }

    public BufferedImage setup(String imagePath, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
            image = uTool.scaleImage(image, width, height);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
