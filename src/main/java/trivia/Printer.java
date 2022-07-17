package trivia;

import java.util.List;

public class Printer {

    public void printAfterAddingPlayer(List<Player> players) {
        System.out.println(players.get(players.size() - 1) + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void printAfterRoll(Player player, int roll) {
        System.out.println(player + " is the current player");
        System.out.println("They have rolled a " + roll);
    }

    public void printPenaltyBoxInteractionMessage(Player player, boolean isGettingOutOfPenaltyBox) {
       String interactionType = createMessageForInteractionType(isGettingOutOfPenaltyBox);
       System.out.println(player + interactionType + " of the penalty box");
    }

    private String createMessageForInteractionType(boolean isGettingOutOfPenaltyBox) {
        if (isGettingOutOfPenaltyBox) {
            return " is getting out";
        }
        return " is not getting out";
    }

    public void printPlayerNewLocation(Player player) {
        System.out.println(player + "'s new location is " + player.getPlace().getIndex());
    }

    public void printQuestion(Player player) {
        Category currentCategory = player.getPlace().getCategory();
        System.out.println("The category is " + currentCategory.getName());
        System.out.println(currentCategory.consumeQuestion());
    }

    public void printForCorrectAnswer(Player player) {
        System.out.println("Answer was correct!!!!");
        System.out.println(player + " now has " + player.getPurse() + " Gold Coins.");
    }

    public void printForIncorrectAnswer(Player player) {
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
    }
}
