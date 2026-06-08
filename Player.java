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

    int captureCount() {
        return this.captureCount;
    }

    Coordinate[] getMove(Board board){
        if (!isComputer){
            System.out.println("Enter a start coordinate: ")
            
        }
    }

}
