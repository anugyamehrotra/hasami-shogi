import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    // main window attributes
    public static final int main_frame_width_x = 850;
    public static final int main_frame_height_y = 621;

    // main menu attributes
    public static final int menu_play_button_x_dist = 350;
    public static final int menu_play_button_y_dist = 334;
    public static final int menu_rules_button_x_dist = 350;
    public static final int menu_rules_button_y_dist = 415;
    public static final int menu_quit_button_x_dist = 350;
    public static final int menu_quit_button_y_dist = 493;
    public static final int menu_button_width_x = 150;
    public static final int menu_button_height_y = 50;

    // main game frame attributes
    public static final int game_box_frame_x_dist = -19; 
    public static final int game_box_frame_y_dist = -1;
    public static final int game_box_width_x = 1906;
    public static final int game_box_height_y= 1064;

    // game grid attributes
    public static final int game_board_x_dist = 20;
    public static final int game_board_y_dist = 20;
    public static final int game_board_width_x = 607;
    public static final int game_board_height_y = 564;
    
    // side-bar attributes
    public static final int side_bar_info_x_dist = 659; 
    public static final int side_bar_info_y_dist = 45;
    public static final int side_bar_info_width_x = 140;
    public static final int side_bar_info_height_y = 100;

    public static void main(String[] args) {

        // user game-choice prompt/menu
        JFrame mainGameFrame = new JFrame("HASAMI SHOGI © 2026");
        mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrame.setSize(main_frame_width_x, main_frame_height_y);
        mainGameFrame.setResizable(false);
        
        CardLayout cardLayout = new CardLayout();
        JPanel mainContentPanel = new JPanel(cardLayout);
        
        // main menu screen attributes
        BackgroundPanel mainMenuPanel = new BackgroundPanel("assets/main_menu_frame.png");
        
        JButton playerButton = new JButton("Play");
        playerButton.setBounds(menu_play_button_x_dist, menu_play_button_y_dist, menu_button_width_x, menu_button_height_y);

        playerButton.setOpaque(false);
        playerButton.setContentAreaFilled(false);
        playerButton.setBorderPainted(false);
        playerButton.setForeground(Color.WHITE);
        playerButton.setFocusPainted(false);
        playerButton.setFont(loadCustomFont(24f, Font.BOLD));
        playerButton.setForeground(new Color(72, 32, 4));
        
        JButton rulesButton = new JButton("Rules");
        rulesButton.setBounds(menu_rules_button_x_dist, menu_rules_button_y_dist, menu_button_width_x, menu_button_height_y);

        rulesButton.setOpaque(false);
        rulesButton.setContentAreaFilled(false);
        rulesButton.setBorderPainted(false);
        rulesButton.setForeground(Color.WHITE);
        rulesButton.setFocusPainted(false);
        rulesButton.setFont(loadCustomFont(24f, Font.BOLD));
        rulesButton.setForeground(new Color(72, 32, 4));
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(menu_quit_button_x_dist, menu_quit_button_y_dist, menu_button_width_x, menu_button_height_y);

        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);

        quitButton.setFont(loadCustomFont(24f, Font.BOLD));
        quitButton.setForeground(new Color(72, 32, 4));
        
        mainMenuPanel.add(playerButton);
        mainMenuPanel.add(rulesButton);
        mainMenuPanel.add(quitButton);
        
        // game board/panel
        BackgroundPanel mainGameBoardPanel = new BackgroundPanel("assets/main_game_overlay.png");
        
        // action event listeners (user options)
        playerButton.addActionListener(userMenuGameTypeAction -> {
            String[] userMenuOptions = {"Player v. Player", "Player v. Computer"};
            int userMenuChoice = JOptionPane.showOptionDialog(mainGameFrame,"Welcome to HASAMI SHOGI © 2026\n Select Game Mode:", "Hasami Shogi Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userMenuOptions, userMenuOptions[0]);
            

            // user cancellation check
            if (userMenuChoice == -1) {
                return; 
            }
            
            boolean isComputerPlayerActive = (userMenuChoice == 1); // validate if pc player is active based on player choice
            
            // start game upon user check
            setupGameBoard(mainGameBoardPanel, isComputerPlayerActive);
            cardLayout.show(mainContentPanel, "GAME");
        });
        
        rulesButton.addActionListener(userMenuRulesAction -> {
            JOptionPane.showMessageDialog(mainGameFrame, "Please visit https://en.wikipedia.org/wiki/Hasami_shogi for all Hasami Shogi Game rules", "Rules", JOptionPane.INFORMATION_MESSAGE);
        });
        
        quitButton.addActionListener(userMenuExitAction -> {
            System.exit(0);
        });

        mainContentPanel.add(mainMenuPanel, "MENU");
        mainContentPanel.add(mainGameBoardPanel, "GAME");
        
        mainGameFrame.add(mainContentPanel);
        mainGameFrame.setVisible(true);
    }
    
    /**
     * Method to dynamically set up the visual GUI layout & framing of the Hasami Shogi Game
     * Builds a visual layout, combining image attachment frames in "assets/" (of "main_menu_frame.png" & "main_game_overlay.png") and game panel layouts, to faciliate an engaging visual gameplay experience
     * @param mainGameBoardPanel JPanel attribute of mainGameBoardPanel containing "assets/main_game_overlay.png"
     * @param isComputerPlayerActive boolean vaidity of computer player activation (by player)
     */
    private static void setupGameBoard(JPanel mainGameBoardPanel, boolean isComputerPlayerActive) {
        mainGameBoardPanel.removeAll();
        
        Player playerOne = new Player("White Player", "w", false);
        Player playerTwo = new Player("Black Player", "b", isComputerPlayerActive);
        Board board = new Board();
        
        JPanel mainGameFrame = new JPanel(null);
        mainGameFrame.setBounds(game_box_frame_x_dist, game_box_frame_y_dist, game_box_width_x, game_box_height_y);
        mainGameFrame.setOpaque(false); 
        
        // sidebar info
        JPanel sidebarInfo = new JPanel();
        sidebarInfo.setLayout(new BoxLayout(sidebarInfo, BoxLayout.Y_AXIS));
        sidebarInfo.setBounds(side_bar_info_x_dist, side_bar_info_y_dist, side_bar_info_width_x, side_bar_info_height_y);
        sidebarInfo.setOpaque(false); 
        
        Font sidebarInfo_font = loadCustomFont(17f, Font.BOLD);
        Font sidebarInfo_font_default = loadCustomFont(17f, Font.BOLD);
        
        // info values & attributes
            JLabel turnLabel = new JLabel("  Turn: " + playerOne.getName());
            turnLabel.setFont(sidebarInfo_font);
            turnLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            turnLabel.setForeground(new Color(72, 32, 4));
            
            JLabel playerOneCaptures = new JLabel("  White Captures: 0");
            playerOneCaptures.setFont(sidebarInfo_font);
            playerOneCaptures.setAlignmentX(Component.LEFT_ALIGNMENT);
            playerOneCaptures.setForeground(new Color(72, 32, 4));
            
            JLabel playerTwoCaptures = new JLabel("  Black Captures: 0");
            playerTwoCaptures.setFont(sidebarInfo_font);
            playerTwoCaptures.setAlignmentX(Component.LEFT_ALIGNMENT);
            playerTwoCaptures.setForeground(new Color(72, 32, 4));
            
            sidebarInfo.add(turnLabel);
            sidebarInfo.add(playerOneCaptures);
            sidebarInfo.add(playerTwoCaptures);

        GamePanel boardPanel = new GamePanel(board, playerOne, playerTwo, isComputerPlayerActive, turnLabel, playerOneCaptures, playerTwoCaptures);
        
        // Column numbers (top)
        JPanel topColNumsJPanel = new JPanel(new GridLayout(1, 9));
        topColNumsJPanel.setBackground(Color.WHITE); // White bar for numerical info
        topColNumsJPanel.setOpaque(true);
        for(int cols = 9; cols >= 1; cols--){
            topColNumsJPanel.add(new JLabel(String.valueOf(cols), SwingConstants.CENTER));
        }
        

        JPanel sideRowCharJPanel = new JPanel(new GridLayout(9, 1));
        sideRowCharJPanel.setBackground(Color.WHITE); // White bar for alphabetical info
        sideRowCharJPanel.setOpaque(true);
        for(char chars = 'a'; chars <= 'i'; chars++){
            sideRowCharJPanel.add(new JLabel("  " + chars + "  "));
        }
        
        JPanel sideBoardInfo = new JPanel(new BorderLayout());
        
        JPanel topBoardInfo = new JPanel(new BorderLayout());
        JLabel topBarSpacing = new JLabel("   ");
        topBarSpacing.setOpaque(true);
        topBarSpacing.setBackground(Color.WHITE);
        topBoardInfo.add(topBarSpacing, BorderLayout.EAST); 
        topBoardInfo.add(topColNumsJPanel, BorderLayout.CENTER);
        topBoardInfo.setOpaque(true);
        topBoardInfo.setBackground(Color.WHITE);
        
        sideBoardInfo.add(topBoardInfo, BorderLayout.NORTH);
        sideBoardInfo.add(sideRowCharJPanel, BorderLayout.EAST); 
        sideBoardInfo.add(boardPanel, BorderLayout.CENTER);
        sideBoardInfo.setOpaque(false);
        
        sideBoardInfo.setBounds(game_board_x_dist, game_board_y_dist, game_board_width_x, game_board_height_y);
        
        mainGameFrame.add(sidebarInfo);
        mainGameFrame.add(sideBoardInfo);
        
        mainGameBoardPanel.add(mainGameFrame);
        mainGameBoardPanel.revalidate();
        mainGameBoardPanel.repaint();
    }

    /**
     * Method to dyamically load custom font from the project assets directory to match stylistic details of Hasami Shogi GUI
     * @param size desired font size in float
     * @param style integer size of font style
     * @return a Font object of type 'AfacadFlux-Regular' (or default-fallback SansSerif Font) to be applied to sidebar panel details
     */
    private static Font loadCustomFont(float size, int style) {
        try {
            java.io.File fontFile = new java.io.File("assets/font-type/AfacadFlux-Regular.ttf");
            if(fontFile.exists()) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                return customFont.deriveFont(style, size);
            }
        } catch (Exception e) {
            System.err.println("Could not load custom font, falling back to SansSerif");
        }
        return new Font("SansSerif", style, (int)size);
    }
}


class BackgroundPanel extends JPanel {
    private Image bgImage;
    public BackgroundPanel(String imagePath) {
        bgImage = new ImageIcon(imagePath).getImage();
        setLayout(null);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}

// Sources:
// https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html -- > Java Frames
// https://stackoverflow.com/questions/30518728/how-does-the-sizing-of-a-jframe-actually-work --> JFrame Sizing
// https://docs.oracle.com/javase/tutorial/uiswing/components/ dialog.html --> Open Dialogue
// https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html -- > Box Layout Orientation & Sizing
// https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html -- > GridLayout Orientation
// https://docs.oracle.com/javase/8/docs/api/java/awt/BorderLayout.html --> Border Layout Orientation
// https://docs.oracle.com/javase/tutorial/uiswing/components/html.html --> HTML JLabel Formatting
