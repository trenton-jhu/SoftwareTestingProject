package chess;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JSliderFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GUITest extends AssertJSwingJUnitTestCase {
    private static Robot robot;
    private FrameFixture window;

    @BeforeAll
    public static void deletePlayerData() throws AWTException {
        // Need fresh chessgamedata.dat in order to test new players
        File f = new File("chessgamedata.dat");
        if (f.delete()) {
            System.out.println("Deleted " + f.getName());
        }
        robot = new Robot();
    }

    /**
     * Simulate input of an entire text string (get rid of this if you figure out how to access text box in JPanel)
     *
     * @param robot the Robot object
     * @param keys  the text string to type
     */
    public void sendKeys(Robot robot, String keys) {
        for (char c : keys.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.delay(25);
            }
            robot.keyPress(keyCode);
            robot.delay(25);
            robot.keyRelease(keyCode);
            robot.delay(25);
            if (Character.isUpperCase(c)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.delay(25);
            }
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

    /**
     * Test new player setup. This test is ordered first so that subsequent tests can use the players created.
     */
    @Test
    @Order(1)
    public void newPlayerGameStart() {
        JButtonFixture whiteNewPlayer = window.button("whiteNewPlayer");
        whiteNewPlayer.click();
        sendKeys(robot, "WhiteTest\n");
        assertEquals(" WhiteTest", window.label("newPlayerName0").text());
        assertEquals(" 0", window.label("newPlayerGamesPlayed0").text());
        assertEquals(" 0", window.label("newPlayerGamesWon0").text());

        JButtonFixture blackNewPlayer = window.button("blackNewPlayer");
        blackNewPlayer.click();
        sendKeys(robot, "BlackTest\n");
        assertEquals(" BlackTest", window.label("newPlayerName1").text());
        assertEquals(" 0", window.label("newPlayerGamesPlayed1").text());
        assertEquals(" 0", window.label("newPlayerGamesWon1").text());

        JSliderFixture timeSlider = window.slider("timeSlider");
        timeSlider.slideTo(5);

        JButtonFixture startGame = window.button("startGame");
        startGame.click();
    }

    /**
     * Test existing player setup. This test is ordered second so that games played/won is known.
     */
    @Test
    @Order(2)
    public void existingPlayerGameStart() {
        JButtonFixture whiteSelectPlayer = window.button("whiteSelectPlayer");
        whiteSelectPlayer.click();
        assertEquals(" WhiteTest", window.label("playerName0").text());
        assertEquals(" 1", window.label("playerGamesPlayed0").text());
        assertEquals(" 0", window.label("playerGamesWon0").text());

        JButtonFixture blackSelectPlayer = window.button("blackSelectPlayer");
        blackSelectPlayer.click();
        assertEquals(" BlackTest", window.label("playerName1").text());
        assertEquals(" 1", window.label("playerGamesPlayed1").text());
        assertEquals(" 0", window.label("playerGamesWon1").text());

        JSliderFixture timeSlider = window.slider("timeSlider");
        timeSlider.slideTo(5);

        JButtonFixture startGame = window.button("startGame");
        startGame.click();
    }

    @AfterEach
    public void onFinish() {
        window.cleanUp();
    }
}
