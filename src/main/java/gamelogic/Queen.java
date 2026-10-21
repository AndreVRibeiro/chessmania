package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type Queen.
 */
public class Queen extends Piece {


    /**
     * This is the constructor of the class Queen
     * @param color The color of the Queen object
     */
    Queen(char color) {
        this.color = color;
        this.p = "Q";
    }

    /**
     * This function checks if the player's Queen Move is valid.
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
                    return checkDiagonalUp(difference,to,from,move,board,coordinates);
                }
            } else {
                int division2 = difference / 1; // check if piece is going right
                if(division2 == 1 || division2 == 2 || division2 == 3 || division2 == 4 ||
                        division2 == 5 || division2 == 6 || division2 == 7 || division2 == 8 ||
                        division2 == 9 || division2 == 10 || division2 == 11 || division2 == 12 || division2 == 13) {
                    int test = from;
                    if (coordinates[0] != coordinates[2]) {
                        return checkDiagonalUp(difference,to,from,move,board,coordinates);
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
                        return checkDiagonalUp(difference,to,from,move,board,coordinates);
                    }
                } else {
                    return checkDiagonalUp(difference,to,from,move,board,coordinates);
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
                    return checkDiagonalDown(difference,to,from,move,board,coordinates);
                }
            } else {
                int division2 = difference / 1; // check if piece is going left
                if(division2 == 1 || division2 == 2 || division2 == 3 || division2 == 4 ||
                        division2 == 5 || division2 == 6 || division2 == 7 || division2 == 8 ||
                        division2 == 9 || division2 == 10 || division2 == 11 || division2 == 12 || division2 == 13) {
                    int test = from;
                    if (coordinates[0] != coordinates[2]) {
                        return checkDiagonalDown(difference,to,from,move,board,coordinates);
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
                        return checkDiagonalDown(difference,to,from,move,board,coordinates);
                    }
                } else {
                    return checkDiagonalDown(difference,to,from,move,board,coordinates);
                }
            }
        }
        return false;
    }

    /**
     * This function moves the Queen if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move,ChessBoard board) {
        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece queen = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],queen);

                if (this.putsKingInCheck(move,board)) {
                    queen = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],queen);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece queen = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],queen);
                this.attack = false;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    queen = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],queen);
                    board.setBox(coordinates[2],coordinates[3],opponent);
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            }
        } else {
            move.valid = false;
            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal Move !!!");
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
    public boolean checkIfLeftUp(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
        // checks if the queen wants to go left up - START
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
    public boolean checkIfRightDown(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
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

    /**
     * This function checks if the player wants to move up diagonal (Left Up and Right Up)
     * It uses the function checkIfLeftUp()
     * @param difference The difference between the starting position and the end position
     * @param to The int value of the end position (coordinates in form of a number)
     * @param from The int value of the start position (coordinates in form of a number)
     * @param board The board that is being played on
     * @param coordinates The x and y coordinates of the start and end position
     * @return Returns true if the player wants to move left up and the move is valid, false if not.
     */
    public boolean checkDiagonalUp(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
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
    }

    /**
     * This function checks if the player wants to move down diagonal (Left Down and Right Down)
     * It uses the function checkIfRightDown()
     * @param difference The difference between the starting position and the end position
     * @param to The int value of the end position (coordinates in form of a number)
     * @param from The int value of the start position (coordinates in form of a number)
     * @param board The board that is being played on
     * @param coordinates The x and y coordinates of the start and end position
     * @return Returns true if the player wants to move left up and the move is valid, false if not.
     */
    public boolean checkDiagonalDown(int difference, int to, int from, Move move, ChessBoard board, int[] coordinates) {
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
}
