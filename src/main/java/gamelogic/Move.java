package gamelogic;

/**
 * This class represents a player move.
 */
public class Move {

    public Player player;
    public String coordinates;
    public boolean valid;
    public Piece killedPiece = null;

    // Array List with dead pieces. TO-DO

    /**
     * This is the constructor of the class Move
     * @param player The player that wants to make a move
     * @param coordinates the coordinates of the move
     */
    public Move(Player player, String coordinates) {
        this.player = player;
        this.coordinates = coordinates;
    }



}
