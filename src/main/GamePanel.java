package main;

import entity.*;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import entity.entity;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 32; // 32x32 tile - default size pentru caractere obiecte si alte chestii
    final int scale = 3; //scala pentru aducerea caracterelor la o marime buna pentru ecrane cu rezolutie mare

    public final int tileSize = originalTileSize*scale; // 96x96 tile(actual tile size)
    public final int maxScreenCol = 16;//o matrice de  16
    public final int maxScreenRow = 9;//16 pe 9
    public final int screenWidth = tileSize*maxScreenCol; //latimea ecranului
    public final int screenHeight = tileSize*maxScreenRow; //inaltimea ecranului

    //astea au fost screen settingsurile

    //World Setting

    public final int maxWorldCol=85;
    public final int maxWorldRow=50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    //FPS
    int FPS=60;
    //acestea au fost setarile pentru FPS

    TileManager tileM= new TileManager(this);



    public KeyHandler keyH = KeyHandler.Singleton(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);

    Thread gameThread;   //thread-ul

    public Player player = new Player(this, keyH);
    public entity []obj = new entity[10];
    public entity []npc = new entity[10];
    public entity []monster = new entity[20];
    ArrayList<entity> entityList = new ArrayList<>();

    //GAME STATE URI
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;



    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);//creeaza un obiect de tip thread
        gameThread.start();//apelat de mai sus, ia ca argument obiectul de tip GamePanel insusi si porneste threadul
    }

    @Override
   /* public void run()//se apeleaza automat cand se instantiaza un thread
    {
        double drawInterval = 1000000000/FPS; //reface desenele o data la 0.01666.. secunde == o data la 1000000000/60 nanosecunde
        double nextDrawTime=System.nanoTime()+drawInterval;//aloca 0.01666.. secunde intre doua redesenari succesive ale ecranului
            //aici vom crea loop ul jocului-continuu!-miezul jocului
        while(gameThread != null)
        {

        //    System.out.println("The game loop is running");   ---
            //1. updatam informatii gen pozitiile caracterelor
            update();
            //2. rescriem informatia pe ecran(gen miscarea)
            repaint();



            try
            {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime=remainingTime/1000000;//transformam din nanosecunde in milisecunde pentru functie
                if (remainingTime < 0)
                {
                    remainingTime = 0; //nu prea se intampla, dar ca masura de singuranta daca dureaza mai mult
                                       // desenatul decat timpul alocat sa nu se intercaleze
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime = nextDrawTime+drawInterval;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }*/

    public void run()
    {
        double drawInterval = 1000000000/FPS; //reface desenele o data la 0.01666.. secunde == o data la 1000000000/60 nanosecunde
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; //pentru a interoga FPS urile
        int drawCount = 0; //pentru a interoga FPS urile

        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            timer+= (currentTime - lastTime) / drawInterval; //pentru a interoga FPS urile

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime -lastTime);
            lastTime = currentTime;

            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++; //pentru a interoga FPS urile
            }

            if(timer >= 1000000000)
            {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer=0;
            }
        }
    }

    public void update()
    {
        if(gameState == playState)
        {
            //Player
            player.update();
            //NPC
            for(int i=0;i< npc.length;++i)
            {
                if(npc[i]!=null)
                    npc[i].update();
            }

            for(int i=0;i< monster.length;++i)
            {
                if(monster[i]!=null)
                    monster[i].update();
            }
        }
        if(gameState == pauseState)
        {
            //nothing
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //super practic face ca apelul functiei sa aiba loc din clasa parinte(in acest caz, clasa JPannel
        Graphics2D g2=(Graphics2D) g; //converteste g, adica un obiect de tip Graphics intr-un obiect de tip Graphics2D
        //clasa Graphics2D extinde(cred) clasa Graphics, adaugandu-i noi functionalitati // nu stiu inca de ce nu am facut direct un obiect Graphics2D

        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        //ALTELE
        else
        {
            //TILE PAINT
            tileM.draw(g2);

            //ADD ENTITIES TO LIST
            entityList.add(player);

            for(int i=0;i<npc.length;++i)
            {
                if(npc[i] != null)
                {
                    entityList.add(npc[i]);
                }
            }

            for(int i=0;i<obj.length;i++)
            {
                if(obj[i]!=null)
                {
                    entityList.add(obj[i]);
                }
            }

            for(int i=0;i<monster.length;++i)
            {
                if(monster[i]!=null)
                {
                    entityList.add(monster[i]);
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<entity>() {
                @Override
                public int compare(entity e1, entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i=0;i<entityList.size();++i)
            {
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();


            //UI
            ui.draw(g2);
        }

        g2.dispose();// practica buna, face dispoze la elementele desenate dupa ce nu le mai foloseste si salveaza resure ale sistemului

    }
}







