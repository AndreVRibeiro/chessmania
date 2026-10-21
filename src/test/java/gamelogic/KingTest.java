package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class KingTest {

    @Test
    public void testMoveKingUp1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up one field
        Move move1 = new Move(player1,"G5/G6");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(8, 6).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingUp2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(8,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving up
        board.setBox(7,6,new Pawn('R'));
        Move move2 = new Move(player1,"G6/G7");
        board.getBox(8,6).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(7, 6).getPiece().p);
        Assert.assertNull(board.getBox(8, 6).getPiece());


    }

    @Test
    public void testMoveKingUp3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up when blocked by a teammates piece
        board.setBox(6,6,new Pawn('G'));
        Move move3 = new Move(player1,"G7/G8");
        Assert.assertFalse(board.getBox(7,6).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingUp4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up when blocked by a piece of the same color
        board.setBox(6,6,new Pawn('B'));
        Move move4 = new Move(player1,"G7/G8");
        Assert.assertFalse(board.getBox(7,6).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingDown1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down one field
        Move move1 = new Move(player1,"G5/G4");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(10, 6).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());


    }

    @Test
    public void testMoveKingDown2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving down
        board.setBox(11,6,new Pawn('R'));
        Move move2 = new Move(player1,"G4/G3");
        board.getBox(10,6).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(11, 6).getPiece().p);
        Assert.assertNull(board.getBox(10, 6).getPiece());
    }

    @Test
    public void testMoveKingDown3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down when blocked by a teammates piece
        board.setBox(12,6,new Pawn('G'));
        Move move3 = new Move(player1,"G3/G2");
        Assert.assertFalse(board.getBox(11,6).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingDown4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down when blocked by a piece of the same color
        board.setBox(12,6,new Pawn('B'));
        Move move4 = new Move(player1,"G3/G2");
        Assert.assertFalse(board.getBox(11,6).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move left one field
        Move move1 = new Move(player1,"G5/F5");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(9, 5).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());


    }

    @Test
    public void testMoveKingLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,5,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving left
        board.setBox(9,4,new Pawn('R'));
        Move move2 = new Move(player1,"F5/E5");
        board.getBox(9,5).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(9, 4).getPiece().p);
        Assert.assertNull(board.getBox(9, 5).getPiece());
    }

    @Test
    public void testMoveKingLeft3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move left when blocked by a teammates piece
        board.setBox(9,3,new Pawn('G'));
        Move move3 = new Move(player1,"E5/D5");
        Assert.assertFalse(board.getBox(9,4).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move left when blocked by a piece of the same color
        board.setBox(9,3,new Pawn('B'));
        Move move4 = new Move(player1,"E5/D5");
        Assert.assertFalse(board.getBox(9,4).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move right one field
        Move move1 = new Move(player1,"G5/H5");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(9, 7).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,7,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving right
        board.setBox(9,8,new Pawn('R'));
        Move move2 = new Move(player1,"H5/I5");
        board.getBox(9,7).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(9, 8).getPiece().p);
        Assert.assertNull(board.getBox(9, 7).getPiece());
    }

    @Test
    public void testMoveKingRight3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move right when blocked by a teammates piece
        board.setBox(9,9,new Pawn('G'));
        Move move3 = new Move(player1,"I5/J5");
        Assert.assertFalse(board.getBox(9,8).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move right when blocked by a piece of the same color
        board.setBox(9,9,new Pawn('B'));
        Move move4 = new Move(player1,"I5/J5");
        Assert.assertFalse(board.getBox(9,8).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingUpRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-right one field
        Move move1 = new Move(player1,"G5/H6");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(8, 7).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingUpRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(8,7,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving up-right
        board.setBox(7,8,new Pawn('R'));
        Move move2 = new Move(player1,"H6/I7");
        board.getBox(8,7).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(7, 8).getPiece().p);
        Assert.assertNull(board.getBox(8, 7).getPiece());
    }

    @Test
    public void testMoveKingUpRight3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-right when blocked by a teammates piece
        board.setBox(6,9,new Pawn('G'));
        Move move3 = new Move(player1,"I7/J8");
        Assert.assertFalse(board.getBox(7,8).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingUpRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-right when blocked by a piece of the same color
        board.setBox(6,9,new Pawn('B'));
        Move move4 = new Move(player1,"I7/J8");
        Assert.assertFalse(board.getBox(7,8).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingUpLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-left one field
        Move move1 = new Move(player1,"G5/F6");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(8, 5).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingUpLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(8,5,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving up-left
        board.setBox(7,4,new Pawn('R'));
        Move move2 = new Move(player1,"F6/E7");
        board.getBox(8,5).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(7, 4).getPiece().p);
        Assert.assertNull(board.getBox(8, 5).getPiece());
    }

    @Test
    public void testMoveKingUpLeft3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-left when blocked by a teammates piece
        board.setBox(6,3,new Pawn('G'));
        Move move3 = new Move(player1,"E7/D8");
        Assert.assertFalse(board.getBox(7,4).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingUpLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move up-left when blocked by a piece of the same color
        board.setBox(6,3,new Pawn('B'));
        Move move4 = new Move(player1,"E7/D8");
        Assert.assertFalse(board.getBox(7,4).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingDownLeft1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-left one field
        Move move1 = new Move(player1,"G5/F4");
        board.getBox(9,6).getPiece().move(move1,board);
        Assert.assertEquals("K", board.getBox(10, 5).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingDownLeft2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,5,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving down-left
        board.setBox(11,4,new Pawn('R'));
        Move move2 = new Move(player1,"F4/E3");
        board.getBox(10,5).getPiece().move(move2,board);
        Assert.assertEquals("K", board.getBox(11, 4).getPiece().p);
        Assert.assertNull(board.getBox(10, 5).getPiece());
    }

    @Test
    public void testMoveKingDownLeft3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-left when blocked by a teammates piece
        board.setBox(12,3,new Pawn('G'));
        Move move3 = new Move(player1,"E3/D2");
        Assert.assertFalse(board.getBox(11,4).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingDownLeft4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,4,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-left when blocked by a piece of the same color
        board.setBox(12,3,new Pawn('B'));
        Move move4 = new Move(player1,"E3/D2");
        Assert.assertFalse(board.getBox(11,4).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testMoveKingDownRight1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-right one field
        Move move = new Move(player1,"G5/H4");
        board.getBox(9,6).getPiece().move(move,board);
        Assert.assertEquals("K", board.getBox(10, 7).getPiece().p);
        Assert.assertNull(board.getBox(9, 6).getPiece());
    }

    @Test
    public void testMoveKingDownRight2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,7,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king captures opponent piece when moving down-right
        board.setBox(11,8,new Pawn('R'));
        Move move = new Move(player1,"H4/I3");
        board.getBox(10,7).getPiece().move(move,board);
        Assert.assertEquals("K", board.getBox(11, 8).getPiece().p);
        Assert.assertNull(board.getBox(10, 7).getPiece());
    }

    @Test
    public void testMoveKingDownRight3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-right when blocked by a teammates piece
        board.setBox(12,9,new Pawn('G'));
        Move move3 = new Move(player1,"I3/J2");
        Assert.assertFalse(board.getBox(11,8).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testMoveKingDownRight4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(11,8,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if the king can move down-right when blocked by a piece of the same color
        board.setBox(12,9,new Pawn('B'));
        Move move4 = new Move(player1,"I3/J2");
        Assert.assertFalse(board.getBox(11,8).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square up
        Move move1 = new Move(player1,"G5/G9");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move1,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square down
        Move move2 = new Move(player1,"G5/G2");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move2,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square left
        Move move3 = new Move(player1,"G5/C5");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square down
        Move move4 = new Move(player1,"G5/L5");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare5() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square down-right
        Move move5 = new Move(player1,"G5/J2");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move5,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare6() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square up-right
        Move move6 = new Move(player1,"G5/K9");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move6,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare7() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square down-left
        Move move7 = new Move(player1,"G5/D12");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move7,board));
    }

    @Test
    public void testCanMoveMoreThanOneSquare8() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Test if king can move more than one square up-left
        Move move8 = new Move(player1,"G5/C10");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move8,board));
    }

    @Test
    public void testPutKingInCheck1(){
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(5,9,new Bishop('R'));

        // Tests if the king is put in a check position after moving and prevents the piece from moving
        Move move = new Move(player1,"G5/G6");
        Assert.assertFalse(board.getBox(9,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testPutKingInCheck2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(5,9,new Bishop('R'));

        // Tests if the king can escape while being in a check position
        board.setBox(7,8,new Bishop('R'));
        Move move = new Move(player1,"G5/G4");
        Assert.assertTrue(board.getBox(9,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testPutKingInCheck3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new King('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(5,9,new Bishop('R'));

        // Tests if the king is put in a check position after moving (Teammate Piece)
        board.setBox(5,9,new Bishop('G'));
        Move move = new Move(player1,"G5/G6");
        Assert.assertTrue(board.getBox(9,6).getPiece().isValidMove(move,board));
    }

    @Test
    public void testMoveIfKingInDoubleCheck() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,13,new King('R'));
        board.setBox(9,13,new Queen('B'));
        board.setBox(10,12,new Bishop('G'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;

        // Tests if the king moves to attack when surrounded by other pieces putting him in double check
        Move move1 = new Move(player1,"N4/N5");
        board.getBox(10,13).getPiece().move(move1,board);
        Assert.assertTrue(board.getBox(10,13).getPiece().p.equals("K"));
        Assert.assertTrue(board.getBox(9,13).getPiece().p.equals("Q"));
        Assert.assertTrue(board.getBox(10,12).getPiece().p.equals("B"));

    }
}