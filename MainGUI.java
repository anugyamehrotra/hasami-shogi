import javax.swing.*;
import java.awt.*;

public class MainGUI {
    public static void main(String[] args) {

        // HASAMI SHOGI GAME -- HOW TO
            // TO RUN GUI BASED PROGRAM:
                // RUN MAINGUI.JAVA TO INTERACT WITH VISUAL-BASED HASAMI SHOGI GAME.
                // AS MENU PROMPT OPENS (TO TRIGGER TYPE OF PLAYER MODE), CLICK PVP (PLAYER VS PLAYER) OR PVC (PLAYER VS COMPUTER) OPTION AS PER PLAYER DESCRETION
                // FOLLOW GAMES RULES ON SIDE BAR TO PLAY GAME

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

        JLabel emptySpacingFormatting = new JLabel(" ");

        JLabel rulesHeader = new JLabel("  HASAMI SHOGI - HOW TO PLAY:");
        JLabel emptySpacingFormatting2 = new JLabel(" ");
        JLabel rulesOne = new JLabel("  Move pieces one space at a time");
        JLabel rulesTwo = new JLabel("  Pieces move in any direction");
        JLabel rulesThree = new JLabel("  Capture by sandwiching opponents");
        JLabel rulesFour = new JLabel("  Trap pieces orthogonally on a row");
        JLabel rulesFive = new JLabel("  Surround enemy pieces between yours");
        JLabel rulesSix = new JLabel("  First player to capture 5 pieces wins!");


        sideGameFrame.add(turnLabel);
        sideGameFrame.add(playerOneCaptures);
        sideGameFrame.add(playerTwoCaptures);
        sideGameFrame.add(emptySpacingFormatting);
        sideGameFrame.add(rulesHeader);
        sideGameFrame.add(emptySpacingFormatting2);
        sideGameFrame.add(rulesOne);
        sideGameFrame.add(rulesTwo);
        sideGameFrame.add(rulesThree);
        sideGameFrame.add(rulesFour);
        sideGameFrame.add(rulesFive);
        sideGameFrame.add(rulesSix);
        

        GamePanel boardPanel = new GamePanel(board, player1, player2, isComputerPlayerActive, turnLabel, playerOneCaptures, playerTwoCaptures);

        JPanel topColNumsJPanel = new JPanel(new GridLayout(1, 9));
        for(int cols = 9; cols >= 1; cols--){
            topColNumsJPanel.add(new JLabel(String.valueOf(cols), SwingConstants.CENTER));
        }

        JPanel sideRowCharJPanel = new JPanel(new GridLayout(9, 1));
        for(char chars = 'a'; chars <= 'i'; chars++){
            sideRowCharJPanel.add(new JLabel("  " + chars + "  "));
        }

        JPanel topBoardInfo = new JPanel(new BorderLayout());
        topBoardInfo.add(new JLabel(BorderLayout.EAST)); 
        topBoardInfo.add(topColNumsJPanel, BorderLayout.CENTER);

        JPanel sideBoardInfo = new JPanel(new BorderLayout());
        sideBoardInfo.add(topBoardInfo, BorderLayout.NORTH);
        sideBoardInfo.add(sideRowCharJPanel, BorderLayout.EAST); 
        sideBoardInfo.add(boardPanel, BorderLayout.CENTER);      

        mainGameFrame.add(sideBoardInfo, BorderLayout.CENTER); 
        mainGameFrame.add(sideGameFrame, BorderLayout.EAST);

        mainGameFrame.setSize(850, 600); 
        mainGameFrame.setVisible(true);
    }
}

// Sources:
// https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html -- > Java Frames
// https://stackoverflow.com/questions/30518728/how-does-the-sizing-of-a-jframe-actually-work --> JFrame Sizing
// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html --> Open Dialogue
// https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html -- > Box Layout Orientation & Sizing
// https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html -- > GridLayout Orientation
// https://docs.oracle.com/javase/8/docs/api/java/awt/BorderLayout.html --> Border Layout Orientation
// https://docs.oracle.com/javase/tutorial/uiswing/components/html.html --> HTML JLabel Formatting

