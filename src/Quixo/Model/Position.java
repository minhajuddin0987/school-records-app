package Quixo.Model;

/**
 * Represents a position on the Quixo game board.
 * Each position has a row, column, and a value that represents the state of that position.
 */
public class Position {
        /**
         * The row index of this position on the game board.
         */
        private int row;

        /**
         * The column index of this position on the game board.
         */
        private int col;

        /**
         * The value at this position ('X', 'O', or another character representing empty).
         */
        private char value;

        /**
         * Creates a new Position with the specified row, column, and value.
         *
         * @param row The row index of the position
         * @param col The column index of the position
         * @param value The value at this position ('X', 'O', or another character)
         */
        public Position(int row, int col, char value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        /**
         * Returns the row index of this position.
         *
         * @return The row index
         */
        public int getRow() {
            return row;
        }

        /**
         * Returns the column index of this position.
         *
         * @return The column index
         */
        public int getCol() {
            return col;
        }

        /**
         * Returns the value at this position.
         *
         * @return The value character ('X', 'O', or another character)
         */
        public char getValue() {
            return value;
        }

        /**
         * Compares this Position with another object for equality.
         * Two positions are considered equal if they have the same row and column indices,
         * regardless of their values.
         *
         * @param obj The object to compare with
         * @return true if the positions have the same row and column, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Position pos = (Position) obj;
            return row == pos.row && col == pos.col;
        }

        /**
         * Returns a string representation of this position in the format "(row, col)".
         *
         * @return A string representation of the position
         */
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }
