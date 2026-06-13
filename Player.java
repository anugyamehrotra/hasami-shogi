import java.util.ArrayList;
import java.util.Scanner;

class Player {
    private String name;
    private String colour;
    private int captureCount;
    private boolean isComputer;

    Player(String name, String colour, boolean isComputer) {
        this.name = name;
        this.colour = colour;
        this.isComputer = isComputer;
        this.captureCount = 0;
    }

    void addCaptures(int caps) {
        this.captureCount += caps;
    }

    String getName() {
        return this.name;
    }

    String getColour(){
        return this.colour;
    }

    int captureCount() {
        return this.captureCount;
    }

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
    

    private Coordinate getValidCoordinateInput(Scanner userInput, String inputType){
        while(true){
            System.out.println(inputType);
            String coordinateMoveInput = userInput.next().toLowerCase();

            if (coordinateMoveInput.equals("exit") || coordinateMoveInput.equals("quit") || coordinateMoveInput.equals("close")){
                System.out.println("\nGoodbye!");
                System.exit(0);
            }

            if (coordinateMoveInput.matches("[1-9][a-iA-I]")){
            return Coordinate.parseString(coordinateMoveInput);
            }

            System.out.println("Invalid input! Please enter a coordinate (e.g 5I, 3A, etc) or 'quit' to exit game. \n");
        }          
    }
    
    Coordinate[] getMove(Board board) {
        Scanner moveInputs = new Scanner(System.in);

        if(!isComputer){
            while(true) {
                ArrayList<String> activePlayerPieces = findActivePlayerPieces(board);

                System.out.println(this.name + "'s pieces are located at: " + activePlayerPieces);
                Coordinate startCoordinate = getValidCoordinateInput(moveInputs, "Select a starting coordinate to move (Enter Coordinate (e.g 5I, 3A, etc)): ");

                if(board.getPiece(startCoordinate).equals(this.colour)){
                    ArrayList<String> activeValidPlayerMoves = findValidMoves(board, startCoordinate);

                    if(!(activeValidPlayerMoves.size() == 0)){
                        System.out.println("You may move to these possible positions: " + activeValidPlayerMoves);
                        Coordinate endCoordinate = getValidCoordinateInput(moveInputs, "Select an end coordinate to move (Enter Coordinate (e.g 5I, 3A, etc)): ");

                        if(board.canMove(startCoordinate, endCoordinate)){
                            System.out.println();
                            return new Coordinate[] {startCoordinate, endCoordinate};
                        } else {
                            System.out.println("Invalid destination! That move is not allowed. Please try again \n");
                        }
                    } else {
                        System.out.println("This piece piece is trapped and cannot be moved! Please try again. \n");
                    }
                } else {
                    System.out.println("Invalid piece selection! You do not have a piece at that coordinate. Please try again.\n");
                }
            } 
        } else {
            return computerMove(board);
        }
    }
}
