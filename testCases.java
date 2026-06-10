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

    }

    // FOR ADDTL. TESTS, USE ABOVE FORMAT
}
