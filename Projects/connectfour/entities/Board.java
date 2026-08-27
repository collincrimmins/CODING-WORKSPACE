package Projects.connectfour.entities;

import Projects.TicTacToe.entities.Cell;
import Projects.connectfour.enums.Token;
import Projects.connectfour.exceptions.GameException;

public class Board {
    private final int ROWS;
    private final int COLS;
    Token[][] grid;

    public Board(int r, int c) {
        ROWS = r;
        COLS = c;
        grid = new Token[r][c];
        
        // Start
        initializeBoard();
        printBoard();
    }

    // Initialze
    void initializeBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS ; c++) {
                grid[r][c] = Token.EMPTY;
            }
        }
    }

    // Place at x,y
    String placeToken(int c, Player player) {
        if (c < 0 || c >= COLS) {
            throw new GameException("[ERROR] INVALID TOKEN PLACEMENT POSITION");
        }

        // Check from Bottom Up
        for (int r = ROWS - 1; r >= 0; r--) {
            if (grid[r][c] == Token.EMPTY) {
                // Set Token
                grid[r][c] = player.getToken();

                // Check Win
                return checkWin(r, c);
            }
        }

        // Error: No token placed (full column)
        throw new GameException("[ERROR] INVALID TOKEN PLACEMENT POSITION");
    }

    // Bounds
    boolean checkInBounds(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) {
            return false;
        }
        return true;
    }

    // Board Full
    boolean boardIsFull() {
         for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS ; c++) {
                if (grid[r][c] == Token.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Print
    void printBoard() {
        // System.out.println("------------------------");
        // for (Token[] row : grid) {
        //     for (int i = 0; i < COLS; i++) {
        //         System.out.print(row[i].getDisplayLetter() + " | ");
        //     }
        //     System.out.println("");
        // }
        // System.out.println("------------------------");

        for (int r = 0; r < ROWS; r++) {
            System.out.print("| ");
            for (int c = 0; c < COLS; c++) {
                char symb = (grid[r][c] == null) ? '.' : grid[r][c].getDisplayLetter();
                System.out.print(symb + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------------");
        System.out.println("  0 1 2 3 4 5 6  \n");
    }

    // Check if win at x/y (after placement)
    String checkWin(int row, int col) {
        Token myToken = grid[row][col];

        int count;
        int r;
        int c;

        // Row
        count = 1;
        r = row;
        c = col;
        while (true) {
            c = c + 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        r = row;
        c = col;
        while (true) {
            c = c - 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        if (count >= 4) {
            System.out.println("(Row Win)");
            return "win";
        }

        // Column
        count = 1;
        r = row;
        c = col;
        while (true) {
            r = r + 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        r = row;
        c = col;
        while (true) {
            r = r - 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        if (count >= 4) {
            System.out.println("(Column Win)");
            return "win";
        }
        

        // Left Diagonal
        count = 1;
        r = row;
        c = col;
        while (true) {
            r = r - 1;
            c = c - 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        r = row;
        c = col;
        while (true) {
            r = r + 1;
            c = c + 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        if (count >= 4) {
            System.out.println("(Left Diagonal Win)");
            return "win";
        }

        // Right Diagonal
        count = 1;
        r = row;
        c = col;
        while (true) {
            r = r - 1;
            c = c + 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        r = row;
        c = col;
        while (true) {
            r = r + 1;
            c = c - 1;

            // Bounds
            if (!checkInBounds(r, c)) { break; }
            // Different Token
            if (grid[r][c] != myToken) { break; }

            count = count + 1;
        }
        if (count >= 4) {
            System.out.println("(Right Diagonal Win)");
            return "win";
        }

        // Default
        return "no win";
    }
}
