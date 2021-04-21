package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTest {

    private static final String ID = "WB01";
    private static final String RES = "/White_Bishop.png";

    private Bishop bishop;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        bishop = new Bishop(ID, RES,0);
        board = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    @Test
    public void testBishopConstructor() {
        assertEquals(bishop.getId(), ID);
        assertEquals(bishop.getPath(), RES);
        assertEquals(bishop.getcolor(), 0);
    }

    /**
     * Test Bishop starting at G6 on empty board
     */
    @Test
    public void testBishopMove() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[1][5],
                board[0][4],
                board[3][7],
                board[1][7],
                board[3][5],
                board[4][4],
                board[5][3],
                board[6][2],
                board[7][1]
        ));
        Set<Cell> result = new HashSet<>(bishop.move(board, 2, 6));
        assertEquals(expected, result);
    }

    /**
     * Test Bishop starting at C8 on empty board
     */
    @Test
    public void testBishopMoveAtEdge() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[1][1],
                board[2][0],
                board[1][3],
                board[2][4],
                board[3][5],
                board[4][6],
                board[5][7]
        ));
        Set<Cell> result = new HashSet<>(bishop.move(board, 0, 2));
        assertEquals(expected, result);
    }

    /**
     * Test Bishop starting at A1 on empty board
     */
    @Test
    public void testBishopMoveAtCorner() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][7],
                board[1][6],
                board[2][5],
                board[3][4],
                board[4][3],
                board[5][2],
                board[6][1]
        ));
        Set<Cell> result = new HashSet<>(bishop.move(board, 7, 0));
        assertEquals(expected, result);
    }

    /**
     * Test Bishop starting at B6 blocked on all diagonals except lower-left diagonal
     */
    @Test
    public void testBishopMoveBlocked() {
        board[1][0] = new Cell(1, 0, new Pawn(ID, RES, 0));
        board[1][2] = new Cell(1, 2, new Knight(ID, RES, 0));
        board[3][2] = new Cell(3, 2, new Pawn(ID, RES, 0));
        ArrayList<Cell> expected = new ArrayList<>(Collections.singletonList(board[3][0]));
        ArrayList<Cell> result = bishop.move(board, 2, 1);
        assertEquals(expected, result);
    }

    /**
     * Test Bishop starting at F8 blocked on lower-left and lower-right diagonals
     */
    @Test
    public void testBishopMoveBlockedAllSides() {
        board[1][4] = new Cell(1, 4, new Pawn(ID, RES, 0));
        board[1][6] = new Cell(1, 6, new Rook(ID, RES, 0));
        ArrayList<Cell> result = bishop.move(board, 0, 5);
        assertEquals(new ArrayList<Cell>(), result);
    }

    /**
     * Test Bishop starting at D5 with an enemy piece in lower-right diagonal path
     */
    @Test
    public void testBishopMoveWithOneCapture() {
        board[6][6] = new Cell(6, 6, new Pawn(ID, RES, 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][0],
                board[1][1],
                board[2][2],
                board[4][4],
                board[5][5],
                board[6][6],
                board[0][6],
                board[1][5],
                board[2][4],
                board[4][2],
                board[5][1],
                board[6][0]
        ));
        Set<Cell> result = new HashSet<>(bishop.move(board, 3, 3));
        assertEquals(expected, result);
    }

    /**
     * Test Bishop starting at D5 with enemy pieces in 3 diagonal paths
     */
    @Test
    public void testBishopMoveWithCaptures() {
        board[2][2] = new Cell(2, 2, new Pawn(ID, RES, 1));
        board[1][5] = new Cell(1, 5, new Queen(ID, RES, 1));
        board[5][1] = new Cell(5, 1, new Knight(ID, RES, 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][2],
                board[4][4],
                board[5][5],
                board[6][6],
                board[7][7],
                board[1][5],
                board[2][4],
                board[4][2],
                board[5][1]
        ));
        Set<Cell> result = new HashSet<>(bishop.move(board, 3, 3));
        assertEquals(expected, result);
    }
}
