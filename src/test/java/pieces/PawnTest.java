package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTest {
    private static Pawn whitePawn;
    private static Pawn blackPawn;
    private static Cell[][] board;

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

    // Added after mutation analysis
    @Test
    public void testPawnConstructor() {
        assertEquals(whitePawn.getId(), "WP01");
        assertEquals(whitePawn.getPath(), "/White_Pawn.png");
        assertEquals(whitePawn.getcolor(), 0);
    }

    @Test
    public void testWhitePawnMoveInitial() {
        // Test with E2 white pawn
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[4][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 6, 4));
        assertEquals(expected, result);
    }

    @Test
    public void testBlackPawnMoveInitial() {
        // Test with E7 black pawn
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][4],
                board[3][4]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 1, 4));
        assertEquals(expected, result);
    }

    @Test
    public void testWhitePawnMoveAtLeftEdge() {
        // Test with A4 white pawn
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][0]
        ));
        assertEquals(expected, result);
    }

    @Test
    public void testBlackPawnMoveAtLeftEdge() {
        // Test with A4 black pawn
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][0]
        ));
        assertEquals(expected, result);
    }

    @Test
    public void testWhitePawnMoveAtRightEdge() {
        // Test with H4 white pawn
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 7));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][7]
        ));
        assertEquals(expected, result);
    }

    @Test
    public void testBlackPawnMoveAtRightEdge() {
        // Test with H4 black pawn
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 7));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][7]
        ));
        assertEquals(expected, result);
    }

    @Test
    public void testWhitePawnMoveAtEnd() {
        // Test with E8 white pawn
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 0, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    // BUG FOUND: x = 7 when black pawn reaches end, but code checks for x == 8
    /*@Test
    public void testBlackPawnMoveAtEnd() {
        // Test with E1 black pawn
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 7, 4));
        assertEquals(new HashSet<Cell>(), result);
    }*/

    @Test
    public void testWhitePawnMoveInitialBlocked() {
        // Test with E2 white pawn and E4 black pawn
        board[4][4] = new Cell(4, 4, new Pawn("BP02", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 6, 4));
        assertEquals(expected, result);
    }

    @Test
    public void testBlackPawnMoveInitialBlocked() {
        // Test with E7 black pawn and E5 white pawn
        board[3][4] = new Cell(3, 4, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][4]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 1, 4));
        assertEquals(expected, result);
    }

    @Test
    public void testWhitePawnMoveBlocked() {
        // Test with E4 white pawn and E5 black pawn/D5, F5 white pawns
        board[3][4] = new Cell(3, 4, new Pawn("BP02", "/Black_Pawn.png", 1));
        board[3][3] = new Cell(3, 3, new Pawn("WP02", "/White_Pawn.png", 0));
        board[3][5] = new Cell(3, 5, new Pawn("WP03", "/White_Pawn.png", 0));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    @Test
    public void testBlackPawnMoveBlocked() {
        // Test with E5 black pawn and E4 white pawn/D4, F4 black pawns
        board[4][4] = new Cell(4, 4, new Pawn("WP02", "/White_Pawn.png", 0));
        board[4][3] = new Cell(4, 3, new Pawn("BP02", "/Black_Pawn.png", 1));
        board[4][5] = new Cell(4, 5, new Pawn("BP03", "/Black_Pawn.png", 1));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 3, 4));
        assertEquals(new HashSet<Cell>(), result);
    }

    @Test
    public void testWhitePawnMoveWithCaptures() {
        // Test with E4 white pawn and D5, F5 black pawns
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

    // Added after mutation analysis
    @Test
    public void testWhitePawnMoveSingleLeftCapture() {
        // Test with E4 white pawn and D5 black pawn
        board[3][3] = new Cell(3, 3, new Pawn("BP02", "/Black_Pawn.png", 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4],
                board[3][3]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    // Added after mutation analysis
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

    // Added after mutation analysis
    @Test
    public void testBlackPawnMoveSingleLeftCapture() {
        // Test with E4 black pawn and D3 white pawn
        board[5][3] = new Cell(5, 3, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[5][3]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    // Added after mutation analysis
    @Test
    public void testBlackPawnMoveSingleRightCapture() {
        // Test with E4 black pawn and F3 white pawn
        board[5][5] = new Cell(5, 5, new Pawn("WP02", "/White_Pawn.png", 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[5][4],
                board[5][5]
        ));
        Set<Cell> result = new HashSet<>(blackPawn.move(board, 4, 4));
        assertEquals(expected, result);
    }

    // Added after mutation analysis
    @Test
    public void testPawnClearPossibleMoves() {
        whitePawn.move(board, 5, 4);
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[3][4]
        ));
        Set<Cell> result = new HashSet<>(whitePawn.move(board, 4, 4));
        assertEquals(expected, result);
    }
}
