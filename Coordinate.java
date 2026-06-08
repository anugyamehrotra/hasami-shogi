public class Coordinate{

    // Coordinate Attributes
    private int row; // x-plane attribute (considering 2D visual surface)
    private String col; // y-plane attribute (considering 2D visual surface)


    /**
     * Constructor for individual Coordinate object in Hasami Shogi 
     * @param row Integer y-plane value of Coordinate (valid domain: 0-9)
     * @param col String x-plane value of Coordinate (valid domain: a-i)
     */
    Coordinate(int row, String col){
        this.row = row;
        this.col = col.toLowerCase();
    }

    /**
     * Method to return the String representation of a Coordinate object
     * @return formatted String representation a Coordinate in '(x,y)'
     */
    @Override
    public String toString(){
        return String.format("(%s,%d)", col, row);
    }

    /**
     * Method to return the y-plane attribute of a Coordinate object
     * @return Integer representation of attribute
     */
    int getRow(){
        return row;
    }

    /**
     * Method to return the x-plane attribute of a Coordinate object
     * @return String representation of attribute
     */
    String getCol(){
        return col;
    }

    /**
     * Method to overwrite y-plane attribute of a Coordinate object
     * @param setRow Integer row-value (x-plane) attribute to be set/overwritten to original Coordinate attributes
     */
    void setRow(int setRow){
        this.row = setRow;
    }

    /**
     * Method to overwrite x-plane attribute of a Coordinate object
     * @param setCol String col-value (y-plane) attribute to be set/overwritten to original Coordinate attributes
     */
    void setCol(String setCol){
        this.col = setCol;
    }


    /**
     * Method to parse user input into a Coordinate object
     * @param coord String coordinate value to be converted into a Coordinate object
     */
    static Coordinate parseString(String coord){
        return new Coordinate(Integer.valueOf(coord.substring(1, 2)), coord.substring(1));
    }
}
