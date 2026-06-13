public class Coordinate{

    // Coordinate Attributes
    private int col; // x-plane attribute (considering 2D visual surface)
    private String row; // y-plane attribute (considering 2D visual surface)


    /**
     * Constructor for individual Coordinate object in Hasami Shogi 
     * @param col String x-plane value of Coordinate (valid domain: 9-1)
     * @param row Integer y-plane value of Coordinate (valid domain: 'a' - 'i')
     
     */
    Coordinate(int col, String row){
        this.col = col;
        this.row = row.toLowerCase();
    }

    
    /**
     * Method to return the String representation of a Coordinate object
     * @return formatted String representation a Coordinate in '(x,y)'
     */
    @Override
    public String toString(){
        return String.format("(%d,%s)", col, row);
    }

    /**
     * Method to return the x-plane attribute of a Coordinate object
     * @return Integer representation of attribute
     */
    int getCol(){
        return col;
    }


    /**
     * Method to return the y-plane attribute of a Coordinate object
     * @return String representation of attribute
     */
    String getRow(){
        return row;
    }

    /**
     * Method to overwrite x-plane attribute of a Coordinate object
     * @param setCol Integer col-value (y-plane) attribute to be set/overwritten to original Coordinate attributes
     */
    void setCol(int setCol){
        this.col = setCol;
    }
    
    /**
     * Method to overwrite y-plane attribute of a Coordinate object
     * @param setRow String row-value (x-plane) attribute to be set/overwritten to original Coordinate attributes
     */
    void setRow(String setRow){
        this.row = setRow;
    }

    /**
     * Method to parse user input into a Coordinate object
     * @param coord String coordinate value to be converted into a Coordinate object
     */
    static Coordinate parseString(String coord){
        return new Coordinate(Integer.valueOf(coord.substring(0, 1)), coord.substring(1));
    }
}