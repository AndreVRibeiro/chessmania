package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class SquareTest {

    @Test
    public void testIsSquareOccupied1() {
        // Test if the square has a piece - TRUE
        Square square1 = new Square(new Pawn('R'),4,2,true);
        Assert.assertTrue(square1.isSquareOccupied());
    }

    @Test
    public void testIsSquareOccupied2() {
        // Test if the square has a piece - FALSE
        Square square2 = new Square(null,4,2,false);
        Assert.assertFalse(square2.isSquareOccupied());
    }

    @Test
    public void testSetPieceMethod() {
        // Test if the setPiece() method works
        Square square = new Square(null, 4,5, true);
        Pawn pawn = new Pawn('R');
        square.setPiece(pawn);
        Assert.assertNotNull(square.piece);
        Assert.assertEquals(pawn,square.piece);
    }

    @Test
    public void testSetPieceFunction() {
        // Test if the setPiece Function works
        Square square = new Square(null,4,5,true);

        square.setPiece('R','P');
        Assert.assertNotNull(square.piece);
        Assert.assertEquals('R', square.piece.color);
        Assert.assertEquals("P", square.piece.p);
    }

    @Test
    public void testGetPiece() {
        // Test if the method is able to return the Piece
        Pawn pawn = new Pawn('R');
        Square square = new Square(pawn,4,2,true);

        Assert.assertNotNull(square.piece);
        Assert.assertEquals(pawn, square.getPiece());
    }

    @Test
    public void testSetSquareEmpty() {
        // Test if the method is able to set a square as empty
        Square square = new Square(new Pawn('R'),4,2,true);

        Assert.assertNotNull(square.piece);
        square.setSquareEmpty();
        Assert.assertNull(square.piece);
    }
}