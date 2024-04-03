package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import main.GamePanel.*;
import main.UI.*;

public class BazaDeDate {
    GamePanel gp;
    public static void CreareBaza()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ranking.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE JOC " +
                    "(NAME STRING NOT NULL, " +
                    " LEVEL INT NOT NULL, " +
                    "ENEMIES KILLED IN NOT NULL)";
            stmt.execute(sql);




            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    public void SaveBaza() {
        Scanner input = new Scanner(System.in);
        String name=input.nextLine();
        String sql = "INSERT INTO JOC(NAME,LEVEL,ENEMIES) VALUES (" + name + "," + gp.player.nivel + "," + gp.player.numarUcisi + ")";
    }
}
