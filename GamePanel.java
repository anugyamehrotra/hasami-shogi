import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currPlayer;
    private boolean isComputerPlayerActive;
    
    private GameButton[][] grid;
    private Coordinate chosenCoordinate;

    private JLabel turnLabel;
    private JLabel playerOneCaptures;
    private JLabel playerTwoCaptures;

    public GamePanel(Board board, Player player1, Player player2, boolean isComputerPlayerActive, JLabel turnLabel, JLabel playerOneCaptures, JLabel playerTwoCaptures) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currPlayer = player1; 
        this.isComputerPlayerActive = isComputerPlayerActive;
        
        this.turnLabel = turnLabel;
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
                gameButton.addActionListener(this);
                
                this.grid[rows][cols] = gameButton;
                this.add(gameButton); 
            }
        }
        updatePanel();
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isGameOver()){
            return;
        }

        if(isComputerPlayerActive && currPlayer == player2){
            return;
        }

        GameButton clickedgameButton = null;
        Coordinate clickedCoord = null;

        for (int rows = 0; rows < 9; rows++) {
            for (int cols = 0; cols < 9; cols++) {
                if (e.getSource() == grid[rows][cols]) {
                    clickedgameButton = grid[rows][cols];
                    clickedCoord = clickedgameButton.getCoordinate();
                    break;
                }
            }
        }

        if (clickedgameButton == null || clickedCoord == null) {
            return; 
        }

        if (chosenCoordinate == null) {
            if (board.getPiece(clickedCoord).equals(currPlayer.getColour())) {
                chosenCoordinate = clickedCoord;
                clickedgameButton.setBackground(new Color(214, 185, 44)); 
            }
        } else {
            if (chosenCoordinate.toString().equals(clickedCoord.toString())) {
                chosenCoordinate = null;
                updatePanel();
            } else {
                if (board.canMove(chosenCoordinate, clickedCoord)) {
                    movePiece(chosenCoordinate, clickedCoord);
                } else {
                    chosenCoordinate = null;
                    updatePanel();
                }
            }
        }
    }

    private void movePiece(Coordinate start, Coordinate end) {
        board.movePiece(start, end);

        int captures = 0;
        captures += board.checkAndKill(end);
        captures += board.cornerKill(end);

        if(captures > 0){
            currPlayer.addCaptures(captures);
        }

        chosenCoordinate = null;
        updatePanel();
        swapTurn();
    }

    private void swapTurn() {
        currPlayer = (currPlayer == player1) ? player2 : player1;
        
        turnLabel.setText("Turn: " + currPlayer.getName());
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

    private void computerMove() {
        Coordinate[] compMove = currPlayer.computerMove(board);
        if (compMove != null && compMove.length == 2) {
            movePiece(compMove[0], compMove[1]);
        }
    }

    private boolean isGameOver() {
        return player1.captureCount() >= 5 || player2.captureCount() >= 5;
    }
}