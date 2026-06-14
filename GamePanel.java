import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel  {

    // GamePanel Attributes
    private Board board;
    private Player player1;
    private Player player2;
    private Player currPlayer;
    private boolean isComputerPlayerActive;
    
    private GameButton[][] grid;
    private Coordinate chosenCoordinate;

    private JLabel playerTurnLabel;
    private JLabel playerOneCaptures;
    private JLabel playerTwoCaptures;

    /**
     * Constructor for GamePanel GUI in Hasami Shogi GUI Version
     * Sets up the main visual game, links backend methods & logic to GUI, and handles & performs user action
     * @param board board Object 
     * @param player1
     * @param player2
     * @param isComputerPlayerActive
     * @param playerTurnLabel
     * @param playerOneCaptures
     * @param playerTwoCaptures
     */
    public GamePanel(Board board, Player player1, Player player2, boolean isComputerPlayerActive, JLabel playerTurnLabel, JLabel playerOneCaptures, JLabel playerTwoCaptures) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currPlayer = player1; 
        this.isComputerPlayerActive = isComputerPlayerActive;
        
        this.playerTurnLabel = playerTurnLabel;
        this.playerOneCaptures = playerOneCaptures;
        this.playerTwoCaptures = playerTwoCaptures;
        
        this.grid = new GameButton[9][9];
        this.chosenCoordinate = null;

        setLayout(new GridLayout(9, 9));

        for (int rows = 0; rows < 9; rows++) {
            for (int cols = 0; cols < 9; cols++) {
                // reverse loop in coordinate declaration to fill in piece positions from columns per row
                Coordinate populCoordinate = new Coordinate(cols + 1, String.valueOf((char) ('a' + rows)));
                
                GameButton gameButton = new GameButton(populCoordinate);
                gameButton.addActionListener(e-> performMovement(gameButton));
                
                this.grid[rows][cols] = gameButton;
                this.add(gameButton); 
            }
        }
        updatePanel();
    }

    /**
     * 
     */
    public void updatePanel() {
        for (int rows = 0; rows < 9; rows++) {
            for (int cols = 0; cols < 9; cols++) {
                GameButton gameButton = grid[rows][cols];
                String piece = board.getPiece(gameButton.getCoordinate());
                gameButton.changePlayerPiece(piece); 
                gameButton.setBackground(new Color(255, 255, 153));
            }
        }
    }


    /**
     * @param userActionButton
     */
    private void performMovement(GameButton userActionButton){
        if(isGameOver()){
            return;
        }

        if(isComputerPlayerActive && currPlayer == player2){
            return;
        }

        Coordinate desiredCoordinate = userActionButton.getCoordinate();

        if(chosenCoordinate == null){
            if(board.getPiece(desiredCoordinate).equals(currPlayer.getColour())){
                chosenCoordinate = desiredCoordinate; 
                userActionButton.setBackground(new Color(214, 185, 44));
            }
        } else {
            if(String.valueOf(chosenCoordinate).equals(String.valueOf(desiredCoordinate))){
                chosenCoordinate = null;
                updatePanel();
            } else {
                if(board.canMove(chosenCoordinate, desiredCoordinate)){
                    movePiece(chosenCoordinate, desiredCoordinate);
                } else {
                    chosenCoordinate = null;
                    JOptionPane.showMessageDialog(this, "Invalid piece movement. Remember, you can only move orthogonally!");
                    updatePanel();
                }
            }   
        }
    }

    /**
     * @param currentCoordinate
     * @param desiredCoordinate
     */
    private void movePiece(Coordinate currentCoordinate, Coordinate desiredCoordinate) {
        board.movePiece(currentCoordinate, desiredCoordinate);

        int captures = 0;
        captures += board.checkAndKill(desiredCoordinate);
        captures += board.cornerKill(desiredCoordinate);

        if(captures > 0){
            currPlayer.addCaptures(captures);
        }

        chosenCoordinate = null;
        updatePanel();
        swapTurn();
    }

    /**
     * 
     */
    private void swapTurn() {
        currPlayer = (currPlayer == player1) ? player2 : player1;
        
        playerTurnLabel.setText("Turn: " + currPlayer.getName());
        playerOneCaptures.setText("White Captures: " + player1.captureCount());
        playerTwoCaptures.setText("Black Captures: " + player2.captureCount());

        if (isGameOver()) {
            Player winner = player1;
            if (player1.captureCount() >= 5) {
                winner = player1;
            } else {
                winner = player2;
            }
            JOptionPane.showMessageDialog(this, winner.getName() + " Wins!");
            return;
        }

        if (isComputerPlayerActive && currPlayer == player2) {
            computerMove();
        }
    }

    /**
     * 
     */
    private void computerMove() {
        Coordinate[] computerMove = currPlayer.computerMove(board);

        if(board.canMove(computerMove[0], computerMove[1])){
            movePiece(computerMove[0], computerMove[1]);
        }
    }

    /**
     * @return
     */
    private boolean isGameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }
}

// Sources
// https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionListener.html --> Add Action Listener & Handle Functions