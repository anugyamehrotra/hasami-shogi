<img width="2457" height="621" alt="hasami_shogi_image_banner" src="https://github.com/user-attachments/assets/c13532ba-658e-4d7c-bac7-1dd6f91cdd20" />

## Overview

**Hasami Shogi** is a comprehensive Java-based implementation of the traditional Japanese board game. It is built to support both a terminal-based Command Line Interface (CLI) and an interactive Graphical User Interface (GUI) driven by Java Swing, allowing for Player versus Player (PvP) and Player versus Computer (PvC) gameplay.

The core game logic models standard Hasami Shogi mechanics across a 9x9 board grid. Players move their designated pieces orthogonally to trap and capture their opponent's pieces through sandwiching mechanics and corner kills. The game actively tracks captures, terminating when a player successfully captures five or more enemy pieces. 

---

## Features & External Dependencies

### Key Features
* **Dual Execution Modes**: The project offers a complete terminal-based console experience initialized via `Main.java`, alongside a fully interactive Java Swing graphical interface initialized via `MainGUI.java`. The terminal mode includes input validation loops utilizing a regular expression (`[1-9][a-iA-I]`) to prevent crashes from invalid coordinate structures.
* **Dynamic Board Initialization**: The backend relies on a custom `Board` object that dynamically populates a 9x9 matrix `String[][] gameBoard`. It automatically assigns White pieces (`w`) to the top row (`a`, index 0), Black pieces (`b`) to the bottom row (`i`, index 8), and designates all other spaces as empty (`x`).
* **Advanced Capture Engine**: The game actively checks for captures in all orthogonal directions after every valid piece movement. 
  * **Sandwich Kills**: The `checkAndKill()` method dynamically searches upward, downward, leftside, and rightside to detect enemy pieces trapped between two of the current player's pieces. It replaces trapped piece strings with an empty `x` state.
  * **Corner Kills**: The `cornerKill()` method contains specific algorithmic checks for the top-left (`[0][0]`), top-right (`[0][8]`), bottom-left (`[8][0]`), and bottom-right (`[8][8]`) positions to validate orthogonal corner traps. 
* **Computer Opponent AI**: The `Player` class supports a boolean `isComputer` state to enable Player vs. Computer mode. The computer's logic utilizes the `computerMove()` method, which scans the board for all active computer pieces, checks them against `canMove()` validity, builds an `ArrayList` of possible moves, and uses `Math.random()` to randomly select and execute a valid coordinate destination.
* **Custom Graphical Interface**:
  * **Interactive Controls & Visualization**: The GUI utilizes a 9x9 `GridLayout` populated with custom `GameButton` components. When a user selects a piece, the interface visually highlights the selection by altering the button's background to a gold color (`Color(214, 185, 44)`). It seamlessly unselects or applies movement on the subsequent click.
  * **Anti-Aliased Custom Rendering**: To ensure high graphical fidelity, the `resizeImageIcon` method converts standard piece PNG assets into buffered images. It utilizes `Graphics2D` with `RenderingHints.KEY_ANTIALIASING`, `KEY_INTERPOLATION`, and `KEY_RENDERING` to smoothly re-scale images into fixed 50x50 pixel formats. 
  * **Custom UI Asset Integration**: The GUI actively relies on external styling, dynamically importing custom font assets like `AfacadFlux-Regular.ttf` through `Font.createFont()`, falling back to standard `SansSerif` if unavailable. It uses `paintComponent()` in a custom `BackgroundPanel` class to draw PNG overlay frames.
* **ASCII Board Rendering & Navigational Aids**: For CLI gameplay, the board is drawn using rows mapped to numbers 9-1 and columns mapped to letters a-i. White pieces are rendered as `☖`, Black pieces as `☗`, and empty spaces as `.`. The engine provides visual aids by generating and printing an `ArrayList` of all active valid starting coordinates and valid movement destinations for the current player before their turn.
* **Win Validation Engine**: Primary game loops actively validate win conditions after every turn using the `gameOver()` or `isGameOver()` methods. The engine checks if `player1.captureCount()` or `player2.captureCount()` is greater than or equal to 5.

### Languages & Frameworks Used

* **Java**: The core programming language used to build the complete backend game logic, object models, pathfinding, terminal interface, and graphical UI.
* **Java Swing & AWT**: Extensively utilized for visual rendering. The project explicitly leverages `JFrame`, `JPanel`, `JButton`, `JLabel`, and layout managers like `CardLayout`, `GridLayout`, and `BorderLayout`. Dialog prompts are managed via `JOptionPane`.
* **Figma**: Used for UI layout design & implementation and visual asset mockups.

### External Dependencies
* **Standard Java Development Kit (JDK)**: No third-party external libraries are required. The project relies entirely on built-in standard Java packages:
  * `java.util`: Relied upon for `ArrayList` data structures to hold valid moves/captured pieces and `Scanner` to parse CLI input loops.
  * `java.awt`: Used for tracking bounds, image rendering (`BufferedImage`, `Graphics2D`), color generation (`Color`), and custom typography (`Font`).
  * `javax.swing`: Manages application windows, UI components, layout structures, and action event listeners (`ActionListener`).

---

## Project Structure

```bash
Hasami-Shogi/
│
├── assets/                             # Graphical & Font Assets
│   ├── main_menu_frame.png             # Main menu background frame
│   ├── main_game_overlay.png           # Gameboard background overlay 
│   ├── font-type/                      # Custom font assets
│   │   └── AfacadFlux-Regular.ttf      # Primary custom TrueType font 
│   └── game_pieces/                    # Piece graphical images
│       ├── blackHasamiShogiPiece.png   # 'White' player image asset
│       └── redHasamiShogiPiece.png     # 'Black' player image asset 
│
└── src/                                # Java Source Code
    ├── Board.java                      # Grid matrix, piece initialization, movement validation, & capture algorithms
    ├── Coordinate.java                 # Object mapping x (column) and y (row) values on the grid
    ├── Game.java                       # Terminal loop managing turns, captures tracking, and win states
    ├── GameButton.java                 # Swing JButton extension with coordinate tracking and image rendering
    ├── GamePanel.java                  # Swing JPanel managing the 9x9 UI grid, click interactions, and active states
    ├── Main.java                       # CLI Entry Point rendering terminal menus and ASCII instructions
    ├── MainGUI.java                    # GUI Entry Point constructing JFrames, navigation screens, and sidebar labels
    └── Player.java                     # Stores player state, captures, valid move generation, and Computer AI logic

```

---


## Installation

Follow these steps to download the project from GitHub to your local machine.

### Prerequisites
Ensure the following are installed on your local system:
* **Java Development Kit (JDK):** Version 8 or higher is required to compile and execute the Java files.
* **Git:** Required to clone the repository to your desktop.

---

### Step 1: Clone the Repository
Open your terminal (Command Prompt, IDE Terminal, PowerShell, or macOS/Linux Terminal) and run the following command to clone the project:

```bash
git clone https://github.com/anugyamehrotra/UNO-Online.git
```

### Step 2: Navigate & Verify the Project Directory
Move into the project directory. Ensure the `assets/` directory remains intact at the root of the project. The Graphical interface strictly expects `assets/game_pieces/`, `assets/font-type/`, and the background PNGs to render without crashing

```bash
cd UNO-Online
```

> If the font type in `assets/font-type/` fails to load, the default `SansSerif` will appear. 

### Step 3: Compile and Run
Choose the command based on your preferred execution mode:

**Option A: Terminal (CLI) Version**
To play the text-based console version utilizing ASCII board generation, run:
```bash
java Main
```

**Option B: Graphical (GUI) Version**
To launch the Java Swing window featuring mouse interactions and visual layouts, run:
```bash
java MainGUI
```

---

## License

This project is open-source and available under the standard MIT License.

```bash
MIT License

Copyright (c) 2026 Anugya Mehrotra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```

## Acknowledgements

Built in collaboration by [Anugya Mehrotra](https://github.com/anugyamehrotra) & [CreativeLapse](https://github.com/CreativeLapse)