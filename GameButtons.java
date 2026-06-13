import javax.swing.JButton; 

class GameButton extends JButton{
    private Coordinate coordinate;

    GameButton(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    Coordinate getCoordinate(){
        return this.coordinate;
    }

    void changeColor(String piece){
        if (piece.equals("b")){
            setText("●");
            setForeGround(Color.BLACK);
        }else if (piece.equals("w")){
            setText("o");
            setForeground(Color.WHITE);
        }
    }
}