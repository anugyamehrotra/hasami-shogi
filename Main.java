import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        // HASAMI SHOGI GAME -- HOW TO
            // TO RUN TERMINAL BASED PROGRAM:
                // RUN MAIN.JAVA TO INTERACT WITH HASAMI SHOGI GAME. ENSURE YOU HAVE ALL JAVA PACKAGES, PLUGINS, AND NECESSARY SOFTWARE INSTALLED TO RUN THE JAVA COMPILER
                // AS IDE LOADS, CLICK RUN TO START THE GAME
                // FOLLOW ALL INPUT INSTRUCTIONS PROVIDED IN THE OUTPUT TERMINAL

            // TO RUN GUI BASED PROGRAM:
                // RUN MAINGUI.JAVA TO INTERACT WITH VISUAL-BASED HASAMI SHOGI GAME.
                // AS MENU PROMPT OPENS (TO TRIGGER TYPE OF PLAYER MODE), CLICK PVP (PLAYER VS PLAYER) OR PVC (PLAYER VS COMPUTER) OPTION AS PER PLAYER DESCRETION
                // FOLLOW GAMES RULES

        System.out.println("*****************************************");
        System.out.println("*          HASAMI SHOGI © 2026          *");
        System.out.println("***************************************** \n");

        System.out.println("************** HOW TO PLAY **************");
        System.out.println("*    Move pieces one space at a time    *");
        System.out.println("*      Pieces move in any direction     *");
        System.out.println("*    Capture by sandwiching opponents   *");
        System.out.println("*   Trap pieces orthogonally on a row   *");
        System.out.println("*  Surround enemy pieces between yours  *");
        System.out.println("*    First player to capture 5 pieces   *");
        System.out.println("*                 wins!                 *");
        System.out.println("***************************************** \n");


        Scanner mainUserMenuScanner = new Scanner(System.in);


        // START GAME LAUNCH
        while(true){
            System.out.println("Please select your choice: TAKE ME TO MENU (ENTER 'PLAY' or 'START') | TAKE ME TO RULES (ENTER 'RULES' or 'HELP' | OR EXIT (ENTER 'EXIT' OR 'QUIT') ");

            try{
                String menuOption = mainUserMenuScanner.nextLine().trim().toLowerCase();

                
                if(menuOption.equals("start") || menuOption.equals("game") || menuOption.equals("play") || menuOption.equals("begin")){
                    Player player1 = null;
                    Player player2 = null;

                    // PLAYER TYPE/MODE LAUNCH
                    while(true){
                        System.out.println("Please enter your game choice: PLAYER V. PLAYER (ENTER '1' or 'ONE') | PLAYER V. COMPUTER (ENTER '2' or 'TWO'): ");

                        try{
                            String userInput = mainUserMenuScanner.nextLine().trim();
                            if(userInput.equals("1") || userInput.equals("one") || userInput.equals("first") || userInput.equals("player")){
                                player1 = new Player("Player 1", "b", false);
                                player2 = new Player("Player 2", "w", false);
                                break;
                            } else if(userInput.equals("2") || userInput.equals("two") || userInput.equals("second") || userInput.equals("computer") || userInput.equals("pc")){
                                player1 = new Player("Player 1", "b", false);
                                player2 = new Player("Computer", "w", true);
                                break;
                            } else {
                                throw new IllegalArgumentException("\n *** Invalid game-choice input! Please try again! *** \n ");
                            }

                        } catch (IllegalArgumentException invalidGameChoiceInput) {
                            System.out.println(invalidGameChoiceInput.getMessage());
                        }
                    }

                    System.out.println("\n****************************************");
                    System.out.println("*             LET's BEGIN              *");
                    System.out.println("**************************************** \n");

                    Game game = new Game(player1, player2);
                    game.startGame();
                } else if (menuOption.equals("rules") || menuOption.equals("help") || menuOption.equals("guide")){
                    System.out.println("\nPlease visit https://en.wikipedia.org/wiki/Hasami_shogi for all Hasami Shogi Game rules");
                } else if(menuOption.equals("exit") || menuOption.equals("quit") || menuOption.equals("close")){
                    System.out.println("\nGoodbye!");
                    mainUserMenuScanner.close();
                    break;
                } else{
                    throw new IllegalArgumentException("\n *** Invalid prompt input! Please try again by inputting a valid user choice! *** \n ");
                }
            
            } catch (IllegalArgumentException invalidMainMenuChoice){
                System.out.println(invalidMainMenuChoice.getMessage());
            }
        }
    }
}
