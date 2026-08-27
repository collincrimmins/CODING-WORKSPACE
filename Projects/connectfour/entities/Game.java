package Projects.connectfour.entities;

import Projects.connectfour.enums.GameStatus;
import Projects.connectfour.enums.Token;

public class Game {
    private Board board;
    private Player[] players;

    private int playerTurnIndex = -1;
    private GameStatus gameStatus;

    public Game(int r, int c, Player player1, Player player2) {
        this.board = new Board(r, c);
        this.players = new Player[]{ player1, player2 };
        this.gameStatus = GameStatus.ACTIVE;
        this.playerTurnIndex = 0;
    }

    public void nextPlayerMakePlacement(int c) {
        // Game Status
        if (gameStatus == GameStatus.ENDED) {
            System.out.println("[Blocked Placement] No game active...");
            return;
        }

        // Action
        String result = board.placeToken(c, players[playerTurnIndex]);

        // Print Board
        board.printBoard();

        // Check Winner
        if (result.equals("win")) {
            System.out.println("Player " + players[playerTurnIndex].getId() + " won!");
            gameEnd();
            return;
        }

        // Change Player
        if (playerTurnIndex == 1) {
            playerTurnIndex = 0;
        } else {
            playerTurnIndex = 1;
        }

        // Check Board Full
        if (board.boardIsFull()) {
            System.out.println("No winner - draw");
            gameEnd();
        }
    }

    void gameEnd() {
        this.gameStatus = GameStatus.ENDED;
        // board.clear();
    }
}
