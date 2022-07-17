package trivia;

import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {
   private final GameBoard gameBoard;
   private final List<Player> players = new ArrayList<>();
   private final PenaltyBox penaltyBox = new PenaltyBox();
   private final GameReferee gameReferee = new GameReferee();
   private int currentPlayer;

   public GameBetter() {
      List<String> categoryNames = Arrays.asList("Pop", "Science", "Sports", "Rock");
      gameBoard = new GameBoard(categoryNames);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
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
      boolean isGettingOutOfPenaltyBox = gameReferee.isGettingOutOfPenaltyBox(roll);

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
      gameBoard.movePlayer(getCurrentPlayer(), roll);
      System.out.println(getCurrentPlayer().getName() + "'s new location is " + getCurrentPlayer().getPlace().getIndex());

      Category currentCategory = getCurrentPlayer().getPlace().getCategory();

      System.out.println("The category is " + currentCategory.getName());
      System.out.println(currentCategory.consumeQuestion());
   }

   public boolean wasCorrectlyAnswered() {
      if (!penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
         System.out.println("Answer was correct!!!!");
         getCurrentPlayer().incrementPurse();
         System.out.println(getCurrentPlayer().getName() + " now has " + getCurrentPlayer().getPurse() + " Gold Coins.");
      }

      boolean shouldGameContinue = shouldGameContinue();
      if (!shouldGameContinue) {
         return false;
      }

      selectNextPlayer();

      return true;
   }

   private boolean shouldGameContinue() {
      if (penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
         return true;
      }
      return !gameReferee.isPlayerTheWinner(getCurrentPlayer());
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");

      penaltyBox.addPrisoner(getCurrentPlayer());
      System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");

      selectNextPlayer();

      return true;
   }

   private Player getCurrentPlayer() {
      return players.get(currentPlayer);
   }

   private void selectNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

}
