package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class KnightTest {

    @Test
    public void testMoveKnightUp1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E6","G5/F7","G5/H7","G5/I6"};

        // Tests if the knight can move up (4 possible squares according to the rules)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(9, 6).getPiece().move(move1, board);
            int[] xy = board.getXYCoordinates(move1);
            Assert.assertEquals("N", board.getBox(xy[2], xy[3]).getPiece().p);
            Assert.assertNull(board.getBox(9, 6).getPiece());
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightUp2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E6","G5/F7","G5/H7","G5/I6"};

        // Tests if the knight captures opponent piece when moving up
        board.setBox(9,6,new Knight('B'));
        board.setBox(8,4,new Pawn('Y'));
        board.setBox(7,5,new Pawn('Y'));
        board.setBox(7,7,new Pawn('Y'));
        board.setBox(8,8,new Pawn('Y'));

        for (String coordinate : coordinates) {
            Move move2 = new Move(player1, coordinate);
            board.getBox(9, 6).getPiece().move(move2, board);
            int[] xy = board.getXYCoordinates(move2);
            Assert.assertEquals("N", board.getBox(xy[2], xy[3]).getPiece().p);
            Assert.assertNull(board.getBox(9, 6).getPiece());
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightUp3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E6","G5/F7","G5/H7","G5/I6"};

        // Tests if the knight can move up when the square is blocked by a teammates piece
        board.setBox(9,6,new Knight('B'));
        board.setBox(8,4,new Pawn('G'));
        board.setBox(7,5,new Pawn('G'));
        board.setBox(7,7,new Pawn('G'));
        board.setBox(8,8,new Pawn('G'));

        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move3, board));
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightUp4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E6","G5/F7","G5/H7","G5/I6"};

        // Tests if the knight can move up when the square is blocked by a piece of the same color
        board.setBox(9,6,new Knight('B'));
        board.setBox(8,4,new Pawn('B'));
        board.setBox(7,5,new Pawn('B'));
        board.setBox(7,7,new Pawn('B'));
        board.setBox(8,8,new Pawn('B'));

        for (String coordinate : coordinates) {
            Move move4 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move4, board));
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightDown1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E4","G5/F3","G5/H3","G5/I4"};

        // Tests if the knight can move down (4 possible squares according to the rules)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(9, 6).getPiece().move(move1, board);
            int[] xy = board.getXYCoordinates(move1);
            Assert.assertEquals("N", board.getBox(xy[2], xy[3]).getPiece().p);
            Assert.assertNull(board.getBox(9, 6).getPiece());
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightDown2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E4","G5/F3","G5/H3","G5/I4"};

        // Tests if the knight captures opponent piece when moving down
        board.setBox(9,6,new Knight('B'));
        board.setBox(10,4,new Pawn('Y'));
        board.setBox(11,5,new Pawn('Y'));
        board.setBox(11,7,new Pawn('Y'));
        board.setBox(10,8,new Pawn('Y'));

        for (String coordinate : coordinates) {
            Move move2 = new Move(player1, coordinate);
            board.getBox(9, 6).getPiece().move(move2, board);
            int[] xy = board.getXYCoordinates(move2);
            Assert.assertEquals("N", board.getBox(xy[2], xy[3]).getPiece().p);
            Assert.assertNull(board.getBox(9, 6).getPiece());
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightDown3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E4","G5/F3","G5/H3","G5/I4"};

        // Tests if the knight can move down when the square is blocked by a teammates piece
        board.setBox(9,6,new Knight('B'));
        board.setBox(10,4,new Pawn('G'));
        board.setBox(11,5,new Pawn('G'));
        board.setBox(11,7,new Pawn('G'));
        board.setBox(10,8,new Pawn('G'));

        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move3, board));
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testMoveKnightDown4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        String[] coordinates = {"G5/E4","G5/F3","G5/H3","G5/I4"};

        // Tests if the knight can move down when the square is blocked by a piece of the same color
        board.setBox(9,6,new Knight('B'));
        board.setBox(10,4,new Pawn('B'));
        board.setBox(11,5,new Pawn('B'));
        board.setBox(11,7,new Pawn('B'));
        board.setBox(10,8,new Pawn('B'));

        for (String coordinate : coordinates) {
            Move move4 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move4, board));
            board.setBox(9, 6, new Knight('B'));
        }
    }

    @Test
    public void testPutKingInCheck1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(3,6,new Rook('R'));

        // Tests if the knight moves, knowing it will put the king in check
        Move move1 = new Move(player1,"G6/H8");
        Assert.assertTrue(board.getBox(8,6).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(7,7).getPiece());
        Assert.assertEquals("N", board.getBox(8, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheck2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Knight('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the knight moves, knowing it will not put the king in check
        board.setBox(3,7,new Rook('R'));
        Move move2 = new Move(player1,"G6/H8");
        board.getBox(8,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(6,7).getPiece());
        Assert.assertEquals("N", board.getBox(6, 7).getPiece().p);
        Assert.assertNull(board.getBox(8,6).getPiece());
    }

    @Test
    public void testInvalidMoves1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if all invalid square coordinates (corner squares) are recognized as invalid
        String[] coordinates = {"G5/A14","G5/A13","G5/A12","G5/B14","G5/B13","G5/B12","G5/C14","G5/C13","G5/C12",
                "G5/L14","G5/L13","G5/L12","G5/M14","G5/M13","G5/M12","G5/N14","G5/N13","G5/N12",
                "G5/A3","G5/A2","G5/A1","G5/B3","G5/B2","G5/B1","G5/C3","G5/C2","G5/C1",
                "G5/L3","G5/L2","G5/L1","G5/M3","G5/M2","G5/M1","G5/N3","G5/N2","G5/N1"};

        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }

    @Test
    public void testInvalidMoves2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if unreachable positions (according to the rules of the knight) are recognized as invalid
        String[] coordinates2 = {"G5/I8","G5/E6","G5/F11","G5/J10"};
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }
}