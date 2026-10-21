package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class PawnTest {

    @Test
    public void testFirstMoveRedPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';

        // Tests if pawn is able to move 2 squares forward on its first move
        Move move1 = new Move(player1,"G2/G4");
        Assert.assertTrue(board.getBox(12,6).getPiece().isValidMove(move1,board));
    }

    @Test
    public void testFirstMoveRedPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,7,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';

        // Tests if pawn is able to move 1 square forward on its first move
        Move move2 = new Move(player1,"H2/H3");
        Assert.assertTrue(board.getBox(12,7).getPiece().isValidMove(move2,board));
    }

    @Test
    public void testFirstMoveRedPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,8,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';

        // Tests if pawn is able to move more than 2 squares on its first move
        String[] coordinates = {"I2/I5","I2/I6","I2/I7","I2/I8","I2/I9","I2/I10","I2/I11","I2/I12","I2/I13","I2/I14"};
        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(12, 8).getPiece().isValidMove(move3, board));
        }
    }

    @Test
    public void testFirstMoveRedPawn4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,8,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';

        // Tests if the pawn is able to move to other directions
        String[] coordinates2 = {"I2/H2","I2/J2","I2/I1"};
        for (String s : coordinates2) {
            Move move4 = new Move(player1, s);
            Assert.assertFalse(board.getBox(12, 8).getPiece().isValidMove(move4, board));
        }
    }

    @Test
    public void testFirstMoveBluePawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';

        // Tests if pawn is able to move 2 squares forward on its first move
        Move move1 = new Move(player1,"B9/D9");
        Assert.assertTrue(board.getBox(5,1).getPiece().isValidMove(move1,board));
    }

    @Test
    public void testFirstMoveBluePawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,1,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';

        // Tests if pawn is able to move 1 square forward on its first move
        Move move2 = new Move(player1,"B8/C8");
        Assert.assertTrue(board.getBox(6,1).getPiece().isValidMove(move2,board));
    }

    @Test
    public void testFirstMoveBluePawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,1,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';

        // Tests if pawn is able to move more than 2 squares on its first move
        String[] coordinates = {"B7/E7","B7/F7","B7/G7","B7/H7","B7/I7","B7/J7","B7/K7","B7/L7","B7/M7","B7/N7"};
        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(7, 1).getPiece().isValidMove(move3, board));
        }
    }

    @Test
    public void testFirstMoveBluePawn4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,1,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';

        // Tests if the pawn is able to move to other directions
        String[] coordinates2 = {"B7/B8","B7/B6","B7/A7"};
        for (String s : coordinates2) {
            Move move4 = new Move(player1, s);
            Assert.assertFalse(board.getBox(7, 1).getPiece().isValidMove(move4, board));
        }
    }

    @Test
    public void testFirstMoveYellowPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';

        // Tests if pawn is able to move 2 squares forward on its first move
        Move move1 = new Move(player1,"F13/F11");
        Assert.assertTrue(board.getBox(1,5).getPiece().isValidMove(move1,board));
    }

    @Test
    public void testFirstMoveYellowPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';

        // Tests if pawn is able to move 1 square forward on its first move
        Move move2 = new Move(player1,"G13/G12");
        Assert.assertTrue(board.getBox(1,6).getPiece().isValidMove(move2,board));
    }

    @Test
    public void testFirstMoveYellowPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';

        // Tests if pawn is able to move more than 2 squares on its first move
        String[] coordinates = {"H13/H10","H13/H9","H13/H8","H13/H7","H13/H6","H13/H5","H13/H4","H13/H3","H13/H2","H13/H1"};
        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(1, 7).getPiece().isValidMove(move3, board));
        }
    }

    @Test
    public void testFirstMoveYellowPawn4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';

        // Tests if the pawn is able to move to other directions
        String[] coordinates2 = {"H13/G13","H13/I13","H13/H14"};
        for (String s : coordinates2) {
            Move move4 = new Move(player1, s);
            Assert.assertFalse(board.getBox(1, 7).getPiece().isValidMove(move4, board));
        }
    }

    @Test
    public void testFirstMoveGreenPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,12,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';

        // Tests if pawn is able to move 2 squares forward on its first move
        Move move1 = new Move(player1,"M9/K9");
        Assert.assertTrue(board.getBox(5,12).getPiece().isValidMove(move1,board));
    }

    @Test
    public void testFirstMoveGreenPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,12,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';

        // Tests if pawn is able to move 1 square forward on its first move
        Move move2 = new Move(player1,"M8/L8");
        Assert.assertTrue(board.getBox(6,12).getPiece().isValidMove(move2,board));
    }

    @Test
    public void testFirstMoveGreenPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,12,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';

        // Tests if pawn is able to move more than 2 squares on its first move
        String[] coordinates = {"M7/J7","M7/I7","M7/H7","M7/G7","M7/F7","M7/E7","M7/D7","M7/C7","M7/B7","M7/A7"};
        for (String coordinate : coordinates) {
            Move move3 = new Move(player1, coordinate);
            Assert.assertFalse(board.getBox(7, 12).getPiece().isValidMove(move3, board));
        }
    }

    @Test
    public void testFirstMoveGreenPawn4() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,12,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';

        // Tests if the pawn is able to move to other directions
        String[] coordinates2 = {"M7/M8","M7/M6","M7/N7"};
        for (String s : coordinates2) {
            Move move4 = new Move(player1, s);
            Assert.assertFalse(board.getBox(7, 12).getPiece().isValidMove(move4, board));
        }
    }

    @Test
    public void testFirstMoveIsAttackRedPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Pawn('R'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Right)
        board.setBox(11,7,new Queen('B'));
        Move move1 = new Move(player1,"G2/H3");
        board.getBox(12,6).getPiece().move(move1,board);
        Assert.assertEquals("P", board.getBox(11, 7).getPiece().p);
        Assert.assertNull(board.getBox(12, 6).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackRedPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,5,new Pawn('R'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Left)
        board.setBox(11,4,new Queen('B'));
        Move move2 = new Move(player1,"F2/E3");
        board.getBox(12,5).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(11, 4).getPiece().p);
        Assert.assertNull(board.getBox(12, 5).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackRedPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,4,new Pawn('R'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;

        // Tests if pawn can capture a teammate on its first move
        board.setBox(11,3,new Queen('Y'));
        Move move3 = new Move(player1,"E2/D11");
        Assert.assertFalse(board.getBox(12,4).getPiece().isValidMove(move3,board));
    }


    @Test
    public void testFirstMoveIsAttackBluePawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,2,new Pawn('B'));
        board.setBox(6,2,new Pawn('B'));
        board.setBox(5,2,new Pawn('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Down)
        board.setBox(8,3,new Queen('R'));
        Move move1 = new Move(player1,"C7/D6");
        board.getBox(7,2).getPiece().move(move1,board);
        Assert.assertEquals("P", board.getBox(8, 3).getPiece().p);
        Assert.assertNull(board.getBox(7, 2).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackBluePawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,2,new Pawn('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Up)
        board.setBox(5,3,new Queen('R'));
        Move move2 = new Move(player1,"C8/D9");
        board.getBox(6,2).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(5, 3).getPiece().p);
        Assert.assertNull(board.getBox(6, 2).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackBluePawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,2,new Pawn('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if pawn can capture a teammate on its first move
        board.setBox(4,3,new Queen('G'));
        Move move3 = new Move(player1,"C9/D10");
        Assert.assertFalse(board.getBox(5,2).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testFirstMoveIsAttackYellowPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Pawn('Y'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'Y';
        teammate.color = 'R';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Down-Right)
        board.setBox(2,7,new Queen('B'));
        Move move1 = new Move(player1,"G13/H12");
        board.getBox(1,6).getPiece().move(move1,board);
        Assert.assertEquals("P", board.getBox(2, 7).getPiece().p);
        Assert.assertNull(board.getBox(1, 6).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackYellowPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,5,new Pawn('Y'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'Y';
        teammate.color = 'R';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Down-Left)
        board.setBox(2,4,new Queen('B'));
        Move move2 = new Move(player1,"F13/E12");
        board.getBox(1,5).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(2, 4).getPiece().p);
        Assert.assertNull(board.getBox(1, 5).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackYellowPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,7,new Pawn('Y'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'Y';
        teammate.color = 'R';
        player1.teammate = teammate;

        // Tests if pawn can capture a teammate on its first move
        board.setBox(2,8,new Queen('R'));
        Move move3 = new Move(player1,"H13/I12");
        Assert.assertFalse(board.getBox(1,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testFirstMoveIsAttackGreenPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,11,new Pawn('G'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'G';
        teammate.color = 'B';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Down)
        board.setBox(8,10,new Queen('R'));
        Move move1 = new Move(player1,"L7/K6");
        board.getBox(7,11).getPiece().move(move1,board);
        Assert.assertEquals("P", board.getBox(8, 10).getPiece().p);
        Assert.assertNull(board.getBox(7, 11).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackGreenPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,11,new Pawn('G'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'G';
        teammate.color = 'B';
        player1.teammate = teammate;

        // Tests if pawn can capture an opponent on its first move (Diagonal-Up)
        board.setBox(5,10,new Queen('R'));
        Move move2 = new Move(player1,"L8/K9");
        board.getBox(6,11).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(5, 10).getPiece().p);
        Assert.assertNull(board.getBox(6, 11).getPiece());
    }

    @Test
    public void testFirstMoveIsAttackGreenPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,11,new Pawn('G'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'G';
        teammate.color = 'B';
        player1.teammate = teammate;

        // Tests if pawn can capture a teammate on its first move
        board.setBox(4,10,new Queen('B'));
        Move move3 = new Move(player1,"L9/K10");
        Assert.assertFalse(board.getBox(5,11).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testRegularAttackRedPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Pawn('R'));
        board.setBox(10,7,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'R';
        Player teammate = new Player();
        teammate.color = 'Y';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"G2/G3");
        board.getBox(12,6).getPiece().move(move1,board);

        // Tests if pawn can capture an opponent AFTER making the first move
        Move move2 = new Move(player1,"G3/H4");
        board.getBox(11,6).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(10, 7).getPiece().p);
        Assert.assertNull(board.getBox(11, 6).getPiece());
    }

    @Test
    public void testRegularAttackRedPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,7,new Pawn('R'));
        board.setBox(9,8,new Pawn('Y'));
        board.setBox(9,6,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';
        Player teammate = new Player();
        teammate.color = 'Y';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"G3/H4");
        board.getBox(10,7).getPiece().move(move1,board);

        // Tests if pawn can capture an teammates piece AFTER making the first move
        Move move3 = new Move(player1,"H4/I5");
        Assert.assertFalse(board.getBox(10,7).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testRegularAttackRedPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(10,7,new Pawn('R'));
        board.setBox(9,8,new Pawn('Y'));
        board.setBox(9,6,new Pawn('R'));

        Player player1 = new Player();
        player1.color = 'R';
        Player teammate = new Player();
        teammate.color = 'Y';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"G3/H4");
        board.getBox(10,7).getPiece().move(move1,board);

        // Tests if the pawn can capture a piece of the same color AFTER making the first move
        Move move4 = new Move(player1,"H4/G5");
        Assert.assertFalse(board.getBox(10,7).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testRegularAttackBluePawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,1,new Pawn('B'));
        board.setBox(6,3,new Pawn('R'));
        board.setBox(5,4,new Pawn('G'));
        board.setBox(7,4,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';
        Player teammate = new Player();
        teammate.color = 'G';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"B9/C9");
        board.getBox(5,1).getPiece().move(move1,board);

        // Tests if pawn can capture an opponent AFTER making the first move
        Move move2 = new Move(player1,"C9/D8");
        board.getBox(5,2).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(6, 3).getPiece().p);
        Assert.assertNull(board.getBox(5, 2).getPiece());
    }

    @Test
    public void testRegularAttackBluePawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,2,new Pawn('B'));
        board.setBox(6,3,new Pawn('R'));
        board.setBox(5,4,new Pawn('G'));
        board.setBox(7,4,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';
        Player teammate = new Player();
        teammate.color = 'G';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"C9/D8");
        board.getBox(5,2).getPiece().move(move1,board);

        // Tests if pawn can capture an teammates piece AFTER making the first move
        Move move3 = new Move(player1,"D8/E9");
        Assert.assertFalse(board.getBox(6,3).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testRegularAttackBluePawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,2,new Pawn('B'));
        board.setBox(6,3,new Pawn('R'));
        board.setBox(5,4,new Pawn('G'));
        board.setBox(7,4,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'B';
        Player teammate = new Player();
        teammate.color = 'G';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"C9/D8");
        board.getBox(5,2).getPiece().move(move1,board);

        // Tests if the pawn can capture a piece of the same color AFTER making the first move
        Move move4 = new Move(player1,"D8/E7");
        Assert.assertFalse(board.getBox(6,3).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testRegularAttackYellowPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1, 6, new Pawn('Y'));
        board.setBox(3, 5, new Pawn('B'));
        board.setBox(4, 4, new Pawn('R'));
        board.setBox(4, 6, new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';
        Player teammate = new Player();
        teammate.color = 'R';
        player1.teammate = teammate;
        Move move1 = new Move(player1, "G13/G12");
        board.getBox(1, 6).getPiece().move(move1, board);

        // Tests if pawn can capture an opponent AFTER making the first move
        Move move2 = new Move(player1, "G12/F11");
        board.getBox(2, 6).getPiece().move(move2, board);
        Assert.assertEquals("P", board.getBox(3, 5).getPiece().p);
        Assert.assertNull(board.getBox(2, 6).getPiece());
    }


    @Test
    public void testRegularAttackYellowPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(2, 5, new Pawn('Y'));
        board.setBox(4, 4, new Pawn('R'));
        board.setBox(4, 6, new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';
        Player teammate = new Player();
        teammate.color = 'R';
        player1.teammate = teammate;
        Move move1 = new Move(player1, "F12/F11");
        board.getBox(2, 5).getPiece().move(move1, board);

        // Tests if pawn can capture an teammates piece AFTER making the first move
        Move move3 = new Move(player1,"F11/E10");
        Assert.assertFalse(board.getBox(3,5).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testRegularAttackYellowPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(2, 5, new Pawn('Y'));
        board.setBox(4, 4, new Pawn('R'));
        board.setBox(4, 6, new Pawn('Y'));

        Player player1 = new Player();
        player1.color = 'Y';
        Player teammate = new Player();
        teammate.color = 'R';
        player1.teammate = teammate;
        Move move1 = new Move(player1, "F12/F11");
        board.getBox(2, 5).getPiece().move(move1, board);

        // Tests if the pawn can capture a piece of the same color AFTER making the first move
        Move move4 = new Move(player1,"F11/G10");
        Assert.assertFalse(board.getBox(3,5).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testRegularAttackGreenPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,12,new Pawn('G'));
        board.setBox(5,10,new Pawn('Y'));
        board.setBox(4,9,new Pawn('B'));
        board.setBox(6,9,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';
        Player teammate = new Player();
        teammate.color = 'B';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"M8/L8");
        board.getBox(6,12).getPiece().move(move1,board);

        // Tests if pawn can capture an opponent AFTER making the first move
        Move move2 = new Move(player1,"L8/K9");
        board.getBox(6,11).getPiece().move(move2,board);
        Assert.assertEquals("P", board.getBox(5, 10).getPiece().p);
        Assert.assertNull(board.getBox(6, 11).getPiece());
    }

    @Test
    public void testRegularAttackGreenPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,11,new Pawn('G'));
        board.setBox(4,9,new Pawn('B'));
        board.setBox(6,9,new Pawn('G'));

        Player player1 = new Player();
        player1.color = 'G';
        Player teammate = new Player();
        teammate.color = 'B';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"L9/K9");
        board.getBox(5,11).getPiece().move(move1,board);

        // Tests if pawn can capture an teammates piece AFTER making the first move
        Move move3 = new Move(player1,"K9/J10");
        Assert.assertFalse(board.getBox(5,10).getPiece().isValidMove(move3,board));
    }

    @Test
    public void testRegularAttackGreenPawn3() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(5,11,new Pawn('G'));
        board.setBox(4,9,new Pawn('B'));

        Player player1 = new Player();
        player1.color = 'G';
        Player teammate = new Player();
        teammate.color = 'B';
        player1.teammate = teammate;
        Move move1 = new Move(player1,"L9/K9");
        board.getBox(5,11).getPiece().move(move1,board);

        // Tests if the pawn can capture a piece of the same color AFTER making the first move
        Move move4 = new Move(player1,"K9/J8");
        Assert.assertFalse(board.getBox(5,10).getPiece().isValidMove(move4,board));
    }

    @Test
    public void testPutKingInCheckRedPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Pawn('R'));
        board.setBox(13,5,new King('R'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;
        board.setBox(8,10,new Bishop('G'));

        // Tests if the pawn moves, knowing it will put the king in check
        Move move1 = new Move(player1,"G2/G4");
        Assert.assertTrue(board.getBox(12,6).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(10,6).getPiece());
        Assert.assertEquals("P", board.getBox(12, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheckRedPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,6,new Pawn('R'));
        board.setBox(13,5,new King('R'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'R';
        teammate.color = 'Y';
        player1.teammate = teammate;

        // Tests if the pawn moves, knowing it will not put the king in check
        board.setBox(8,11,new Bishop('G'));
        Move move2 = new Move(player1,"G2/G3");
        board.getBox(12,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(11,6).getPiece());
        Assert.assertEquals("P", board.getBox(11, 6).getPiece().p);
        Assert.assertNull(board.getBox(12,6).getPiece());
    }

    @Test
    public void testPutKingInCheckBluePawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,1,new Pawn('B'));
        board.setBox(6,0,new King('B'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;
        board.setBox(11,5,new Bishop('R'));

        // Tests if the pawn moves, knowing it will put the king in check
        Move move1 = new Move(player1,"B7/C7");
        Assert.assertTrue(board.getBox(7,1).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(7,2).getPiece());
        Assert.assertEquals("P", board.getBox(7, 1).getPiece().p);

    }

    @Test
    public void testPutKingInCheckBluePawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(7,1,new Pawn('B'));
        board.setBox(6,0,new King('B'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if the pawn moves, knowing it will not put the king in check
        board.setBox(12,5,new Bishop('R'));
        Move move2 = new Move(player1,"B7/C7");
        board.getBox(7,1).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(7,2).getPiece());
        Assert.assertEquals("P", board.getBox(7, 2).getPiece().p);
        Assert.assertNull(board.getBox(7,1).getPiece());
    }

    @Test
    public void testPutKingInCheckYellowPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Pawn('Y'));
        board.setBox(0,7,new King('Y'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'Y';
        teammate.color = 'R';
        player1.teammate = teammate;
        board.setBox(5,2,new Bishop('B'));

        // Tests if the pawn moves, knowing it will put the king in check
        Move move1 = new Move(player1,"G13/G12");
        Assert.assertTrue(board.getBox(1,6).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(2,6).getPiece());
        Assert.assertEquals("P", board.getBox(1, 6).getPiece().p);
    }

    @Test
    public void testPutKingInCheckYellowPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Pawn('Y'));
        board.setBox(0,7,new King('Y'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'Y';
        teammate.color = 'R';
        player1.teammate = teammate;

        // Tests if the pawn moves, knowing it will not put the king in check
        board.setBox(5,1,new Bishop('B'));
        Move move2 = new Move(player1,"G13/G12");
        board.getBox(1,6).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(2,6).getPiece());
        Assert.assertEquals("P", board.getBox(2, 6).getPiece().p);
        Assert.assertNull(board.getBox(1,6).getPiece());
    }

    @Test
    public void testPutKingInCheckGreenPawn1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,12,new Pawn('G'));
        board.setBox(7,13,new King('G'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'G';
        teammate.color = 'B';
        player1.teammate = teammate;
        board.setBox(2,8,new Bishop('R'));

        // Tests if the pawn moves, knowing it will put the king in check
        Move move1 = new Move(player1,"M8/L8");
        Assert.assertTrue(board.getBox(6,12).getPiece().isValidMove(move1,board));
        Assert.assertNull(board.getBox(6,11).getPiece());
        Assert.assertEquals("P", board.getBox(6, 12).getPiece().p);
    }

    @Test
    public void testPutKingInCheckGreenPawn2() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,12,new Pawn('G'));
        board.setBox(7,13,new King('G'));

        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'G';
        teammate.color = 'B';
        player1.teammate = teammate;

        // Tests if the pawn moves, knowing it will not put the king in check
        board.setBox(1,8,new Bishop('R'));
        Move move2 = new Move(player1,"M8/L8");
        board.getBox(6,12).getPiece().move(move2,board);
        Assert.assertNotNull(board.getBox(6,11).getPiece());
        Assert.assertEquals("P", board.getBox(6, 11).getPiece().p);
        Assert.assertNull(board.getBox(6,12).getPiece());
    }

    @Test
    public void testPromotionRedPawn() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(12,8,new Pawn('R'));
        Player player1 = new Player();
        player1.color = 'R';
        int x = 12;
        int y = 8;

        // Tests if the pawn promotes to a queen after reaching rank 8
        String[] coordinates = {"I2/I3","I3/I4","I4/I5","I5/I6","I6/I7","I7/I8"};
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            x--;
        }
        Assert.assertEquals("Q",board.getBox(6,8).getPiece().p);
    }

    @Test
    public void testPromotionBluePawn() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,1,new Pawn('B'));
        Player player1 = new Player();
        player1.color = 'B';
        int x = 6;
        int y = 1;

        // Tests if the pawn promotes to a queen after reaching rank 8
        String[] coordinates = {"B8/C8","C8/D8","D8/E8","E8/F8","F8/G8","G8/H8"};
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            y++;
        }
        Assert.assertEquals("Q",board.getBox(6,7).getPiece().p);
    }

    @Test
    public void testPromotionYellowPawn() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(1,6,new Pawn('Y'));
        Player player1 = new Player();
        player1.color = 'Y';
        int x = 1;
        int y = 6;

        // Tests if the pawn promotes to a queen after reaching rank 8
        String[] coordinates = {"G13/G12","G12/G11","G11/G10","G10/G9","G9/G8","G8/G7"};
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            x++;
        }
        Assert.assertEquals("Q",board.getBox(7,6).getPiece().p);
    }

    @Test
    public void testPromotionGreenPawn() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(6,12,new Pawn('G'));
        Player player1 = new Player();
        player1.color = 'G';
        int x = 6;
        int y = 12;

        // Tests if the pawn promotes to a queen after reaching rank 8
        String[] coordinates = {"M8/L8","L8/K8","K8/J8","J8/I8","I8/H8","H8/G8"};
        for (String coordinate : coordinates) {
            Move move1 = new Move(player1, coordinate);
            board.getBox(x, y).getPiece().move(move1, board);
            y--;
        }
        Assert.assertEquals("Q",board.getBox(6,6).getPiece().p);
    }

    @Test
    public void testInvalidMoves1() {
        ChessBoard board = new ChessBoard(0);
        board.setBox(9,6,new Pawn('B'));
        Player player1 = new Player();
        Player teammate = new Player();
        player1.color = 'B';
        teammate.color = 'G';
        player1.teammate = teammate;

        // Tests if all invalid square coordinates (corner squares) are recognized as invalid (testing only one color is
        // sufficient since every color goes through this part of the code)
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
        board.setBox(9,6,new Pawn('B'));
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
        board.setBox(9,6,new Pawn('R'));
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
        board.setBox(9,6,new Pawn('Y'));
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
        board.setBox(9,6,new Pawn('G'));
        for (String s : coordinates2) {
            Move move1 = new Move(player1, s);
            Assert.assertFalse(board.getBox(9, 6).getPiece().isValidMove(move1, board));
        }
    }

}