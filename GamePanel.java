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

    // JLabels Attributes 
    private JLabel playerTurnLabel; 
    private JLabel playerOneCaptures;
    private JLabel playerTwoCaptures;

    /**
     * Constructor for GamePanel GUI in Hasami Shogi GUI Version
     * Sets up the main visual game, links backend methods & logic to GUI, and handles & performs user action
     * @param board board Object containing pieces of Hasami Shogi Game
     * @param player1 first player interacting in Hasami Shogi Game (White)
     * @param player2 second player interacting in Hasami Shogi Game (Black)
     * @param isComputerPlayerActive boolean validity of active computer player in Hasmi Shogi Game 
     * @param playerTurnLabel text label attribute of player turns in side-bar GUI in game interface
     * @param playerOneCaptures text label attribute of player captures in side-bar GUI in game interface
     * @param playerTwoCaptures text label attribute of player captures in side-bar GUI in game interface
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
     * Method to dynamically update & refresh GUI interface grid to match instantaneous back-end game state(s) 
     * Sets pieces, image icons, and background-color information according to backend game state
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
     * Method to dynamically handle player piece selection/de-selection & movement across Hasami Shogi Game Board
     * Handles valid selection/de-selection, movements, and piece interaction in game, ensuring robust, smooth gameplay
     * @param userActionButton GameButton Object to describe specific button interacted/clicked on Hasami Shogi grid
     */
    private void performMovement(GameButton userActionButton){
        if(isGameOver()){
            return;
        }

        // avoid all conflict between manual player movement & computer movement
        if(isComputerPlayerActive && currPlayer == player2){
            return;
        }

        Coordinate desiredCoordinate = userActionButton.getCoordinate();

        // handle invalid final destination
        if(chosenCoordinate == null){
            if(board.getPiece(desiredCoordinate).equals(currPlayer.getColour())){
                chosenCoordinate = desiredCoordinate; 
                userActionButton.setBackground(new Color(214, 185, 44));
            }
        } else {
            // ensure if piece selected is same, ignore case & return to original position while displayed on board
            if(String.valueOf(chosenCoordinate).equals(String.valueOf(desiredCoordinate))){
                chosenCoordinate = null;
                updatePanel();
            } else {
                if(board.canMove(chosenCoordinate, desiredCoordinate)){
                    movePiece(chosenCoordinate, desiredCoordinate);
                } else {
                    chosenCoordinate = null;
                    JOptionPane.showMessageDialog(this, "Invalid piece movement. Pieces must move orthogonally and cannot be placed on other pieces.");
                    updatePanel();
                }
            }   
        }
    }

    /**
     * Method to physically move Hasami Shogi pieces across game board
     * Selects valid current & desired coordinates and move pieces accordingly, tracks & records captures, and passes turns while visually displaying all changes
     * @param currentCoordinate Coordinate Object for current coordinate position selected by user in Hasami Shogi Game 
     * @param desiredCoordinate Coordinate Object for desired coordinate position chosen by user in Hasami Shogi Game
     */
    private void movePiece(Coordinate currentCoordinate, Coordinate desiredCoordinate) {
        board.movePiece(currentCoordinate, desiredCoordinate);

        int captures = 0;
        captures += board.checkAndKill(desiredCoordinate);
        captures += board.cornerKill(desiredCoordinate);

        if(captures > 0){
            currPlayer.addCaptures(captures);
        }

        chosenCoordinate = null; // set null for 'unclicked' behavior, where once clicked, chosen coord is set to null after movement to continue game
        updatePanel();
        swapTurn();
    }

    /**
     * Method to dynamically organize, activate, and update active players, score count labels, and winner validity in Hasami Shogi Game
     */
    private void swapTurn() {
        if(currPlayer == player1){
            currPlayer = player2;
        } else {
            currPlayer = player1;
        }
                
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

        // avoid all conflict between manual player movement & computer movement
        if (isComputerPlayerActive && currPlayer == player2) {
            computerMove();
        }
    }

    /**
     * Method to randomly move pieces on behalf of a 'Computer' player in Hasami Shogi Game
     * Moves pieces randomly based on backend Computer Movement logic & algorithm
     */
    private void computerMove() {
        Coordinate[] computerMove = currPlayer.computerMove(board);

        if(board.canMove(computerMove[0], computerMove[1])){
            movePiece(computerMove[0], computerMove[1]);
        }
    }

    /**
     * Method to return boolean validity of Winning Game State in Hasami Shogi
     * @return boolean validity if five (5) or more pieces are captured by either players, satisfying the win condition, across the game
     */
    private boolean isGameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }
}

// Sources
// https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionListener.html --> Add Action Listener & Handle Functions