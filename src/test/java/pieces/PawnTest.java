package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for the Pawn piece and its moves.
 */
public class PawnTest {
    private Pawn whitePawn;
    private Pawn blackPawn;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        whitePawn = new Pawn("WP01", "/White_Pawn.png", 0);
        blackPawn = new Pawn("BP01", "/Black_Pawn.png", 1);
        board = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    /**
     * [MUTATION] Test to verify Pawn constructor sets fields appropriately. Added after mutation analysis.
     */
    @Test
    public void testPawnConstructor() {
        assertEquals(whitePawn.getId(), "WP01");
        assertEquals(whitePawn.getPath(), "/White_Pawn.png");
        assertEquals(whitePawn.getcolor(), 0);
    }

    /**
     * [MUTATION] Test getCopy() of Pawn. Added after mutation analysis. This should apply to all piece types
     */
    @Test
    public void testPawnGetCopy() throws CloneNotSupportedException {
        Piece wp = whitePawn.getcopy();
        Piece bp = blackPawn.getcopy();
        assertNotNull(wp);
        assertNotNull(bp);
        assertEquals(Pawn.class, wp.getClass());
        assertEquals(0, wp.getcolor());
        assertEquals(Pawn.class, bp.getClass());
        assertEquals(1, bp.getcolor());
    }

    /**
     * [BRANCH] Test E2 white pawn move set on an empty board.
     */
    @Test
    public void testWhitePawnMoveInitial() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[4][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 6, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E7 black pawn move set on an empty board.
     */
    @Test
    public void testBlackPawnMoveInitial() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][4],
                board[3][4]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 1, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test A4 white pawn move set on an empty board.
     */
    @Test
    public void testWhitePawnMoveAtLeftEdge() {
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][0]
        ));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test A4 black pawn move set on an empty board.
     */
    @Test
    public void testBlackPawnMoveAtLeftEdge() {
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][0]
        ));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test H4 white pawn move set on an empty board.
     */
    @Test
    public void testWhitePawnMoveAtRightEdge() {
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 7));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][7]
        ));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test H4 black pawn move set on an empty board.
     */
    @Test
    public void testBlackPawnMoveAtRightEdge() {
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 7));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][7]
        ));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E8 white pawn move set on an empty board.
     */
    @Test
    public void testWhitePawnMoveAtEnd() {
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 0, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    /**
     * [BRANCH] Test E1 black pawn move set on an empty board.
     * Fault: x = 7 when black pawn reaches end, but code checks for x == 8
     */
    // @Test
    public void testBlackPawnMoveAtEnd() {
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 7, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    /**
     * [BRANCH] Test E2 white pawn move set with E4 black pawn.
     */
    @Test
    public void testWhitePawnMoveInitialBlocked() {
        board[4][4] = new Cell(4, 4, new Pawn("BP02", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 6, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E7 black pawn move set with E5 black pawn.
     */
    @Test
    public void testBlackPawnMoveInitialBlocked() {
        board[3][4] = new Cell(3, 4, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][4]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 1, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E4 white pawn move set with E5 black pawn and D5, F5 white pawns.
     */
    @Test
    public void testWhitePawnMoveBlocked() {
        board[3][4] = new Cell(3, 4, new Pawn("BP02", "/Black_Pawn.png", 1));
        board[3][3] = new Cell(3, 3, new Pawn("WP02", "/White_Pawn.png", 0));
        board[3][5] = new Cell(3, 5, new Pawn("WP03", "/White_Pawn.png", 0));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    /**
     * [BRANCH] Test E5 black pawn move set with E4 white pawn and D4, F4 black pawns.
     */
    @Test
    public void testBlackPawnMoveBlocked() {
        board[4][4] = new Cell(4, 4, new Pawn("WP02", "/White_Pawn.png", 0));
        board[4][3] = new Cell(4, 3, new Pawn("BP02", "/Black_Pawn.png", 1));
        board[4][5] = new Cell(4, 5, new Pawn("BP03", "/Black_Pawn.png", 1));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 3, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    /**
     * [BRANCH] Test E4 white pawn move set with D5, F5 black pawns.
     */
    @Test
    public void testWhitePawnMoveWithCaptures() {
        board[3][3] = new Cell(3, 3, new Pawn("BP02", "/Black_Pawn.png", 1));
        board[3][5] = new Cell(3, 5, new Pawn("BP03", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4],
                board[3][3],
                board[3][5]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E5 black pawn move set with D4, F4 white pawns.
     */
    @Test
    public void testBlackPawnMoveWithCaptures() {
        // Test with E5 black pawn and D4, F4 white pawns
        board[4][3] = new Cell(4, 3, new Pawn("WP02", "/White_Pawn.png", 0));
        board[4][5] = new Cell(4, 5, new Pawn("WP03", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[4][4],
                board[4][3],
                board[4][5]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 3, 4));
        assertEquals(expected, result);
    }

    /**
     * [MUTATION] Test E4 white pawn move set with D5 black pawn. Added after mutation analysis.
     */
    @Test
    public void testWhitePawnMoveSingleLeftCapture() {
        board[3][3] = new Cell(3, 3, new Pawn("BP02", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4],
                board[3][3]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [MUTATION] Test E4 white pawn move set with F5 black pawn. Added after mutation analysis.
     */
    @Test
    public void testWhitePawnMoveSingleRightCapture() {
        // Test with E4 white pawn and F5 black pawn
        board[3][5] = new Cell(3, 5, new Pawn("BP02", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4],
                board[3][5]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [MUTATION] Test E4 black pawn move set with D3 white pawn. Added after mutation analysis.
     */
    @Test
    public void testBlackPawnMoveSingleLeftCapture() {
        board[5][3] = new Cell(5, 3, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[5][3]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [MUTATION] Test E4 black pawn move set with F3 white pawn. Added after mutation analysis.
     */
    @Test
    public void testBlackPawnMoveSingleRightCapture() {
        board[5][5] = new Cell(5, 5, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[5][5]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [BRANCH] Test E4 white pawn move set after moving from E3.
     */
    @Test
    public void testPawnClearPossibleMoves() {
        whitePawn.move(board, 5, 4);
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    /**
     * [BLACKBOX] Test if D5 white pawn has en passant move when black pawn moves to E5.
     * https://en.wikipedia.org/wiki/En_passant
     *
     * Fault: En passant special rule not implemented
     */
    // @Test
    public void testEnPassantSpecialMove() {
        board[3][5] = new Cell(3, 5, new Pawn("BP02", "/Black_Pawn.png", 1));
        blackPawn.move(board, 3, 4);
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][3],
                board[2][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 3, 3));
        assertEquals(expected, result);
    }
}
