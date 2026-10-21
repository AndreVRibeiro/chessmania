package gamelogic;


import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    @Test
    public void testInvalidMoveCommands1() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the move is accepted if it is an empty String ""
        Move move = new Move(game.getPlayerFromList(0),"");
        game.move(game.getPlayerFromList(0),move);
        Assert.assertNotNull(board.getBox(12,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }

    @Test
    public void testInvalidMoveCommands2() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the move is accepted if it is a String with a word
        Move move0 = new Move(game.getPlayerFromList(0),"halloWELT");
        game.move(game.getPlayerFromList(0),move0);
        Assert.assertNotNull(board.getBox(12,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }

    @Test
    public void testInvalidMoveCommands3() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the move is accepted if it is a String with a word and a '/'
        Move move01 = new Move(game.getPlayerFromList(0),"hallo/WELT");
        game.move(game.getPlayerFromList(0),move01);
        Assert.assertNotNull(board.getBox(12,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }



    @Test
    public void testInvalidMoveCommands4() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the move is accepted if it doesn't contain '/'
        Move move1 = new Move(game.getPlayerFromList(0),"G2G4");
        game.move(game.getPlayerFromList(0),move1);
        Assert.assertNull(board.getBox(11,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }



    @Test
    public void testInvalidMoveCommands5() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if unplayable squares (are not part of the chessboard) are recognized and handled
        Move move2 = new Move(game.getPlayerFromList(0), "G2/B1");
        game.move(game.getPlayerFromList(0), move2);
        Assert.assertNull(board.getBox(11, 6).getPiece());
        Assert.assertEquals(pawn, board.getBox(12, 6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0), game.getCurrentTurn());

        Move move3 = new Move(game.getPlayerFromList(0), "B1/G4");
        game.move(game.getPlayerFromList(0), move3);
        Assert.assertNull(board.getBox(11, 6).getPiece());
        Assert.assertEquals(pawn, board.getBox(12, 6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0), game.getCurrentTurn());
    }


    @Test
    public void testInvalidMoveCommands6() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the program knows that there is no piece to be moved on the first square
        Move move4 = new Move(game.getPlayerFromList(0),"G1/G4");
        game.move(game.getPlayerFromList(0),move4);
        Assert.assertNull(board.getBox(11,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }

    @Test
    public void testInvalidMoveCommands7() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if the player can move pieces that don't belong to them
        Pawn pawn2 = new Pawn('B');
        board.setBox(5,1,pawn2);
        Move move5 = new Move(game.getPlayerFromList(0),"B9/C9");
        game.move(game.getPlayerFromList(0),move5);
        Assert.assertNull(board.getBox(5,2).getPiece());
        Assert.assertEquals(pawn2,board.getBox(5,1).getPiece());
        Assert.assertEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }

    @Test
    public void testInvalidMoveCommands8() {
        ChessBoard board = new ChessBoard(0);
        Game game = new Game(board);
        Pawn pawn = new Pawn('R');
        board.setBox(12,6,pawn);

        // Tests if player can make a move when it is not their turn
        game.nextTurn();
        Move move6 = new Move(game.getPlayerFromList(0),"G2/G4");
        game.move(game.getPlayerFromList(0),move6);
        Assert.assertNull(board.getBox(11,6).getPiece());
        Assert.assertEquals(pawn,board.getBox(12,6).getPiece());
        Assert.assertNotEquals(game.getPlayerFromList(0),game.getCurrentTurn());
    }

}