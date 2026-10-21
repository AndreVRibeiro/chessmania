package gamelogic;

/**
 * This class creates a square that can hold a chess piece.
 */
public class Square {

    Piece piece;
    int x;
    int y;
    boolean valid;

    /**
     * This is the constructor of the class Square
     * @param piece The chess piece that is on the square
     * @param x The x coordinate of the square's location
     * @param y The y coordinate of the square's location
     * @param valid True if the square is part of the board, false if not
     */
    public Square(Piece piece, int x, int y, boolean valid) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.valid = valid;
    }

    /**
     * This method tells if the Square is being occupied by a piece.
     * @return Returns true if a piece is on the square, false is not.
     */
    public boolean isSquareOccupied() {
        return this.piece != null;
    }

    /**
     * This method returns the piece that is on the square.
     * @return The piece that is on the square.
     */
    public Piece getPiece() {
        if (this.piece != null) {
            return this.piece;
        } else {
            return null;
        }
    }

    /**
     * This returns the color and the type of the chess piece.
     * @return The color and the type.
     */
    public String getPieceStr() {
        if (this.piece != null) {
            return this.piece.getPieceStr();
        } else {
            return "null";
        }
    }

    /**
     * This sets the square as empty.
     */
    public void setSquareEmpty() {
        if (this.piece != null) {
            this.piece = null;
        }
    }

    /**
     * This function is used in the decode function in the class ChessBoard and helps to put pieces in squares.
     * @param color The color of the piece
     * @param piece The kind of piece
     */
    public void setPiece(char color, char piece) {
        if (piece == 'P') {
            this.piece = new Pawn(color);
        } else if (piece == 'R') {
            this.piece = new Rook(color);
        } else if (piece == 'N') {
            this.piece = new Knight(color);
        } else if (piece == 'B') {
            this.piece = new Bishop(color);
        } else if (piece == 'Q') {
            this.piece = new Queen(color);
        } else if (piece == 'K') {
            this.piece = new King(color);
        } else {
            System.out.println("error in decoding, not the right char. color: " + color + "   piece: " + piece);
        }
    }

    /**
     * This method sets a Piece on a square
     * @param piece The piece that is supposed to go on the square
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
