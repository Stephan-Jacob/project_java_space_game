package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];

    public TileManager(GamePanel gp)
    {
        this.gp=gp;

        tile = new Tile[16];
        mapTileNumber=new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/map.txt"); // !!!
    }

    public void getTileImage()
    {

            setup(0, "dirt", false);
            setup(1, "snow_with_foca", true);
            setup(2, "snow", false);
            setup(3, "snow_with_tree", true);
            setup(4, "water", true);
            setup(5, "wall", true);
            setup(6, "wood", false);
            setup(7, "catre_lac", true);
            setup(8, "catre_vila", true);
            setup(9, "racheta", true);
            setup(10, "sand", false);
            setup(11, "wall" ,true);
            setup(12,"dirt",false);
            setup(13,"trooper1",false);
            setup(14,"trooper2",false);
            setup(15,"antagonist",true);



    }
    public void setup(int index, String imagePath, boolean collision)
    {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br= new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = br.readLine();

                while(col < gp.maxWorldCol)
                {
                    String []numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol)
                {
                    col=0;
                    row++;
                }


            }
            br.close();
        } catch (Exception e) {

        }

    }
    public void draw(Graphics2D g2)
    {

        int worldCol=0;
        int worldRow=0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {
            int tileNumber = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize/*expandeaza cu un tile ca sa nu apara margini aiurea*/ > gp.player.worldX - gp.player.screenX && //ca sa nu
                worldX - gp.tileSize/*expandeaza la fel*/ < gp.player.worldX + gp.player.screenX && //randeze toata mapa,
                worldY + gp.tileSize/*expandeaza la fel*/ > gp.player.worldY - gp.player.screenY && //ci doar ce poate fi vazut de player
                worldY - gp.tileSize/*expandeaza la fel*/ < gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tile[tileNumber].image, screenX, screenY,null);
            }

            worldCol++;


            if(worldCol == gp.maxWorldCol)
            {
                worldCol=0;
                worldRow++;
            }
        }
    }
}
