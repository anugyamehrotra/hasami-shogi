import java.util.ArrayList;

public class Game {

    // Game Attributes
    private Player player1;
    private Player player2;
    private Board board;

    /**
     * Constructor to initialize Hasami Shogi Game & Board
     * @param player1 Player Object for player one interacting in Hasami Shogi Game (White)
     * @param player2 Player Object for player two interacting in Hasami Shogi Game (White)
     */
    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
    }

    /**
     * Method to dynamically initialize, manage, and maintain primary game loop of Hasami Shogi Game
     * Display board, alternate player logic/turns, & handle win conditions across primary game
     * Relies on display captures & turn alternation methods to carry out main primary loop functionality
     */
    void startGame() {
        System.out.println("HASAMI SHOGI © 2026 ** " + player1.getName() + " vs. " + player2.getName() + "\n");
       
        Player currPlayer = player1;
        while (!gameOver()) {
            board.displayBoard();

            // display arrayList of captures per round
            displayPlayerCaptures(player1); 
            displayPlayerCaptures(player2);

            System.out.println( currPlayer.getName() + "'s TURN: \n");
            takeTurn(currPlayer);

            if (currPlayer == player1) {
                currPlayer = player2;
            } else {
                currPlayer = player1;
            }
        }

        Player winner = player1;
        if (player1.captureCount() >= 5) {
            winner = player1;
        } else {
            winner = player2;
        }

        System.out.println(winner.getName() + " is the winner of this game! \n");
        board.displayBoard(); // display final board to show winner & last movements
    }

    /**
     * Method to visually build & print a list of captured enemy pieces of a given player
     * Searches for player capture counts (per player) & builds an arrayList containing the amount of captured enemy pieces
     * @param player Player Object for player whose captures are logged & displayed
     */
    private void displayPlayerCaptures(Player player){
        String colorName;
        String enemyPlayerPiece;

        // alternate enemy & piece color depending on turns in game --> handle any conversion mid-game to avoid conflict of display elements
        if (player.getColour().equals("w")) {
            colorName = "White";
            enemyPlayerPiece = "☗";
        } else {
            colorName = "Black";
            enemyPlayerPiece = "☖";
        }

        // search thru capture count built from kills & respective board methods
        ArrayList<String> capturedPiecesList = new ArrayList<>();
        for(int i = 0; i < player.captureCount(); i ++){
            capturedPiecesList.add(enemyPlayerPiece);
        }

        System.out.println(colorName.toUpperCase() + "'s CAPTURED PIECES: " + capturedPiecesList + "\n");
    }

    /**
     * Method to dynamically organize, activate, and update active player(s) turns in Hasami Shogi Game
     * Retrieve moves (based on final position) & check for all captures while alternating turns per player
     * @param currPlayer Player Object for current player taking their turn in-game
     */
    void takeTurn(Player currPlayer) {
        Coordinate[] pos = currPlayer.getMove(board);
        board.movePiece(pos[0], pos[1]);

        int captures = 0;
        captures += board.checkAndKill(pos[1]);
        captures += board.cornerKill(pos[1]);

        if(captures > 0){
            currPlayer.addCaptures(captures);
            System.out.printf("\n*** %s CAPTURED %d PIECE(S)! *** \n \n", currPlayer.getName(), captures);
        }
    }

    /**
     * Method to return boolean validity of Winning Game State in Hasami Shogi
     * @return boolean validity if five (5) or more pieces are captured by either players, satisfying the win condition, across the game
     */
    boolean gameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }

}
