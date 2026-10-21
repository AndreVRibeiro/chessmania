package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class RookTest {

    @Test
    public void testMoveRookUp1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 5;
        String[] coordinates = {"F1/F2","F2/F3","F3/F4","F4/F5","F5/F6","F6/F7"};

        // Test if the rook can move up
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("R", board.getBox(x - 1, y).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
        }
    }

    @Test
    public void testMoveRookUp2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the rook can move up various squares at once
        Move move6 = new Move(player1,"F7/F14");
        board.getBox(7,5).getPiece().move(move6,board);
        Assert.assertEquals("R", board.getBox(0, 5).getPiece().p);
        Assert.assertNull(board.getBox(7,5).getPiece());
    }

    @Test
    public void testMoveRookUp3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook captures opponent piece when moving up
        board.setBox(13,5,new Rook('B'));
        board.setBox(12,5,new Pawn('R'));
        Move move2 = new Move(player1,"F1/F2");
        board.getBox(13,5).getPiece().move(move2,board);
        Assert.assertEquals("R", board.getBox(12, 5).getPiece().p);
        Assert.assertNull(board.getBox(13, 5).getPiece());
    }

    @Test
    public void testMoveRookUp4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move up when blocked by a teammates piece
        board.setBox(11,5,new Pawn('G'));
        Move move3 = new Move(player1,"F2/F3");
        Assert.assertFalse(board.getBox(12,5).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveRookUp5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move up when blocked by a piece of the same color
        board.setBox(11,5,new Pawn('B'));
        Move move4 = new Move(player1,"F2/F3");
        Assert.assertFalse(board.getBox(12,5).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveRookUp6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move past a different piece
        board.setBox(11,5,new Pawn('B'));
        Move move5 = new Move(player1,"F2/F5");
        Assert.assertFalse(board.getBox(12,5).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveRookDown1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 5;
        String[] coordinates = {"F14/F13","F13/F12","F12/F11","F11/F10","F10/F9","F9/F8"};

        // Test if the rook can move up
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("R", board.getBox(x + 1, y).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
        }
    }

    @Test
    public void testMoveRookDown2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the rook can move up various squares at once
        Move move6 = new Move(player1,"F8/F1");
        board.getBox(6,5).getPiece().move(move6,board);
        Assert.assertEquals("R", board.getBox(13, 5).getPiece().p);
        Assert.assertNull(board.getBox(6,5).getPiece());
    }

    @Test
    public void testMoveRookDown3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook captures opponent piece when moving up
        board.setBox(0,5,new Rook('B'));
        board.setBox(1,5,new Pawn('R'));
        Move move2 = new Move(player1,"F14/F13");
        board.getBox(0,5).getPiece().move(move2,board);
        Assert.assertEquals("R", board.getBox(1, 5).getPiece().p);
        Assert.assertNull(board.getBox(0, 5).getPiece());
    }

    @Test
    public void testMoveRookDown4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move up when blocked by a teammates piece
        board.setBox(2,5,new Pawn('G'));
        Move move3 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveRookDown5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move up when blocked by a piece of the same color
        board.setBox(2,5,new Pawn('B'));
        Move move4 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveRookDown6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move past a different piece
        board.setBox(2,5,new Pawn('B'));
        Move move5 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveRookLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,13,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 5;
        int y = 13;
        String[] coordinates = {"N9/M9","M9/L9","L9/K9","K9/J9","J9/I9","I9/H9"};

        // Test if the rook can move left
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("R", board.getBox(x, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            y--;
        }
    }

    @Test
    public void testMoveRookLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,7,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the rook can move left various squares at once
        Move move6 = new Move(player1,"H9/A9");
        board.getBox(5,7).getPiece().move(move6,board);
        Assert.assertEquals("R", board.getBox(5, 0).getPiece().p);
        Assert.assertNull(board.getBox(5,7).getPiece());
    }

    @Test
    public void testMoveRookLeft3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook captures opponent piece when moving left
        board.setBox(5,13,new Rook('B'));
        board.setBox(5,12,new Pawn('R'));
        Move move2 = new Move(player1,"N9/M9");
        board.getBox(5,13).getPiece().move(move2,board);
        Assert.assertEquals("R", board.getBox(5, 12).getPiece().p);
        Assert.assertNull(board.getBox(5, 13).getPiece());
    }

    @Test
    public void testMoveRookLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move left when blocked by a teammates piece
        board.setBox(5,11,new Pawn('G'));
        Move move3 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveRookLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move left when blocked by a piece of the same color
        board.setBox(5,11,new Pawn('B'));
        Move move4 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveRookLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move past a different piece
        board.setBox(5,11,new Pawn('B'));
        Move move5 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveRookRight() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,0,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 5;
        int y = 0;
        String[] coordinates = {"A9/B9","B9/C9","C9/D9","D9/E9","E9/F9","F9/G9"};

        // Test if the rook can move left
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("R", board.getBox(x, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            y++;
        }
    }

    @Test
    public void testMoveRookRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,6,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the rook can move left various squares at once
        Move move6 = new Move(player1,"G9/N9");
        board.getBox(5,6).getPiece().move(move6,board);
        Assert.assertEquals("R", board.getBox(5, 13).getPiece().p);
        Assert.assertNull(board.getBox(5,6).getPiece());
    }

    @Test
    public void testMoveRookRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook captures opponent piece when moving left
        board.setBox(5,0,new Rook('B'));
        board.setBox(5,1,new Pawn('R'));
        Move move2 = new Move(player1,"A9/B9");
        board.getBox(5,0).getPiece().move(move2,board);
        Assert.assertEquals("R", board.getBox(5, 1).getPiece().p);
        Assert.assertNull(board.getBox(5, 0).getPiece());
    }

    @Test
    public void testMoveRookRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move left when blocked by a teammates piece
        board.setBox(5,2,new Pawn('G'));
        Move move3 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveRookRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move left when blocked by a piece of the same color
        board.setBox(5,2,new Pawn('B'));
        Move move4 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveRookRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the rook can move past a different piece
        board.setBox(5,2,new Pawn('B'));
        Move move5 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testPutKingInCheck1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(3,6,new Rook('R'));

        // Tests if the bishop moves knowing it will put the king in check
        Move move1 = new Move(player1,"G6/H6");
        Assert.assertTrue(board.getBox(8,6).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(8,7).getPiece());
        Assert.assertEquals("R", board.getBox(8, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheck2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Rook('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop moves knowing it will not put the king in check
        board.setBox(3,7,new Rook('R'));
        Move move2 = new Move(player1,"G6/H6");
        board.getBox(8,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(8,7).getPiece());
        Assert.assertEquals("R", board.getBox(8, 7).getPiece().p);
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

        // Tests if unreachable positions (according to the rules of the rook) are recognized as invalid
        String[] coordinates2 = {"G5/I8","G5/E6","G5/F11","G5/J10"};
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }
}