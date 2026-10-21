package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class ChessBoardTest {

    @Test
    public void testAmountOfSquares1() {
        ChessBoard board = new ChessBoard();

        // Test if the square array has the right dimensions
        Assert.assertTrue(board.boxes.length == 14 && board.boxes[0].length == 14);
    }

    @Test
    public void testAmountOfSquares2() {
        ChessBoard board = new ChessBoard();

        // Test if the square array has 196 squares in total
        int count = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                count++;
            }
        }
        Assert.assertEquals(count,196);
    }

    @Test
    public void testAmountOfPlayableSquares() {
        ChessBoard board = new ChessBoard();

        // Test if the board has 160 playable (valid) squares
        int count = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (board.boxes[i][j].valid) {
                    count++;
                }
            }
        }
        Assert.assertEquals(count, 160);
    }

    @Test
    public void testAmountOfInvalidSquares() {
        ChessBoard board = new ChessBoard();

        // Test if the board has 36 invalid squares
        int count = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (!board.boxes[i][j].valid) {
                    count++;
                }
            }
        }
        Assert.assertEquals(count, 36);
    }

    @Test
    public void testAmountOfRedPieces() {
        ChessBoard board = new ChessBoard();

        // Tests if there are 16 red pieces
        int countRed = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (board.boxes[i][j].getPiece() != null && board.boxes[i][j].getPiece().color == 'R') {
                    countRed++;
                }
            }
        }
        Assert.assertEquals(countRed, 16);

        Assert.assertEquals('R', board.boxes[13][3].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][4].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][5].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][6].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][7].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][8].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][9].getPiece().color);
        Assert.assertEquals('R', board.boxes[13][10].getPiece().color);

        Assert.assertEquals('R', board.boxes[12][3].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][4].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][5].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][6].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][7].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][8].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][9].getPiece().color);
        Assert.assertEquals('R', board.boxes[12][10].getPiece().color);
    }

    @Test
    public void testAmountOfBluePieces() {
        ChessBoard board = new ChessBoard();

        // Tests if there are 16 blue pieces
        int countBlue = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (board.boxes[i][j].getPiece() != null && board.boxes[i][j].getPiece().color == 'B') {
                    countBlue++;
                }
            }
        }
        Assert.assertEquals(countBlue, 16);

        Assert.assertEquals('B', board.boxes[3][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[4][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[5][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[6][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[7][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[8][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[9][0].getPiece().color);
        Assert.assertEquals('B', board.boxes[10][0].getPiece().color);

        Assert.assertEquals('B', board.boxes[3][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[4][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[5][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[6][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[7][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[8][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[9][1].getPiece().color);
        Assert.assertEquals('B', board.boxes[10][1].getPiece().color);
    }

    @Test
    public void testAmountOfYellowPieces() {
        ChessBoard board = new ChessBoard();

        // Tests if there are 16 blue pieces
        int countYellow = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (board.boxes[i][j].getPiece() != null && board.boxes[i][j].getPiece().color == 'B') {
                    countYellow++;
                }
            }
        }
        Assert.assertEquals(countYellow, 16);

        Assert.assertEquals(board.boxes[0][3].getPiece().color, 'Y');
        Assert.assertEquals('Y', board.boxes[0][4].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][5].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][6].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][7].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][8].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][9].getPiece().color);
        Assert.assertEquals('Y', board.boxes[0][10].getPiece().color);

        Assert.assertEquals('Y', board.boxes[1][3].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][4].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][5].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][6].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][7].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][8].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][9].getPiece().color);
        Assert.assertEquals('Y', board.boxes[1][10].getPiece().color);
    }

    @Test
    public void testAmountOfGreenPieces() {
        ChessBoard board = new ChessBoard();

        // Tests if there are 16 blue pieces
        int countGreen = 0;
        for (int i = 0; i < board.boxes[0].length; i++) {
            for (int j = 0; j < board.boxes.length; j++) {
                if (board.boxes[i][j].getPiece() != null && board.boxes[i][j].getPiece().color == 'B') {
                    countGreen++;
                }
            }
        }
        Assert.assertEquals(countGreen, 16);

        Assert.assertEquals('G', board.boxes[3][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[4][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[5][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[6][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[7][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[8][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[9][13].getPiece().color);
        Assert.assertEquals('G', board.boxes[10][13].getPiece().color);

        Assert.assertEquals('G', board.boxes[3][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[4][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[5][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[6][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[7][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[8][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[9][12].getPiece().color);
        Assert.assertEquals('G', board.boxes[10][12].getPiece().color);
    }

}