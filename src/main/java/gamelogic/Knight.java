package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type Knight.
 */
public class Knight extends Piece {


    /**
     * This is the constructor of the class Knight
     * @param color The color of the Knight object
     */
    Knight(char color) {
        this.color = color;
        this.p = "N";
    }

    /**
     * This function checks if the player's Knight Move is valid.
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     * @return Returns true if valid, false if not
     */
    @Override
    public boolean isValidMove(Move move,ChessBoard board) {
        if (!this.areValidSquares(move)) {
            return false;
        }
        int[] coordinates = this.getXYCoordinates(move);
        int from = this.numbers[coordinates[0]][coordinates[1]];
        int to = this.numbers[coordinates[2]][coordinates[3]];

        if (from < to) {
            int difference = to - from;
            if (difference == 12 || difference == 27 || difference == 16 || difference == 29) {
                if (difference == 29 || difference == 16) {
                    if (coordinates[1] > coordinates[3]) {
                        return false;
                    }
                } else if (difference == 27 || difference == 12) {
                    if (coordinates[1] < coordinates[3]) {
                        return false;
                    }
                }
                if (board.getBox(coordinates[2],coordinates[3]).getPiece() == null) {
                    return true;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece().color == move.player.color) {
                    return false;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                        board.getBox(coordinates[2],coordinates[3]).getPiece().color != move.player.color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    this.attack = true;
                    return true;
                }
            } else {
                return false;
            }
        } else if (from > to) {
            int difference = from - to;
            if (difference == 12 || difference == 27 || difference == 16 || difference == 29) {
                if (difference == 29 || difference == 16) {
                    if (coordinates[1] < coordinates[3]) {
                        return false;
                    }
                } else if (difference == 27 || difference == 12) {
                    if (coordinates[1] > coordinates[3]) {
                        return false;
                    }
                }
                if (board.getBox(coordinates[2],coordinates[3]).getPiece() == null) {
                    return true;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece().color == move.player.color) {
                    return false;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                        board.getBox(coordinates[2],coordinates[3]).getPiece().color != move.player.color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    this.attack = true;
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * This function moves the Knight if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move,ChessBoard board) {
        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece knight = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],knight);

                if (this.putsKingInCheck(move,board)) {
                    knight = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],knight);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece knight = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],knight);
                this.attack = false;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    knight = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],knight);
                    board.setBox(coordinates[2],coordinates[3],opponent);
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }
            }
        } else {
            move.valid = false;
            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal Move !!!");
            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
        }
    }
}
