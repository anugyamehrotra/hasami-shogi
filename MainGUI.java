import javax.swing.*;
import java.awt.*;

public class MainGUI {
    public static void main(String[] args) {
        // handle user menu choice (pvp or pvc option)
        String[] userMenuOptions = {"Player v. Player", "Player v. Computer"};
        int userOption = JOptionPane.showOptionDialog(null,"Welcome to HASAMI SHOGI © 2026\n Select Game Mode:", "Hasami Shogi Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userMenuOptions, userMenuOptions[0]);
        boolean isComputerPlayerActive = false;

        if(userOption == -1){
            System.exit(0);
        } else if (userOption == 1){
            isComputerPlayerActive = true;
        }
        
        // declare players & respective attributes
        Player player1 = new Player("White Player", "w", false);
        Player player2 = new Player("Black Player", "b", isComputerPlayerActive);
        Board board = new Board();

        // main game frame
        JFrame mainGameFrame = new JFrame("HASAMI SHOGI © 2026");
        mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrame.setLayout(new BorderLayout()); 
   
        // main game panel & side-bar panel for game qualities & information
        JPanel sideGameFrame = new JPanel();
        sideGameFrame.setLayout(new BoxLayout(sideGameFrame, BoxLayout.Y_AXIS));
        sideGameFrame.setBackground(Color.WHITE);

        JLabel turnLabel = new JLabel("  Turn: " + player1.getName());
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        JLabel playerOneCaptures = new JLabel("  White Captures: 0");
        JLabel playerTwoCaptures = new JLabel("  Black Captures: 0");

        sideGameFrame.add(turnLabel);
        sideGameFrame.add(playerOneCaptures);
        sideGameFrame.add(playerTwoCaptures);

        GamePanel boardPanel = new GamePanel(board, player1, player2, isComputerPlayerActive, turnLabel, playerOneCaptures, playerTwoCaptures);
        
        mainGameFrame.add(boardPanel);
        mainGameFrame.add(sideGameFrame, BorderLayout.EAST);

        mainGameFrame.setSize(800, 600); 
        mainGameFrame.setVisible(true);
    }
}

// Sources:
// https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html -- > Java Frames
// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html --> Open Dialogue
// https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html -- > Box Layout & Sizing

