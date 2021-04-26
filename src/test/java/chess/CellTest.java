package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private static Cell whiteCell;  // White cell refers to color of cell, not piece it holds
    private static Cell blackCell;  // Same here, color of cell is black

    @BeforeEach
    public void setup() {
        Pawn whitePawn = new Pawn("WP01", "/White_Pawn.png", 0);
        whiteCell = new Cell(4, 4, whitePawn);
        King blackKing = new King("BK", "/Black_King.png", 1, 2, 3);
        blackCell = new Cell(2, 3, blackKing);
    }

    @Test
    public void testToString() {
        assertEquals("(4, 4)", whiteCell.toString());
    }

    @Test
    public void testWhiteCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(whiteCell);
        assertNotEquals(cellClone, whiteCell);
    }

    @Test
    public void testBlackCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(blackCell);
        assertNotEquals(cellClone, blackCell);
    }

    @Test
    public void testNoPieceCellClone() throws CloneNotSupportedException {
        Cell emptyCell = new Cell(5, 5, null);
        Cell cellClone = new Cell(emptyCell);
        assertNotEquals(cellClone, emptyCell);
    }

    @Test
    public void testRemovePieceNotKing() {
        whiteCell.removePiece();
        assertNull(whiteCell.getpiece());
    }

    @Test
    public void testRemovePieceKing() {
        blackCell.removePiece();
        assertNull(blackCell.getpiece());
    }

    @Test
    public void testSelect() {
        whiteCell.select();
        assertTrue(whiteCell.isselected());
    }

    @Test
    public void testDeselect() {
        whiteCell.select();
        whiteCell.deselect();
        assertFalse(whiteCell.isselected());
    }

    @Test
    public void testSetPossibleDestination() {
        whiteCell.setpossibledestination();
        assertTrue(whiteCell.ispossibledestination());
    }

    @Test
    public void testRemovePossibleDestination() {
        whiteCell.setpossibledestination();
        whiteCell.removepossibledestination();
        assertFalse(whiteCell.ispossibledestination());
    }

    @Test
    public void testBlackCellSetCheck() {
        blackCell.setcheck();
        assertTrue(blackCell.ischeck());
    }

    @Test
    public void testBlackCellRemoveCheck() {
        blackCell.setcheck();
        blackCell.removecheck();
        assertFalse(blackCell.ischeck());
    }

    @Test
    public void testWhiteCellRemoveCheck() {
        // whiteCell does not contain a King, but code does not check for this
        whiteCell.setcheck();
        whiteCell.removecheck();
        assertFalse(whiteCell.ischeck());
    }
}
