package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

import main.GamePanel;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


    public class NPC_Trooper extends entity {
        public NPC_Trooper(GamePanel gp) {
            super(gp);

            direction = "down";
            speed = 0;

            getPenguinImage();
            setDialogue();
        }

        public void getPenguinImage() {
            try {
                up1 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/res/antagonist/trooper.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void setDialogue()
        {
            dialogues[0] = "Vei fi distrus! Seful meu te asteapta...pentru a vorbi cu acesta, trebuie\n" +
                    "sa treci de PAIANJENI!";
        }

        public void setAction() {

        }
        public void speak()
        {
            if(dialogues[dialogueIndex] == null)
            {
                dialogueIndex=0;
            }
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
            switch(gp.player.direction)
            {
                case "up":
                    direction = "down";
                    break;
                case "down":
                    direction = "up";
                    break;
                case "left":
                    direction = "right";
                    break;
                case "right":
                    direction = "left";
                    break;
            }
        }
    }