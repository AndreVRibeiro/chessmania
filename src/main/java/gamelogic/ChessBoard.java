package gamelogic;

import gui.GameGUI;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class creates a chessboard that consists of squares and can take chess pieces.
 */
public class ChessBoard extends GameGUI {
    public Square[][] boxes;
    private final String[] numberAxis = {"14", "13", "12", "11", "10", " 9", " 8", " 7", " 6", " 5", " 4", " 3", " 2", " 1"};
    private final String[] characterAxis = {"   ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
    private int[][] chessBoard;
    private final String[] invalidSquares = {"A14", "A13", "A12", "B14", "B13", "B12", "C14", "C13", "C12",
            "L14", "L13", "L12", "M14", "M13", "M12", "N14", "N13", "N12",
            "A3", "A2", "A1", "B3", "B2", "B1", "C3", "C2", "C1",
            "L3", "L2", "L1", "M3", "M2", "M1", "N3", "N2", "C1",};
    private final String[][] coordinates = {{"A14", "B14", "C14", "D14", "E14", "F14", "G14", "H14", "I14", "J14", "K14", "L14", "M14", "N14"},
            {"A13", "B13", "C13", "D13", "E13", "F13", "G13", "H13", "I13", "J13", "K13", "L13", "M13", "N13"},
            {"A12", "B12", "C12", "D12", "E12", "F12", "G12", "H12", "I12", "J12", "K12", "L12", "M12", "N12"},
            {"A11", "B11", "C11", "D11", "E11", "F11", "G11", "H11", "I11", "J11", "K11", "L11", "M11", "N11"},
            {"A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "I10", "J10", "K10", "L10", "M10", "N10"},
            {"A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "K9", "L9", "M9", "N9"},
            {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "K8", "L8", "M8", "N8"},
            {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "K7", "L7", "M7", "N7"},
            {"A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "I6", "J6", "K6", "L6", "M6", "N6"},
            {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "K5", "L5", "M5", "N5"},
            {"A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "I4", "J4", "K4", "L4", "M4", "N4"},
            {"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "I3", "J3", "K3", "L3", "M3", "N3"},
            {"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "I2", "J2", "K2", "L2", "M2", "N2"},
            {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1"}};


    /**
     * This is the constructor of the class ChessBoard
     */
    public ChessBoard() {
        this.boxes = new Square[14][14];

        this.clearBoard();

        // Setting all unnecessary Squares as not valid
        this.boxes[0][0] = new Square(null, 0, 0, false);
        this.boxes[0][1] = new Square(null, 0, 1, false);
        this.boxes[0][2] = new Square(null, 0, 2, false);
        this.boxes[1][0] = new Square(null, 1, 0, false);
        this.boxes[1][1] = new Square(null, 1, 1, false);
        this.boxes[1][2] = new Square(null, 1, 2, false);
        this.boxes[2][0] = new Square(null, 2, 0, false);
        this.boxes[2][1] = new Square(null, 2, 1, false);
        this.boxes[2][2] = new Square(null, 2, 2, false);

        this.boxes[11][0] = new Square(null, 11, 0, false);
        this.boxes[11][1] = new Square(null, 11, 1, false);
        this.boxes[11][2] = new Square(null, 11, 2, false);
        this.boxes[12][0] = new Square(null, 12, 0, false);
        this.boxes[12][1] = new Square(null, 12, 1, false);
        this.boxes[12][2] = new Square(null, 12, 2, false);
        this.boxes[13][0] = new Square(null, 13, 0, false);
        this.boxes[13][1] = new Square(null, 13, 1, false);
        this.boxes[13][2] = new Square(null, 13, 2, false);

        this.boxes[0][11] = new Square(null, 0, 11, false);
        this.boxes[0][12] = new Square(null, 0, 12, false);
        this.boxes[0][13] = new Square(null, 0, 13, false);
        this.boxes[1][11] = new Square(null, 1, 11, false);
        this.boxes[1][12] = new Square(null, 1, 12, false);
        this.boxes[1][13] = new Square(null, 1, 13, false);
        this.boxes[2][11] = new Square(null, 2, 11, false);
        this.boxes[2][12] = new Square(null, 2, 12, false);
        this.boxes[2][13] = new Square(null, 2, 13, false);

        this.boxes[11][11] = new Square(null, 11, 11, false);
        this.boxes[11][12] = new Square(null, 11, 12, false);
        this.boxes[11][13] = new Square(null, 11, 13, false);
        this.boxes[12][11] = new Square(null, 12, 11, false);
        this.boxes[12][12] = new Square(null, 12, 12, false);
        this.boxes[12][13] = new Square(null, 12, 13, false);
        this.boxes[13][11] = new Square(null, 13, 11, false);
        this.boxes[13][12] = new Square(null, 13, 12, false);
        this.boxes[13][13] = new Square(null, 13, 13, false);

        this.newGameBoard();
    }

    /**
     * This is also a constructor for the Chessboard class.
     * @param boardSetup The setup that is to be created.
     */
    public ChessBoard(int boardSetup) { // boardSetup 0 = empty board, 1 = start of the game
        this.boxes = new Square[14][14];

        this.clearBoard();

        // Setting all unnecessary Squares as not valid
        this.boxes[0][0] = new Square(null, 0, 0, false);
        this.boxes[0][1] = new Square(null, 0, 1, false);
        this.boxes[0][2] = new Square(null, 0, 2, false);
        this.boxes[1][0] = new Square(null, 1, 0, false);
        this.boxes[1][1] = new Square(null, 1, 1, false);
        this.boxes[1][2] = new Square(null, 1, 2, false);
        this.boxes[2][0] = new Square(null, 2, 0, false);
        this.boxes[2][1] = new Square(null, 2, 1, false);
        this.boxes[2][2] = new Square(null, 2, 2, false);

        this.boxes[11][0] = new Square(null, 11, 0, false);
        this.boxes[11][1] = new Square(null, 11, 1, false);
        this.boxes[11][2] = new Square(null, 11, 2, false);
        this.boxes[12][0] = new Square(null, 12, 0, false);
        this.boxes[12][1] = new Square(null, 12, 1, false);
        this.boxes[12][2] = new Square(null, 12, 2, false);
        this.boxes[13][0] = new Square(null, 13, 0, false);
        this.boxes[13][1] = new Square(null, 13, 1, false);
        this.boxes[13][2] = new Square(null, 13, 2, false);

        this.boxes[0][11] = new Square(null, 0, 11, false);
        this.boxes[0][12] = new Square(null, 0, 12, false);
        this.boxes[0][13] = new Square(null, 0, 13, false);
        this.boxes[1][11] = new Square(null, 1, 11, false);
        this.boxes[1][12] = new Square(null, 1, 12, false);
        this.boxes[1][13] = new Square(null, 1, 13, false);
        this.boxes[2][11] = new Square(null, 2, 11, false);
        this.boxes[2][12] = new Square(null, 2, 12, false);
        this.boxes[2][13] = new Square(null, 2, 13, false);

        this.boxes[11][11] = new Square(null, 11, 11, false);
        this.boxes[11][12] = new Square(null, 11, 12, false);
        this.boxes[11][13] = new Square(null, 11, 13, false);
        this.boxes[12][11] = new Square(null, 12, 11, false);
        this.boxes[12][12] = new Square(null, 12, 12, false);
        this.boxes[12][13] = new Square(null, 12, 13, false);
        this.boxes[13][11] = new Square(null, 13, 11, false);
        this.boxes[13][12] = new Square(null, 13, 12, false);
        this.boxes[13][13] = new Square(null, 13, 13, false);

        if (boardSetup == 0) {
            // empty board
        }

        if (boardSetup == 1) {
            this.newGameBoard();
        }
    }

    /**
     * This method clears the whole chessboard.
     */
    private void clearBoard() {
        // Setting all Squares as empty
        for (int i = 0; i <= 13; i++) {
            for (int j = 0; j <= 13; j++) {
                this.boxes[i][j] = new Square(null, i, j, true);
            }
        }
    }


    /**
     * This method encodes the chessboard in order to be able to send the status of a board
     * to the client via network protocol. It iterates over each square in a set order and
     * sums up following empty squares as an integer and pieces on a square as "rK" for the
     * red players king for example. A new row gets signaled by a "/".
     *
     * @return returns the encoded chessboard as string, ready to be sent to client.
     */
    public String encode() {
        StringBuilder str = new StringBuilder();

        int spaceCounter = 0;
        for (int i = 0; i <= 13; i++) {
            for (int j = 0; j <= 13; j++) {
                if (this.boxes[i][j].valid) {
                    if (this.boxes[i][j].isSquareOccupied()) {
                        if (spaceCounter != 0) {
                            str.append(spaceCounter);
                            spaceCounter = 0;
                        }
                        str.append(this.boxes[i][j].getPiece().color).append(this.boxes[i][j].getPiece().p);
                    } else {
                        ++spaceCounter;
                    }
                }
            }
//            if (spaceCounter == 8 || spaceCounter == 14) {
            if (spaceCounter != 0) {
                str.append(spaceCounter);
                spaceCounter = 0;
            }
            str.append("/");
        }
        return str.toString();
    }

    /**
     * This function is used to decode the chessboard.
     * @param str The string that needs to be decoded.
     */
    public void decode(String str) {
//        System.out.println(str);
//        this.clearBoard();

        Queue<Character> queue = new LinkedList<>();

        for (int i = 0; i < str.length(); i++) {
            queue.add(str.charAt(i));
        }


//        while (queue.peek() != null) {
//            int squareCounter = 0;
        char currentChar = 0;
        int spaceCounter = 0;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (this.boxes[i][j].valid) {
                    if (spaceCounter == 0) {
                        currentChar = queue.remove();
                        if (currentChar == '/') {
                            try {
                                currentChar = queue.remove();
                            } catch (Exception e) {
                                System.out.println("end of encoded string");
                                return;
                            }
                            if (queue.peek() == null) {
                                return;
                            }
                        }
                    }
                    if (Character.isDigit(currentChar)) {
                        String digit;

                        char nextDigit;
                        try {
                            nextDigit = queue.peek();
                            if (Character.isDigit(nextDigit)) { // more than 9 spaces
                                digit = "" + currentChar + queue.peek();
                                queue.remove();
                            } else {
                                digit = "" + currentChar;
                            }
                            if (spaceCounter == 0) {
                                spaceCounter = Integer.parseInt(digit);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("thats where its going wrong");
                        }

                        if (spaceCounter > 0) {
                            this.boxes[i][j].setSquareEmpty();
                            --spaceCounter;
                        }
                    } else {
                        // set chess piece
                        char color = currentChar;
                        char piece;
                        piece = queue.remove();
                        this.boxes[i][j].setPiece(color, piece);
                    }
                }
            }
        }
    }



    /**
     * This method creates a new Chessboard with all 4 colored Figure Sets. All the figures are located at their
     * starting positions. The Squares that are not part of the board are declared as not valid.
     */
    public void newGameBoard() {
        // Setting the yellow pieces on the Squares of the board (TOP)
        this.boxes[0][3] = new Square(new Rook('Y'), 0, 3, true);
        this.boxes[0][4] = new Square(new Knight('Y'), 0, 4, true);
        this.boxes[0][5] = new Square(new Bishop('Y'), 0, 5, true);
        this.boxes[0][6] = new Square(new King('Y'), 0, 6, true);
        this.boxes[0][7] = new Square(new Queen('Y'), 0, 7, true);
        this.boxes[0][8] = new Square(new Bishop('Y'), 0, 8, true);
        this.boxes[0][9] = new Square(new Knight('Y'), 0, 9, true);
        this.boxes[0][10] = new Square(new Rook('Y'), 0, 10, true);

        this.boxes[1][3] = new Square(new Pawn('Y'), 1, 3, true);
        this.boxes[1][4] = new Square(new Pawn('Y'), 1, 4, true);
        this.boxes[1][5] = new Square(new Pawn('Y'), 1, 5, true);
        this.boxes[1][6] = new Square(new Pawn('Y'), 1, 6, true);
        this.boxes[1][7] = new Square(new Pawn('Y'), 1, 7, true);
        this.boxes[1][8] = new Square(new Pawn('Y'), 1, 8, true);
        this.boxes[1][9] = new Square(new Pawn('Y'), 1, 9, true);
        this.boxes[1][10] = new Square(new Pawn('Y'), 1, 10, true);

        // Setting the blue pieces on the Squares of the board (LEFT)
        this.boxes[3][0] = new Square(new Rook('B'), 3, 0, true);
        this.boxes[4][0] = new Square(new Knight('B'), 4, 0, true);
        this.boxes[5][0] = new Square(new Bishop('B'), 5, 0, true);
        this.boxes[6][0] = new Square(new King('B'), 6, 0, true);
        this.boxes[7][0] = new Square(new Queen('B'), 7, 0, true);
        this.boxes[8][0] = new Square(new Bishop('B'), 8, 0, true);
        this.boxes[9][0] = new Square(new Knight('B'), 9, 0, true);
        this.boxes[10][0] = new Square(new Rook('B'), 10, 0, true);

        this.boxes[3][1] = new Square(new Pawn('B'), 3, 1, true);
        this.boxes[4][1] = new Square(new Pawn('B'), 4, 1, true);
        this.boxes[5][1] = new Square(new Pawn('B'), 5, 1, true);
        this.boxes[6][1] = new Square(new Pawn('B'), 6, 1, true);
        this.boxes[7][1] = new Square(new Pawn('B'), 7, 1, true);
        this.boxes[8][1] = new Square(new Pawn('B'), 8, 1, true);
        this.boxes[9][1] = new Square(new Pawn('B'), 9, 1, true);
        this.boxes[10][1] = new Square(new Pawn('B'), 10, 1, true);

        // Setting the green pieces on the Squares of the board (RIGHT)
        this.boxes[3][13] = new Square(new Rook('G'), 3, 13, true);
        this.boxes[4][13] = new Square(new Knight('G'), 4, 13, true);
        this.boxes[5][13] = new Square(new Bishop('G'), 5, 13, true);
        this.boxes[6][13] = new Square(new Queen('G'), 6, 13, true);
        this.boxes[7][13] = new Square(new King('G'), 7, 13, true);
        this.boxes[8][13] = new Square(new Bishop('G'), 8, 13, true);
        this.boxes[9][13] = new Square(new Knight('G'), 9, 13, true);
        this.boxes[10][13] = new Square(new Rook('G'), 10, 13, true);

        this.boxes[3][12] = new Square(new Pawn('G'), 3, 12, true);
        this.boxes[4][12] = new Square(new Pawn('G'), 4, 12, true);
        this.boxes[5][12] = new Square(new Pawn('G'), 5, 12, true);
        this.boxes[6][12] = new Square(new Pawn('G'), 6, 12, true);
        this.boxes[7][12] = new Square(new Pawn('G'), 7, 12, true);
        this.boxes[8][12] = new Square(new Pawn('G'), 8, 12, true);
        this.boxes[9][12] = new Square(new Pawn('G'), 9, 12, true);
        this.boxes[10][12] = new Square(new Pawn('G'), 10, 12, true);

        // Setting the red pieces on the Squares of the board (BOTTOM)
        this.boxes[13][3] = new Square(new Rook('R'), 13, 3, true);
        this.boxes[13][4] = new Square(new Knight('R'), 13, 4, true);
        this.boxes[13][5] = new Square(new Bishop('R'), 13, 5, true);
        this.boxes[13][6] = new Square(new Queen('R'), 13, 6, true);
        this.boxes[13][7] = new Square(new King('R'), 13, 7, true);
        this.boxes[13][8] = new Square(new Bishop('R'), 13, 8, true);
        this.boxes[13][9] = new Square(new Knight('R'), 13, 9, true);
        this.boxes[13][10] = new Square(new Rook('R'), 13, 10, true);

        this.boxes[12][3] = new Square(new Pawn('R'), 12, 3, true);
        this.boxes[12][4] = new Square(new Pawn('R'), 12, 4, true);
        this.boxes[12][5] = new Square(new Pawn('R'), 12, 5, true);
        this.boxes[12][6] = new Square(new Pawn('R'), 12, 6, true);
        this.boxes[12][7] = new Square(new Pawn('R'), 12, 7, true);
        this.boxes[12][8] = new Square(new Pawn('R'), 12, 8, true);
        this.boxes[12][9] = new Square(new Pawn('R'), 12, 9, true);
        this.boxes[12][10] = new Square(new Pawn('R'), 12, 10, true);
    }


    /**
     * This method prints out the current state of the chessboard on to the console.
     */
    public void print() {
        for (int i = 0; i < 15; i++) {
            System.out.print("  " + this.characterAxis[i] + "  ");
        }

        System.out.println();
        System.out.println();

        for (int i = 0; i < 14; i++) {
            System.out.print(" " + this.numberAxis[i] + "    ");
            for (int j = 0; j < 14; j++) {
                if (!this.boxes[i][j].valid) {
                    System.out.print("     ");
                } else if (this.boxes[i][j].getPiece() == null) {
                    System.out.print("  .  ");
                } else if (this.boxes[i][j].getPiece() != null) {
                    if (this.boxes[i][j].getPiece().color == 'R') {
                        System.out.print(" " + this.boxes[i][j].getPiece().color + "-" + this.boxes[i][j].getPiece().p + " ");
                    } else if (this.boxes[i][j].getPiece().color == 'B') {
                        System.out.print(" " + this.boxes[i][j].getPiece().color + "*" + this.boxes[i][j].getPiece().p + " ");
                    } else if (this.boxes[i][j].getPiece().color == 'Y') {
                        System.out.print(" " + this.boxes[i][j].getPiece().color + "+" + this.boxes[i][j].getPiece().p + " ");
                    } else if (this.boxes[i][j].getPiece().color == 'G') {
                        System.out.print(" " + this.boxes[i][j].getPiece().color + ":" + this.boxes[i][j].getPiece().p + " ");
                    }
                }
            }
            System.out.print("    " + this.numberAxis[i]);
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < 15; i++) {
            System.out.print("  " + this.characterAxis[i] + "  ");
        }
        System.out.println();
    }

    /**
     * This function returns a specific Square of the Chessboard
     *
     * @param x The x coordinate of the square's location
     * @param y The y coordinate of the square's location
     * @return Returns the square
     */
    public Square getBox(int x, int y) {
        return boxes[x][y];
    }

    /**
     * This function allows to set a Piece on a specific square
     *
     * @param x     The x coordinate of the square's location
     * @param y     The y coordinate of the square's location
     * @param piece The piece
     */
    public void setBox(int x, int y, Piece piece) {
        this.boxes[x][y].setPiece(piece);
    }

    public Piece getPiece(int x, int y) {
        return this.boxes[x][y].getPiece();
    }


    /**
     * This function checks gets a piece string from the called coordinate of the chessboard.
     * It gets a string in the form of "RK" for a red king in example.
     * @param x
     * @param y
     * @return
     */
    public String getPieceStr(int x, int y) {
        return this.boxes[x][y].getPieceStr();
    }

    /**
     * This function transforms the players move into x and y coordinates
     *
     * @param move The move that the player wants to make
     * @return Returns an int array containing the x and y coordinates of the old position and
     * the x and y coordinates of the new position
     */
    public int[] getXYCoordinates(Move move) {
        int old_x = 0;
        int old_y = 0;
        int new_x = 0;
        int new_y = 0;

        int indexOfSeparation = move.coordinates.indexOf('/');
        String from = move.coordinates.substring(0, indexOfSeparation);

        for (int i = 0; i < this.coordinates.length; i++) {
            for (int j = 0; j < this.coordinates.length; j++) {
                if (this.coordinates[i][j].equals(from)) {
                    old_x = i;
                    old_y = j;
                }
            }
        }

        String to = move.coordinates.substring(indexOfSeparation + 1);

        for (int i = 0; i < this.coordinates.length; i++) {
            for (int j = 0; j < this.coordinates.length; j++) {
                if (this.coordinates[i][j].equals(to)) {
                    new_x = i;
                    new_y = j;
                }
            }
        }

        int[] moveCoordinates = {old_x, old_y, new_x, new_y};
        return moveCoordinates;
    }





}
