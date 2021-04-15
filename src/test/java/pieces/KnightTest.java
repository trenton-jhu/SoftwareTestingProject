package pieces;

import chess.Cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KnightTest {

    private static Knight knight;
    private static Cell[][] board;

    @BeforeAll
    public static void setup() {
        knight = new Knight("WK01","/White_Knight.png",0);
        board = new chess.Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    @Test
    public void KnightMove() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[6][5]);
        expected.add(board[5][6]);
        Set<Cell> result = new HashSet<>(knight.move(board, 7, 7));
        assertEquals(expected, result);
    }

    @Test
    public void KnightMoveBlockedByOwnPiece() {
		Pawn pawn1 = new Pawn("WP01","/White_Pawn.png",0);
		Pawn pawn2 = new Pawn("BP01","/Black_Pawn.png",1);
        Cell c1 = new Cell(1, 2, pawn1);
        Cell c2 = new Cell(2, 1, pawn2);
        board[1][2] = c1;
        board[2][1] = c2;
        board[2][1] = c2;
        
        Set<Cell> expected = new HashSet<>();
        expected.add(board[2][1]);
        Set<Cell> result = new HashSet<>(knight.move(board, 0, 0));
        assertEquals(expected, result);
    }

    // @Test
    // public void KnightTestInvalidMove() {
    //     Set<Cell> result = new HashSet<>(knight.move(board, -1, -1));
    //     assertEquals(new HashSet<Cell>(), result);
    //     // Should return empty
    // }
}
