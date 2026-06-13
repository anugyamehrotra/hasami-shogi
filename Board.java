public class Board {
    
    // Board Attributes 
    private String[][] gameBoard;

    /**
     * Constructor for Board Object in Hasami Shogi
     */
    public Board(){
        this.gameBoard = new String[9][9];
        initialize();
    }

    /**
     * Method to dynamically initialize Board object in-game
     * Iterate into row & column populations & assign white ('w') or black ('b') pieces to require row positions
     */
    public void initialize(){
        // Populate gameBoard
        for(int rowPop = 0; rowPop < 9; rowPop++){
            for(int colPop = 0; colPop < 9; colPop++){
                if(rowPop == 0){
                    gameBoard[rowPop][colPop] = "w"; // White ☖ piece on row 'a'
                } else if (rowPop == 8){
                    gameBoard[rowPop][colPop] = "b"; // Black ☗ piece on row 'i'
                } else {
                    gameBoard[rowPop][colPop] = "x"; // Blank piece (across all rows except 'a' & 'i')
                }
            }
        }
    }

    /**
     * Method to return the inner-column index of a Coordinate on a Board
     * @param coordinate a coordinate on the Hasami Shogi board
     * @return an Integer index value for the specific column of the coordinate (based on column domain of 9-1; where nine (9) = 0 (index) & one (1) = 8 (index))
     */
    private int parseColumnIndex(Coordinate coordinate){
        // Gather column values & assign valid int value (according to domain of: 9-1 for nine rows)
        return 9 - coordinate.getCol();
    }
 
    /**
     * Method to return the inner-row index of a Coordinate on a Board
     * @param coordinate a coordinate on the Hasami Shogi board
     * @return an Integer index value for the specific row of the coordinate (based on row-domain of a-i; where 'a' = 0 (index) & 'i' = 8 (index))
     */
    private int parseRowIndex(Coordinate coordinate){
        return coordinate.getRow().charAt(0) - 'a';

    }
    
    /** 
     * Method to get the String value of a Coordinate
     * @param coordinate a Coordinate object on the Hasami Shogi board
     * @return a String datatype of the Coordinate (in either 'w' or 'b' depending on type)
     */
    public String getPiece(Coordinate coordinate){
        int row = parseRowIndex(coordinate);
        int col = parseColumnIndex(coordinate);
        return gameBoard[row][col];
    }

    
    /**
     * Method to return boolean validity of valid piece movement on a Hasami Shogi board
     * @param currentCoordinate a Coordinate object of the current player's piece
     * @param desiredLocation a Coordinate object of the current player's desired location (to place the piece)
     * @return boolean validity if moveable
     */
    public boolean canMove(Coordinate currentCoordinate, Coordinate desiredLocation){
        // A piece, in Hasami Shogi, is only limited to straight directional paths (up, down, left, right)

        // Parse values for current rows and columns & desired rows and columns
        int currentRow = parseRowIndex(currentCoordinate);
        int currentCol = parseColumnIndex(currentCoordinate);
        int desiredRow = parseRowIndex(desiredLocation);
        int desiredCol = parseColumnIndex(desiredLocation);

        if(gameBoard[currentRow][currentCol].equals("x")){
            return false;
        }

        if(!(gameBoard[desiredRow][desiredCol].equals("x"))){
            return false;
        }

        // CONSIDER TO BE REPLACED (IF WE WANT TO LET PLAYERS PLAY AT THE SAME PIECE THEY STARTED AT)
        if(currentRow == desiredRow && currentCol == desiredCol){
            return false;
        }

        if(currentRow != desiredRow && currentCol != desiredCol){
            return false;
        }

        if(currentRow == desiredRow){
            return canMoveHorizontally(currentRow, currentCol, desiredCol);
        }

        return canMoveVertically(currentRow, currentCol, desiredRow);
    }

    /**
     * Method to return boolean validity of valid, horizontal piece movement on Hasami Shogi board
     * @param currentRow index of current coordinate row
     * @param currentCol index of current coordinate column
     * @param desiredCol index of desired coordinate column
     * @return boolean validity if piece is moveable horizontally
     */
    public boolean canMoveHorizontally(int currentRow, int currentCol, int desiredCol){
        // Find minimum & maximum horizontal movements
        int startPos = Math.min(currentCol, desiredCol);
        int endPos = Math.max(currentCol, desiredCol);

        for(int i = startPos + 1; i < endPos; i++){
            if(!(gameBoard[currentRow][i].equals("x"))){
                return false; // located obstacle on board
            }
        }

        return true;
    }

    /**
     * Method to return boolean validity of valid, vertical piece movement on Hasami Shogi board
     * @param currentRow index of current coordinate row
     * @param currentCol index of current coordinate column
     * @param desiredRow index of desired coordinate column
     * @return boolean validity if piece if movable vertically
     */
    public boolean canMoveVertically(int currentRow, int currentCol, int desiredRow){
        // Find minimum & maximum vertical movements
        int startPos = Math.min(currentRow, desiredRow);
        int endPos = Math.max(currentRow, desiredRow);

        for(int i = startPos + 1; i < endPos; i++){
            if(!(gameBoard[i][currentCol].equals("x"))){
                return false; // located obstacle on board
            }
        }

        return true;
    }

    /**
     * Method to move piece to a valid position & direction on a board 
     * @param currentCoordinate a Coordinate object of the current player's location
     * @param desiredLocation a Coordinate object of the current player's desired location
     */
    public void movePiece(Coordinate currentCoordinate, Coordinate desiredLocation){
        if(canMove(currentCoordinate, desiredLocation)){

            // Parse values for current rows and columns & desired rows and columns
            int currentRow = parseRowIndex(currentCoordinate);
            int currentCol = parseColumnIndex(currentCoordinate);
            int desiredRow = parseRowIndex(desiredLocation);
            int desiredCol = parseColumnIndex(desiredLocation);

            // Shuffle pieces (fixed) to valid positions
            String currentPiece = gameBoard[currentRow][currentCol];
            gameBoard[currentRow][currentCol] = "x";
            gameBoard[desiredRow][desiredCol] = currentPiece;
            
        }
    }

    /** 
     * Method check and kill pieces (across all directions)
     * Dynamically look through board from desired location (and overwrite player pieces based on player type) from four (4) directions & remove targets accordingly
     * @param desiredCoordinate a Coordinate object of the current player's desired location
     */
    public int checkAndKill(Coordinate desiredCoordinate){

        // declare current row & column data of desired coordinate
        int rowTarget = parseRowIndex(desiredCoordinate);
        int colTarget = parseColumnIndex(desiredCoordinate);
        String playerPiece = gameBoard[rowTarget][colTarget];

        if(playerPiece.equals("x")){
            return 0 ;
        }

        // handle overwriting of playerPieces based on player turns
        String targetPiece = "w";
        if(playerPiece.equals("w")){
            targetPiece = "b";
        }

        int total = 0;
        total += checkUpward(rowTarget, colTarget, playerPiece, targetPiece);
        total += checkDownward(rowTarget, colTarget, playerPiece, targetPiece);
        total += checkRightside(rowTarget, colTarget, playerPiece, targetPiece);
        total += checkLeftside(rowTarget, colTarget, playerPiece, targetPiece);

        return total;
    }

    /** 
     * Method to dynamically search board upward from desired coordinate for "sandwiched" opponent pieces 
     * @param rowTarget index of target row
     * @param colTarget index of target column
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int checkUpward(int rowTarget, int colTarget, String playerPiece, String targetPiece){
        int currentRow = rowTarget - 1;
        int currentCol = colTarget;

        int numCapturable = 0;

        // loop through current row (upward) to locate enemy pieces in vertical arrangement
        while(currentRow >= 0 && gameBoard[currentRow][currentCol].equals(targetPiece)){
            numCapturable++;
            currentRow--;
        }

        // vertical (upward) check-and-kill validation process
            // validate, from current position, if another piece (of same type as current player) is directly across a vertical check & is 'sandwiched'
            // replace all instances of 'sandwiched' enemy pieces with 'x' (blank space representation)
        if(currentRow >= 0 && gameBoard[currentRow][currentCol].equals(playerPiece) && numCapturable > 0){
            int removeRow = rowTarget - 1;
            while(removeRow > currentRow){
                gameBoard[removeRow][colTarget] = "x";
                removeRow--;
            }
            return numCapturable;
        }
        return 0;
    }

    /** 
     * Method to dynamically search board downward from desired coordinate for "sandwiched" opponent pieces 
     * @param rowTarget index of target row
     * @param colTarget index of target column
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int checkDownward(int rowTarget, int colTarget, String playerPiece, String targetPiece){
        int currentRow = rowTarget + 1; 
        int currentCol = colTarget; 

        int numCapturable = 0;

        // loop through current row (downward) to locate enemy pieces in vertical arrangement
        while(currentRow < 9 && gameBoard[currentRow][currentCol].equals(targetPiece)){
            numCapturable++;
            currentRow++;
        }

        // vertical (downward) check-and-kill validation process
            // validate, from current position, if another piece (of same type as current player) is directly across a vertical check & is 'sandwiched'
            // replace all instances of 'sandwiched' enemy pieces with 'x' (blank space representation)
        if(currentRow < 9 && gameBoard[currentRow][currentCol].equals(playerPiece) && numCapturable > 0){
            int removeRow = rowTarget + 1;
            while(removeRow < currentRow){
                gameBoard[removeRow][colTarget] = "x";
                removeRow++;
            }
            return numCapturable;
        }
        return 0;
    }

    /** 
     * Method to dynamically search board leftside from desired coordinate for "sandwiched" opponent pieces 
     * @param rowTarget index of target row
     * @param colTarget index of target column
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int checkLeftside(int rowTarget, int colTarget, String playerPiece, String targetPiece){
        int currentRow = rowTarget;
        int currentCol = colTarget -1;

        int numCapturable = 0;

        // loop through current column (leftside) to locate enemy pieces in horizontal arrangement
        while(currentCol >= 0 && gameBoard[currentRow][currentCol].equals(targetPiece)){
            numCapturable++;
            currentCol--;
        }
        
        // horitzontal (leftside) check-and-kill validation process
            // validate, from current position, if another piece (of same type as current player) is directly across horitzontally (left-side) & is 'sandwiched'
            // replace all instances of 'sandwiched' enemy pieces with 'x' (blank space representation)
        if(currentCol >= 0 && gameBoard[currentRow][currentCol].equals(playerPiece) && numCapturable > 0){
            int removeCol = colTarget - 1;
            while(removeCol > currentCol){
                gameBoard[rowTarget][removeCol] = "x";
                removeCol--;
            }
            return numCapturable;
        }
        return 0;
    }

    /** 
     * Method to dynamically search board rightside from desired coordinate for "sandwiched" opponent pieces 
     * @param rowTarget index of target row
     * @param colTarget index of target column
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int checkRightside(int rowTarget, int colTarget, String playerPiece, String targetPiece){
        int currentRow = rowTarget;
        int currentCol = colTarget + 1;

        int numCapturable = 0;

        // loop through current row (rightside) to locate enemy pieces in horizontal arrangement
        while(currentCol < 9 && gameBoard[currentRow][currentCol].equals(targetPiece)){
            numCapturable++;
            currentCol++;
        }

        // horitzontal (rightside) check-and-kill validation process
            // validate, from current position, if another piece (of same type as current player) is directly across horitzontally (right-side) & is 'sandwiched'
            // replace all instances of 'sandwiched' enemy pieces with 'x' (blank space representation)
        if(currentCol < 9 && gameBoard[currentRow][currentCol].equals(playerPiece) && numCapturable > 0){
            int removeCol = colTarget + 1;
            while(removeCol < currentCol){
                gameBoard[rowTarget][removeCol] = "x";
                removeCol++;
            }
            return numCapturable;
        }
        return 0;
    }

    /**
     * Method to dynamically kill & count enemy pieces arranged orthogonally
     * Splits checks into respective corners and returns respective player-kill instances
     * @param desiredCoordinate a Coordinate object of the desired location to validate an 'orthogonal'/corner-kill
     * @return integer index of number of eliminated enemy pieces
     */
    public int cornerKill(Coordinate desiredCoordinate){
        // declare current row & column data of desired coordinate
        int currentRow = parseRowIndex(desiredCoordinate);
        int currentCol = parseColumnIndex(desiredCoordinate);
        String playerPiece = gameBoard[currentRow][currentCol];

        if(playerPiece.equals("x")){
            return 0;
        }

        // handle overwriting of playerPieces based on player turns
        String targetPiece = "w";
        if(playerPiece.equals("w")){
            targetPiece = "b";
        }

        int total = 0;
        total += topLeftCornerKill(currentRow, currentCol, playerPiece, targetPiece);
        total += topRightCornerKill(currentRow, currentCol, playerPiece, targetPiece);
        total += bottomLeftCornerKill(currentRow, currentCol, playerPiece, targetPiece);
        total += bottomRightCornerKill(currentRow, currentCol, playerPiece, targetPiece);

        return total;
    }

    /**
     * Method to dynamically search instances of enemy pieces from the top-left corner 
     * Search and log instances of enemy targets (of player) from the top-left corner ([0][0]), validate valid corner-kill condition, and replace all enemy instances with a "killed" piece
     * @param currentRow row index of a Coordinate Object
     * @param currentCol column index of a Coordinate Object
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int topLeftCornerKill(int currentRow, int currentCol, String playerPiece, String targetPiece){
       // declare corner-position attributes (for top-left corner) & capturable enemies
        int numCapturable = 0;

        if(gameBoard[0][0].equals((targetPiece))){
            int rowTarget = 0;
            int colTarget = 0;

            // check how many row & col targets vertically and horizontally
            while(rowTarget < 9 && gameBoard[rowTarget][0].equals(targetPiece)){
                rowTarget++;
            }

            while(colTarget < 9 && gameBoard[0][colTarget].equals(targetPiece)){
                colTarget++;
            }

            // corner kill (top-left: ([0][0])) validation process
                // ensure row & column targets (occupying instances of enemy players) are in-range of board (from range: 0 to 8) & sandwich condition is true (if final position on row & column targets are player pieces and differentiate from enemy pieces; in orthogonal shape")
            if(rowTarget < 9 && colTarget < 9 && gameBoard[rowTarget][0].equals(playerPiece) && gameBoard[0][colTarget].equals(playerPiece)){
               // confirm validity of horizontal and vertical indexes referring to values of the desired coordinate (current row & column)
                if((currentRow == rowTarget && currentCol == 0) || (currentRow == 0 && currentCol == colTarget)){
                    for(int i = 0; i < rowTarget; i++){
                        if(!gameBoard[i][0].equals("x")){
                            gameBoard[i][0] = "x";
                            numCapturable++;
                        }
                    }

                    for(int c = 0; c < colTarget; c++){
                        if(!gameBoard[0][c].equals("x")){
                            gameBoard[0][c] = "x";
                            numCapturable++;
                        }
                    }
                }
            }
        }
        return numCapturable;
    }


    /**
     * Method to dynamically search instances of enemy pieces from the top-right corner 
     * Search and log instances of enemy targets (of player) from the top-right corner ([0][8]), validate valid corner-kill condition, and replace all enemy instances with a "killed" piece
     * @param currentRow row index of a Coordinate Object
     * @param currentCol column index of a Coordinate Object
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int topRightCornerKill(int currentRow, int currentCol, String playerPiece, String targetPiece){
        // declare corner-position attributes (for top-right corner) & capturable enemies
        int numCapturable = 0;

        if(gameBoard[0][8].equals(targetPiece)){
            int rowTarget = 0;
            int colTarget = 8;

            while(rowTarget < 9 && gameBoard[rowTarget][8].equals(targetPiece)){
                rowTarget++;
            }

            while(colTarget >= 0 && gameBoard[0][colTarget].equals(targetPiece)){
                colTarget--;
            }

            // corner kill (top-right: ([0][8])) validation process
                // ensure row & column targets (occupying instances of enemy players) are in-range of board (from range: 0 to 8) & sandwich condition is true (if final position on row & column targets are player pieces and differentiate from enemy pieces; in orthogonal shape")
            if(rowTarget < 9 && colTarget >= 0 && gameBoard[rowTarget][8].equals(playerPiece) && gameBoard[0][colTarget].equals(playerPiece)){
                // confirm validity of horizontal and vertical indexes referring to values of the desired coordinate (current row & column)
                if((currentRow == rowTarget && currentCol == 8) || (currentRow == 0 && currentCol == colTarget)){
                    for(int i = 0; i < rowTarget; i++){
                        if(!gameBoard[i][8].equals("x")){
                            gameBoard[i][8] = "x";
                            numCapturable++;
                        }
                    }

                    for(int c = colTarget + 1; c <= 8; c++){
                        if(!gameBoard[0][c].equals("x")){
                            gameBoard[0][c] = "x";
                            numCapturable++;
                        }
                    }
                }
            }
        }
        return numCapturable;
    }

    /**
     * Method to dynamically search instances of enemy pieces from the bottom-left corner 
     * Search and log instances of enemy targets (of player) from the top-right corner ([8][0]), validate valid corner-kill condition, and replace all enemy instances with a "killed" piece
     * @param currentRow row index of a Coordinate Object
     * @param currentCol column index of a Coordinate Object
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int bottomLeftCornerKill(int currentRow, int currentCol, String playerPiece, String targetPiece){
        // declare corner-position (for bottom-left corner) attributes & capturable enemies
        int numCapturable = 0;

        if(gameBoard[8][0].equals(targetPiece)){
            int rowTarget = 8;
            int colTarget = 0;

            while(rowTarget >= 0 && gameBoard[rowTarget][0].equals(targetPiece)){
                rowTarget--;
            }

            while(colTarget < 9 && gameBoard[8][colTarget].equals(targetPiece)){
                colTarget++;
            }

            // corner kill (bottom-left: ([8][0])) validation process
                // ensure row & column targets (occupying instances of enemy players) are in-range of board (from range: 0 to 8) & sandwich condition is true (if final position on row & column targets are player pieces and differentiate from enemy pieces; in orthogonal shape")
            if(rowTarget >= 0 && colTarget < 9 && gameBoard[rowTarget][0].equals(playerPiece) && gameBoard[8][colTarget].equals(playerPiece)){
                // confirm validity of horizontal and vertical indexes referring to values of the desired coordinate (current row & column)
                if((currentRow == rowTarget && currentCol == 0) || (currentRow == 8 && currentCol == colTarget)){
                    for(int i = rowTarget + 1; i <= 8; i++){
                        if(!gameBoard[i][0].equals("x")){
                            gameBoard[i][0] = "x";
                            numCapturable++;
                        }
                    }

                    for(int c = 0; c < colTarget; c++){
                        if(!gameBoard[8][c].equals("x")){
                            gameBoard[8][c] = "x";
                            numCapturable++;
                        }
                    }
                }
            }
        }
        return numCapturable;
    }
  
    /**
     * Method to dynamically search instances of enemy pieces from the bottom-right corner 
     * Search and log instances of enemy targets (of player) from the bottom-right corner ([8][8]), validate valid corner-kill condition, and replace all enemy instances with a "killed" piece
     * @param currentRow row index of a Coordinate Object
     * @param currentCol column index of a Coordinate Object
     * @param playerPiece String value of current player piece
     * @param targetPiece String value of target piece(s)
     */
    private int bottomRightCornerKill(int currentRow, int currentCol, String playerPiece, String targetPiece){
        // declare corner-position attributes (for bottom-right corner) & capturable enemies
        int numCapturable = 0;

        if(gameBoard[8][8].equals(targetPiece)){
            int rowTarget = 8;
            int colTarget = 8;

            while(rowTarget >= 0 && gameBoard[rowTarget][8].equals(targetPiece)){
                rowTarget--;
            }

            while(colTarget >= 0 && gameBoard[8][colTarget].equals(targetPiece)){
                colTarget--;
            }
            
            // corner kill (bottom-right: ([8][8])) validation process
                // ensure row & column targets (occupying instances of enemy players) are in-range of board (from range: 0 to 8) & sandwich condition is true (if final position on row & column targets are player pieces and differentiate from enemy pieces; in orthogonal shape")
            if(rowTarget >= 0 && colTarget >= 0 && gameBoard[rowTarget][8].equals(playerPiece) && gameBoard[8][colTarget].equals(playerPiece)){
                // confirm validity of horizontal and vertical indexes referring to values of the desired coordinate (current row & column)
                if((currentRow == rowTarget && currentCol == 8) || (currentRow == 8 && currentCol == colTarget)){
                    for(int i = rowTarget + 1; i <= 8; i++){
                        if(!gameBoard[i][8].equals("x")){
                            gameBoard[i][8] = "x";
                            numCapturable++;
                        }
                    }

                    for(int c = colTarget + 1; c <= 8; c++){
                        if(!gameBoard[8][c].equals("x")){
                            gameBoard[8][c] = "x";
                            numCapturable++;
                        }
                    }
                }
            }
        }
        return numCapturable;
    } 

    /**
     * Method to dynamically display a String-visual representation of a Hasami Shogi Board
     * Dynamically builds a String representation of the board by assigning current player qualities & pieces to ASCII representation
     */
    public void displayBoard(){
        int rowNum = 9;
        char colChar = 'a';

        for(int col = 0; col < 9; col++){
            System.out.print(rowNum + " ");
            rowNum--;
        }
        System.out.println();

        for(int col = 0; col < 9; col++){
            System.out.print("― ");
        }
        System.out.println();

        for(int row = 0; row < 9; row++){
            for(int c = 0; c < 9; c++){
                if(gameBoard[row][c].equals("w")){
                    System.out.print("☖ ");
                } else if (gameBoard[row][c].equals("b")){
                    System.out.print("☗ ");
                } else if (gameBoard[row][c].equals("x")){
                    System.out.print(". "); 
                }
            }

            System.out.println(" | " + colChar );
            colChar++;
        }
        System.out.println();
    }

    public static void main(String[] args){
        Board testBoard = new Board();
        testBoard.displayBoard();
    }
}
