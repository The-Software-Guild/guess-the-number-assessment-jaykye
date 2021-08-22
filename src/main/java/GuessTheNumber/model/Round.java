package GuessTheNumber.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Round {
    int id; // id of the Round. Let the database decide this. Will be autoIncremented but need the field for later when I retrieve it.
    String guess;
    LocalDateTime timeOfGuess;
    int numExactMatch;
    int numPartialMatch;
    String result; // in form of e:0:p:0
    int gameId;



    public int getId() {
        return id;
    }

    public String getGuess() {
        return guess;
    }

    public LocalDateTime getTimeOfGuess() {
        return timeOfGuess;
    }

    public int getNumExactMatch() {
        return numExactMatch;
    }

    public int getNumPartialMatch() {
        return numPartialMatch;
    }

    public String getResult() {
        return result;
    }

    public int getGameId() {
        return gameId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public void setTimeOfGuess(LocalDateTime timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    public void setNumExactMatch(int numExactMatch) {
        this.numExactMatch = numExactMatch;
    }

    public void setNumPartialMatch(int numPartialMatch) {
        this.numPartialMatch = numPartialMatch;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id && numExactMatch == round.numExactMatch && numPartialMatch == round.numPartialMatch && gameId == round.gameId && Objects.equals(guess, round.guess) && Objects.equals(timeOfGuess, round.timeOfGuess) && Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guess, timeOfGuess, numExactMatch, numPartialMatch, result, gameId);
    }
}
