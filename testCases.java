import java.util.Arrays;

public class testCases {
    static void clear(Board boardTest){
        for(int r=1; r<=9; r++){
            for(char c='a'; c<='i'; c++) boardTest.setPiece(new Coordinate(r, String.valueOf(c)), "x");
        }
}
    public static void main(String[] args) {
        
        // MANUAL BOARD ASSIGNMENT & TESTING
            // VERIFY CORRECT ROW & COLUMN DECLARATION
        String[][] boardTest = new String[9][9];

        for(int rowPop = 0; rowPop < 9; rowPop++){
            for(int colPop = 0; colPop < 9; colPop++){
                boardTest[rowPop][colPop] = "x";
            }
        }

        boardTest[0][0] = "w";
        boardTest[2][4] = "w";
        boardTest[3][0] = "b";
        boardTest[8][2] = "b";
        boardTest[2][5] = "w";

        System.out.println(Arrays.deepToString(boardTest));

        Board board = new Board();

        //getPiece
        clear(board);
        board.setPiece(new Coordinate(9, "a"), "w");
        board.setPiece(new Coordinate(1, "i"), "b");
        System.out.println(board.getPiece(new Coordinate(7, "a")));
        System.out.println(board.getPiece(new Coordinate(9, "a")));
        System.out.println(board.getPiece(new Coordinate(1, "i")));
        board.displayBoard();
        System.out.println();

        //canMoveHorizontally
        clear(board);
        board.setPiece(new Coordinate(7, "e"), "b");
        System.out.println(board.canMoveHorizontally(4, 0, 5));

        //canMoveVertically
        clear(board);
        board.setPiece(new Coordinate(9, "c"), "b");
        System.out.println(board.canMoveVertically(0, 0, 4));

        //canMove
        clear(board);
        Coordinate startMove = new Coordinate(9, "e");
        Coordinate endMove = new Coordinate(5, "e");
        board.setPiece(startMove, "b");
        System.out.println(board.canMove(startMove, endMove));

        //checkAndKill
        clear(board);
        board.setPiece(new Coordinate(9, "d"), "w");
        board.setPiece(new Coordinate(8, "d"), "b");
        Coordinate attackingMove = new Coordinate(7, "d"); 
        board.setPiece(attackingMove, "w");

        System.out.println("Board before checkAndKill test: \n");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.checkAndKill(attackingMove));
        
        System.out.println("Board after checkAndKill test: \n");
        board.displayBoard();
        System.out.println();


        //checkAndKill #2 -- longer kill chain test
        System.out.println("longer kill chain test");
        clear(board);
        board.setPiece(new Coordinate(5, "b"), "w");
        board.setPiece(new Coordinate(5, "c"), "b");
        board.setPiece(new Coordinate(5, "d"), "b");
        board.setPiece(new Coordinate(5, "e"), "b");
        board.setPiece(new Coordinate(5, "f"), "b");
        Coordinate attackingMove2 = new Coordinate(5, "g");
        board.setPiece(attackingMove2, "w");

        System.out.println("Board before checkAndKill test: \n");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.checkAndKill(attackingMove2));
        
        System.out.println("Board after checkAndKill test: \n");
        board.displayBoard();
        System.out.println();


        //checkAndKill #3 -- wall captures
        System.out.println("wall capture:");
        clear(board);
        board.setPiece(new Coordinate(9, "i"), "w");
        board.setPiece(new Coordinate(9, "h"), "b");
        Coordinate attackingMoveWall = new Coordinate(9, "g");
        board.setPiece(attackingMoveWall, "w");

        System.out.println("Board before Wall Capture test:");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.checkAndKill(attackingMoveWall));
        
        System.out.println("Board after Wall Capture test:");
        board.displayBoard();
        System.out.println();


        //checkAndKill #4 -- double sandwich test
        System.out.println("double sandwich:");
        clear(board);
        board.setPiece(new Coordinate(5, "c"), "w");
        board.setPiece(new Coordinate(5, "d"), "b");
        board.setPiece(new Coordinate(5, "f"), "b");
        board.setPiece(new Coordinate(5, "g"), "w");
        Coordinate attackingMoveDouble = new Coordinate(5, "e");
        board.setPiece(attackingMoveDouble, "w");

        System.out.println("Board before Double-Sided Sandwich test:");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.checkAndKill(attackingMoveDouble));
        
        System.out.println("Board after Double-Sided Sandwich test:");
        board.displayBoard();
        System.out.println();

        //checkAndKill #5 -- invalid kill attempt
        System.out.println("invalid friendly fire test:");
        clear(board);
        board.setPiece(new Coordinate(5, "c"), "w");
        board.setPiece(new Coordinate(5, "d"), "w");
        Coordinate attackingMoveFriendly = new Coordinate(5, "e");
        board.setPiece(attackingMoveFriendly, "w");

        System.out.println("Board before invalid sandwich test:");
        board.displayBoard();
        System.out.println("Pieces Captured: " + board.checkAndKill(attackingMoveFriendly));
        System.out.println("Board after invalid sandwich test:");
        board.displayBoard();
        System.out.println();

        //cornerKill
        clear(board);
        board.setPiece(new Coordinate(9, "a"), "b");
        board.setPiece(new Coordinate(9, "b"), "w");
        attackingMove = new Coordinate(8, "a"); 
        board.setPiece(attackingMove, "w");
        
        System.out.println("Board before cornerKill test:");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.cornerKill(attackingMove));
        
        System.out.println("Board after cornerKill test:");
        board.displayBoard();
        System.out.println();

        //cornerKill #2
        System.out.println("longer chain test for corner kill");
        clear(board);
        
        board.setPiece(new Coordinate(9, "a"), "b");
        board.setPiece(new Coordinate(8, "a"), "b");
        board.setPiece(new Coordinate(7, "a"), "b");
        board.setPiece(new Coordinate(6, "a"), "b");
        board.setPiece(new Coordinate(5, "a"), "b");
        
        board.setPiece(new Coordinate(4, "a"), "w");

        Coordinate attackingChainMove = new Coordinate(9, "b"); 
        board.setPiece(attackingChainMove, "w");
        
        System.out.println("Board BEFORE 5-piece cornerKill test:");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.cornerKill(attackingChainMove));
        
        System.out.println("Board AFTER 5-piece cornerKill test:");
        board.displayBoard();
        System.out.println();

        // corner kill #3 -- longer corner L shape
        System.out.println("corner kill L-shape");
        clear(board);
        
        board.setPiece(new Coordinate(9, "a"), "b");
        board.setPiece(new Coordinate(8, "a"), "b");
        board.setPiece(new Coordinate(7, "a"), "b");
        board.setPiece(new Coordinate(9, "b"), "b");
        board.setPiece(new Coordinate(9, "c"), "b");
        
        board.setPiece(new Coordinate(6, "a"), "w");
        Coordinate attackingLMove = new Coordinate(9, "d"); 
        board.setPiece(attackingLMove, "w");

        System.out.println("Board BEFORE L-shape cornerKill test:");
        board.displayBoard();
        
        System.out.println("Pieces Captured: " + board.cornerKill(attackingLMove));
        
        System.out.println("Board AFTER L-shape cornerKill test:");
        board.displayBoard();
        System.out.println();

        //coordinate methods
        Coordinate test1 = new Coordinate(5, "d");
        System.out.println(test1.toString()); 
        System.out.println(test1.getCol());
        System.out.println(test1.getRow());
        System.out.println(Coordinate.parseString("3f"));

        //player methods
        Player test2 = new Player("Test", "w", false);
        System.out.println(test2.getName());
        System.out.println(test2.getColour());
        System.out.println(test2.captureCount());

        System.out.println(" ");
        Player playerOne = new Player("Computer", "w", true);
        
        System.out.println("player name" + playerOne.getName());
        System.out.println("player color: " + playerOne.getColour());
        
        System.out.println("initial captures: " + playerOne.captureCount());
        playerOne.addCaptures(2);
        System.out.println("captures + 2: " + playerOne.captureCount());
        playerOne.addCaptures(3);
        System.out.println("captures + 3: " + playerOne.captureCount());
        
        clear(board);
        board.setPiece(new Coordinate(1, "a"), "w");
        board.setPiece(new Coordinate(1, "b"), "w");
        board.setPiece(new Coordinate(2, "a"), "w");
        
        System.out.println("computer move testing (always random):");
        Coordinate[] move = playerOne.computerMove(board);
        System.out.println("original coordinate: " + move[0].toString() + " final coordinate: " + move[1].toString());
        System.out.println();

        //game methods
        Player test3 = new Player("P2", "b", false);
        Game game = new Game(test2, test3);
        System.out.println(game.gameOver());
    }
}