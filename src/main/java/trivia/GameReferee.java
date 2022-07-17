package trivia;

public class GameReferee {

    public boolean isGettingOutOfPenaltyBox(int roll) {
        return (roll % 2 != 0);
    }

    public boolean isPlayerTheWinner(Player player) {
        return player.getPurse() == 6;
    }
}
