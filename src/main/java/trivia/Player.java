package trivia;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
