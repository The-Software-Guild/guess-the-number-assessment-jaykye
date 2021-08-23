package GuessTheNumber.service;

import GuessTheNumber.dao.GuessTheNumberGameDao;
import GuessTheNumber.dao.GuessTheNumberRoundDao;
import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GuessTheNumberService {
    @Autowired
    GuessTheNumberRoundDao roundDao;
    @Autowired
    GuessTheNumberGameDao gameDao;

    // calculating the results of a guess.

    public String generateAnswer(){
        List<Integer> intPool = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Random rSeed = new Random();

        String out = "";
        for (int i = 1; i<=4; i++){
            out += intPool.remove(rSeed.nextInt(intPool.size()));
        }
        return out;
    }

    public String calculateResult(String answer, String guess){
        int exactMatch =0;
        int partialMatch =0;

        String[] ansToken = answer.split("");
        String[] guessToken = guess.split("");

        for (int i = 0; i < answer.length(); i++) {
            if (ansToken[i].equals(guessToken[i])) {
                exactMatch += 1;
            }
            else if (answer.contains(guessToken[i])) {
                partialMatch += 1;
            }
        }
        return "e:" + exactMatch + ":" + "p:" + partialMatch;
    }

    public void updateGameStatus(Round round, Game game) throws Exception {
        round.parseResult();
        if (round.getGameId() != game.getGameId()) {
            throw new Exception("Wrong round - game pair");
        }
        if (round.getNumExactMatch() == 4) {
            game.setFinished(true);
        }
    }

    // Round
    public void validateRound(Round round) throws InvalidRoundException{
        // check guess, timeOfGuess, result and gameId
        if (round.getGuess().length() != 4){  // rest is done by controller/service
            throw new InvalidRoundException("Round object is not valid.");
        }
        if (Arrays.stream(round.getGuess().split("")).collect(Collectors.toSet()).size() < 4) {
            throw new InvalidRoundException("Guess should contain 4 different numbers.");
        }
    }

    public Round addRound(Round round) throws InvalidRoundException{
        validateRound(round);
        return roundDao.addRound(round);
    }

    public List<Round> getAllRounds() {
        return roundDao.getAllRounds();
    }

    public Round getRoundById(int id) {
        return roundDao.getRoundById(id);
    }

    public void deleteRoundById(int id) {
        roundDao.deleteRoundById(id);
    }

    public void editRound(Round round) throws InvalidRoundException{
        validateRound(round);
        roundDao.updateRound(round);
    }

    // Game
//    public void validateGame(Game game) throws InvalidGameException{
//        // check answer
//        if (game.getAnswer().length() != 4 ){
//            throw new InvalidGameException("Game object is not valid.");
//        }
//    }
//
    public Game addGame(Game game) {
//        validateGame(game);
        return gameDao.addGame(game);
    }

    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    public Game getGameById(int id) {
        return gameDao.getGameById(id);
    }

    public void deleteGameById(int id) {
        gameDao.deleteGameById(id);
    }

    public void editGame(Game game) {
//        validateGame(game);
        gameDao.updateGame(game);
    }
}
