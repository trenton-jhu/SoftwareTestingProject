package pieces;

import chess.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BishopTest {

    private static Bishop bishop;
    private static Cell[][] board;

    @BeforeAll
    public static void setup() {
        bishop = new Bishop("WB01","White_Bishop.png",0);
        board = new chess.Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    @Test
    public void BishopMoveDiagonal() {
        System.out.println(bishop.move(board, 0, 0));
    }
}
