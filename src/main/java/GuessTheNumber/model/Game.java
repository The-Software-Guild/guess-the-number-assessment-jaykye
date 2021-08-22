package GuessTheNumber.model;

import java.util.Objects;

public class Game {
    // A game has many rounds.
    int gameId;   // id of the Game. Let the database decide this.
    String answer;
    boolean finished; // In Progress or Finished. if In progress, do not display answer.

    public int getGameId() {
        return gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId && finished == game.finished && Objects.equals(answer, game.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, answer, finished);
    }
}
