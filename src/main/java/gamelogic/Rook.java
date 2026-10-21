package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type Rook.
 */
public class Rook extends Piece {


    /**
     * This is the constructor of the class Rook
     * @param color The color of the Rook object
     */
    Rook(char color) {
        this.color = color;
        this.p = "R";
    }

    /**
     * This function checks if the player's Rook Move is valid.
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     * @return Returns true if valid, false if not
     */
    @Override
    public boolean isValidMove(Move move, ChessBoard board) {
        if (!this.areValidSquares(move)) {
            return false;
        }
        int[] coordinates = this.getXYCoordinates(move);
        int from = this.numbers[coordinates[0]][coordinates[1]];
        int to = this.numbers[coordinates[2]][coordinates[3]];

        if (from < to) {
            int difference = to - from;
            int division = difference / 14; //check if piece is going up
            if(division == 1 || division == 2 || division == 3 || division == 4 || division == 5 || division == 6 ||
                    division == 7 || division == 8 || division == 9 || division == 10 || division == 11 || division == 12 || division == 13) {
                int test = from;
                for (int i = 0; i < division; i++) {
                    test = test + 14;
                }
                if (test == to) {
                    int test2 = from;
                    for (int i = 0; i < division-1; i++) {
                        test2 = test2 + 14;
                        int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                        if (board.getBox(coordinatesNumbers[0],coordinatesNumbers[1]).getPiece() != null) {
                            return false;
                        }
                    }
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                                this.color == board.getBox(coordinates[2],coordinates[3]).getPiece().color){
                        return false;
                    } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else {
                    return false;
                }

            } else {
                int division2 = difference / 1; // check if piece is going right
                if(division2 == 1 || division2 == 2 || division2 == 3 || division2 == 4 ||
                        division2 == 5 || division2 == 6 || division2 == 7 || division2 == 8 ||
                        division2 == 9 || division2 == 10 || division2 == 11 || division2 == 12 || division2 == 13) {
                    int test = from;
                    if (coordinates[0] != coordinates[2]) {
                        return false;
                    }
                    for (int i = 0; i < division2; i++) {
                        test = test + 1;
                    }
                    if (test == to) {
                        int test2 = from;
                        for (int i = 0; i < division2-1; i++) {
                            test2 = test2 + 1;
                            int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                            if (board.getBox(coordinatesNumbers[0],coordinatesNumbers[1]).getPiece() != null) {
                                return false;
                            }
                        }
                        if (coordinates[1] > coordinates[3]) {
                            return false;
                        }
                        if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                                this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                                move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                            this.attack = true;
                            return true;
                        } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                                this.color == board.getBox(coordinates[2],coordinates[3]).getPiece().color){
                            return false;
                        } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                    } else {
                        return false;
                    }
                }

            }
        } else if (from > to) {
            int difference = from - to;
            int division = difference / 14; //check if piece is going down
            if (division == 1 || division == 2 || division == 3 || division == 4 || division == 5 || division == 6 ||
                    division == 7 || division == 8 || division == 9 || division == 10 || division == 11 || division == 12 || division == 13) {
                int test = from;
                for (int i = 0; i < division; i++) {
                    test = test - 14;
                }
                if (test == to) {
                    int test2 = from;
                    for (int i = 0; i < division - 1; i++) {
                        test2 = test2 - 14;
                        int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                        if (board.getBox(coordinatesNumbers[0], coordinatesNumbers[1]).getPiece() != null) {
                            return false;
                        }
                    }
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color == board.getBox(coordinates[2],coordinates[3]).getPiece().color){
                        return false;
                    } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else {
                    return false;
                }
            } else {
                int division2 = difference / 1; // check if piece is going left
                if(division2 == 1 || division2 == 2 || division2 == 3 || division2 == 4 ||
                        division2 == 5 || division2 == 6 || division2 == 7 || division2 == 8 ||
                        division2 == 9 || division2 == 10 || division2 == 11 || division2 == 12 || division2 == 13) {
                    int test = from;
                    if (coordinates[0] != coordinates[2]) {
                        return false;
                    }
                    for (int i = 0; i < division2; i++) {
                        test = test - 1;
                    }
                    if (test == to) {
                        int test2 = from;
                        for (int i = 0; i < division2-1; i++) {
                            test2 = test2 - 1;
                            int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                            if (board.getBox(coordinatesNumbers[0],coordinatesNumbers[1]).getPiece() != null) {
                                return false;
                            }
                        }
                        if (coordinates[1] < coordinates[3]) {
                            return false;
                        }
                        if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                                this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                                move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                            this.attack = true;
                            return true;
                        } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                                this.color == board.getBox(coordinates[2],coordinates[3]).getPiece().color){
                            return false;
                        } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This function moves the Rook if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move, ChessBoard board) {
        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece rook = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],rook);

                if (this.putsKingInCheck(move,board)) {
                    rook = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],rook);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece rook = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],rook);
                this.attack = false;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    rook = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],rook);
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
