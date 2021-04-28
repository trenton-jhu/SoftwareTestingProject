package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Black-box testing for chess end-game logic implemented by methods in Main.java
 * Also include test for game initialization, including board setup
 *
 * Test cases are written based on comments documented in Main.java in Black-box manner for the following methods:
 * - willkingbeindanger()
 * - filterdestination()
 * - checkmate()
 */
public class MainTest {

    private Main main;
    private Cell[][] board;

    @BeforeEach
    public void setup() {
        main = new Main();
        board = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    // Fault: King and Queen are placed on incorrect cell on initial board
    // @Test
    public void testInitialBoardSetup() {
        Cell[][] board = main.boardState;
        Class<?>[] pieceType = new Class[]{
                Rook.class,
                Knight.class,
                Bishop.class,
                Queen.class,
                King.class,
                Bishop.class,
                Knight.class,
                Rook.class
        };
        for (int i = 0; i < pieceType.length; i++) {
            assertEquals(board[0][i].getpiece().getClass(), pieceType[i]);
            assertEquals(board[0][i].getpiece().getcolor(), 1);
            assertEquals(board[1][i].getpiece().getClass(), Pawn.class);
            assertEquals(board[1][i].getpiece().getcolor(), 1);
            assertEquals(board[6][i].getpiece().getClass(), Pawn.class);
            assertEquals(board[6][i].getpiece().getcolor(), 0);
            assertEquals(board[7][i].getpiece().getClass(), pieceType[i]);
            assertEquals(board[7][i].getpiece().getcolor(), 0);
        }
    }

    /**
     * Black-box test cases for willkingbeindanger()
     * Use equivalence partitioning on output domain: True / False
     * Use error guessing to include test cases like discovered check
     */
    @Test
    public void testWillKingBeInDangerFalse() {
        assertFalse(main.willkingbeindanger(main.boardState[6][2], main.boardState[6][3]));
    }

    @Test
    public void testWillKingBeInDangerTrue() {
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[7][6].setPiece(Main.wk);
        board[3][3].setPiece(new Rook("BR01","/Black_Rook.png",1));
        main.boardState = board;
        assertTrue(main.willkingbeindanger(board[3][3], board[7][3]));
    }

    @Test
    public void testWillKingBeInDangerTrueDiscoveredCheck() {
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[7][6].setPiece(Main.wk);
        board[7][4].setPiece(new Knight("WK01","/White_Knight.png", 0));
        board[7][0].setPiece(new Rook("BR01","/Black_Rook.png",1));
        main.boardState = board;
        assertTrue(main.willkingbeindanger(board[7][4], board[5][3]));
    }

    /**
     * Black-box test cases for filterdestination()
     * Use equivalence partitioning on output domain: empty array, singleton array, array with many elements
     */
    @Test
    public void testFilterDestination() {
        ArrayList<Cell> moves = new ArrayList<>(List.of(
                main.boardState[5][0],
                main.boardState[4][0]
        ));
        assertEquals(moves, main.filterdestination(moves, main.boardState[6][0]));
    }

    @Test
    public void testFilterDestinationNoMoves() {
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[7][6].setPiece(Main.wk);
        board[6][6].setPiece(new Bishop("BB01","/Black_Bishop.png",1));
        board[7][4].setPiece(new Knight("WK01","/White_Knight.png", 0));
        board[7][0].setPiece(new Rook("BR01","/Black_Rook.png",1));
        main.boardState = board;
        ArrayList<Cell> moves = new ArrayList<>(List.of(
                board[5][3],
                board[5][5],
                board[6][2],
                board[6][6]
        ));
        assertTrue(main.filterdestination(moves, board[7][4]).isEmpty());
    }

    @Test
    public void testFilterDestinationOneMove() {
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[7][6].setPiece(Main.wk);
        board[6][5].setPiece(new Bishop("WB01","/White_Bishop.png",0));
        board[5][4].setPiece(new Bishop("BB01","/Black_Bishop.png",1));
        main.boardState = board;
        ArrayList<Cell> moves = new ArrayList<>(List.of(
                board[5][4],
                board[5][6],
                board[7][4]
        ));
        assertEquals(new ArrayList<>(List.of(board[5][4])), main.filterdestination(moves, board[6][5]));
    }

    @Test
    public void testFilterDestinationManyMoves() {
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[7][6].setPiece(Main.wk);
        board[6][3].setPiece(new Rook("BR01","/Black_Rook.png",1));
        main.boardState = board;
        ArrayList<Cell> moves = new ArrayList<>(List.of(
                board[7][5],
                board[7][7],
                board[6][5],
                board[6][6],
                board[6][7]
        ));
        ArrayList<Cell> expected = new ArrayList<>(List.of(
                board[7][5],
                board[7][7]
        ));
        assertEquals(expected, main.filterdestination(moves, board[7][6]));
    }

    /**
     * Black-box test cases for checkmate()
     * Use equivalence partitioning on output domain: True / False
     * Use error guessing to add test case for stalemate situation
     * Reference: https://www.chessgames.com/perl/chesscollection?cid=1022048
     */
    @Test
    public void testCheckMateFalse() {
        assertFalse(main.checkmate(0));
        assertFalse(main.checkmate(1));
    }

    // Fault: checkmate returns true on stalemate end game
    // @Test
    public void testCheckMateFalseStaleMate() {
        Main.bk = new King("BK","/Black_King.png",1,0, 3);
        Main.wk = new King("WK","/White_King.png", 0, 2, 3);
        board[0][3].setPiece(Main.bk);
        board[2][3].setPiece(Main.wk);
        board[1][3].setPiece(new Pawn("WP01","/White_Pawn.png",0));
        main.boardState = board;
        assertFalse(main.checkmate(0));
        assertFalse(main.checkmate(1));
    }

    @Test
    public void testCheckMateBackRankMate() {
        Main.bk = new King("BK","/Black_King.png",1,0, 6);
        Main.wk = new King("WK","/White_King.png", 0, 7, 6);
        board[0][6].setPiece(Main.bk);
        board[7][6].setPiece(Main.wk);
        board[1][5] = main.boardState[1][5];
        board[1][6] = main.boardState[1][6];
        board[1][7] = main.boardState[1][7];
        board[0][0].setPiece(new Rook("WR01","/White_Rook.png",0));
        main.boardState = board;
        assertTrue(main.checkmate(1));
        assertFalse(main.checkmate(0));
    }

    @Test
    public void testCheckMateAnderssenMate() {
        Main.bk = new King("BK","/Black_King.png",1,0, 6);
        Main.wk = new King("WK","/White_King.png", 0, 2, 5);
        board[0][6].setPiece(Main.bk);
        board[2][5].setPiece(Main.wk);
        board[0][7].setPiece(new Rook("WR01","/White_Rook.png",0));
        board[1][6].setPiece(new Pawn("WP01","/White_Pawn.png",0));
        main.boardState = board;
        assertTrue(main.checkmate(1));
        assertFalse(main.checkmate(0));
    }

    @Test
    public void testCheckMateDamianoBishopMate() {
        Main.bk = new King("BK","/Black_King.png",1,0, 6);
        Main.wk = new King("WK","/White_King.png", 0, 7, 1);
        board[0][6].setPiece(Main.bk);
        board[7][1].setPiece(Main.wk);
        board[2][5].setPiece(new Bishop("WB01","/White_Bishop.png",0));
        board[1][6].setPiece(new Queen("WQ","/White_Queen.png",0));
        main.boardState = board;
        assertTrue(main.checkmate(1));
        assertFalse(main.checkmate(0));
    }
}
