import javax.swing.*;
import java.awt.*;

class GameButton extends JButton{
    private Coordinate coordinate;
    private static final ImageIcon blackHasamiShogiPiece = new ImageIcon("blackHasamiShogiPiece");
    private static final ImageIcon redHasamiShogiPiece = new ImageIcon("redHasamiShogiPiece");

    GameButton(Coordinate coordinate){
        this.coordinate = coordinate;
        this.setBackground(Color.yellow);
        this.setOpaque(true);
    }

    Coordinate getCoordinate(){
        return this.coordinate;
    }

    void changeColour(String piece){
        this.setIcon(null);
        
        if(piece.equals("b")){
            this.setIcon(resizImageIcon(blackHasamiShogiPiece));
        } else if (piece.equals("w")){
            this.setIcon(resizImageIcon(redHasamiShogiPiece));
        }
    }

    private ImageIcon resizImageIcon (ImageIcon pieceImages){
        Image img = pieceImages.getImage();
        Image resizedImage = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Sources:
    // https://docs.oracle.com/javase/8/docs/api/java/awt/Image.html
    // https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html

}
