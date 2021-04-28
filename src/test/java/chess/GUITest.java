package chess;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.*;
import pieces.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.swing.core.matcher.JButtonMatcher.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GUI tests are implemented using AssertJ Swing which simulates user action and input on the
 * actual interface.
 * Do not move the mouse or use the keyboard while running the tests, as this may lead to unexpected
 * test results
 * Running all the tests may take a long time, you can run certain test individually for convenience
 *
 * There may be an Illegal Reflective Access Operation warning, this is due to AssertJ not quite
 * compatible with Java SDK 11, but this warning can be ignored.
 * Switching to Java SDK 8 will eliminate the warnings.
 */
public class GUITest extends AssertJSwingJUnitTestCase {

    private static final String REALFILE = System.getProperty("user.dir")+ File.separator + "chessgamedata.dat";
    private static final String TESTFILE = System.getProperty("user.dir")+ File.separator + "test.dat";

    private FrameFixture window;

    /**
     * To ensure we do not interfere with stored data when doing GUI testing, we make a copy of
     * the current real data into a new temporary test file before running the tests.
     * When all the tests are completed, we then transfer the real data back to the original file.
     *
     * Note: if tests are terminated before running to completion, the real data will be stored in
     * test.dat and so needs to be copied over to chessgamedata.dat.
     */
    @BeforeAll
    public static void setup() throws Exception {
        File inFile = new File(REALFILE);
        File outFile = new File(TESTFILE);
        if (outFile.exists()) {
            outFile.delete();
        }
        if (inFile.exists()) {
            Files.copy(inFile.toPath(), outFile.toPath());
            inFile.delete();
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(inFile))) {
            os.writeObject(new Player("player1"));
            os.writeObject(new Player("player2"));
        }
    }

    @AfterAll
    public static void clean() throws IOException {
        File inFile = new File(TESTFILE);
        File outFile = new File(REALFILE);
        if (outFile.exists()) {
            outFile.delete();
        }
        if (inFile.exists()) {
            Files.copy(inFile.toPath(), outFile.toPath());
            inFile.delete();
        } else {
            outFile.delete();
        }
    }

    @BeforeEach
    public void onSetUp() {
        Main frame = GuiActionRunner.execute(() -> {
            Main.main(new String[0]);
            return Main.Mainboard;
        });
        window = new FrameFixture(frame);

    }

    @AfterEach
    public void onCleanUp() {
        window.cleanUp();
    }

    @Test
    public void testStartWithoutSelectingPlayer() {
        window.button(withText("Start")).click();
        window.optionPane().requireVisible();
        window.optionPane().requireMessage("Fill in the details");
        window.optionPane().okButton().click();
    }

    // Fault: On Mac OS, cannot make new players when there are existing players already
    // @Test
    public void testStartMakeNewPlayers() {
        window.button("WNewPlayer").click();
        window.optionPane().requireVisible();
        window.optionPane().textBox().requireEditable();
        window.optionPane().textBox().enterText("test1");
        window.optionPane().okButton().click();

        window.button("BNewPlayer").click();
        window.optionPane().requireVisible();
        window.optionPane().textBox().requireEditable();
        window.optionPane().textBox().enterText("test1");
        window.optionPane().okButton().click();
        window.optionPane().requireMessage("Player exists");
        window.optionPane().okButton().click();

        window.button("BNewPlayer").click();
        window.optionPane().requireVisible();
        window.optionPane().textBox().requireEditable();
        window.optionPane().textBox().enterText("test2");
        window.optionPane().okButton().click();

        window.button(withText("Start")).click();
        window.panel("chessboard").requireVisible();
    }

    @Test
    public void testStartUseExistingPlayers() {
        window.button("wselect").click();
        window.button("bselect").click();
        window.button(withText("Start")).click();
        window.panel("chessboard").requireVisible();
    }

    @Test
    public void testInitialMoveByWhitePawn() {
        performMove(new ArrayList<>(List.of("62", "42")));
        checkPiece("42", Pawn.class, 0);
        checkPiece("62", null, 0);
        window.label("turn").requireText("Black");
    }

    @Test
    public void testInvalidInitialMoveByBlackPawn() {
        performMove(new ArrayList<>(List.of("12", "22")));
        checkPiece("22", null, 1);
        checkPiece("12", Pawn.class, 1);
        window.label("turn").requireText("White");
    }

    @Test
    public void testPawnCapture() {
        performMove(new ArrayList<>(List.of("62", "42", "13", "33", "42", "33")));
        checkPiece("33", Pawn.class, 0);
        window.label("turn").requireText("Black");
    }

    @Test
    public void testPawnBlocked() {
        performMove(new ArrayList<>(List.of("62", "42", "12", "32", "42", "32")));
        checkPiece("42", Pawn.class, 0);
        checkPiece("32", Pawn.class, 1);
        window.label("turn").requireText("White");
    }

    // Fault: castling is not implemented
    // @Test
    public void testCastling() {
        performMove(new ArrayList<>(List.of("63", "43", "13", "23", "62", "42", "14", "34", "43", "33", "06", "25",
                "71", "52", "16", "26", "72", "36", "05", "16", "73", "71")));
        checkPiece("71", King.class, 0);
        checkPiece("72", Rook.class, 0);
        window.label("turn").requireText("Black");
    }

    // Fault: pawn promotion is not implemented
    // @Test
    public void testPromotePawn() {
        performMove(new ArrayList<>(List.of("63", "43", "14", "34", "43", "34", "01", "22", "72", "36", "15", "25",
                "34", "25", "04", "26", "25", "16", "26", "36", "16", "05")));
        checkPiece("05", Queen.class, 0);
        window.label("turn").requireText("Black");
    }

    @Test
    public void testCheckmateWinFoolsMate() {
        performMove(new ArrayList<>(List.of("62", "52", "13", "33", "61", "41", "04", "40")));
        window.optionPane().requireVisible();
        window.optionPane().requireMessage("Checkmate!!!\nplayer2 wins");
        window.optionPane().okButton().click();
    }

    @Test
    public void testCheckmateWinScholarsMate() {
        performMove(new ArrayList<>(List.of("63", "43", "13", "33", "74", "30", "06", "25", "72", "45", "01", "22", "30", "12")));
        window.optionPane().requireVisible();
        window.optionPane().requireMessage("Checkmate!!!\nplayer1 wins");
        window.optionPane().okButton().click();
    }

    // Fault: game does not check for stalemate
    // https://www.chess.com/forum/view/more-puzzles/stalemate-in-10-moves
    //@Test
    public void testStalemate() {
        performMove(new ArrayList<>(List.of("63", "53", "17", "37", "74", "30", "07", "27", "30", "37", "10", "30",
                "37", "15", "27", "20", "60", "40", "12", "22", "15", "14", "03", "12", "14", "16", "04", "54", "16",
                "06", "54", "10", "06", "05", "12", "21", "05", "23")));
        window.optionPane().requireVisible();   // Test will time out waiting for option pane to appear
    }

    // Fault: game does not allow draws by threefold repetition (player should have option to call draw)
    // https://en.wikipedia.org/wiki/Threefold_repetition
    //@Test
    public void testRepetitionDraw() {
        performMove(new ArrayList<>(List.of("63", "53", "13", "23", "73", "63", "03", "13", "63", "73", "13", "03",
                "73", "63", "03", "13", "63", "73", "13", "03")));
        window.optionPane().requireVisible();   // Test will time out waiting for option pane to appear
    }

    // Fault: en passant special capture not implemented
    // https://en.wikipedia.org/wiki/En_passant
    //@Test
    public void testEnPassant() {
        performMove(new ArrayList<>(List.of("63", "53", "10", "30", "53", "43", "11", "31", "43", "33", "31", "41", "60", "40", "41", "50")));
        checkPiece("40", null, 0);
    }

    /**
     * Helper to start the game and then perform a sequence of chessboard moves on the GUI
     * acting as both players
     * @param cells list of cells to click on the GUI, each cell is represented by
     *              x y coordinate in string format. For example, upper-left cell is "00"
     */
    private void performMove(ArrayList<String> cells) {
        window.button("wselect").click();
        window.button("bselect").click();
        window.button(withText("Start")).click();
        for (String s : cells) {
            window.panel("chessboard").panel(s).click();
        }
    }

    /**
     * Helper to check the content of a cell in the current chessboard
     * @param cell current cell represented by x y coordinate in string format
     * @param pieceType class of the expected piece type
     * @param color color of the expected piece
     */
    private void checkPiece(String cell, Class<?> pieceType, int color) {
        Cell c = window.panel("chessboard").panel(cell).targetCastedTo(Cell.class);
        if (pieceType == null) {
            assertNull(c.getpiece());
        } else {
            assertNotNull(c.getpiece());
            assertEquals(pieceType, c.getpiece().getClass());
            assertEquals(color, c.getpiece().getcolor());
        }
    }
}
