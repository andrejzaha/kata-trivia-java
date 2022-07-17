package trivia;

import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {
   private final GameBoard gameBoard;
   private final List<Player> players = new ArrayList<>();
   private final PenaltyBox penaltyBox = new PenaltyBox();
   private final GameReferee gameReferee = new GameReferee();
   private final Printer printer = new Printer();
   private int currentPlayer;

   public GameBetter() {
      List<String> categoryNames = Arrays.asList("Pop", "Science", "Sports", "Rock");
      gameBoard = new GameBoard(categoryNames);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      printer.printAfterAddingPlayer(players);
      return true;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      printer.printAfterRoll(getCurrentPlayer(), roll);

      if (penaltyBox.isGivenPlayerPrisoner(getCurrentPlayer())) {
         handleRollForPlayerInPenaltyBox(roll);
      } else {
         handleRollForPlayerWithoutPenalty(roll);
      }
   }

   private void handleRollForPlayerInPenaltyBox(int roll) {
      boolean isGettingOutOfPenaltyBox = gameReferee.isGettingOutOfPenaltyBox(roll);

      printer.printPenaltyBoxInteractionMessage(getCurrentPlayer(), isGettingOutOfPenaltyBox);

      if (isGettingOutOfPenaltyBox) {
         penaltyBox.releasePrisoner(getCurrentPlayer());
         handleRollForPlayerWithoutPenalty(roll);
      }
   }

   private void handleRollForPlayerWithoutPenalty(int roll) {
      Player currentPlayer = getCurrentPlayer();

      gameBoard.movePlayer(currentPlayer, roll);
      printer.printPlayerNewLocation(currentPlayer);
      printer.printQuestion(currentPlayer);
   }

   public boolean wasCorrectlyAnswered() {
      Player currentPlayer = getCurrentPlayer();

      if (!penaltyBox.isGivenPlayerPrisoner(currentPlayer)) {
         currentPlayer.incrementPurse();
         printer.printForCorrectAnswer(currentPlayer);
      }

      boolean shouldGameContinue = shouldGameContinue(currentPlayer);
      if (!shouldGameContinue) {
         return false;
      }

      selectNextPlayer();

      return true;
   }

   private boolean shouldGameContinue(Player currentPlayer) {
      if (penaltyBox.isGivenPlayerPrisoner(currentPlayer)) {
         return true;
      }
      return !gameReferee.isPlayerTheWinner(currentPlayer);
   }

   public boolean wrongAnswer() {
      Player currentPlayer = getCurrentPlayer();

      penaltyBox.addPrisoner(currentPlayer);
      printer.printForIncorrectAnswer(currentPlayer);
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
