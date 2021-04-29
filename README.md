# Software Testing & Debugging Project
> Team Members: Jason Wong, Trenton Yuntian Wang, Kevin Hong

## Software Under Test
The software that we choose to test is an implementation of a two-player chess game.
Anyone who enjoys playing chess or wants to learn how to play can download the project and run it. 
The project is implemented in Java with a Graphical User Interface (GUI) implemented using Swing, a GUI widget
tookit for Java.

- Software Under Test: https://github.com/ashish1294/ChessOOP  
- Link to class diagram: https://drive.google.com/file/d/1PmJZKeHaLFNqBNITpbvxuZB0rGvcge8z/view?usp=sharing

## Testing Methods and Frameworks
To thoroughly test our SUT, we make use of the following testing techniques and testing tools / frameworks:
- Black-box unit testing (JUnit)
- White-box unit testing (JUnit)
- Mutation testing (PITest)
- Mock testing (PowerMockito, Mockito)
- GUI testing (AssertJ Swing)


## Black-Box Testing
We use black-box testing to test some specific functionalities of the chess game implemented in `Main.java`. 
In particular, we test for game initialization and all the end game logic including checking, checkmate, stalemate, etc. 
To write test cases, we rely on the comment documented in `Main.java` as well as common knowledge of chess and resources from the Internet. We employ equivalence partitioning and error guessing techniques to ensure better test cases. 

How to run:  
- Run all JUnit tests found in `MainTest.java`

Faults Identified:
- King and Queen are placed on incorrect cells on the initial chessboard, and their position should be switched. 
- `checkmate()` method returns true on a stalemate but should return false.

## White-Box Testing
We use white-box testing technique to test all the logic of the different chess pieces implemented by all the 
different classes in the `main/java/pieces` directory. We plan to achieve branch coverage.

How to run:
- Run all JUnit tests found in `test/java/pieces` directory
- Run `gradlew jacocoTestReport` to generate jacoco coverage report

Test Results:
- 100% branch coverage for all piece classes except for `Pawn.java`, which cannot be achieved due to a fault.
  See jacoco test reports for details.
- Note that jacoco measures conditional coverage, which is actually stronger coverage criterion than branch coverage.

Faults Identified:
- `x = 7` when black pawn reaches end, but code checks for `x == 8`. This does not lead to failure because it will have no effect on the GUI.
- Pawn En Passant special move is not implemented.

## Mutation Testing
We run mutation testing on the white-box tests mentioned earlier using PITest. Then we perform mutation analysis and try to add more test cases so that we can achieve a higher mutation score.

How to run:
- Run `gradlew pitest`

Test Results:
- 100% mutation coverage for all piece classes except for `Pawn.java`, which cannot be achieved due to same fault mentioned earlier. See PITest report for details

## Mock Testing
We use mock testing for `Player.java` and `Time.java`. This is because `Player.java` is mainly used for writing player data (player name, number of games won, number of games played) to a data file. Thus, in order to test this program without modifying actual player data that might be stored, we use mock testing. `Time.java` deals with counting down a timer for switching turns in the chess game. We used mock testing for this as well because the program relies on certain GUI components, which we can mock.

Test Results:
- Covered all interactions between `Player.java` and its collaborators, including File, and different Input and Output streams.
  
- 73% line coverage for `Player.java`, because lines that call `JOptionPane` cannot be covered as it cannot be mocked.
- Covered all interactions between `Time.java` and the count down display JLabel. 
- 100% line coverage for `Time.java`

Faults Identified:
- Divide by zero error when trying to get win percentage of a newly created player.
- Rounding error when calculating win percentage that does not result in integer percent value. For example, should round 2/3 to 67 percent instead of 66 percent.

## GUI Testing
We use the AssertJ Swing testing framework for performing GUI testing on the interface of the chess game. We write test cases to various aspects of how real users may interact with the game interface. The test cases include scenarios like making new players, selecting players, moving pieces, capturing pieces, checkmate, stalemate, illegal moves, special moves (castling, en passant), etc.

Test Results:
- 92% line coverage for `Main.java`, which is where majority of the game logic and GUI components logic lie.

Faults Identified:
- Move that exposes the king to check is allowed. This fault occurs when the attacking piece checks by capturing another piece.
- Pawn En Passant special move is not implemented.
- Castling is not implemented
- Pawn promotion to Queen when reaching the opposite end of board is not implemented
- Game does not detect and show message when ends in stalemate
- Game does not provide option for player to claim draw after a threefold repetition