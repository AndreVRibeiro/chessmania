package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class QueenTest {

    @Test
    public void testMoveQueenUp1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 5;
        String[] coordinates = {"F1/F2","F2/F3","F3/F4","F4/F5","F5/F6","F6/F7"};

        // Test if the queen can move up
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x - 1, y).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
        }
    }

    @Test
    public void testMoveQueenUp2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up various squares at once
        Move move6 = new Move(player1,"F7/F14");
        board.getBox(7,5).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(0, 5).getPiece().p);
        Assert.assertNull(board.getBox(7,5).getPiece());
    }

    @Test
    public void testMoveQueenUp3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen captures opponent piece when moving up
        board.setBox(13,5,new Queen('B'));
        board.setBox(12,5,new Pawn('R'));
        Move move2 = new Move(player1,"F1/F2");
        board.getBox(13,5).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(12, 5).getPiece().p);
        Assert.assertNull(board.getBox(13, 5).getPiece());
    }

    @Test
    public void testMoveQueenUp4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Queen('B'));
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
    public void testMoveQueenUp5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Queen('B'));
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
    public void testMoveQueenUp6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Queen('B'));
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
    public void testMoveQueenDown1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 5;
        String[] coordinates = {"F14/F13","F13/F12","F12/F11","F11/F10","F10/F9","F9/F8"};

        // Test if the queen can move down
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x + 1, y).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
        }
    }

    @Test
    public void testMoveQueenDown2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down various squares at once
        Move move6 = new Move(player1,"F8/F1");
        board.getBox(6,5).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(13, 5).getPiece().p);
        Assert.assertNull(board.getBox(6,5).getPiece());
    }

    @Test
    public void testMoveQueenDown3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen captures opponent piece when moving down
        board.setBox(0,5,new Queen('B'));
        board.setBox(1,5,new Pawn('R'));
        Move move2 = new Move(player1,"F14/F13");
        board.getBox(0,5).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(1, 5).getPiece().p);
        Assert.assertNull(board.getBox(0, 5).getPiece());
    }

    @Test
    public void testMoveQueenDown4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move down when blocked by a teammates piece
        board.setBox(2,5,new Pawn('G'));
        Move move3 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenDown5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move down when blocked by a piece of the same color
        board.setBox(2,5,new Pawn('B'));
        Move move4 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenDown6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move past a different piece
        board.setBox(2,5,new Pawn('B'));
        Move move5 = new Move(player1,"F13/F12");
        Assert.assertFalse(board.getBox(1,5).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveQueenLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,13,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 5;
        int y = 13;
        String[] coordinates = {"N9/M9","M9/L9","L9/K9","K9/J9","J9/I9","I9/H9"};

        // Test if the queen can move left
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            y--;
        }
    }

    @Test
    public void testMoveQueenLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move left various squares at once
        Move move6 = new Move(player1,"H9/A9");
        board.getBox(5,7).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(5, 0).getPiece().p);
        Assert.assertNull(board.getBox(5,7).getPiece());
    }

    @Test
    public void testMoveQueenLeft3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen captures opponent piece when moving left
        board.setBox(5,13,new Queen('B'));
        board.setBox(5,12,new Pawn('R'));
        Move move2 = new Move(player1,"N9/M9");
        board.getBox(5,13).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(5, 12).getPiece().p);
        Assert.assertNull(board.getBox(5, 13).getPiece());
    }

    @Test
    public void testMoveQueenLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move left when blocked by a teammates piece
        board.setBox(5,11,new Pawn('G'));
        Move move3 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move left when blocked by a piece of the same color
        board.setBox(5,11,new Pawn('B'));
        Move move4 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move past a different piece
        board.setBox(5,11,new Pawn('B'));
        Move move5 = new Move(player1,"M9/L9");
        Assert.assertFalse(board.getBox(5,12).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveQueenRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,0,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 5;
        int y = 0;
        String[] coordinates = {"A9/B9","B9/C9","C9/D9","D9/E9","E9/F9","F9/G9"};

        // Test if the queen can move right
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            y++;
        }
    }

    @Test
    public void testMoveQueenRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move right various squares at once
        Move move6 = new Move(player1,"G9/N9");
        board.getBox(5,6).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(5, 13).getPiece().p);
        Assert.assertNull(board.getBox(5,6).getPiece());
    }

    @Test
    public void testMoveQueenRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen captures opponent piece when moving right
        board.setBox(5,0,new Queen('B'));
        board.setBox(5,1,new Pawn('R'));
        Move move2 = new Move(player1,"A9/B9");
        board.getBox(5,0).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(5, 1).getPiece().p);
        Assert.assertNull(board.getBox(5, 0).getPiece());
    }

    @Test
    public void testMoveQueenRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move right when blocked by a teammates piece
        board.setBox(5,2,new Pawn('G'));
        Move move3 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move right when blocked by a piece of the same color
        board.setBox(5,2,new Pawn('B'));
        Move move4 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the queen can move past a different piece
        board.setBox(5,2,new Pawn('B'));
        Move move5 = new Move(player1,"B9/C9");
        Assert.assertFalse(board.getBox(5,1).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveQueenUpRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 5;
        String[] coordinates = {"F1/G2","G2/H3","H3/I4","I4/J5"};

        // Tests if the queen can move up-right (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x - 1, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
            y++;
        }
    }

    @Test
    public void testMoveQueenUpRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,9,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-right (diagonal) various squares at once
        Move move6 = new Move(player1,"J5/N9");
        board.getBox(9,9).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(5, 13).getPiece().p);
        Assert.assertNull(board.getBox(9,9).getPiece());
    }

    @Test
    public void testMoveQueenUpRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen captures opponent piece when moving up-right (diagonal)
        board.setBox(13,5,new Queen('B'));
        board.setBox(12,6,new Pawn('R'));
        Move move2 = new Move(player1,"F1/G2");
        board.getBox(13,5).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(12, 6).getPiece().p);
        Assert.assertNull(board.getBox(13, 5).getPiece());
    }

    @Test
    public void testMoveQueenUpRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-right (diagonal) when blocked by a teammates piece
        board.setBox(11,7,new Pawn('G'));
        Move move3 = new Move(player1,"G2/H3");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenUpRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-right (diagonal) when blocked by a piece of the same color
        board.setBox(11,7,new Pawn('B'));
        Move move4 = new Move(player1,"G2/H3");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenUpRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the bishop can move past a different piece
        board.setBox(11,7,new Pawn('B'));
        Move move5 = new Move(player1,"G2/J5");
        Assert.assertFalse(board.getBox(12,6).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveBishopUpLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(13,8,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 13;
        int y = 8;
        String[] coordinates = {"I1/H2","H2/G3","G3/F4","F4/E5"};

        // Tests if the queen can move up-left (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x - 1, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x--;
            y--;
        }
    }

    @Test
    public void testMoveBishopUpLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,4,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-left (diagonal) various squares at once
        Move move6 = new Move(player1,"E5/A9");
        board.getBox(9,4).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(5, 0).getPiece().p);
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

        // Tests if the queen captures opponent piece when moving up-left (diagonal)
        board.setBox(13,8,new Queen('B'));
        board.setBox(12,7,new Pawn('R'));
        Move move2 = new Move(player1,"I1/H2");
        board.getBox(13,8).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(12, 7).getPiece().p);
        Assert.assertNull(board.getBox(13, 8).getPiece());
    }

    @Test
    public void testMoveBishopUpLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-left (diagonal) when blocked by a teammates piece
        board.setBox(11,6,new Pawn('G'));
        Move move3 = new Move(player1,"H2/G3");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveBishopUpLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move up-left (diagonal) when blocked by a piece of the same color
        board.setBox(11,6,new Pawn('B'));
        Move move4 = new Move(player1,"H2/G3");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveBishopUpLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen cam move past a different piece
        board.setBox(11,6,new Pawn('B'));
        Move move5 = new Move(player1,"H2/E5");
        Assert.assertFalse(board.getBox(12,7).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveQueenDownLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,8,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 8;
        String[] coordinates = {"I14/H13","H13/G12","G12/F11","F11/E10"};

        // Tests if the queen can move down-left (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x + 1, y - 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
            y--;
        }
    }

    @Test
    public void testMoveQueenDownLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(4,4,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-left (diagonal) various squares at once
        Move move6 = new Move(player1,"E10/A6");
        board.getBox(4,4).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(8, 0).getPiece().p);
        Assert.assertNull(board.getBox(4,4).getPiece());
    }

    @Test
    public void testMoveQueenDownLeft3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen captures opponent piece when moving down-left (diagonal)
        board.setBox(0,8,new Queen('B'));
        board.setBox(1,7,new Pawn('R'));
        Move move2 = new Move(player1,"I14/H13");
        board.getBox(0,8).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(1, 7).getPiece().p);
        Assert.assertNull(board.getBox(0, 8).getPiece());
    }

    @Test
    public void testMoveQueenDownLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-left (diagonal) when blocked by a teammates piece
        board.setBox(2,6,new Pawn('G'));
        Move move3 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenDownLeft5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-left (diagonal) when blocked by a piece of the same color
        board.setBox(2,6,new Pawn('B'));
        Move move4 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenDownLeft6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move past a different piece
        board.setBox(2,6,new Pawn('B'));
        Move move5 = new Move(player1,"H13/G12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testMoveQueenDownRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(0,5,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        int x = 0;
        int y = 5;
        String[] coordinates = {"F14/G13","G13/H12","H12/I11","I11/J10"};

        // Tests if the queen can move down-right (diagonal)
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            Assert.assertEquals("Q", board.getBox(x + 1, y + 1).getPiece().p);
            Assert.assertNull(board.getBox(x, y).getPiece());
            x++;
            y++;
        }
    }

    @Test
    public void testMoveQueenDownRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(4,9,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-right (diagonal) various squares at once
        Move move6 = new Move(player1,"J10/N6");
        board.getBox(4,9).getPiece().move(move6,board);
        Assert.assertEquals("Q", board.getBox(8, 13).getPiece().p);
        Assert.assertNull(board.getBox(4,9).getPiece());
    }

    @Test
    public void testMoveQueenDownRight3() {
        ChessBoard board = new ChessBoard(0);
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen captures opponent piece when moving down-right (diagonal)
        board.setBox(0,5,new Queen('B'));
        board.setBox(1,6,new Pawn('R'));
        Move move2 = new Move(player1,"F14/G13");
        board.getBox(0,5).getPiece().move(move2,board);
        Assert.assertEquals("Q", board.getBox(1, 6).getPiece().p);
        Assert.assertNull(board.getBox(0, 5).getPiece());
    }

    @Test
    public void testMoveQueenDownRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-right (diagonal) when blocked by a teammates piece
        board.setBox(2,7,new Pawn('G'));
        Move move3 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveQueenDownRight5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move down-right (diagonal) when blocked by a piece of the same color
        board.setBox(2,7,new Pawn('B'));
        Move move4 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveQueenDownRight6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen can move past a different piece
        board.setBox(2,7,new Pawn('B'));
        Move move5 = new Move(player1,"G13/H12");
        Assert.assertFalse(board.getBox(1,6).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testPutKingInCheck1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(3,6,new Rook('R'));

        // Tests if the queen moves knowing it will put the king in check
        Move move1 = new Move(player1,"G6/H6");
        Assert.assertTrue(board.getBox(8,6).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(8,7).getPiece());
        Assert.assertEquals("Q", board.getBox(8, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheck2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        board.setBox(8,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the queen moves knowing it will not put the king in check
        board.setBox(3,7,new Rook('R'));
        Move move2 = new Move(player1,"G6/H6");
        board.getBox(8,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(8,7).getPiece());
        Assert.assertEquals("Q", board.getBox(8, 7).getPiece().p);
        Assert.assertNull(board.getBox(8,6).getPiece());
    }

    @Test
    public void testInvalidMoves1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Queen('B'));
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
        board.setBox(9,6,new Queen('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if unreachable positions (according to the rules of the queen) are recognized as invalid
        String[] coordinates2 = {"G5/I8","G5/E6","G5/F11","G5/J10"};
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }
}