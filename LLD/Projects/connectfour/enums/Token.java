package LLD.Projects.connectfour.enums;

public enum Token {
    RED('R'),
    YELLOW('Y'),
    EMPTY('_');

    private final char letter;

    Token(char letter) {
        this.letter = letter;
    }

    public char getDisplayLetter() {
        return letter;
    }
}
