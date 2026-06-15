import java.util.ArrayList;
import java.util.Scanner;

class Player {

    // Player Attributes
    private String name;
    private String colour;
    private int captureCount;
    private boolean isComputer;

    /**
     * Constructor for Player Object in Hasami Shogi Game 
     * @param name Player name
     * @param colour Player piece color ("w" for White, "b" for Black)
     * @param isComputer Boolean validity for Computer Player (in PVC mode)
     */
    Player(String name, String colour, boolean isComputer) {
        this.name = name;
        this.colour = colour;
        this.isComputer = isComputer;
        this.captureCount = 0;
    }

    /**
     * Method to add player's total capture count in Hasami Shogi Game
     * @param captures Integer value of current player captures 
     */
    void addCaptures(int captures) {
        this.captureCount += captures;
    }

    /**
     * Method to return player's name attribute in Hasami Shogi Game
     * @return String representation of individual Player names
     */
    String getName() {
        return this.name;
    }

    /**
     * Method to return color attribute of Player's pieces in Hasami Shogi Game
     * @return String representation of color attribute recognized in backend
     */
    String getColour(){
        return this.colour;
    }

    /**
     * Method to return total count of Player's captured (enemy) pieces in Hasami Shogi Game
     * @return Integer value of captured enemy pieces
     */
    int captureCount() {
        return this.captureCount;
    }

    /**
     * Method to randomly move & alternate pieces on behalf of Computer Player in Hasami Shogi game
     * Locate current pieces belonnging to Computer Player, valid possible valid movements across board, & randomly move pieces according to respective pieces & valid positions 
     * @param board Board Object of the Game Board in respective state
     * @return ArrayList of starting & ending coordinates of a Computer Player
     */
    Coordinate[] computerMove(Board board){
        ArrayList<Coordinate[]> possibleMoves = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                for (char c = 'a'; c <= 'i'; c++) {
                    Coordinate startCoord = new Coordinate(i, String.valueOf(c));

                    if (board.getPiece(startCoord).equals(this.colour)) {
                        for (int x = 1; x <= 9; x++) {
                            for (char y = 'a'; y <= 'i'; y++) {
                                Coordinate endCoord = new Coordinate(x, String.valueOf(y));

                                if (board.canMove(startCoord, endCoord)) {
                                    possibleMoves.add(new Coordinate[] { startCoord, endCoord });
                                }
                            }
                        }
                    }
                }
            }
            return possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    }


    /**
     * Method to dynamically locate all pieces belonging to a Player in Hasami Shogi Game
     * Scans entire board for valid pieces belonging to a player & builds them into an ArrayList to visually display active pieces available to a Player
     * @param board Board Object of the Game Board in respective state
     * @return ArrayList of coordinates of all active player pieces
     */
    private ArrayList<String> findActivePlayerPieces(Board board){
        // GATHER ACTIVE PIECES FOR CURRENT PLAYER
        ArrayList<String> activePlayerPieces = new ArrayList<>();
        for(int i = 1; i <= 9; i ++){
            for(char c = 'a'; c <= 'i'; c++){
                Coordinate findCoord = new Coordinate(i, String.valueOf(c));
                if(board.getPiece(findCoord).equals(this.colour)){
                    activePlayerPieces.add(i + String.valueOf(c).toUpperCase());
                }
            }
        }

        return activePlayerPieces;
    }

    /**
     * Method to dynamically locate all valid, possible movement Coordinates for a Player in Hasami Shogi Game
     * Scans board to locate all possible destinations that a piece can move to Orthogonally 
     * @param board Board Object of the Game Board in respective state
     * @param starCoordinate Coordinate Object of current coordinate piece chosen by Player
     * @return ArrayList of coordinates of all valid player positions to be played by a Player
     */
    private ArrayList<String> findValidMoves(Board board, Coordinate starCoordinate){
        // GATHER VALID MOVES FOR CURRENT PLAYER BASED ON STARTING COORDINATE
        ArrayList<String> validMoves = new ArrayList<>();
        for(int i = 1; i <= 9; i ++){
            for(char c = 'a'; c <= 'i'; c++){
                Coordinate endCoordinate = new Coordinate(i, String.valueOf(c));
                if(board.canMove(starCoordinate, endCoordinate)){
                    validMoves.add(i + String.valueOf(c).toUpperCase());
                }
            }
        }

        return validMoves;
    }
    

    /**
     * Method to gather valid Coordinate Object input from user in Hasami Shogi Game
     * Handles invalid input until a user enters a valid Coordinate Object (in format of: #,C)
     * @param userInput Scanner Object to read Player text input
     * @param inputType Unique prompt message(s) to be displayed to user depending on game-state 
     * @return Coordinate Object of user input
     */
    private Coordinate getValidCoordinateInput(Scanner userInput, String inputType){
        while(true){
            System.out.println(inputType);
            String coordinateMoveInput = userInput.next().toLowerCase();

            if (coordinateMoveInput.equals("exit") || coordinateMoveInput.equals("quit") || coordinateMoveInput.equals("close")){
                System.out.println("\nGoodbye!");
                System.exit(0); // exit entirely
            }

            if (coordinateMoveInput.matches("[1-9][a-iA-I]")){ // enter coordinate in format of #,C
                return Coordinate.parseString(coordinateMoveInput);
            }

            System.out.println("Invalid input! Please enter a coordinate (e.g 5I, 3A, etc) or 'quit' to exit game. \n");
        }          
    }
    
    /**
     * Method to retrieve valid move from Player in Hasami Shogi Game
     * Orchestrates process of getting valid Coordinate movement from Player, handles player versus computer movement, and validates valid piece movement across Game Board
     * @param board Board Object of the Game Board in respective state
     * @return Coordinate Object of valid starting & ending coordinates chosen by Player
     */
    Coordinate[] getMove(Board board) {
        Scanner moveInputs = new Scanner(System.in);

        if(!isComputer){
            while(true) {
                ArrayList<String> activePlayerPieces = findActivePlayerPieces(board);

                // display arrayList of where player pieces are located
                System.out.println(this.name + "'s pieces are located at: " + activePlayerPieces);
                Coordinate startCoordinate = getValidCoordinateInput(moveInputs, "Select a starting coordinate to move (Enter Coordinate (e.g 5I, 3A, etc)): ");

                if(board.getPiece(startCoordinate).equals(this.colour)){
                    ArrayList<String> activeValidPlayerMoves = findValidMoves(board, startCoordinate);

                    // ensure active valid player moves is never 0 & player can actually move
                    if(!(activeValidPlayerMoves.size() == 0)){
                        System.out.println("You may move to these possible positions: " + activeValidPlayerMoves); // display valid positions for players to move at to help guide user
                        Coordinate endCoordinate = getValidCoordinateInput(moveInputs, "Select an end coordinate to move (Enter Coordinate (e.g 5I, 3A, etc)): ");

                        if(board.canMove(startCoordinate, endCoordinate)){
                            System.out.println();
                            return new Coordinate[] {startCoordinate, endCoordinate};
                        } else {
                            System.out.println("Invalid destination! That move is not allowed. Please try again \n"); // error #1 --> breaks orthogonal movement requirement
                        }
                    } else {
                        System.out.println("This piece piece is trapped and cannot be moved! Please try again. \n"); // error #2 --> attempting to move a trapped piece (over an enemy/itself)
                    }
                } else {
                    System.out.println("Invalid piece selection! You do not have a piece at that coordinate. Please try again.\n"); // error 3 --> invalid coordinate selection
                }
            } 
        } else {
            return computerMove(board);
        }
    }
}
