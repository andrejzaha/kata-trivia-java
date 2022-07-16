package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class GameBetter implements IGame {
   private ArrayList players = new ArrayList();
   private int[] places = new int[6];
   private int[] purses = new int[6];
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
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
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
      System.out.println(players.get(currentPlayer) + interactionType + " of the penalty box");
   }

   private String createMessageForInteractionType(boolean isGettingOutOfPenaltyBox) {
      if (isGettingOutOfPenaltyBox) {
         return " is getting out";
      }
      return " is not getting out";
   }

   private void handleRollForPlayerWithoutPenalty(int roll) {
      places[currentPlayer] = places[currentPlayer] + roll;
      if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

      System.out.println(players.get(currentPlayer)
                         + "'s new location is "
                         + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
      askQuestion();
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
      if (places[currentPlayer] == 0) return "Pop";
      if (places[currentPlayer] == 4) return "Pop";
      if (places[currentPlayer] == 8) return "Pop";
      if (places[currentPlayer] == 1) return "Science";
      if (places[currentPlayer] == 5) return "Science";
      if (places[currentPlayer] == 9) return "Science";
      if (places[currentPlayer] == 2) return "Sports";
      if (places[currentPlayer] == 6) return "Sports";
      if (places[currentPlayer] == 10) return "Sports";
      return "Rock";
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            selectNextPlayer();

            return winner;
         } else {
            selectNextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         selectNextPlayer();

         return winner;
      }
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
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;
   }

   private void selectNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
