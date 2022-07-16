package trivia;

public class Player {

    private final String name;
    private int place;

    public Player(String name) {
        this.name = name;
        this.place = 0;
    }

    public String getName() {
        return name;
    }

    public void updatePlaceByRoll(int roll, int maxNbOfPlaces) {
        int placeAfterRoll = place + roll;
        if (placeAfterRoll > (maxNbOfPlaces - 1)) {
            placeAfterRoll -= maxNbOfPlaces;
        }
        place = placeAfterRoll;
    }

    public int getPlace() {
        return place;
    }
}
