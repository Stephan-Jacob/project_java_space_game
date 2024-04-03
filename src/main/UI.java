package main;

import res.object.OBJ_Inima;
import res.object.OBJ_Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import entity.entity;


public class UI {

    GamePanel gp;
    Graphics2D g2;
    BufferedImage inimaFull, inimaJumate, inimaGoala;
    Font arial_40, arial_80B;
    public boolean messageOn=false;
    public String message ="";
    public boolean firstLevelFinished = false;
    public boolean secondLevelFinished = false;
    public String currentDialogue = " ";
    public int commandNum = 0;
    public int commandNumShop = 0;
    public int titleScreenState = 0; //0: primul ecran, 1: intro ul
    public boolean primulNivelTerminat;

    public UI(GamePanel gp)
    {
        this.gp=gp;

        arial_40=new Font("Arial", Font.PLAIN, 40);
        arial_80B=new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);

        entity inima = new OBJ_Inima(gp);
        inimaFull = inima.image;
        inimaJumate = inima.image2;
        inimaGoala = inima.image3;
    }




    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2)
    {

        if(firstLevelFinished==true)
        {
            primulNivelTerminat=true;
            gp.gameState=gp.titleState;
            titleScreenState=2;
            firstLevelFinished=false;
            messageOn=false;
        }
        else
        {
            g2.setFont(arial_40);
            g2.setColor(Color.BLACK);
           // g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            if(gp.gameState== gp.playState)
                g2.drawString("Task:", gp.tileSize+50, gp.tileSize+10);

            //MESSAGE
            if(messageOn == true)
            {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize+150, gp.tileSize+10);
            }
            else if(messageOn == false && gp.gameState == gp.playState)
            {
                if(primulNivelTerminat==true)
                {
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString("Vorbeste cu paznicul castelului lui Vladorov!", gp.tileSize + 150, gp.tileSize + 10);
                }
                else if(primulNivelTerminat==false)
                {
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString("Vorbeste cu fratele domnului Pinguin.", gp.tileSize + 150, gp.tileSize + 10);
                }
            }
        }
////////////////////
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.black);
        //TITLE STATE
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }

        //PLAY STATE
        if(gp.gameState == gp.playState)
        {
            drawPlayerLife();

        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }

        //DIALOGUE STATE
        if(gp.gameState== gp.dialogueState)
        {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    public void drawPlayerLife()
    {
        int x = (int)(gp.tileSize*12.5);
        int y = gp.tileSize;
        int i = 0;

        //DRAW BLANK HEARTS
        while(i<gp.player.maxLife/2)
        {
            g2.drawImage(inimaGoala,x,y,null);
            i++;
            x = x+gp.tileSize;
        }

        //RESET
        x = (int)(gp.tileSize*12.5);
        y = gp.tileSize;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life)
        {
            g2.drawImage(inimaJumate, x, y, null);
            i++;
            if(i < gp.player.life)
            {
                g2.drawImage(inimaFull, x, y, null);
            }
            ++i;
            x = x + gp.tileSize;
        }
    }

    public void drawTitleScreen()
    {
        if(titleScreenState == 0) {
            try {
                BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/maps/spatiu.png"));
                g2.drawImage(backgroundImage, 0, 0, 1920, 1080, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru fundalul title state ului!");
            }
            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
            String text = "Combat Against";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 1;

            //UMBRA TEXTULUI FRUMOS
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            x = x - gp.tileSize;
            y = y + gp.tileSize;
            text = "Interstellar Dictatorship";

            //UMBRA TEXT SECUNDAR
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //CURSORUL
            BufferedImage racheta = null;
            try {
                racheta = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/racheta.png"));
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru racheta!");
            }

            //PLANETELE
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            x = gp.tileSize;
            y = gp.tileSize * 7;
            try {
                BufferedImage planeta = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/BluePlanet.png"));
                g2.drawImage(planeta, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru planeta!");
            }
            if (commandNum == 0) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Intro", x, y);


            x = gp.tileSize * 3;
            y = gp.tileSize * 5;

            try {
                BufferedImage planeta2 = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/WhiteMoon.png"));
                g2.drawImage(planeta2, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru planeta!");
            }
            if (commandNum == 1) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Level 1", x, y);


            x = gp.tileSize * 5;
            y = gp.tileSize * 6;
            try {
                BufferedImage planeta3 = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/PurplePlanet.png"));
                g2.drawImage(planeta3, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru planeta!");
            }
            if (commandNum == 2) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Level 2", x, y);


            x = gp.tileSize * 8;
            y = gp.tileSize * 7;

            try {
                BufferedImage planeta4 = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/RedMoon.png"));
                g2.drawImage(planeta4, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru planeta!");
            }
            if (commandNum == 3) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Shop", x, y);

            x = gp.tileSize * 8;
            y = gp.tileSize * 3;

            try {
                BufferedImage satelit = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/Satellite.png"));
                g2.drawImage(satelit, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru satelit!");
            }

            x = gp.tileSize * 1;
            y = gp.tileSize * 1;

            try {
                BufferedImage stele = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/YellowStars.png"));
                g2.drawImage(stele, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru stele!");
            }

            x = (gp.screenWidth / 3) * 2;
            y = gp.tileSize * 5;

            try {
                BufferedImage soare = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/Sun.png"));
                g2.drawImage(soare, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru soare!");
            }
            if (commandNum == 4) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Save Game", x, y);

            x = (gp.screenWidth / 5) * 4;
            y = gp.tileSize * 6;

            try {
                BufferedImage luna = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/YellowHalfMoon.png"));
                g2.drawImage(luna, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru soare!");
            }
            if (commandNum == 5) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Save game", x, y);

            x = (gp.screenWidth / 9) * 8;
            y = (int) (gp.tileSize * 7.5);

            try {
                BufferedImage gaura = ImageIO.read(getClass().getResourceAsStream("/res/maps/texturi/Hurricane.png"));
                g2.drawImage(gaura, x, y, 64, 64, null);
            } catch (IOException e) {
                System.out.print("Nu a fost gasita imaginea pentru soare!");
            }
            if (commandNum == 6) {
                g2.drawImage(racheta, x - gp.tileSize / 2, y + gp.tileSize / 4, 32, 32, null);
            }
            y += gp.tileSize;
            g2.drawString("Exit game", x, y);
        }
        else if(titleScreenState == 1)
        {
            g2.setColor(Color.black);
            g2.setFont(g2.getFont().deriveFont(Font.ITALIC,42F));
            gp.setBackground(Color.lightGray);
            String text = "Prolog";
            int x =getXforCenteredText(text);
            int y = (int)(gp.tileSize*1.5);
            g2.drawString(text,x,y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,36F));
            text="Actiunea este situata intr-o galaxie, unde locuitorii fiecarei\n" +
                    "planete, reprezentati de diferite specii de animale(dar si de oameni) traiesc in armonie.\n\n" +
                    "Fiecare dintre aceste planete au o clima si un relief specific(planete inghetate, planete\n" +
                    "unde se afla desert etc.). Desi sunt conditii specifice pe fiecare planeta, cetatenii \n" +
                    "sunt fericiti deoarece au tot felul de drepturi, reprezentatii(conducatorii) \n" +
                    "acestora asigurandu-le un bun trai prin schimburile pe care le fac intre planete, \n" +
                    "dat fiind faptul ca pe fiecare dintre acestea se afla o abundenta de resurse.\n\n" +
                    "Totul are sa se schimbe cand Vladorov, seful planetei oamenilor, decide ca vrea toata\n" +
                    "puterea si duce o ampla campanie militara prin care subjuga restul planetelor.\n\n" +
                    "Insa, cum mereu raul este urmat de bine, protagonistul jocului Apollus, un simplu\n" +
                    "cosmonaut, cetatean al planetei lui Vladorov, realizeaza ca el este cel ales sa \n" +
                    "readuca pacea, fiind impins de ideea ca seamana intr-o oarecare masura cu un \n" +
                    "erou dintr-o celebra carte ce era un best-seller in toata galaxia. Pentru a-l putea \n" +
                    "invinge pe antagonist, Apollo cere ajutorul domnului Pinguin, seful planetei inghetate.";
            x=gp.tileSize;
            y=gp.tileSize*3;
            for(String line : text.split("\n"))
            {
                g2.drawString(line,x ,y);
                y=y+gp.tileSize/3;
            }
        }
        else if(titleScreenState==2)
        {
            messageOn=false;

            gp.setBackground(Color.lightGray);
            g2.setFont(arial_40);
            g2.setColor(Color.black);
            String text;
            int textLength;
            int x;
            int y;

            text = "a salva lumea putea salva lumea, acesta trebuie infrant!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize);
            g2.drawString(text, x, y);

            text = "Acesta iti spune ca Vladorov se afla pe planeta desertica si ca pentru ";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 2);
            g2.drawString(text, x, y);

            text = "Ai reusit sa-l salvezi pe domnul Pinguin";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text = "Felicitari!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);
        }
        else if(titleScreenState == 3)
        {
            messageOn=false;

            gp.setBackground(Color.lightGray);
            g2.setFont(arial_40);
            g2.setColor(Color.black);
            String text;
            int textLength;
            int x;
            int y;

            text = "Ai terminat jocul!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text = "Felicitari!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);
        }
        /*else if(titleScreenState==4)
        {
            messageOn=false;

            gp.setBackground(Color.lightGray);
            g2.setFont(arial_40);
            g2.setColor(Color.YELLOW);

            //MAGAZIN
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
            String text="Magazin: Toate itemele costa 2 banuti";
            int x = getXforCenteredText(text);
            int y =gp.tileSize*3;
            g2.drawString(text, x, y);

            //banii tai
            g2.setColor(Color.yellow);
            text="Banii tai:"+gp.player.banuti;
            x = getXforCenteredText(text);
            y =gp.tileSize*4;
            g2.drawString(text, x, y);

            g2.setColor(Color.black);

            //Sabie
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text="Sabie: Creste damage-ul dat cu o unitate.";
            x = getXforCenteredText(text);
            y =gp.tileSize*5;
            g2.drawString(text, x, y);
            if(commandNumShop==0)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }

            //VIata
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text="Viata: Ai o inima de viata in plus.";
            x = getXforCenteredText(text);
            y =gp.tileSize*6;
            g2.drawString(text, x, y);
            if(commandNumShop==1)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
        }*/
    }

    public void drawDialogueScreen()
    {
        //WINDOW
        int x = gp.tileSize*2;
        int y =gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line,x ,y);
            y=y+40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0, 100);
        g2.setColor(c);
        g2.fillRoundRect(x,y, width, height,35,35);

        c= new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
    public void drawPauseScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "Paused";
        int x=getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text)
    {
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x=gp.screenWidth/2 - length/2;
        messageOn=false;
        return x;
    }
}
