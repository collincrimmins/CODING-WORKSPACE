package LLD.Projects.connectfour.entities;

import LLD.Projects.connectfour.enums.Token;

public class Player {
    private int id;
    private Token token;

    public Player(int id, Token token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    // public char getPlayerDisplayLetter() {
    //     return token.getDisplayLetter();
    // }

    public Token getToken() {
        return token;
    }
}
