package entity;

import main.GamePanel;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC_Penguin extends entity {
    public NPC_Penguin(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getPenguinImage();
        setDialogue();
    }

    public void getPenguinImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/pinguin/pinguin_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogue()
    {
        dialogues[0] = "Salut Apollo, tu esti singurul care ne mai poate scapa din asta!\n" +
                "Ajuta-l pe fratele meu sa evadeze din mainile garzilor lui Vlodorov si acesta iti va\n" +
                "spune unde ase afla antagonistul pentru a-l putea invinge!\n" +
                "Din ce am auzit, fratele meu este tinut captiv in baraca de pe drumul din dreapta,\n" +
                "insa pentru a-l putea elibera, va fi nevoie sa sustragi cheia de la una dintre\n" +
                "garzile care sta de paza pe lac...";
        dialogues[1] = "Ce mai astepti? Ia cheia de la garda de pe lac si salveaza-mi fratele!\n" +
                "Promit ca vei avea parte de admiratie din partea localnicilor si\n" +
                "vom avea grija sa iti oferim multe foloase daca ne vei ajuta!";
    }

    public void setAction() {
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