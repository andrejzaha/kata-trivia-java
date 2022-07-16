package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {
   private final List<Player> players = new ArrayList<>();
   private boolean[] inPenaltyBox = new boolean[6];

   private LinkedList popQuestions = new LinkedList();
   private LinkedList scienceQuestions = new LinkedList();
   private LinkedList sportsQuestions = new LinkedList();
   private LinkedList rockQuestions = new LinkedList();

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   private String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      initializeAddedPlayer();
      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private void initializeAddedPlayer() {
      inPenaltyBox[howManyPlayers()] = false;
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(getCurrentPlayer().getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         handleRollForPlayerInPenaltyBox(roll);
      } else {
         handleRollForPlayerWithoutPenalty(roll);
      }
   }

   private void handleRollForPlayerInPenaltyBox(int roll) {
      isGettingOutOfPenaltyBox = (roll % 2 != 0);

      printPenaltyBoxInteractionMessage(isGettingOutOfPenaltyBox);

      if (isGettingOutOfPenaltyBox) {
         handleRollForPlayerWithoutPenalty(roll);
      }
   }

   private void printPenaltyBoxInteractionMessage(boolean isGettingOutOfPenaltyBox) {
      String interactionType = createMessageForInteractionType(isGettingOutOfPenaltyBox);
      System.out.println(getCurrentPlayer().getName() + interactionType + " of the penalty box");
   }

   private String createMessageForInteractionType(boolean isGettingOutOfPenaltyBox) {
      if (isGettingOutOfPenaltyBox) {
         return " is getting out";
      }
      return " is not getting out";
   }

   private void handleRollForPlayerWithoutPenalty(int roll) {
      updateCurrentPlayerPlace(roll);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   private void updateCurrentPlayerPlace(int roll) {
      getCurrentPlayer().updatePlaceByRoll(roll, 12);

      System.out.println(getCurrentPlayer().getName()
                         + "'s new location is "
                         + getCurrentPlayer().getPlace());
   }

   private void askQuestion() {
      if (currentCategory() == "Pop")
         System.out.println(popQuestions.removeFirst());
      if (currentCategory() == "Science")
         System.out.println(scienceQuestions.removeFirst());
      if (currentCategory() == "Sports")
         System.out.println(sportsQuestions.removeFirst());
      if (currentCategory() == "Rock")
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (getCurrentPlayer().getPlace() == 0) return "Pop";
      if (getCurrentPlayer().getPlace() == 4) return "Pop";
      if (getCurrentPlayer().getPlace() == 8) return "Pop";
      if (getCurrentPlayer().getPlace() == 1) return "Science";
      if (getCurrentPlayer().getPlace() == 5) return "Science";
      if (getCurrentPlayer().getPlace() == 9) return "Science";
      if (getCurrentPlayer().getPlace() == 2) return "Sports";
      if (getCurrentPlayer().getPlace() == 6) return "Sports";
      if (getCurrentPlayer().getPlace() == 10) return "Sports";
      return "Rock";
   }

   public boolean wasCorrectlyAnswered() {
      if (!inPenaltyBox[currentPlayer] || isGettingOutOfPenaltyBox) {
         handleCurrentPlayerCorrectAnswer();
      }
      boolean shouldGameContinue = shouldGameContinue();
      selectNextPlayer();
      return shouldGameContinue;
   }

   private boolean shouldGameContinue() {
      if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
         return true;
      }
      return isCurrentPlayerNotAWinner();
   }

   private boolean isCurrentPlayerNotAWinner() {
      return getCurrentPlayer().getPurse() != 6;
   }

   private void handleCurrentPlayerCorrectAnswer() {
      System.out.println("Answer was correct!!!!");
      getCurrentPlayer().incrementPurse();
      System.out.println(getCurrentPlayer().getName()
                         + " now has "
                         + getCurrentPlayer().getPurse()
                         + " Gold Coins.");
   }

   public boolean wrongAnswer() {
      handleCurrentPlayerWrongAnswer();

      return true;
   }

   private void handleCurrentPlayerWrongAnswer() {
      System.out.println("Question was incorrectly answered");

      sendCurrentPlayerToPenaltyBox();

      selectNextPlayer();
   }

   private void sendCurrentPlayerToPenaltyBox() {
      System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;
   }

   private Player getCurrentPlayer() {
      return players.get(currentPlayer);
   }

   private void selectNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

}
