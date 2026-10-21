package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class BishopTest {


    @Test
    public void testMoveBishopUpRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,5,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 5;
        String[] coordinates = {"F1/G2","G2/H3","H3/I4","I4/J5"};

        // Tests if the bishop can move up-right (diagonal)
        for (String coordinate : coordinates) {
            Move move = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move, board);
            Assert.assertEquals("B", board.getBox(x - 1, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
            y++;
        }
    }

    @Test
    public void testMoveBishopUpRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,9,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-right (diagonal) various squares at once
        Move move = new Move(player1,"J5/N9");
        board.getBox(9,9).getPiece().move(move,board);
        Assert.assertEquals("B", board.getBox(5, 13).getPiece().p);
        Assert.assertNull(board.getBox(9,9).getPiece());
    }

    @Test
    public void testMoveBishopUpRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop captures opponent piece when moving up-right (diagonal)
        board.setBox(13,5,new Bishop('B'));
        board.setBox(12,6,new Pawn('R'));
        Move move = new Move(player1,"F1/G2");
        board.getBox(13,5).getPiece().move(move,board);
        Assert.assertEquals("B", board.getBox(12, 6).getPiece().p);
        Assert.assertNull(board.getBox(13, 5).getPiece());
    }

    @Test
    public void testMoveBishopUpRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-right (diagonal) when blocked by a teammates piece
        board.setBox(11,7,new Pawn('G'));
        Move move = new Move(player1,"G2/H3");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testMoveBishopUpRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-right (diagonal) when blocked by a piece of the same color
        board.setBox(11,7,new Pawn('B'));
        Move move = new Move(player1,"G2/H3");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testMoveBishopUpRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move past a different piece
        board.setBox(11,7,new Pawn('B'));
        Move move = new Move(player1,"G2/J5");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testMoveBishopUpLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,8,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 8;
        String[] coordinates = {"I1/H2","H2/G3","G3/F4","F4/E5"};

        // Tests if the bishop can move up-left (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("B", board.getBox(x - 1, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
            y--;
        }
    }

    @Test
    public void testMoveBishopUpLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,4,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-left (diagonal) various squares at once
        Move move6 = new Move(player1,"E5/A9");
        board.getBox(9,4).getPiece().move(move6,board);
        Assert.assertEquals("B", board.getBox(5, 0).getPiece().p);
        Assert.assertNull(board.getBox(9,4).getPiece());
    }

    @Test
    public void testMoveBishopUpLeft3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop captures opponent piece when moving up-left (diagonal)
        board.setBox(13,8,new Bishop('B'));
        board.setBox(12,7,new Pawn('R'));
        Move move2 = new Move(player1,"I1/H2");
        board.getBox(13,8).getPiece().move(move2,board);
        Assert.assertEquals("B", board.getBox(12, 7).getPiece().p);
        Assert.assertNull(board.getBox(13, 8).getPiece());
    }

    @Test
    public void testMoveBishopUpLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-left (diagonal) when blocked by a teammates piece
        board.setBox(11,6,new Pawn('G'));
        Move move3 = new Move(player1,"H2/G3");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveBishopUpLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move up-left (diagonal) when blocked by a piece of the same color
        board.setBox(11,6,new Pawn('B'));
        Move move4 = new Move(player1,"H2/G3");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveBishopUpLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop cam move past a different piece
        board.setBox(11,6,new Pawn('B'));
        Move move5 = new Move(player1,"H2/E5");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveBishopDownLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,8,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 8;
        String[] coordinates = {"I14/H13","H13/G12","G12/F11","F11/E10"};

        // Tests if the bishop can move down-left (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("B", board.getBox(x + 1, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
            y--;
        }
    }

    @Test
    public void testMoveBishopDownLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(4,4,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-left (diagonal) various squares at once
        Move move6 = new Move(player1,"E10/A6");
        board.getBox(4,4).getPiece().move(move6,board);
        Assert.assertEquals("B", board.getBox(8, 0).getPiece().p);
        Assert.assertNull(board.getBox(4,4).getPiece());
    }

    @Test
    public void testMoveBishopDownLeft3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop captures opponent piece when moving down-left (diagonal)
        board.setBox(0,8,new Bishop('B'));
        board.setBox(1,7,new Pawn('R'));
        Move move2 = new Move(player1,"I14/H13");
        board.getBox(0,8).getPiece().move(move2,board);
        Assert.assertEquals("B", board.getBox(1, 7).getPiece().p);
        Assert.assertNull(board.getBox(0, 8).getPiece());
    }

    @Test
    public void testMoveBishopDownLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-left (diagonal) when blocked by a teammates piece
        board.setBox(2,6,new Pawn('G'));
        Move move3 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveBishopDownLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-left (diagonal) when blocked by a piece of the same color
        board.setBox(2,6,new Pawn('B'));
        Move move4 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveBishopDownLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move past a different piece
        board.setBox(2,6,new Pawn('B'));
        Move move5 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveBishopDownRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,5,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 5;
        String[] coordinates = {"F14/G13","G13/H12","H12/I11","I11/J10"};

        // Tests if the bishop can move down-right (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("B", board.getBox(x + 1, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
            y++;
        }
    }

    @Test
    public void testMoveBishopDownRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(4,9,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-right (diagonal) various squares at once
        Move move = new Move(player1,"J10/N6");
        board.getBox(4,9).getPiece().move(move,board);
        Assert.assertEquals("B", board.getBox(8, 13).getPiece().p);
        Assert.assertNull(board.getBox(4,9).getPiece());
    }

    @Test
    public void testMoveBishopDownRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop captures opponent piece when moving down-right (diagonal)
        board.setBox(0,5,new Bishop('B'));
        board.setBox(1,6,new Pawn('R'));
        Move move2 = new Move(player1,"F14/G13");
        board.getBox(0,5).getPiece().move(move2,board);
        Assert.assertEquals("B", board.getBox(1, 6).getPiece().p);
        Assert.assertNull(board.getBox(0, 5).getPiece());


    }

    @Test
    public void testMoveBishopDownRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-right (diagonal) when blocked by a teammates piece
        board.setBox(2,7,new Pawn('G'));
        Move move3 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move3,board));


    }

    @Test
    public void testMoveBishopDownRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move down-right (diagonal) when blocked by a piece of the same color
        board.setBox(2,7,new Pawn('B'));
        Move move4 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move4,board));


    }

    @Test
    public void testMoveBishopDownRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move past a different piece
        board.setBox(2,7,new Pawn('B'));
        Move move5 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testPutKingInCheck1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(3,6,new Rook('R'));

        // Tests if the bishop moves knowing it will put the king in check
        Move move = new Move(player1,"G6/H7");
        Assert.assertTrue(board.getBox(8,6).getPiece().isValidMove(move,board));
        Assert.assertNull(board.getBox(7,7).getPiece());
        Assert.assertEquals("B", board.getBox(8, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheck2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(3,6,new Rook('R'));

        // Tests if the bishop moves knowing it will not put the king in check
        board.setBox(3,6,null);
        board.setBox(3,7,new Rook('R'));
        Move move2 = new Move(player1,"G6/H7");
        board.getBox(8,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(7,7).getPiece());
        Assert.assertEquals("B", board.getBox(7, 7).getPiece().p);
        Assert.assertNull(board.getBox(8,6).getPiece());
    }

    @Test
    public void testInvalidMoves1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Bishop('B'));
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
        board.setBox(9,6,new Bishop('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if unreachable positions (according to the rules of the bishop) are recognized as invalid
        String[] coordinates2 = {"G5/I5","G5/E5","G5/G7","G5/G11"};
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }
}