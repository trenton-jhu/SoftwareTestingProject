package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import javax.swing.*;

import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TimeTest {

    private Time time;

    @Mock
    private JLabel label;

    @BeforeEach
    public void setup() {
        label = mock(JLabel.class);
        time = new Time(label);
    }

    @Test
    public void testTimeConstructor() {
        assertEquals(time.countdownTimer.getInitialDelay(), 1000);
        ActionListener[] listeners = time.countdownTimer.getActionListeners();
        assertEquals(listeners.length, 1);
        Assertions.assertTrue(listeners[0] instanceof Time.CountdownTimerListener);
    }

    @Test
    public void testTimeReset() {
        time.Timerem = 1;
        time.reset();
        assertEquals(time.Timerem, 60);
    }

    @Test
    public void testTimeStartOneSecond() throws Exception {
        time.start();
        Thread.sleep(1500);
        verify(label, times(1)).setText("1:00");
        assertEquals(time.Timerem, 59);
    }

    @Test
    public void testTimeStartFiveSeconds() throws Exception {
        time.start();
        Thread.sleep(5500);
        InOrder inOrder = inOrder(label);
        inOrder.verify(label, times(1)).setText("1:00");
        inOrder.verify(label, times(1)).setText("0:59");
        inOrder.verify(label, times(1)).setText("0:58");
        inOrder.verify(label, times(1)).setText("0:57");
        inOrder.verify(label, times(1)).setText("0:56");
        assertEquals(time.Timerem, 55);
    }

    /**
     * An exception will be thrown here due to Main not initialized.
     * This is ok because we are doing mock testing on Time and do not need Main.
     */
    @Test
    public void testTimeOutOfTime() throws Exception {
        time.Timerem = 5; // Five seconds remaining
        time.start();
        Thread.sleep(6500);
        InOrder inOrder = inOrder(label);
        inOrder.verify(label, times(1)).setText("0:05");
        inOrder.verify(label, times(1)).setText("0:04");
        inOrder.verify(label, times(1)).setText("0:03");
        inOrder.verify(label, times(1)).setText("0:02");
        inOrder.verify(label, times(1)).setText("0:01");
        inOrder.verify(label, times(1)).setText("Time's up!");
        assertEquals(time.Timerem, 60); // Timer should be reset
    }
}
