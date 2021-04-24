package chess;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GUITest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;

    @BeforeEach
    public void onSetUp() {
        Main frame = GuiActionRunner.execute(() -> {
            Main.main(new String[0]);
            return Main.Mainboard;
        });
        window = new FrameFixture(frame);
    }

    @Test
    public void shouldClick() {
        window.panel("null.contentPane").click();
    }
}
