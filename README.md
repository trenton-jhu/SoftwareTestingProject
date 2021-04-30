# Software Testing & Debugging Project
> Team Members: Jason Wong, Trenton Yuntian Wang, Kevin Hong

## Software Under Test
The software that we choose to test is an implementation of a two-player chess game. Anyone who enjoys playing chess or wants to learn how to play can download the project and run it. The project is implemented in Java with a Graphical User Interface (GUI) implemented using Swing, a GUI widget tookit for Java.

- Software Under Test: [ChessOOP](https://github.com/ashish1294/ChessOOP)
- Link to class diagram: [Class Diagram](https://drive.google.com/file/d/1PmJZKeHaLFNqBNITpbvxuZB0rGvcge8z/view?usp=sharing)

## Testing Methods and Frameworks
To thoroughly test our SUT, we make use of the following testing techniques and testing tools / frameworks:
- Black-box unit testing (JUnit5)
- White-box unit testing (JUnit5)
- Mutation testing ([PITest](https://pitest.org/))
- Mock testing ([PowerMockito](https://github.com/powermock/powermock/wiki/Mockito), Mockito)
- GUI testing ([AssertJ Swing](https://joel-costigliola.github.io/assertj/assertj-swing.html))

All the tests are located in the `test/java/` directory. The tests that result in failures are commented out for more convenient running of successful tests.


## Black-Box Testing
We use black-box testing to test some specific functionalities of the chess game implemented in `Main.java`. In particular, we test for game initialization and all the end game logic including checking, checkmate, stalemate, etc. To write test cases, we rely on the comment documented in `Main.java` as well as common knowledge of chess and resources from the Internet. We employ equivalence partitioning and error guessing techniques to ensure better test cases. 

How to run:  
- Run all JUnit tests found in `MainTest.java`

Faults Identified:
- King and Queen are placed on incorrect cells on the initial chessboard, and their position should be switched. 
- `checkmate()` method returns true on a stalemate but should return false.

## White-Box Testing
We use white-box testing technique to test all the logic of the different chess pieces implemented by all the different classes in the `main/java/pieces` directory. We plan to achieve branch coverage.

How to run:
- Run all JUnit tests found in `test/java/pieces` directory
- Run `gradlew jacocoTestReport` to generate jacoco coverage report. 
  Note that this will also run GUI tests and while running these tests, the application window will pop up. You should not use the mouse or keyboard while the tests are running as that may lead to unexpected or incorrect test results.

Test Results:
- 100% branch coverage for all piece classes except for `Pawn.java` (97%), which cannot achieve full coverage due to a fault.
  See jacoco test reports for details.
- Note that jacoco measures conditional coverage, which is actually a stronger coverage criterion than branch coverage.
- jacoco test reports will not include coverage achieved by mock testing using PowerMockito. 

Faults Identified:
- `x = 7` when black pawn reaches end, but code checks for `x == 8`. This does not lead to failure because it will have no effect on the GUI. However, this fault leads to a branch of unreachable code, and so full branch coverage and mutation coverage cannot be achieved because of this.
- `move()` methods for all pieces return incorrect set of moves for pieces when starting position is invalid (for example `x = -1, y = -1`). Does not lead to failure because there is no way to for these methods to be invoked with invalid input.
- Pawn En Passant special move is not implemented.

## Mutation Testing
We run mutation testing on the white-box tests mentioned earlier using PITest. Then we perform mutation analysis and try to add more test cases so that we can achieve a higher mutation score.

How to run:
- Run `gradlew pitest`

Test Results:
- 100% mutation coverage for all piece classes except for `Pawn.java` (98%), which cannot be achieved due to same fault mentioned earlier. See PITest report for details

## Mock Testing
We use mock testing for `Player.java` and `Time.java`. This is because `Player.java` is mainly used for writing player data (player name, number of games won, number of games played) to a data file. Thus, in order to test this program without modifying actual player data that might be stored, we use mock testing. Mock testing also makes sense because reading and writing data functionalities are better suited for behavior-based testing. `Time.java` deals with counting down a timer for switching turns in the chess game. We used mock testing for this as well because the program relies on certain GUI components. Since we do really need to initialize the GUI for just testing the timer functionalities, we can use mock for the GUI components.

How to run:
- Run all tests in `PlayerTest.java` and `TimeTest.java`.
- Tests in `PlayerTest.java` uses JUnit4 with PowerMockito and needs to be run with Intellij test runner instead of Gradle test runner. To ensure Intellij test runner is used, open Settings>Build, Execution, Deployment>Build Tools>Gradle, and make sure `Intellij IDEA` is selected for `Run tests using`

Test Results:
- Covered all interactions between `Player.java` and its collaborators, including File, and different Input and Output streams.
- 73% line coverage for `Player.java`, because lines that call `JOptionPane` cannot be covered as it cannot be mocked.
- Covered all interactions between `Time.java` and the count down display, which is a `JLabel` component. 
- 100% line coverage for `Time.java`

Faults Identified:
- Divide by zero error when trying to get win percentage of a newly created player.
- Rounding error when calculating win percentage that does not result in integer percent value. For example, should round 2/3 to 67 percent instead of 66 percent.

## GUI Testing
We use the AssertJ Swing testing framework for performing GUI testing on the interface of the chess game. We write test cases to various aspects of how real users may interact with the game interface. The test cases include scenarios like making new players, selecting players, moving pieces, capturing pieces, checkmate, stalemate, illegal moves, special moves (castling, en passant), etc.

How to run:
- Run all tests in `GUITest.java`. When the GUI tests are running, the application window will pop up. You should not use the mouse or keyboard while the tests are running as that may lead to unexpected or incorrect test results.

Test Results:
- 92% line coverage for `Main.java`, which is where majority of the game logic and GUI components logic lie.

Faults Identified:
- Move that exposes the king to check is allowed. This fault occurs when the attacking piece checks by capturing another piece.
- Pawn En Passant special move is not implemented.
- Castling is not implemented
- Pawn promotion to Queen when reaching the opposite end of board is not implemented
- Game does not detect and show message when ends in stalemate
- Game does not provide option for a player to claim draw after a threefold repetition
- Cannot make new players when there are already two players that exist (Only for running on Mac OS machines, not sure if this is an intended feature or a fault)
  
## Discussion
- Important for software development to make program more testable:
  - Breakdown classes that are too large and contains too many methods, logic. `Main.java` tries to encompass too many 
  tasks including GUI setup, game initialization, game logic, etc.
  - Should use dependency injection instead of instantiation for classes with high couplings. For example `Player.java` makes use of many different input and output streams and file handlers and so using dependency injection would be much easier to test using mock.
    
- Some faults discovered for certain methods do not necessarily lead to failures because their consumers interact with them in a specific way to avoid it. However, for more reliable software that is robust and flexible to extension and further development, these faults should still be fixed.
- Cross-platform compatibility of program: important to maintain consistency for software across different platforms.
- It is difficult to achieve fault-free code for any non-trivial software development project, and so software testing is a continuous process that can help to increase the quality of the software we write.
- Good to mention in design docs or software specs what features are not implemented or what are future development steps.
- Need to have many different software testing techniques to achieve more thorough and comprehensive testing of a piece of software. Different testing methods may lead to different type of faults being revealed.
