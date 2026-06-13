import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class GamePanel extends JPanel{
    private Board board;
    private Game game;
    private JButton[][] grid;
    private Coordinate chosenCoord;

    GamePanel(Board board, Game game){
        this.board = board;
        this.game = game;
        this.grid = new GameButton[9][9];
        this.chosenCoord = null;

        setLayout(new GridLayout(9,9));

        for(int rows = 0; rows < 9; rows++){
            for(int cols = 0; cols < 9; cols++){
                Coordinate boardCoordinate = new Coordinate(9 - rows, String.valueOf((char) 'a' + cols));

                GameButton boardButton = new GameButton(boardCoordinate);
                boardButton.setBackground(Color.YELLOW);
                boardButton.addActionListener(this);

                this.grid[rows][cols] = boardButton;
                this.add(boardButton); 
            }
        }
        
        refeshGamePanel();
    }

    public void refeshGamePanel(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String piece = board.getPiece(grid[i][j].getCoordinate());
                grid[i][j].changeColour(piece);
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if(chosenCoord == null){
                
        }
    }
    

    // Sources:
    // https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionEvent.html


}

