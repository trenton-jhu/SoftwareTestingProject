package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueenTest {

    private static final String ID = "WQ";
    private static final String RES = "/White_Queen.png";

    private Queen queen;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        queen = new Queen(ID, RES,0);
        board = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    @Test
    public void testQueenMove() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][0],
                board[1][1],
                board[2][2],
                board[4][4],
                board[5][5],
                board[6][6],
                board[7][7],
                board[0][6],
                board[1][5],
                board[2][4],
                board[4][2],
                board[5][1],
                board[6][0],
                board[3][0],
                board[3][1],
                board[3][2],
                board[3][4],
                board[3][5],
                board[3][6],
                board[3][7],
                board[0][3],
                board[1][3],
                board[2][3],
                board[4][3],
                board[5][3],
                board[6][3],
                board[7][3]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 3, 3));
        assertEquals(expected, result);
    }

    @Test
    public void testQueenMoveAtEdge() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[1][1],
                board[2][0],
                board[1][3],
                board[2][4],
                board[3][5],
                board[4][6],
                board[5][7],
                board[0][0],
                board[0][1],
                board[0][3],
                board[0][4],
                board[0][5],
                board[0][6],
                board[0][7],
                board[1][2],
                board[2][2],
                board[3][2],
                board[4][2],
                board[5][2],
                board[6][2],
                board[7][2]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 0, 2));
        assertEquals(expected, result);
    }

    @Test
    public void testQueenMoveAtCorner() {
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][7],
                board[1][6],
                board[2][5],
                board[3][4],
                board[4][3],
                board[5][2],
                board[6][1],
                board[6][0],
                board[5][0],
                board[4][0],
                board[3][0],
                board[2][0],
                board[1][0],
                board[0][0],
                board[7][1],
                board[7][2],
                board[7][3],
                board[7][4],
                board[7][5],
                board[7][6],
                board[7][7]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 7, 0));
        assertEquals(expected, result);
    }

    @Test
    public void testQueenMoveBlocked() {
        board[1][0] = new Cell(1, 0, new Pawn(ID, RES, 0));
        board[1][2] = new Cell(1, 2, new Knight(ID, RES, 0));
        board[3][2] = new Cell(3, 2, new Pawn(ID, RES, 0));
        board[3][1] = new Cell(3, 1, new Rook(ID, RES, 0));
        board[2][2] = new Cell(2, 2, new Bishop(ID, RES, 0));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[2][0],
                board[3][0],
                board[1][1],
                board[0][1]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 2, 1));
        assertEquals(expected, result);
    }

    @Test
    public void testQueenMoveBlockedAllSides() {
        board[3][0] = new Cell(3, 0, new Pawn(ID, RES, 0));
        board[3][1] = new Cell(3, 1, new Pawn(ID, RES, 0));
        board[3][2] = new Cell(3, 2, new Rook(ID, RES, 0));
        board[5][0] = new Cell(5, 0, new Pawn(ID, RES, 0));
        board[5][1] = new Cell(5, 1, new Knight(ID, RES, 0));
        board[5][2] = new Cell(5, 2, new Pawn(ID, RES, 0));
        board[4][0] = new Cell(4, 0, new Bishop(ID, RES, 0));
        board[4][2] = new Cell(4, 2, new Pawn(ID, RES, 0));
        ArrayList<Cell> result = queen.move(board, 4, 1);
        assertEquals(new ArrayList<Cell>(), result);
    }

    @Test
    public void testQueenMoveWithOneCapture() {
        board[3][1] = new Cell(3, 1, new Pawn(ID, RES, 1));
        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][0],
                board[1][1],
                board[2][2],
                board[4][4],
                board[5][5],
                board[6][6],
                board[7][7],
                board[0][6],
                board[1][5],
                board[2][4],
                board[4][2],
                board[5][1],
                board[6][0],
                board[3][1],
                board[3][2],
                board[3][4],
                board[3][5],
                board[3][6],
                board[3][7],
                board[0][3],
                board[1][3],
                board[2][3],
                board[4][3],
                board[5][3],
                board[6][3],
                board[7][3]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 3, 3));
        assertEquals(expected, result);
    }

    @Test
    public void testQueenMoveWithCaptures() {
        board[0][0] = new Cell(0, 0, new Pawn(ID, RES, 1));
        board[2][3] = new Cell(2, 3, new Rook(ID, RES, 1));
        board[5][3] = new Cell(5, 3, new Knight(ID, RES, 1));
        board[5][1] = new Cell(5, 1, new Knight(ID, RES, 1));
        board[1][5] = new Cell(1, 5, new Pawn(ID, RES, 1));
        board[4][4] = new Cell(4, 4, new Pawn(ID, RES, 1));
        board[3][5] = new Cell(3, 5, new Bishop(ID, RES, 1));

        Set<Cell> expected = new HashSet<>(Arrays.asList(
                board[0][0],
                board[1][1],
                board[2][2],
                board[4][4],
                board[1][5],
                board[2][4],
                board[4][2],
                board[5][1],
                board[3][0],
                board[3][1],
                board[3][2],
                board[3][4],
                board[3][5],
                board[2][3],
                board[4][3],
                board[5][3]
        ));
        Set<Cell> result = new HashSet<>(queen.move(board, 3, 3));
        assertEquals(expected, result);
    }
}
