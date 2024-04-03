package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import monster.MON_Foca;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey=0;
    public boolean canEliberate=false;

    int existaMonstri=0;

    public int nivel;
    public int numarUcisi=0;

    public Player(GamePanel gp, KeyHandler keyH)
    {
        super(gp);
        this.keyH = keyH;

        screenX=gp.screenWidth/2 - (gp.tileSize/2); // am scazut gp.tileSize/2 deoarece voiam sa pozitionez caracterul cu totul la mijloc
        screenY=gp.screenHeight/2 - (gp.tileSize/2); // si nu doar din colutul din stanga sus

        solidArea = new Rectangle(8, 16 ,52 , 54);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 72;
        attackArea.height = 72;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues()
    {
        if(gp.ui.primulNivelTerminat==false) {
            worldX = gp.tileSize * 13;
            worldY = gp.tileSize * 37;
            speed = 5;
            direction = "down";
        }
        else if(gp.ui.primulNivelTerminat==true)
        {
            worldX = gp.tileSize * 68;
            worldY = gp.tileSize * 39;
            speed = 5;
            direction = "down";
        }
        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;

    }
    public void getPlayerImage()
    {
        up1=setup("/res/player/player_up_1", gp.tileSize, gp.tileSize);
        up2=setup("/res/player/player_up_2", gp.tileSize, gp.tileSize);
        down1=setup("/res/player/player_down_1", gp.tileSize, gp.tileSize);
        down2=setup("/res/player/player_down_2", gp.tileSize, gp.tileSize);
        left1=setup("/res/player/player_left_1", gp.tileSize, gp.tileSize);
        left2=setup("/res/player/player_left_2", gp.tileSize, gp.tileSize);
        right1=setup("/res/player/player_right_1", gp.tileSize, gp.tileSize);
        right2=setup("/res/player/player_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage()
    {
        attackUp1=setup("/res/player/player_up_1_battle", gp.tileSize, gp.tileSize*2);
        attackUp2=setup("/res/player/player_up_2_battle", gp.tileSize, gp.tileSize*2);
        attackDown1=setup("/res/player/player_down_1_battle", gp.tileSize, gp.tileSize*2);
        attackDown2=setup("/res/player/player_down_2_battle", gp.tileSize, gp.tileSize*2);
        attackLeft1=setup("/res/player/player_left_1_battle", gp.tileSize*2, gp.tileSize);
        attackLeft2=setup("/res/player/player_left_2_battle", gp.tileSize*2, gp.tileSize);
        attackRight1=setup("/res/player/player_right_1_battle", gp.tileSize*2, gp.tileSize);
        attackRight2=setup("/res/player/player_right_2_battle", gp.tileSize*2, gp.tileSize);
    }

    public BufferedImage setup(String imageName)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/"+ imageName +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
    }
    return image;
    }

    public void update()
    {
        dead();
        if(attacking == true)
        {
            attacking();
        }
        if(keyH.upPressed==true || keyH.downPressed==true || keyH.leftPressed==true || keyH.rightPressed==true || keyH.enterPressed == true)
        {
            if(keyH.upPressed==true)
            {
                direction="up";

            }
            else if(keyH.downPressed==true)
            {
                direction="down";

            }
            else if(keyH.leftPressed==true)
            {
                direction="left";

            }
            else if(keyH.rightPressed==true)
            {
                direction="right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && keyH.enterPressed == false)
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

            gp.keyH.enterPressed = false;

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
        }
        //ASTA TREBUIE SA FIE IN AFARA KEY IF STATEMENT ULUI
        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter > 60)
            {
                invincible=false;
                invincibleCounter=0;
            }
        }
    }

    public void attacking()
    {
        getPlayerAttackImage();
        spriteCounter++;
        if(spriteCounter <=5)
        {
            spriteNum=1;
        }
        if(spriteCounter > 5 && spriteCounter <=25)
        {
            spriteNum=2;

            //Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currendWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Ajust player's worldX and worldY for the attackArea
            switch(direction)
            {
                case "up":
                    worldY -=attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check monster collision with the uploaded worldX, worldY and solidArea
            int monsterIndex=gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currendWorldY;
            solidArea.width=solidAreaWidth;
            solidArea.height=solidAreaHeight;

        }
        if(spriteCounter>25)
        {
            spriteNum=1;
            spriteCounter=0;
            attacking=false;
        }


    }

    public void pickUpObject(int i)
    {
        if(i!=999)
        {
            String objectName = gp.obj[i].name;

            switch(objectName)
            {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Bine lucrat, ai gasit cheia!\n Acum va trebui sa te duci la fortareata!");
                    break;
                case "Door":
                    if(hasKey > 0)
                    {
                        gp.obj[i] = null;
                        if(gp.obj[i+1]!=null && gp.obj[i+1].name=="Door")
                            gp.obj[i+1] = null;
                        if(gp.obj[i-1]!=null && gp.obj[i-1].name=="Door")
                            gp.obj[i-1] = null;
                        hasKey--;
                        gp.ui.showMessage("Gaseste o cale de a-l elibera pe domnul pingin!");
                    }
                    else
                    {
                        gp.ui.showMessage("Ai nevoie de o cheie! Vorbeste cu fratele domnului Pinguin");
                    }
                    break;
                case "Chest":
                    gp.obj[i] = null;
                    gp.ui.showMessage("Acum ca ai cheia celulei, elibereaza-l pe domnul pinguin!");
                    canEliberate=true;
                    break;
                case "Cage":
                        if (canEliberate == true) {
                            gp.obj[i] = null;
                            gp.ui.firstLevelFinished = true;
                            gp.ui.showMessage("Vorbeste cu paznicul castelului lui Vladarov!");
                        } else {
                            gp.ui.showMessage("Nu ai cheia celulei, gaseste-o! (Hint: Oare nu a fost pusa in cutie?)");
                        }
                        break;
                case "Boss":

                    if(existaMonstri==3)
                    {
                        gp.obj[i] = null;
                        gp.ui.secondLevelFinished = true;
                        gp.gameState=gp.titleState;
                        gp.ui.titleScreenState=3;
                    }
                    break;

                case "Castle":
                        gp.obj[i] = null;
                    break;
            }
        }
    }

    public void interactNPC(int i)
    {
        if(gp.keyH.enterPressed == true)
        {
            if(i !=999)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            else{
                if(gp.keyH.enterPressed == true)
                {
                    attacking = true;
                }
            }
        }


    }

    public void contactMonster(int i)
    {
        if(i!=999)
        {
            if(invincible == false)
            {
                life -= 1;
                invincible=true;
            }
        }
    }

    public void damageMonster(int i)
    {
        if(i != 999)
        {
            if(gp.monster[i].invincible == false)
            {
                gp.monster[i].life--; //DE AICI SA MODIFICI SABIA
                gp.monster[i].invincible = true;

                if(gp.monster[i].life <=0)
                {
                    if(gp.monster[i].type==3)
                        existaMonstri++;
                    gp.monster[i]=null;
                    numarUcisi++;
                }
            }
        }
    }

    public void dead()
    {
        if (life<=0)
        {
            gp.gameState=0;
            setDefaultValues();
        }
    }

    public void draw(Graphics2D g2)
    {
        //g2.setColor(Color.white);
        //g2.fillRect(x,y,gp.tileSize,gp.tileSize);

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction) {
            case "up":
                if(attacking == false)
                {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if(attacking == true)
                {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum==1)
                    {
                        image = attackUp1;
                    }
                    if(spriteNum==2)
                    {
                        image = attackUp2;
                    }
                }
                break;

            case "down":
                if(attacking == false)
                {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if(attacking == true)
                {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(attacking == false)
                {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if(attacking == true)
                {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(attacking == false)
                {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if(attacking == true)
                {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        if(invincible == true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //RESET ALPHA DRAW
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        if(gp.ui.primulNivelTerminat==true)
            nivel=1;
        if(gp.ui.secondLevelFinished)
            nivel=2;
    }
}
