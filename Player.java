import java.util.ArrayList;
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

    String getName() {
        return this.name;
    }

    int captureCount() {
        return this.captureCount;
    }

    Coordinate[] computerMove(Board board){
        ArrayList<Coordinate[]> possibleMoves = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                for (char c = 'A'; c <= 'I'; c++) {
                    Coordinate startCoord = new Coordinate(i, String.valueOf(c));

                    if (board.getPiece(startCoord).equals(this.colour)) {
                        for (int x = 1; x <= 9; x++) {
                            for (char y = 'A'; y <= 'I'; y++) {
                                Coordinate endCoord = new Coordinate(x, String.valueOf(y));

                                if (board.canMove(startCoord, endCoord)) {
                                    possibleMoves.add(new Coordinate[] { startCoord, endCoord });
                                }
                            }
                        }
                    }
                }
            }
            return possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    }

    Coordinate[] getMove(Board board) {
        Scanner input = new Scanner(System.in);
        String start = "";
        String end = "";

        if (!isComputer) {
            while (true) {
                System.out.println("Enter a start coordinate: ");
                Coordinate startCoord = Coordinate.parseString(input.next());
                System.out.println("Enter a start coordinate: ");
                Coordinate endCoord = Coordinate.parseString(input.next());

                if (!board.canMove(startCoord, endCoord)) {
                    System.out.println("Invalid move.");
                } else {
                    Coordinate[] pos = { startCoord, endCoord };
                    return pos;
                }
            }

        } else {
            return computerMove(board);
        }

    }
}
