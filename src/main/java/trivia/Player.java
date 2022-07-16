package trivia;

public class Player {

    private final String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void updatePlaceByRoll(int roll, int maxNbOfPlaces) {
        int placeAfterRoll = place + roll;
        if (placeAfterRoll > (maxNbOfPlaces - 1)) {
            placeAfterRoll -= maxNbOfPlaces;
        }
        place = placeAfterRoll;
    }

    public int getPurse() {
        return purse;
    }

    public void incrementPurse() {
        purse++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void moveToPenaltyBox() {
        inPenaltyBox = true;
    }
}
