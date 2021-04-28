package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for Cell objects.
 */
public class CellTest {

    private Cell whiteCell;  // White cell refers to color of cell, not piece it holds
    private Cell blackCell;  // Same here, color of cell is black

    @BeforeEach
    public void setup() {
        Pawn whitePawn = new Pawn("WP01", "/White_Pawn.png", 0);
        whiteCell = new Cell(4, 4, whitePawn);
        King blackKing = new King("BK", "/Black_King.png", 1, 2, 3);
        blackCell = new Cell(2, 3, blackKing);
    }

    /**
     * [BRANCH] Test if toString() returns correct output.
     */
    @Test
    public void testToString() {
        assertEquals("(4, 4)", whiteCell.toString());
    }

    /**
     * [BRANCH] Test if copy constructor with a white cell returns a new Cell.
     */
    @Test
    public void testWhiteCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(whiteCell);
        assertNotEquals(cellClone, whiteCell);
    }

    /**
     * [BRANCH] Test if copy constructor with a black cell returns a new Cell.
     */
    @Test
    public void testBlackCellClone() throws CloneNotSupportedException {
        Cell cellClone = new Cell(blackCell);
        assertNotEquals(cellClone, blackCell);
    }

    /**
     * [BRANCH] Test if copy constructor works with a Cell that has no piece.
     */
    @Test
    public void testNoPieceCellClone() throws CloneNotSupportedException {
        Cell emptyCell = new Cell(5, 5, null);
        Cell cellClone = new Cell(emptyCell);
        assertNotEquals(cellClone, emptyCell);
    }

    /**
     * [BRANCH] Test if removePiece() removes Kings.
     */
    @Test
    public void testRemovePieceNotKing() {
        whiteCell.removePiece();
        assertNull(whiteCell.getpiece());
    }

    /**
     * [BRANCH] Test if removePiece() removes non-King pieces.
     */
    @Test
    public void testRemovePieceKing() {
        blackCell.removePiece();
        assertNull(blackCell.getpiece());
    }

    /**
     * [BRANCH] Test if select() on a cell sets isSelected field properly.
     */
    @Test
    public void testSelect() {
        whiteCell.select();
        assertTrue(whiteCell.isselected());
    }

    /**
     * [BRANCH] Test if deselect() on a cell sets isSelected field properly.
     */
    @Test
    public void testDeselect() {
        whiteCell.select();
        whiteCell.deselect();
        assertFalse(whiteCell.isselected());
    }

    /**
     * [BRANCH] Test if setpossibledestination() on a cell sets ispossibledestination field properly.
     */
    @Test
    public void testSetPossibleDestination() {
        whiteCell.setpossibledestination();
        assertTrue(whiteCell.ispossibledestination());
    }

    /**
     * [BRANCH] Test if removepossibledestination() on a cell sets ispossibledestination field properly.
     */
    @Test
    public void testRemovePossibleDestination() {
        whiteCell.setpossibledestination();
        whiteCell.removepossibledestination();
        assertFalse(whiteCell.ispossibledestination());
    }

    /**
     * [BRANCH] Test if setcheck() on a cell sets ischeck field properly.
     */
    @Test
    public void testBlackCellSetCheck() {
        blackCell.setcheck();
        assertTrue(blackCell.ischeck());
    }

    /**
     * [BRANCH] Test if removecheck() on a black cell sets ischeck field properly.
     */
    @Test
    public void testBlackCellRemoveCheck() {
        blackCell.setcheck();
        blackCell.removecheck();
        assertFalse(blackCell.ischeck());
    }

    /**
     * [BRANCH] Test if removecheck() on a white cell sets ischeck field properly.
     */
    @Test
    public void testWhiteCellRemoveCheck() {
        // whiteCell does not contain a King, but code does not check for this
        whiteCell.setcheck();
        whiteCell.removecheck();
        assertFalse(whiteCell.ischeck());
    }
}
