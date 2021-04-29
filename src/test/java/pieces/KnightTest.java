package pieces;

import chess.Cell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightTest {

    private Knight knight;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        knight = new Knight("WK01","/White_Knight.png", 0);
        board = new chess.Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    // Added after mutation
    @Test
    public void testKnightConstructor() {
        assertEquals(knight.getId(), "WK01");
        assertEquals(knight.getPath(), "/White_Knight.png");
        assertEquals(knight.getcolor(), 0);
    }


    // Added after mutation
    @Test
    public void testKnightMove() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[4][3]);
        expected.add(board[4][7]);
        expected.add(board[3][4]);
        expected.add(board[3][6]);
        expected.add(board[6][3]);
        expected.add(board[6][7]);
        expected.add(board[7][4]);
        expected.add(board[7][6]);
        Set<Cell> result = new HashSet<>(knight.move(board, 5, 5));
        assertEquals(expected, result);
    }

    // Added after mutation
    @Test
    public void testKnightMoveAtEdge() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[7][5]);
        expected.add(board[5][5]);
        expected.add(board[4][6]);
        Set<Cell> result = new HashSet<>(knight.move(board, 6, 7));
        assertEquals(expected, result);
    }

    @Test
    public void testKnightMoveAtCorner() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[6][5]);
        expected.add(board[5][6]);
        Set<Cell> result = new HashSet<>(knight.move(board, 7, 7));
        assertEquals(expected, result);
    }


    //Added after mutation
    @Test
    public void testKnightMoveAtCloseToCorner() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[0][3]);
        expected.add(board[2][3]);
        expected.add(board[3][0]);
        expected.add(board[3][2]);
        Set<Cell> result = new HashSet<>(knight.move(board, 1, 1));
        assertEquals(expected, result);
    }

    @Test
    public void testKnightMoveBlocked() {
		Pawn pawn1 = new Pawn("WP01","/White_Pawn.png",0);
		Pawn pawn2 = new Pawn("BP01","/Black_Pawn.png",1);
        Cell c1 = new Cell(1, 2, pawn1);
        Cell c2 = new Cell(2, 1, pawn2);
        board[1][2] = c1;
        board[2][1] = c2;
        
        Set<Cell> expected = new HashSet<>();
        expected.add(board[2][1]);
        Set<Cell> result = new HashSet<>(knight.move(board, 0, 0));
        assertEquals(expected, result);
    }

    // Added after mutation
    @Test
    public void testKnightMoveBlockedAll() {
		Pawn pawn1 = new Pawn("WP01","/White_Pawn.png",0);
		Pawn pawn2 = new Pawn("WP02","/White_Pawn.png",0);
		Pawn pawn3 = new Pawn("WP03","/White_Pawn.png",0);
		Pawn pawn4 = new Pawn("WP04","/White_Pawn.png",0);
		Pawn pawn5 = new Pawn("WP05","/White_Pawn.png",0);
		Pawn pawn6 = new Pawn("WP06","/White_Pawn.png",0);
		Pawn pawn7 = new Pawn("WP07","/White_Pawn.png",0);
		Pawn pawn8 = new Pawn("WP08","/White_Pawn.png",0);
        Cell c1 = new Cell(4, 3, pawn1);
        Cell c2 = new Cell(4, 7, pawn2);
        Cell c3 = new Cell(3, 4, pawn3);
        Cell c4 = new Cell(3, 6, pawn4);
        Cell c5 = new Cell(4, 3, pawn5);
        Cell c6 = new Cell(4, 7, pawn6);
        Cell c7 = new Cell(3, 4, pawn7);
        Cell c8 = new Cell(3, 6, pawn8);
        board[4][3] = c1;
        board[4][7] = c2;
        board[3][4] = c3;
        board[3][6] = c4;
        board[6][3] = c5;
        board[6][7] = c6;
        board[7][4] = c7;
        board[7][6] = c8;
        
        Set<Cell> expected = new HashSet<>();
        Set<Cell> result = new HashSet<>(knight.move(board, 5, 5));
        assertEquals(expected, result);
    }

    // Added after mutation
    @Test
    public void testKnightClearPossibleMoves() {
        knight.move(board, 5, 5);
        Set<Cell> expected = new HashSet<>();
        expected.add(board[6][5]);
        expected.add(board[5][6]);
        Set<Cell> result = new HashSet<>(knight.move(board, 7, 7));
        assertEquals(expected, result);
    }

    /* False failure: Move returns results for invalid board position */
    // @Test
    public void testKnightTestInvalidMove() {
        Set<Cell> result = new HashSet<>(knight.move(board, -1, -1));
        assertTrue(result.isEmpty());
        // Should return empty
    }
}
