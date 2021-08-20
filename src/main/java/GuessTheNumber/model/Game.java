package GuessTheNumber.model;

public class Game {
    // A game has many rounds.
    int gameId;   // id of the Game. Let the database decide this.
    String answer;
    boolean finished; // In Progress or Finished. if In progress, do not display answer.

}
