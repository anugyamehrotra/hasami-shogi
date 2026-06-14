import java.util.Arrays;

public class testCases {
    public static void main(String[] args) {
         // FORMAT OF TEST BOARD
            // REF TO BOARD

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

        Board board = new Board()

        //getPiece
        System.out.println(board.getPiece(new Coordinate(3, "a")));
        System.out.println(board.getPiece(new Coordinate(9, "a")));
        System.out.println(board.getPiece(new Coordinate(1, "i")));
        
        //canMove
        System.out.println(board.canMove(new Coordinate(9, "a"), new Coordinate(9, "c"))); 
        System.out.println(board.canMove(new Coordinate(9, "a"), new Coordinate(5, "a"))); 
        System.out.println(board.canMove(new Coordinate(9, "a"), new Coordinate(1, "i"))); 

        //canMoveHorizontally
        System.out.println(board.canMoveHorizontally(0, 0, 5)); 

        //canMoveVertically
        System.out.println(board.canMoveVertically(0, 0, 5)); 

        //checkAndKill
        System.out.println(board.checkAndKill(new Coordinate(9, "a"))); 

        //cornerKill
        System.out.println(board.cornerKill(new Coordinate(9, "a")));

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

        //game methods
        Player test3 = new Player("P2", "b", false);
        Game game = new Game(test2, test3);
        System.out.println(game.gameOver());

        
        


    }

    // FOR ADDTL. TESTS, USE ABOVE FORMAT
}
