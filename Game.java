import java.util.ArrayList;
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

    void startGame() {
        System.out.println("HASAMI SHOGI © 2026 \n");
       
        Player currPlayer = player1;
        while (!gameOver()) {
            board.displayBoard();

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

        System.out.println(winner.getName() + " is the winner of this game!");
    }

    private void displayPlayerCaptures(Player player){
        String colorName;
        String enemyPlayerPiece;

        if (player.getColour().equals("w")) {
            colorName = "White";
            enemyPlayerPiece = "☗";
        } else {
            colorName = "Black";
            enemyPlayerPiece = "☖";
        }

        ArrayList<String> capturedPiecesList = new ArrayList<>();
        for(int i = 0; i < player.captureCount(); i ++){
            capturedPiecesList.add(enemyPlayerPiece);
        }

        System.out.println(colorName.toUpperCase() + "'s CAPTURED PIECES: " + capturedPiecesList + "\n");
    }

    void takeTurn(Player currPlayer) {
        Coordinate[] pos = currPlayer.getMove(board);
        board.movePiece(pos[0], pos[1]);

        int captures = 0;
        captures += board.checkAndKill(pos[1]);
        captures += board.cornerKill(pos[1]);

        String enemyPlayerPiece = "";
        String enemyColorName = "";

        if(captures > 0){
            currPlayer.addCaptures(captures);
            System.out.printf("\n*** %s CAPTURED %d PIECE(S)! ***\n", currPlayer.getName(), captures);
        }
    }

    boolean gameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }

}
