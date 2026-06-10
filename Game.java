import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;

    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
    }

    void setupGame(){
        System.out.println("---- Hasami Shogi ----- \n");
        System.out.println("Enter (1) for player vs. player or (2) for player vs. computer");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if (choice == 1) {
            player1 = new Player("Player 1", "b", false);
            player2 = new Player("Player 2", "w", false);
        } else {
            player1 = new Player("Player 1", "b", false);
            player2 = new Player("Computer", "w", true);
        }
    }


    void startGame() {
        setupGame();

        Player currPlayer = player1;
        while (!gameOver()) {
            System.out.println(board);
            System.out.println(currPlayer.getName() + "'s turn");

            takeTurn(currPlayer);

            if (currPlayer == player1) {
                currPlayer = player2;
            } else {
                currPlayer = player1;
            }
        }

        System.out.println(board);
        Player winner = player1;
        if (player1.captureCount() >= 5) {
            winner = player1;
        } else {
            winner = player2;
        }

        System.out.println(winner.getName() + " is the winner of this game!");

    }

    void takeTurn(Player currPlayer) {
        Coordinate[] pos = currPlayer.getMove(board);
        board.movePiece(pos[0], pos[1]);

        int captures = board.checkAndKill(pos[1]) + board.checkCornerKill(pos[1]);
        currPlayer.addCaptures(captures);
        if (captures > 0) {
            System.out.printf("%s captured %d pieces.\n", currPlayer.getName(), captures);
        }
    }

    boolean gameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }

}
