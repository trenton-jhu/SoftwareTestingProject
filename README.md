# Software Testing & Debugging Project
> Team Members: Jason Wong, Trenton Wang, Kevin Hong

Software Under Test: https://github.com/ashish1294/ChessOOP

Line Coverage (Intellij) Results:
- Knight.java = 100%
- Rook.java = 100%
- King.java = 100%
- Bishop.java = 100%
- Pawn.java = 96%
    - Bug on line 49: x = 7 when a black pawn reaches the end, but the code checks for x == 8 (impossible branch)
- Queen.java = 100%

Conditional Coverage (jacoco) Results: This is stronger than branch coverage.
- Knight.java = 100%
- Rook.java = 100%
- King.java = 100%
- Bishop.java = 100%
- Pawn.java = 97%
    - Bug on line 49: x = 7 when a black pawn reaches the end, but the code checks for x == 8 (impossible branch)
- Queen.java = 100%
