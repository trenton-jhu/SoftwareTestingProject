package chess;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Before;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Uses PowerMock which does not support JUnit 5, so need to write with IntelliJ instead of Gradle
 * There may be an Illegal Reflective Access Operation warning, this is due to PowerMock not quite
 * compatible with Java SDK 11, but this warning can be ignored.
 * Switching to Java SDK 8 will eliminate the warnings.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Player.class})
public class PlayerTest {

    private static final String FILE_NAME = System.getProperty("user.dir")+ File.separator + "chessgamedata.dat";
    private static final String TEMP_NAME = System.getProperty("user.dir")+ File.separator + "tempfile.dat";

    private Player player;
    private ArrayList<Player> players;

    private File inputFileMock;
    private File outputFileMock;
    private ObjectOutputStream oosMock;


    @Before
    public void setup() throws Exception {
        player = new Player("test");
        players = new ArrayList<>(List.of(
                new Player("test1"),
                new Player("test2"),
                new Player("test3")
        ));

        inputFileMock = mock(File.class);
        outputFileMock = mock(File.class);
        oosMock = mock(ObjectOutputStream.class);
        FileInputStream fisMock = mock(FileInputStream.class);
        FileOutputStream fosMock = mock(FileOutputStream.class);

        whenNew(File.class).withArguments(FILE_NAME).thenReturn(inputFileMock);
        whenNew(File.class).withArguments(TEMP_NAME).thenReturn(outputFileMock);
        whenNew(FileInputStream.class).withAnyArguments().thenReturn(fisMock);
        whenNew(FileOutputStream.class).withAnyArguments().thenReturn(fosMock);
    }

    // Fault: Division by zero error when calculating Win Percent on newly instantiated player.
//    @Test
//    public void testPlayerInitialWinPercent() {
//        assertEquals(p.winpercent(), Integer.valueOf(0));
//    }

    @Test
    public void testPlayerWinFiftyPercent() {
        player.updateGamesPlayed();
        player.updateGamesWon();
        player.updateGamesPlayed();
        player.updateGamesPlayed();
        player.updateGamesWon();
        player.updateGamesPlayed();
        assertEquals(player.gamesplayed(), Integer.valueOf(4));
        assertEquals(player.gameswon(), Integer.valueOf(2));
        assertEquals(player.winpercent(), Integer.valueOf(50));
    }

    @Test
    public void testPlayerWinHundredPercent() {
        player.updateGamesPlayed();
        player.updateGamesWon();
        player.updateGamesPlayed();
        player.updateGamesWon();
        player.updateGamesPlayed();
        player.updateGamesWon();
        assertEquals(player.gamesplayed(), Integer.valueOf(3));
        assertEquals(player.gameswon(), Integer.valueOf(3));
        assertEquals(player.winpercent(), Integer.valueOf(100));
    }

    @Test
    public void testFetchPlayers() throws Exception {
        ObjectInputStream inputStream = prepareInputStream(players);
        whenNew(ObjectInputStream.class).withAnyArguments().thenReturn(inputStream);

        ArrayList<Player> result = Player.fetch_players();
        assertEquals(players.size(), result.size());
        for (int i = 0; i < players.size(); i++) {
            assertEquals(players.get(i).name(), result.get(i).name());
        }
    }

    @Test
    public void testFetchPlayersFileNotFound() throws Exception {
        whenNew(ObjectInputStream.class).withAnyArguments().thenThrow(FileNotFoundException.class);
        ArrayList<Player> result = Player.fetch_players();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testUpdatePlayersExistingPlayer() throws Exception {
        when(inputFileMock.exists()).thenReturn(true);
        when(outputFileMock.exists()).thenReturn(true);
        ObjectInputStream inputStream = prepareInputStream(players);
        whenNew(ObjectInputStream.class).withAnyArguments().thenReturn(inputStream);
        whenNew(ObjectOutputStream.class).withAnyArguments().thenReturn(oosMock);

        Player player = new Player("test2");
        player.Update_Player();
        InOrder inOrder = inOrder(oosMock, inputFileMock, outputFileMock);
        inOrder.verify(oosMock, times(1)).writeObject(player);
        inOrder.verify(inputFileMock, times(1)).delete();
        inOrder.verify(oosMock, times(1)).close();
        inOrder.verify(outputFileMock, times(1)).renameTo(inputFileMock);
    }

    @Test
    public void testUpdatePlayersNonExistingPlayer() throws Exception {
        when(inputFileMock.exists()).thenReturn(true);
        when(outputFileMock.exists()).thenReturn(true);
        ObjectInputStream inputStream = prepareInputStream(players);
        whenNew(ObjectInputStream.class).withAnyArguments().thenReturn(inputStream);
        whenNew(ObjectOutputStream.class).withAnyArguments().thenReturn(oosMock);

        Player player = new Player("new_player");
        player.Update_Player();
        InOrder inOrder = inOrder(oosMock, inputFileMock, outputFileMock);
        inOrder.verify(oosMock, times(1)).writeObject(player);
        inOrder.verify(inputFileMock, times(1)).delete();
        inOrder.verify(oosMock, times(1)).close();
        inOrder.verify(outputFileMock, times(1)).renameTo(inputFileMock);
    }

    @Test
    public void testUpdatePlayersFilesDoNotExist() throws Exception {
        when(inputFileMock.exists()).thenReturn(false);
        when(outputFileMock.exists()).thenReturn(false);
        ObjectInputStream inputStream = prepareInputStream(players);
        whenNew(ObjectInputStream.class).withAnyArguments().thenReturn(inputStream);
        whenNew(ObjectOutputStream.class).withAnyArguments().thenReturn(oosMock);

        Player player = new Player("test1");
        player.Update_Player();
        InOrder inOrder = inOrder(oosMock, inputFileMock, outputFileMock);
        inOrder.verify(oosMock, times(1)).writeObject(player);
        inOrder.verify(inputFileMock, times(1)).delete();
        inOrder.verify(oosMock, times(1)).close();
        inOrder.verify(outputFileMock, times(1)).renameTo(inputFileMock);
    }

    /**
     * Helper method for creating an ObjectInputStream of players data
     * @param players list of players
     * @return an ObjectInputStream of serialized players data
     */
    private ObjectInputStream prepareInputStream(ArrayList<Player> players) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        for (Player p : players) {
            out.writeObject(p);
        }
        out.flush();
        out.close();
        return new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
    }
}
