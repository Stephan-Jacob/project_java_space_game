package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;
    private static KeyHandler instance=null;
    private KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static KeyHandler Singleton(GamePanel gp)
    {
        if(instance==null)
            instance=new KeyHandler(gp);
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();//code primeste codul tastei apasate

        //TITLE STATE

        if(gp.gameState == gp.titleState)
        {
            if(gp.ui.titleScreenState == 0) {

                if (code == KeyEvent.VK_D) //daca code=tasta
                {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 6)
                        gp.ui.commandNum = 0;
                }

                if (code == KeyEvent.VK_A) //daca code=tasta
                {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0)
                        gp.ui.commandNum = 6;
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }

                    if (gp.ui.commandNum == 1 && gp.ui.primulNivelTerminat==false) {
                        gp.gameState = gp.playState;
                    }
                    else if(gp.ui.commandNum == 1 && gp.ui.primulNivelTerminat==true)
                    {
                        System.out.print("Primul nivel a fost infrant deja!");
                    }
                    if (gp.ui.commandNum == 2 && gp.ui.primulNivelTerminat==true) {
                        gp.player.setDefaultValues();
                        gp.gameState = gp.playState;
                    }
                    else if(gp.ui.commandNum == 2 && gp.ui.primulNivelTerminat==false)
                    {
                        System.out.print("Trebuie invins primul nivel pentru a-l accesa pe cel de al doilea!");
                    }

                    if (gp.ui.commandNum == 3) {
                        //merchant
                    }

                    if (gp.ui.commandNum == 4) {
                        // save game
                        BazaDeDate baza = new BazaDeDate();
                        //baza.SaveBaza();
                        //baza.CreareBaza();
                    }

                    if (gp.ui.commandNum == 5) {
                        //load game
                    }

                    if (gp.ui.commandNum == 6) {
                        System.exit(0);
                    }
                }
            } else if(gp.ui.titleScreenState == 1)
            {
                if (code == KeyEvent.VK_ENTER)
                {
                    gp.ui.titleScreenState=0;
                }
            }
            else if(gp.ui.titleScreenState == 2)
            {
                if (code == KeyEvent.VK_ENTER)
                {
                    gp.ui.titleScreenState=0;
                }
            }

            else if(gp.ui.titleScreenState==3)
            {
                if (code == KeyEvent.VK_ENTER)
                {
                    gp.ui.titleScreenState=0;
                }
            }

        }

        //PLAY STATE
        else if (gp.gameState == gp.playState)
        {
            if (code == KeyEvent.VK_W) //daca code=tasta
            {
                upPressed = true;//setam un boolean cu valoarea true pentru a arata ca e apasata tasta pentru mersul in sus
            }

            if (code == KeyEvent.VK_S) //daca code=tasta
            {
                downPressed = true;
            }

            if (code == KeyEvent.VK_A) //daca code=tasta
            {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_D) //daca code=tasta
            {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_P) //daca code=tasta
            {
                gp.gameState = gp.pauseState;
            }

            if (code == KeyEvent.VK_ENTER) //daca code=tasta
            {
                enterPressed = true;
            }
        }
        //PAUSE STATE
        if (gp.gameState == gp.pauseState)
        {
            if (code == KeyEvent.VK_ESCAPE) //daca code=tasta
                gp.gameState = gp.playState;
        }

        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState)
        {
            if (code == KeyEvent.VK_ENTER)
            {
                gp.gameState=gp.playState;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code=e.getKeyCode();

        if(code == KeyEvent.VK_W) //daca code=tasta
        {
            upPressed=false;//punem pe boolean valoarea false pentru a arata ca se s-a oprit tastarea
        }

        if(code == KeyEvent.VK_S) //daca code=tasta
        {
            downPressed=false;
        }

        if(code == KeyEvent.VK_A) //daca code=tasta
        {
            leftPressed=false;
        }

        if(code == KeyEvent.VK_D) //daca code=tasta
        {
            rightPressed=false;
        }
    }

}
