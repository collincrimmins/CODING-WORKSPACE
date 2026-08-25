package LLD.Projects.connectfour;

import LLD.Projects.connectfour.entities.Game;
import LLD.Projects.connectfour.entities.Player;
import LLD.Projects.connectfour.enums.Token;

public class ConnectFour {
    public static void main(String[] args) {
        Player bob = new Player(1, Token.RED);
        Player joe = new Player(2, Token.YELLOW);

        Game game = new Game(6, 7, bob, joe);

        // Test Column
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(1);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(1);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(1);
        // game.nextPlayerMakePlacement(0);

        // Test Row
        game.nextPlayerMakePlacement(0);
        game.nextPlayerMakePlacement(0);
        game.nextPlayerMakePlacement(2);
        game.nextPlayerMakePlacement(2);
        game.nextPlayerMakePlacement(3);
        game.nextPlayerMakePlacement(3);
        game.nextPlayerMakePlacement(4);
        game.nextPlayerMakePlacement(4);
        game.nextPlayerMakePlacement(5);
        game.nextPlayerMakePlacement(5);

        // Test Left Diagonal


        // Test Right Diagonal


        // Test out of bounds (column)
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);
        // game.nextPlayerMakePlacement(0);

        // Test Full Board
        // for (int c = 0; c < 7; c++) {
        //     for (int r = 0; r < 6; r++) {
        //         game.nextPlayerMakePlacement(c);
        //     }
        // }
    }
}
