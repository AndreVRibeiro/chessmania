package gamelogic;

import networking.Protocol;
import server.Server;

/**
 * This class creates chess pieces of the type King.
 */
public class King extends Piece {

    public boolean isBeingChecked = false;

    /**
     * This is the constructor of the class King
     * @param color The color of the King object
     */
    King(char color) {

        this.color = color;
        this.p = "K";
    }

    /**
     * This function checks if the player's King Move is valid.
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
            if (difference == 14 || difference == 13 || difference == 15 || difference == 1) {
                if (difference == 15 || difference == 1) {
                    if (coordinates[1] > coordinates[3]) {
                        return false;
                    }
                } else if (difference == 13) {
                    if (coordinates[1] < coordinates[3]) {
                        return false;
                    }
                }
                if (board.getBox(coordinates[2],coordinates[3]).getPiece() == null) {
                    if (this.isBeingChecked = true) {
                        if (this.canBeChecked(move,board)) {
                            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "You can't put your king in a check position");
                            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece().color == move.player.color) {
                    return false;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                        board.getBox(coordinates[2],coordinates[3]).getPiece().color != move.player.color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    if (this.isBeingChecked = true) {
                        if (this.canBeChecked(move,board)) {
                            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "You can't put your king in a check position");
                            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                            return false;
                        } else {
                            this.attack = true;
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (from > to) {
            int difference = from - to;
            if (difference == 14 || difference == 13 || difference == 15 || difference == 1) {
                if (difference == 15 || difference == 1) {
                    if (coordinates[1] < coordinates[3]) {
                        return false;
                    }
                } else if (difference == 13) {
                    if (coordinates[1] > coordinates[3]) {
                        return false;
                    }
                }
                if (board.getBox(coordinates[2],coordinates[3]).getPiece() == null) {
                    if (this.isBeingChecked = true) {
                        if (this.canBeChecked(move,board)) {
                            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "You can't put your king in a check position");
                            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece().color == move.player.color) {
                    return false;
                } else if (board.getBox(coordinates[2],coordinates[3]).getPiece() != null &&
                        board.getBox(coordinates[2],coordinates[3]).getPiece().color != move.player.color &&
                        move.player.teammate.color != board.getBox(coordinates[2],coordinates[3]).getPiece().color) {
                    if (this.isBeingChecked = true) {
                        if (this.canBeChecked(move,board)) {
                            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, you can't put your king in a check position");
                            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                            return false;
                        } else {
                            this.attack = true;
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * This function moves the King if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    @Override
    public void move(Move move, ChessBoard board) {
        this.isBeingChecked = true;

        if (this.isValidMove(move,board)) {
            move.valid = true;
            if (!this.attack) {
                int[] coordinates = this.getXYCoordinates(move);
                Piece king = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                board.setBox(coordinates[2],coordinates[3],king);
                this.isBeingChecked = false;
                this.hasMoved = true;

                if (this.canBeChecked(move,board)) {
                    king = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],king);
                    move.valid = false;
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }

            } else {
                int[] coordinates = this.getXYCoordinates(move);
                Piece king = board.getBox(coordinates[0],coordinates[1]).getPiece();
                board.setBox(coordinates[0],coordinates[1],null);
                Piece opponent = board.getBox(coordinates[2],coordinates[3]).getPiece();
                opponent.isKilled = true;
                move.killedPiece = opponent;
                board.setBox(coordinates[2],coordinates[3],king);
                this.attack = false;
                this.isBeingChecked = false;
                this.hasMoved = true;

                if (this.putsKingInCheck(move,board)) {
                    opponent.isKilled = false;
                    move.killedPiece = null;
                    move.valid = false;
                    king = board.getBox(coordinates[2],coordinates[3]).getPiece();
                    board.setBox(coordinates[2],coordinates[3],null);
                    board.setBox(coordinates[0],coordinates[1],king);
                    board.setBox(coordinates[2],coordinates[3],opponent);
                    Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal, your King would be put in Check");
                    Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");
                }
            }
        } else {
            move.valid = false;
            this.isBeingChecked = false;
            Server.sendTo(move.player.getID(), Protocol.LOBBYCHAT, "Illegal Move !!!");
            Server.sendTo(move.player.getID(), Protocol.SOUND,"INVALIDMOVE");

        }

    }

    /**
     * This function is used to examine if a king is put in a check position after making the move. It goes through all
     * present enemy figures and determines whether those figures can reach the kings position. If yes then the king is
     * not allowed to move to that position, because he would be put in check.
     * @param move The move that the player wants to make.
     * @param board The board that is being played on.
     * @return Returns true if the king is put in check, false if not.
     */
    public boolean canBeChecked(Move move, ChessBoard board) {
        String kingMove = move.coordinates;
        int indexOfSeparation = kingMove.indexOf('/');
        String pieceMove = "";
        String to = kingMove.substring(indexOfSeparation);

        Player player1 = new Player();
        Player player2 = new Player();

        if (move.player.isRed()) {
            player1.color = 'B';
            player1.teammate = player2;
            player2.color = 'G';
            player2.teammate = player1;
        } else if (move.player.isGreen()) {
            player1.color = 'R';
            player1.teammate = player2;
            player2.color = 'Y';
            player2.teammate = player1;
        } else if (move.player.isBlue()) {
            player1.color = 'R';
            player1.teammate = player2;
            player2.color = 'Y';
            player2.teammate = player1;
        } else if (move.player.isYellow()) {
            player1.color = 'B';
            player1.teammate = player2;
            player2.color = 'G';
            player2.teammate = player1;
        }

        for (String[] coordinate : this.coordinates) {
            for (int j = 0; j < this.coordinates.length; j++) {
                String from = coordinate[j];
                pieceMove = from + to;
                Move move1 = new Move(player1, pieceMove);
                Move move2 = new Move(player2, pieceMove);
                int[] coordinates = board.getXYCoordinates(move1);

                if (board.getBox(coordinates[0], coordinates[1]).getPiece() != null) {
                    if (board.getBox(coordinates[0], coordinates[1]).getPiece().color == player1.color) {
                        if (board.getBox(coordinates[0], coordinates[1]).getPiece().isValidMove(move1, board)) {
                            return true;
                        }
                    } else if (board.getBox(coordinates[0], coordinates[1]).getPiece().color == player2.color) {
                        if (board.getBox(coordinates[0], coordinates[1]).getPiece().isValidMove(move2, board)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
