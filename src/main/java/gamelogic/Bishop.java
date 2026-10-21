package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type Bishop.
 */
public class Bishop extends Piece {


    /**
     * This is the constructor of the class Bishop
     * @param color The color of the Bishop object
     */
    Bishop(char color) {

        this.color = color;
        this.p = "B";
    }

    /**
     * This function checks if the player's Bishop Move is valid.
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
            // checks if the bishop wants to go right up - START
            int difference = to - from;
            int division = difference / 15;
            if (division == 1 || division == 2 || division == 3 || division == 4
                    || division == 5 || division == 6 || division == 7 || division == 8) {
                int test = from;
                for (int i = 0; i < division; i++) {
                    test = test + 15;
                }
                if (test == to) {
                    int test2 = from;
                    for (int i = 0; i < division-1; i++) {
                        test2 = test2 + 15;
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
                    return checkIfLeftUp(difference,to,from,move,board,coordinates);
                }
            } else {
                // checks if the bishop wants to go left up - START
                return checkIfLeftUp(difference,to,from,move,board,coordinates);
            }
        } else if (from > to) {
            // checks if the bishop wants to go left down - START
            int difference = from - to;
            int division = difference / 15;
            if (division == 1 || division == 2 || division == 3 || division == 4
                    || division == 5 || division == 6 || division == 7 || division == 8) {
                int test = from;
                for (int i = 0; i < division; i++) {
                    test = test - 15;
                }
                if (test == to) {
                    int test2 = from;
                    for (int i = 0; i < division - 1; i++) {
                        test2 = test2 - 15;
                        int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                        if (board.getBox(coordinatesNumbers[0], coordinatesNumbers[1]).getPiece() != null) {
                            return false;
                        }
                    }
                    if (coordinates[1] < coordinates[3]) {
                        return false;
                    }
                    if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2], coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                            this.color == board.getBox(coordinates[2], coordinates[3]).getPiece().color) {
                        return false;
                    } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else {
                    return checkIfRightDown(difference,to,from,move,board,coordinates);
                }
            }
            // checks if the bishop wants to go right down - START
            else {
                return checkIfRightDown(difference,to,from,move,board,coordinates);
            }
        }
        return false;
    }

    /**
     * This function moves the Bishop if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move,ChessBoard board) {
        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece bishop = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],bishop);

                if (this.putsKingInCheck(move,board)) {
                    bishop = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],bishop);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece bishop = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],bishop);
                this.attack = false;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    bishop = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],bishop);
                    board.setBox(coordinates[2],coordinates[3],opponent);
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }
            }
        } else {
            move.valid = false;
            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal Move !!!");
            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
        }
    }

    /**
     * This function checks if the player wants to move diagonal (Left Up)
     * @param difference The difference between the starting position and the end position
     * @param to The int value of the end position (coordinates in form of a number)
     * @param from The int value of the start position (coordinates in form of a number)
     * @param board The board that is being played on
     * @param coordinates The x and y coordinates of the start and end position
     * @return Returns true if the player wants to move left up and the move is valid, false if not.
     */
    private boolean checkIfLeftUp(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
        // checks if the bishop wants to go left up - START
        int division = difference / 13;
        if (division == 1 || division == 2 || division == 3 || division == 4
                || division == 5 || division == 6 || division == 7 || division == 8) {
            int test = from;
            for (int i = 0; i < division; i++) {
                test = test + 13;
            }
            if (test == to) {
                int test2 = from;
                for (int i = 0; i < division - 1; i++) {
                    test2 = test2 + 13;
                    int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                    if (board.getBox(coordinatesNumbers[0], coordinatesNumbers[1]).getPiece() != null) {
                        return false;
                    }
                }
                if (coordinates[1] < coordinates[3]) {
                    return false;
                }
                if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                        this.color != board.getBox(coordinates[2], coordinates[3]).getPiece().color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    this.attack = true;
                    return true;
                } else if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                        this.color == board.getBox(coordinates[2], coordinates[3]).getPiece().color) {
                    return false;
                } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * This function checks if the player wants to move diagonal (Right Down)
     * @param difference The difference between the starting position and the end position
     * @param to The int value of the end position (coordinates in form of a number)
     * @param from The int value of the start position (coordinates in form of a number)
     * @param board The board that is being played on
     * @param coordinates The x and y coordinates of the start and end position
     * @return Returns true if the player wants to move left up and the move is valid, false if not.
     */
    private boolean checkIfRightDown(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
        int division = difference / 13;
        if (division == 1 || division == 2 || division == 3 || division == 4
                || division == 5 || division == 6 || division == 7 || division == 8) {
            int test = from;
            for (int i = 0; i < division; i++) {
                test = test - 13;
            }
            if (test == to) {
                int test2 = from;
                for (int i = 0; i < division - 1; i++) {
                    test2 = test2 - 13;
                    int[] coordinatesNumbers = this.getXYFromNumbers(test2);
                    if (board.getBox(coordinatesNumbers[0], coordinatesNumbers[1]).getPiece() != null) {
                        return false;
                    }
                }
                if (coordinates[1] > coordinates[3]) {
                    return false;
                }
                if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                        this.color != board.getBox(coordinates[2], coordinates[3]).getPiece().color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    this.attack = true;
                    return true;
                } else if (board.getBox(coordinates[2], coordinates[3]).getPiece() != null &&
                        this.color == board.getBox(coordinates[2], coordinates[3]).getPiece().color) {
                    return false;
                } else return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
            } else {
                return false;
            }
        }
        return false;
    }


}
