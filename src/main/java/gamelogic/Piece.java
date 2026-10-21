package gamelogic;

/**
 * This is the abstract Piece class.
 */
public abstract class Piece {

    char color;
    String p;
    boolean isKilled = false;
    boolean attack = false;
    boolean hasMoved = false;
    public boolean promotedPawn = false;

    public String[] invalidSquares = {"A14","A13","A12","B14","B13","B12","C14","C13","C12",
            "L14","L13","L12","M14","M13","M12","N14","N13","N12",
            "A3","A2","A1","B3","B2","B1","C3","C2","C1",
            "L3","L2","L1","M3","M2","M1","N3","N2","N1",};

    public String[][] coordinates = {{"A14","B14","C14","D14","E14","F14","G14","H14","I14","J14","K14","L14","M14","N14"},
            {"A13","B13","C13","D13","E13","F13","G13","H13","I13","J13","K13","L13","M13","N13"},
            {"A12","B12","C12","D12","E12","F12","G12","H12","I12","J12","K12","L12","M12","N12"},
            {"A11","B11","C11","D11","E11","F11","G11","H11","I11","J11","K11","L11","M11","N11"},
            {"A10","B10","C10","D10","E10","F10","G10","H10","I10","J10","K10","L10","M10","N10"},
            {"A9","B9","C9","D9","E9","F9","G9","H9","I9","J9","K9","L9","M9","N9"},
            {"A8","B8","C8","D8","E8","F8","G8","H8","I8","J8","K8","L8","M8","N8"},
            {"A7","B7","C7","D7","E7","F7","G7","H7","I7","J7","K7","L7","M7","N7"},
            {"A6","B6","C6","D6","E6","F6","G6","H6","I6","J6","K6","L6","M6","N6"},
            {"A5","B5","C5","D5","E5","F5","G5","H5","I5","J5","K5","L5","M5","N5"},
            {"A4","B4","C4","D4","E4","F4","G4","H4","I4","J4","K4","L4","M4","N4"},
            {"A3","B3","C3","D3","E3","F3","G3","H3","I3","J3","K3","L3","M3","N3"},
            {"A2","B2","C2","D2","E2","F2","G2","H2","I2","J2","K2","L2","M2","N2"},
            {"A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1","L1","M1","N1"}};

    public int[][] numbers = {{182,183,184,185,186,187,188,189,190,191,192,193,194,195},
                                {168,169,170,171,172,173,174,175,176,177,178,179,180,181},
                                {154,155,156,157,158,159,160,161,162,163,164,165,166,167},
                                {140,141,142,143,144,145,146,147,148,149,150,151,152,153},
                                {126,127,128,129,130,131,132,133,134,135,136,137,138,139},
                                {112,113,114,115,116,117,118,119,120,121,122,123,124,125},
                                {98,99,100,101,102,103,104,105,106,107,108,109,110,111},
                                {84,85,86,87,88,89,90,91,92,93,94,95,96,97},
                                {70,71,72,73,74,75,76,77,78,79,80,81,82,83},
                                {56,57,58,59,60,61,62,63,64,65,66,67,68,69},
                                {42,43,44,45,46,47,48,49,50,51,52,53,54,55},
                                {28,29,30,31,32,33,34,35,36,37,38,39,40,41},
                                {14,15,16,17,18,19,20,21,22,23,24,25,26,27},
                                {0,1,2,3,4,5,6,7,8,9,10,11,12,13}};

    public int[] invalidNumbers = {0,1,2,11,12,13,14,15,16,28,29,30,39,40,41,54,55,56,65,66,67,68,69,70,79,80,81,82,83,84,93,94,95};

    /**
     * This is the constructor of the class Piece
     */
    Piece() {
        this.p = ".";
    }


    /**
     * This method returns a string containing the color and the identifier (letter that symbolizes the type of piece).
     * @return The color and type of piece.
     */
    public String getPieceStr() {
        if (p == null) {
            return "null";
        }
        return color + p;
    }


    /**
     * This abstract function checks if the player's Piece Move is valid.
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     * @return Returns true if valid, false if not
     */
    public abstract boolean isValidMove(Move move, ChessBoard board);

    /**
     * This abstract function moves the Piece after checking if the Move is Valid
     * @param move The move that the player wants to make
     * @param board The board that is being played on
     */
    public abstract void move(Move move, ChessBoard board);


    /**
     * This function transforms the players String commands into x and y coordinates
     * that can be used to move and check movements
     * @param move The players commands
     * @return Returns an array of old and new coordinates
     */
    public int[] getXYCoordinates(Move move){
        int old_x = 0;
        int old_y = 0;
        int new_x = 0;
        int new_y = 0;

        int indexOfSeperation = move.coordinates.indexOf('/');
        String from = move.coordinates.substring(0,indexOfSeperation);

        for (int i = 0; i < this.coordinates.length; i++) {
            for (int j = 0; j < this.coordinates.length; j++) {
                if (this.coordinates[i][j].equals(from)) {
                    old_x = i;
                    old_y = j;
                }
            }
        }

        String to = move.coordinates.substring(indexOfSeperation+1);

        for (int i = 0; i < this.coordinates.length; i++) {
            for (int j = 0; j < this.coordinates.length; j++) {
                if (this.coordinates[i][j].equals(to)) {
                    new_x = i;
                    new_y = j;
                }
            }
        }

        int[] moveCoordinates = {old_x,old_y,new_x,new_y};
        return moveCoordinates;

    }

    /**
     * This function returns the x and y coordinates of the numbers in the numbers array
     * @param number The numbers which coordinate is to be extracted
     * @return The x and y coordinates of the number in the numbers array
     */
    public int[] getXYFromNumbers(int number) {

        int x = 0;
        int y = 0;

        for (int i = 0; i < this.numbers.length; i++) {
            for (int j = 0; j < this.numbers.length; j++) {
                if (this.numbers[i][j] == number) {
                    x = i;
                    y = j;
                }
            }
        }
        int[] array = {x,y};
        return array;
    }

    /**
     * This function tells if the given move contains an illegal Square
     * @param move The move that the client wants to do (D2/D3)
     * @return Returns true if the Squares are valid, False if not
     */
    public boolean areValidSquares(Move move) {
        int indexOfSeparation = move.coordinates.indexOf('/');
        String from = move.coordinates.substring(0,indexOfSeparation);

        for (int i = 0; i < this.invalidSquares.length; i++) {
            if (this.invalidSquares[i].equals(from)) {
                    return false;
            }
        }

        String to = move.coordinates.substring(indexOfSeparation+1);

        for (int i = 0; i < this.invalidSquares.length; i++) {
            if (this.invalidSquares[i].equals(to)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks if after moving a piece, the players king gets put in a check position by
     * the opponent's pieces. This function is used in the move function of the pieces.
     * @param move The move that the client wants to do. Needed in order to use its variables.
     * @param board The board where all the pieces are located.
     * @return true if moving puts the king in a check position, false if not
     */
    public boolean putsKingInCheck(Move move, ChessBoard board) {
        int x = 0;
        int y = 0;

        // This loop finds the position of the king that belongs to the player who made a move and
        // puts the coordinates in x and y
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (board.getBox(i,j).getPiece() != null && board.getBox(i,j).getPiece().p.equals("K") &&
                        board.getBox(i,j).getPiece().color == move.player.color) {
                    x = i;
                    y = j;
                }
            }
        }

        String kingPosition = "/" + this.coordinates[x][y];
        String pieceMove = "";

        Player player1 = new Player();
        Player player2 = new Player();

        // This sets player1 and player2 with the colors and teammates of the opponents
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

        // This loop checks if the opponents pieces are able to reach the position of the king
        for (int i = 0; i < this.coordinates.length; i++) {
            for (int j = 0; j < this.coordinates.length; j++) {
                String from = this.coordinates[i][j];
                pieceMove = from + kingPosition;
                Move move1 = new Move(player1,pieceMove);
                Move move2 = new Move(player2,pieceMove);
                int[] coordinates = board.getXYCoordinates(move1);

                if (board.getBox(coordinates[0],coordinates[1]).getPiece() != null) {
                    if (board.getBox(coordinates[0],coordinates[1]).getPiece().color == player1.color) {
                        if (board.getBox(coordinates[0],coordinates[1]).getPiece().isValidMove(move1,board) == true) {
                            return true;
                        }
                    } else if (board.getBox(coordinates[0],coordinates[1]).getPiece().color == player2.color) {
                        if (board.getBox(coordinates[0],coordinates[1]).getPiece().isValidMove(move2,board) == true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
