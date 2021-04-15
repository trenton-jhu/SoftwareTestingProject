package pieces;

import chess.Cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class KingTest {

    private static King king;
    private static Cell[][] board;

    @BeforeEach
    public void setup() {
        // White king at (3, 4) by default
        king = new King("WK","/White_King.png", 0, 3, 4);
        board = new chess.Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    @Test
    public void testGetXGetY() {
        assertEquals(3, king.getx());
        assertEquals(4, king.gety());
    }

    @Test
    public void testKingMove() {
        Set<Cell> expected = new HashSet<>();
        expected.add(board[6][6]);
        expected.add(board[6][7]);
        expected.add(board[7][6]);
        Set<Cell> result = new HashSet<>(king.move(board, 7, 7));
        assertEquals(expected, result);
    }

    @Test
    public void testKingMoveBlockedByOwnPiece() {
		Pawn pawn1 = new Pawn("WP01","/White_Pawn.png", 0);
		Pawn pawn2 = new Pawn("BP01","/Black_Pawn.png", 1);
        Cell c1 = new Cell(1, 0, pawn1);
        Cell c2 = new Cell(0, 1, pawn2);
        board[1][0] = c1;
        board[0][1] = c2;
        
        Set<Cell> expected = new HashSet<>();
        expected.add(board[0][1]);
        expected.add(board[1][1]);
        Set<Cell> result = new HashSet<>(king.move(board, 0, 0));
        assertEquals(expected, result);
    }

    // black king is (0, 3)
    // white king is (7, 3)
    // queen is to the right at y=4

    @ParameterizedTest
    @MethodSource({"dangerLeftRightUpDown", "dangerDiagonal", "dangerKnight", "dangerKing", "dangerPawn"})
    public void testKingIsInDanger(Piece p, King k, int x, int y, boolean result) {
        Cell c = new Cell(x, y, p);
        board[x][y] = c;
        if (k == null) {
            k = king;
        }
        assertEquals(result, k.isindanger(board));
    }

    @TestFactory
    private static Stream<Arguments> dangerLeftRightUpDown() {
		Queen queenw = new Queen("WQ","/White_Queen.png", 0);
		Queen queenb = new Queen("BQ","/Black_Queen.png", 1);
		Rook rookb = new Rook("BR01","/Black_Rook.png", 1);
		Knight knightb = new Knight("BK01","/Black_Knight.png", 1);

        return Stream.of(
            // Right
            Arguments.of(queenw, null, 5, 4, false), // same color
            Arguments.of(queenb, null, 5, 4, true), // queen
            Arguments.of(rookb, null, 5, 4, true), // rook
            Arguments.of(knightb, null, 5, 4, false), // other piece
            // Left
            Arguments.of(queenw, null, 2, 4, false),
            Arguments.of(queenb, null, 2, 4, true), 
            Arguments.of(rookb, null, 2, 4, true),
            Arguments.of(knightb, null, 2, 4, false),
            // Down
            Arguments.of(queenw, null, 3, 6, false),
            Arguments.of(queenb, null, 3, 6, true), 
            Arguments.of(rookb, null, 3, 6, true),
            Arguments.of(knightb, null, 3, 6, false),
            // Up
            Arguments.of(queenw, null, 3, 2, false),
            Arguments.of(queenb, null, 3, 2, true), 
            Arguments.of(rookb, null, 3, 2, true),
            Arguments.of(knightb, null, 3, 2, false)
        );
    }

    @TestFactory
    private static Stream<Arguments> dangerDiagonal() {
		Queen queenw = new Queen("WQ","/White_Queen.png", 0);
		Queen queenb = new Queen("BQ","/Black_Queen.png", 1);
		Bishop bishopb = new Bishop("BB01","/Black_Bishop.png", 1);
		Knight knightb = new Knight("BK01","/Black_Knight.png", 1);
        
        return Stream.of(
            // Top Right
            Arguments.of(queenw, null, 1, 6, false), // same color
            Arguments.of(queenb, null, 1, 6, true), // queen
            Arguments.of(bishopb, null, 1, 6, true), // bishop
            Arguments.of(knightb, null, 1, 6, false), // other piece
            // Top Left
            Arguments.of(queenw, null, 1, 2, false),
            Arguments.of(queenb, null, 1, 2, true), 
            Arguments.of(bishopb, null, 1, 2, true),
            Arguments.of(knightb, null, 1, 2, false),
            // Bottom Right
            Arguments.of(queenw, null, 5, 6, false),
            Arguments.of(queenb, null, 5, 6, true), 
            Arguments.of(bishopb, null, 5, 6, true),
            Arguments.of(knightb, null, 5, 6, false),
            // Bottom Left
            Arguments.of(queenw, null, 5, 2, false),
            Arguments.of(queenb, null, 5, 2, true), 
            Arguments.of(bishopb, null, 5, 2, true),
            Arguments.of(knightb, null, 5, 2, false)
        );
    }


    @TestFactory
    private static Stream<Arguments> dangerKnight() {
		Knight knightw = new Knight("WK01","/White_Knight.png", 0);
		Knight knightb = new Knight("BK01","/Black_Knight.png", 1);
		Bishop bishopb = new Bishop("BB01","/Black_Bishop.png", 1);

        return Stream.of(
            // White Knight
            Arguments.of(knightw, null, 2, 2, false),
            // Black Knight
            Arguments.of(knightb, null, 2, 2, true),
            // Other Piece in Knight Position
            Arguments.of(bishopb, null, 2, 2, false)
        );
    }

    @TestFactory
    private static Stream<Arguments> dangerKing() {
		King kingw = new King("WK","/White_King.png", 0, 0, 0); // won't happen
		King kingb = new King("BK","/Black_King.png", 1, 0, 0);
		Bishop bishopb = new Bishop("BB01","/Black_Bishop.png", 1);

        King king1 = new King("WK","/White_King.png", 0, 0, 0);
        King king2 = new King("WK","/White_King.png", 0, 7, 7);
        King king3 = new King("WK","/White_King.png", 0, 0, 7);
        King king4 = new King("WK","/White_King.png", 0, 7, 0);
        King king5 = new King("BK","/Black_King.png", 1, 0, 0);
        King king6 = new King("BK","/Black_King.png", 1, 7, 7);
        King king7 = new King("BK","/Black_King.png", 1, 0, 7);
        King king8 = new King("BK","/Black_King.png", 1, 7, 0);
        
        return Stream.of(
            // White King
            Arguments.of(kingw, null, 4, 4, false),
            // Black King
            Arguments.of(kingb, null, 4, 4, true),
            // Other Piece in King Position
            Arguments.of(bishopb, null, 4, 4, false),
            // Test corners of king
            Arguments.of(null, king1, 0, 0, false),
            Arguments.of(null, king2, 0, 0, false),
            Arguments.of(null, king3, 0, 0, false),
            Arguments.of(null, king4, 0, 0, false),
            Arguments.of(null, king5, 0, 0, false),
            Arguments.of(null, king6, 0, 0, false),
            Arguments.of(null, king7, 0, 0, false),
            Arguments.of(null, king8, 0, 0, false)
        );
    }


    @TestFactory
    private static Stream<Arguments> dangerPawn() {
		Pawn pawnw = new Pawn("WP01","/White_Pawn.png", 0);
		Pawn pawnb = new Pawn("BP01","/Black_Pawn.png", 1);
		Knight knightw = new Knight("WK01","/White_Knight.png", 0);
		Knight knightb = new Knight("BK01","/Black_Knight.png", 1);
        King kingb = new King("WK","/White_King.png", 1, 1, 4);
        
        return Stream.of(
            // White King
            Arguments.of(pawnb, null, 2, 3, true),
            Arguments.of(pawnb, null, 2, 5, true),
            Arguments.of(pawnw, null, 2, 3, false),
            Arguments.of(pawnw, null, 2, 5, false),
            Arguments.of(knightb, null, 2, 3, false),
            Arguments.of(knightb, null, 2, 5, false),
            // Black King
            Arguments.of(pawnw, kingb, 2, 3, true),
            Arguments.of(pawnw, kingb, 2, 5, true),
            Arguments.of(pawnb, kingb, 2, 3, false),
            Arguments.of(pawnb, kingb, 2, 5, false),
            Arguments.of(knightw, kingb, 2, 3, false),
            Arguments.of(knightw, kingb, 2, 5, false)
        );
    }
}
