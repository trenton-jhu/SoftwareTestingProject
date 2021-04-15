package pieces;

import chess.Cell;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
        expected.add(board[0][2]);
        expected.add(board[2][2]);
        expected.add(board[3][1]);
        Set<Cell> result = new HashSet<>(knight.move(board, 1, 0));
        assertEquals(expected, result);
    }

    @Test
    public void KnightMoveBlockedByOwnPiece() {
        Knight knight2 = new Knight("WK02","/White_Knight.png",0);
        Cell c = new Cell(1, 2, knight2);
        board[1][2] = c;
        
        Set<Cell> expected = new HashSet<>();
        expected.add(board[2][1]);
        Set<Cell> result = new HashSet<>(knight.move(board, 0, 0));
        assertEquals(expected, result);
    }
}
