package Quixo.Model;

/**
 * Represents a single cube on the Quixo game board.
 * Each cube can have a symbol representing a player or be empty.
 */
public class Cube {
    /** The symbol on the cube (' ' for empty, 'X' or 'O' for players) */
    private char symbol;

    /**
     * Constructor that initializes a cube with an empty symbol.
     */
    public Cube() {
        symbol = ' ';
    }

    /**
     * Gets the symbol on this cube.
     * 
     * @return The symbol character (' ' for empty, or a player symbol)
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol on this cube.
     * 
     * @param symbol The symbol to set (' ' for empty, or a player symbol)
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

}
