package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    private static Cell whiteCell;  // White cell refers to color of cell, not piece it holds
    private static Pawn whitePawn;
    private static Cell blackCell;  // Same here, color of cell is black
    private static King blackKing;

    @BeforeEach
    public void setup() {
        whitePawn = new Pawn("WP01", "/White_Pawn.png", 0);
        whiteCell = new Cell(4, 4, whitePawn);
        blackKing = new King("BK", "/Black_King.png", 1, 2, 3);
        blackCell = new Cell(2, 3, blackKing);
    }

    @Test
    public void testToString() {
        assertEquals("(4, 4)", whiteCell.toString());
    }

    @Test
    public void testWhiteCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(whiteCell);
        assertEquals(false, whiteCell.equals(cellClone));
    }

    @Test
    public void testBlackCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(blackCell);
        assertEquals(false, blackCell.equals(cellClone));
    }

    @Test
    public void testNoPieceCellClone() throws CloneNotSupportedException {
        Cell emptyCell = new Cell(5, 5, null);
        Cell cellClone = new Cell(emptyCell);
        assertEquals(false, emptyCell.equals(cellClone));
    }

    @Test
    public void testRemovePieceNotKing() {
        whiteCell.removePiece();
        assertEquals(null, whiteCell.getpiece());
    }

    @Test
    public void testRemovePieceKing() {
        blackCell.removePiece();
        assertEquals(null, blackCell.getpiece());
    }

    @Test
    public void testSelect() {
        whiteCell.select();
        assertEquals(true, whiteCell.isselected());
    }

    @Test
    public void testDeselect() {
        whiteCell.select();
        whiteCell.deselect();
        assertEquals(false, whiteCell.isselected());
    }

    @Test
    public void testSetPossibleDestination() {
        whiteCell.setpossibledestination();
        assertEquals(true, whiteCell.ispossibledestination());
    }

    @Test
    public void testRemovePossibleDestination() {
        whiteCell.setpossibledestination();
        whiteCell.removepossibledestination();
        assertEquals(false, whiteCell.ispossibledestination());
    }

    @Test
    public void testBlackCellSetCheck() {
        blackCell.setcheck();
        assertEquals(true, blackCell.ischeck());
    }

    @Test
    public void testBlackCellRemoveCheck() {
        blackCell.setcheck();
        blackCell.removecheck();
        assertEquals(false, blackCell.ischeck());
    }

    @Test
    public void testWhiteCellRemoveCheck() {
        // whiteCell does not contain a King, but code does not check for this
        whiteCell.setcheck();
        whiteCell.removecheck();
        assertEquals(false, whiteCell.ischeck());
    }
}
