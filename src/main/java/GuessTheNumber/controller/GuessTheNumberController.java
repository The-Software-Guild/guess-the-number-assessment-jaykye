package GuessTheNumber.controller;

import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import GuessTheNumber.service.GuessTheNumberService;
import GuessTheNumber.service.InvalidGameException;
import GuessTheNumber.service.InvalidRoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {
    /*
    "begin" - POST – Starts a game, generates an answer, and sets the correct status.
    Should return a 201 CREATED message as well as the created gameId.

    "guess" – POST – Makes a guess by passing the guess and gameId in as JSON.
    The program must calculate the results of the guess and mark the game finished if the guess is correct.
    It returns the Round object with the results filled in.

    "game" – GET – Returns a list of all games. Be sure in-progress games do not display their answer.

    "game/{gameId}" - GET – Returns a specific game based on ID. Be sure in-progress games do not display their answer.

    "rounds/{gameId} – GET – Returns a list of rounds for the specified game sorted by time.
     */

    private final GuessTheNumberService service;  // I don't need to autowire. Spring Boot assumes it.
    public GuessTheNumberController(GuessTheNumberService service) {
        this.service = service;
    }


    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity beginGame() {
        // generate Game
        Game game = new Game();
        game.setAnswer(service.generateAnswer());
        game.setFinished(false);
        service.addGame(game);
        String message = "New game created: Game # " + game.getGameId();
        return new ResponseEntity(message, HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Round> create(@RequestBody Round round) throws InvalidRoundException {
        // takes guess and gameId.
        String message = "Guess :" + round.getGuess() + " for Game: " + round.getGameId();
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Game game = service.getGameById(round.getGameId());
        if (game.isFinished()){
            return new ResponseEntity("You cannot guess for a finished game.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String result = service.calculateResult(game.getAnswer(), round.getGuess());
        round.setResult(result);
        round.parseResult();

        // if guess is correct, finish the game.
        if (round.getNumExactMatch() == 4) {
            game.setFinished(true);
            // Then update game in storage.
            service.editGame(game);
        }
        service.addRound(round);
        return ResponseEntity.ok(round);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        // 자바 obj를 직접적으로 보낼 수 없으니 ResponseEntity 에 싸서 보낸다.
        List<Game> games = service.getAllGames();
        for (Game game : games) {

            // Do not show answer when game is in progress.
            if (!game.isFinished()) {
                game.setAnswer("Game still in progress. Finish the game to see answer.");
            }
        }
        return games;
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game>  getGameById(@PathVariable int gameId) {
        Game game = service.getGameById(gameId);
        if (!game.isFinished()) {
            game.setAnswer("Game still in progress. Finish the game to see answer.");
        }

        return ResponseEntity.ok(game);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round>  getAllRoundsForAGame(@PathVariable int gameId) {
        List<Round> rounds = service.getAllRounds();
        List<Round> roundsOfGame = rounds.stream().filter(x -> x.getGameId() == gameId).sorted(
                Comparator.comparing(Round::getTimeOfGuess)).collect(Collectors.toList());
        // Either one works.
//        Collections.sort(roundsOfGame, (s1, s2) -> s1.getTimeOfGuess().compareTo(s2.getTimeOfGuess()));
        return roundsOfGame;
    }
}
