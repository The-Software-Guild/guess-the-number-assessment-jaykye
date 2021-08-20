package GuessTheNumber.model;

import java.time.LocalDateTime;

public class Round {
    int id; // id of the Round. Let the database decide this. Will be autoIncremented but need the field for later when I retrieve it.
    String guess;
    LocalDateTime timeOfGuess;
    int numExactMatch;
    int numPartialMatch;
    String result; // in form of e:0:p:0

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
}
