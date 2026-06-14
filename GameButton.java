import javax.swing.*;
import java.awt.*;

class GameButton extends JButton{

    // GameButton Attributes
    private Coordinate coordinate; // Coordinate attribute of Hasami Shogi GUI Game Buttons
    private static final ImageIcon blackHasamiShogiPiece = new ImageIcon("assets/redHasamiShogiPiece.png"); // Image attribute of Hasami Shogi 'Black' Game Pieces
    private static final ImageIcon redHasamiShogiPiece = new ImageIcon("assets/blackHasamiShogiPiece.png"); // Image attribute of Hasami Shogi 'Red' Game Pieces

    /**
     * Constructor for individual GameButton object in Hasami Shogi GUI
     * @param coordinate Coordinate object for game button 
     */
    GameButton(Coordinate coordinate){
        this.coordinate = coordinate;
        this.setBackground(Color.yellow);
        this.setOpaque(true);
    }

    /**
     * Method to return the Coordinate representation of a position on the Hasami Shogi Board
     * @return Coordinate representation of a position/instance on the board
     */
    Coordinate getCoordinate(){
        return this.coordinate;
    }

    /**
     * Method to dynamically convert player piece attributes to image representation
     * Converts 'Black' & 'White' attributes to image representations
     * @param gamePiece String representation of game pieces ("b" representing 'Black', "w" representing 'White' & "x"/blank spaces ignored for grid piece representation)
     */
    void changePlayerPiece(String gamePiece){
        this.setIcon(null);
        if(gamePiece.equals("b")){
            this.setIcon(resizeImageIcon(blackHasamiShogiPiece));
        } else if (gamePiece.equals("w")){
            this.setIcon(resizeImageIcon(redHasamiShogiPiece));
        }
    }

    /**
     * Method to dynamically re-scale raw image icons to fit in Hasami Shogi grid pieces
     * @param pieceImages Image Object of the original, raw icons to be resized
     * @return a re-scaled Image Icon Object to fit into the Game Board
     */
    private ImageIcon resizeImageIcon (ImageIcon pieceImages){
        Image img = pieceImages.getImage();
        Image resizedImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Sources:
    // https://docs.oracle.com/javase/8/docs/api/java/awt/Image.html --> Image Handling & Processing
    // https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html --> Image Icon Implementation
    // https://stackoverflow.com/questions/7252983/resizing-image-java-getscaledinstance --> Scaling Images Via Function
}
