package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing class for the Rook piece and its moves.
 */
public class RookTest {
    private Rook rook;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        rook = new Rook("WR01", "/White_Rook.png", 0);
        board = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    /**
     * [MUTATION] Test to verify Rook constructor sets fields appropriately. Added after mutation analysis.
     */
    @Test
    public void testRookConstructor() {
        assertEquals(rook.getId(), "WR01");
        assertEquals(rook.getPath(), "/White_Rook.png");
        assertEquals(rook.getcolor(), 0);
    }

    /**
     * [BRANCH] Test E4 white rook move set on an empty board.
     */
    @Test
    public void testRookMove() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[4][0],
                board[4][1],
                board[4][2],
                board[4][3],
                board[4][5],
                board[4][6],
                board[4][7],
                board[0][4],
                board[1][4],
                board[2][4],
                board[3][4],
                board[5][4],
                board[6][4],
                board[7][4]
        ));
        Set<Cell> result = new HashSet<>(rook.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E4 white rook move set when immediately surrounded on all 4 sides by white pieces.
     */
    @Test
    public void testRookMoveBlocked() {
        board[3][4] = new Cell(3, 4, new Pawn("WP01", "/White_Pawn.png", 0));   // E5 pawn
        board[5][4] = new Cell(5, 4, new Pawn("WP02", "/White_Pawn.png", 0));   // E3 pawn
        board[4][3] = new Cell(4, 3, new Pawn("WP03", "/White_Pawn.png", 0));   // D4 pawn
        board[4][5] = new Cell(4, 5, new Pawn("WP04", "/White_Pawn.png", 0));   // F4 pawn
        Set<Cell> result = new HashSet<>(rook.move(board, 4, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    /**
     * [BRANCH] Test E4 white rook move set when immediately surrounded on all 4 sides by black pieces.
     */
    @Test
    public void testRookMoveWithCaptures() {
        board[3][4] = new Cell(3, 4, new Pawn("BP01", "/Black_Pawn.png", 1));   // E5 pawn
        board[5][4] = new Cell(5, 4, new Pawn("BP02", "/Black_Pawn.png", 1));   // E3 pawn
        board[4][3] = new Cell(4, 3, new Pawn("BP03", "/Black_Pawn.png", 1));   // D4 pawn
        board[4][5] = new Cell(4, 5, new Pawn("BP04", "/Black_Pawn.png", 1));   // F4 pawn
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4],
                board[5][4],
                board[4][3],
                board[4][5]
        ));
        Set<Cell> result = new HashSet<>(rook.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [MUTATION] Test E4 rook move set after moving from E5. Added after mutation analysis.
     */
    @Test
    public void testRookClearPossibleMoves() {
        rook.move(board, 3, 4);
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[4][0],
                board[4][1],
                board[4][2],
                board[4][3],
                board[4][5],
                board[4][6],
                board[4][7],
                board[0][4],
                board[1][4],
                board[2][4],
                board[3][4],
                board[5][4],
                board[6][4],
                board[7][4]
        ));
        Set<Cell> result = new HashSet<>(rook.move(board, 4, 4));
        assertEquals(expected, result);
    }
}
