package trivia;

import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {
   private final List<Player> players = new ArrayList<>();
   private final PenaltyBox penaltyBox = new PenaltyBox();
   private final GameBoard gameBoard;

   private int currentPlayer = 0;

   public GameBetter() {
      List<String> categoryNames = Arrays.asList("Pop", "Science", "Sports", "Rock");
      gameBoard = new GameBoard(categoryNames);
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(getCurrentPlayer().getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
         handleRollForPlayerInPenaltyBox(roll);
      } else {
         handleRollForPlayerWithoutPenalty(roll);
      }
   }

   private void handleRollForPlayerInPenaltyBox(int roll) {
      boolean isGettingOutOfPenaltyBox = (roll % 2 != 0);

      printPenaltyBoxInteractionMessage(isGettingOutOfPenaltyBox);

      if (isGettingOutOfPenaltyBox) {
         penaltyBox.releasePrisoner(getCurrentPlayer());
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

      Category currentCategory = getCurrentPlayer().getPlace().getCategory();
      System.out.println("The category is " + currentCategory.getName());

      askQuestion();
   }

   private void updateCurrentPlayerPlace(int roll) {
      gameBoard.movePlayer(getCurrentPlayer(), roll);

      System.out.println(getCurrentPlayer().getName()
                         + "'s new location is "
                         + getCurrentPlayer().getPlace().getIndex());
   }

   private void askQuestion() {
      Category currentCategory = getCurrentPlayer().getPlace().getCategory();
      System.out.println(currentCategory.consumeQuestion());
   }

   public boolean wasCorrectlyAnswered() {
      if (!penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
         handleCurrentPlayerCorrectAnswer();
      }
      boolean shouldGameContinue = shouldGameContinue();
      selectNextPlayer();
      return shouldGameContinue;
   }

   private boolean shouldGameContinue() {
      if (penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
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
      penaltyBox.addPrisoner(getCurrentPlayer());
   }

   private Player getCurrentPlayer() {
      return players.get(currentPlayer);
   }

   private void selectNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

}
