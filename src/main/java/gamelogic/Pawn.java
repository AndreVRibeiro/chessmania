package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type Pawn.
 */
public class Pawn extends Piece {

    boolean firstMove = true;

    /**
     * This is the constructor of the class Pawn
     * @param color The color of the Pawn object
     */
    Pawn(char color) {
        this.color = color;
        this.p = "P";
    }

    /**
     * This function checks if the move is possible for the pawn
     * @param move The move coordinates
     * @return Returns false if invalid, true if valid
     */
    @Override
    public boolean isValidMove(Move move,ChessBoard board) {
        if (!this.areValidSquares(move)){
            return false;
        }
        int[] coordinates = this.getXYCoordinates(move);
        int from = this.numbers[coordinates[0]][coordinates[1]];
        int to = this.numbers[coordinates[2]][coordinates[3]];

        // the if cases are temporary while the board doesn't rotate for each player

        if (move.player.isRed()) {
            if (!this.firstMove) {
                if (from + 14 == to) {
                    return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else if (from + 15 == to || from + 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (this.firstMove) {
                if (from + 14 == to || from + 14*2 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null) {
                        return false;
                    } else {
                        this.firstMove = false;
                        return true;
                    }
                } else if (from + 15 == to || from + 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if (move.player.isBlue()) {
            if (!this.firstMove) {
                if (from + 1 == to) {
                    return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else if (from + 15 == to || from - 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (this.firstMove) {
                if (from + 1 == to || from + 2 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null) {
                        return false;
                    } else {
                        this.firstMove = false;
                        return true;
                    }
                } else if (from + 15 == to || from - 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if (move.player.isYellow()) {
            if (!this.firstMove) {
                if (from - 14 == to) {
                    return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else if (from - 15 == to || from - 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (this.firstMove) {
                if (from - 14 == to || from - 14*2 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null) {
                        return false;
                    } else {
                        this.firstMove = false;
                        return true;
                    }
                } else if (from - 15 == to || from - 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if (move.player.isGreen()) {
            if (!this.firstMove) {
                if (from - 1 == to) {
                    return board.getBox(coordinates[2], coordinates[3]).getPiece() == null;
                } else if (from - 15 == to || from + 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (this.firstMove) {
                if (from - 1 == to || from - 2 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null) {
                        return false;
                    } else {
                        this.firstMove = false;
                        return true;
                    }
                } else if (from - 15 == to || from + 13 == to) {
                    if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                            this.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color &&
                            move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                        this.attack = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This function moves the Pawn if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move, ChessBoard board) {

        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece pawn = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],pawn);

                if (this.putsKingInCheck(move,board)) {
                    pawn = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],pawn);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                } else {
                    this.checkPromotion(move,board,coordinates);
                }
            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece pawn = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],pawn);
                this.attack = false;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    pawn = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],pawn);
                    board.setBox(coordinates[2],coordinates[3],opponent);
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                } else {
                    this.checkPromotion(move,board,coordinates);
                }
            }
        } else {
            move.valid = false;
            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal Move !!!");
            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
        }
    }

    /**
     * This function checks if the pawn has reached the position where it gets promoted. If it is true the pawn
     * transforms into a queen.
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     * @param coordinates The location of the pawn
     */
    public void checkPromotion(Move move, ChessBoard board, int[] coordinates) {
        if (move.player.isRed()) {
            if (coordinates[2] == 6) {
                Queen promotedPawn = new Queen(move.player.color);
                promotedPawn.promotedPawn = true;
                board.setBox(coordinates[2],coordinates[3],promotedPawn);
            }
        } else if (move.player.isBlue()) {
            if (coordinates[3] == 7) {
                Queen promotedPawn = new Queen(move.player.color);
                promotedPawn.promotedPawn = true;
                board.setBox(coordinates[2],coordinates[3],promotedPawn);
            }
        } else if (move.player.isYellow()) {
            if (coordinates[2] == 7) {
                Queen promotedPawn = new Queen(move.player.color);
                promotedPawn.promotedPawn = true;
                board.setBox(coordinates[2],coordinates[3],promotedPawn);
            }
        } else if (move.player.isGreen()) {
            if (coordinates[3] == 6) {
                Queen promotedPawn = new Queen(move.player.color);
                promotedPawn.promotedPawn = true;
                board.setBox(coordinates[2],coordinates[3],promotedPawn);
            }
        }
    }


}
