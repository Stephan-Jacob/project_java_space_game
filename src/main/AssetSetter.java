package main;

import entity.NPC_Penguin;
import entity.NPC_Trooper;
import monster.MON_Foca;
import monster.MON_paianjen;
import res.object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp=gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 10 * gp.tileSize;
        gp.obj[0].worldY = 14 * gp.tileSize;


        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 40 * gp.tileSize;
        gp.obj[1].worldY = 15 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 41 * gp.tileSize;
        gp.obj[2].worldY = 15 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].worldX = 42 * gp.tileSize;
        gp.obj[3].worldY = 4 * gp.tileSize;

        gp.obj[4] = new OBJ_Cage(gp);
        gp.obj[4].worldX = 35 * gp.tileSize;
        gp.obj[4].worldY = 6 * gp.tileSize;

        gp.obj[5] = new OBJ_DOOR_BOSS(gp);
        gp.obj[5].worldX = 72 * gp.tileSize;
        gp.obj[5].worldY = 25 * gp.tileSize;

        gp.obj[6] = new OBJ_DOOR_CASTLE(gp);
        gp.obj[6].worldX = 68 * gp.tileSize;
        gp.obj[6].worldY = 33 * gp.tileSize;
    }
    public void setNPC()
    {
        gp.npc[0] = new NPC_Penguin(gp);
        gp.npc[0].worldX = gp.tileSize*16;
        gp.npc[0].worldY = gp.tileSize*37;

        gp.npc[1] = new NPC_Penguin(gp);
        gp.npc[1].worldX = gp.tileSize*35;
        gp.npc[1].worldY = gp.tileSize*5;

        gp.npc[2] = new NPC_Trooper(gp);
        gp.npc[2].worldX = gp.tileSize*67;
        gp.npc[2].worldY = gp.tileSize*39;

    }

    public void setMonster()
    {
        gp.monster[0] = new MON_Foca(gp);
        gp.monster[0].worldX = gp.tileSize*39;
        gp.monster[0].worldY = gp.tileSize*16;


        gp.monster[1] = new MON_Foca(gp);
        gp.monster[1].worldX = gp.tileSize*42;
        gp.monster[1].worldY = gp.tileSize*16;

        gp.monster[2] = new MON_Foca(gp);
        gp.monster[2].worldX = gp.tileSize*41;
        gp.monster[2].worldY = gp.tileSize*12;

        gp.monster[3] = new MON_Foca(gp);
        gp.monster[3].worldX = gp.tileSize*39;
        gp.monster[3].worldY = gp.tileSize*10;

        gp.monster[4] = new MON_Foca(gp);
        gp.monster[4].worldX = gp.tileSize*45;
        gp.monster[4].worldY = gp.tileSize*9;

        gp.monster[5] = new MON_Foca(gp);
        gp.monster[5].worldX = gp.tileSize*45;
        gp.monster[5].worldY = gp.tileSize*5;

        gp.monster[6] = new MON_Foca(gp);
        gp.monster[6].worldX = gp.tileSize*34;
        gp.monster[6].worldY = gp.tileSize*7;

        gp.monster[7] = new MON_Foca(gp);
        gp.monster[7].worldX = gp.tileSize*37;
        gp.monster[7].worldY = gp.tileSize*8;

        gp.monster[8] = new MON_paianjen(gp);
        gp.monster[8].worldX = gp.tileSize*72;
        gp.monster[8].worldY = gp.tileSize*26;

        gp.monster[9] = new MON_paianjen(gp);
        gp.monster[9].worldX = gp.tileSize*72;
        gp.monster[9].worldY = gp.tileSize*27;

        gp.monster[10] = new MON_paianjen(gp);
        gp.monster[10].worldX = gp.tileSize*72;
        gp.monster[10].worldY = gp.tileSize*28;
    }
}
