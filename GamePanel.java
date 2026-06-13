import javax.swing.JButton;
import javax.swing.JPanel;

class GamePanel extends JPanel{
    private Board board;
    private Game game;
    private JButton[][] grid;
    private Coordinate chosenCoord;

    GamePanel(Board board, Game game){
        JButton[][] buttons = new JButton[9][9];
        for (int i = 0; i < 9; i ++){
            for (int j = 0; j < 9; j ++){
                buttons[i][j] = new JButton();
            }

            
        }
        this.grid = buttons;
    }


}
