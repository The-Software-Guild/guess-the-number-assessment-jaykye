package GuessTheNumber.dao;

import GuessTheNumber.TestApplicationConfiguration;
import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GuessTheNumberRoundDaoDBTest {
    @Autowired
    GuessTheNumberRoundDao roundDao;
    @Autowired
    GuessTheNumberGameDao gameDao;


    @BeforeEach
    public void setUp() {
        // 여기서 따로 establish connection 하기 싫은가보다. 그래서 getAllRounds 사용함 (which internally establish connection.)
        // 대신 이 method가 작동을 잘 해야지.
        // connection 만들면 그냥 drop해버리면 되는데. jdbc(DataSource).update("Drop table room;")
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds) {
            roundDao.deleteRoundById(round.getId());
        }
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }


    @Test
    public void testAddGetRound() {
        // Adding start from taking care of the primary keys of the foreign keys.
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("2341");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("2389");
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:1:p:2");
        round.setNumExactMatch(1);
        round.setNumPartialMatch(2);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getId());

        assertEquals(round, fromDao);
    }

    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("2341");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("2389");
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:1:p:2");
        round.setNumExactMatch(1);
        round.setNumPartialMatch(2);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Game game2 = new Game();
        game2.setGameId(1);
        game2.setAnswer("2341");
        game2.setFinished(false);
        game2 = gameDao.addGame(game2);

        Round round2 = new Round();
        round2.setGuess("2389");
        round2.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round2.setResult("e:1:p:2");
        round2.setNumExactMatch(1);
        round2.setNumPartialMatch(2);
        round2.setGameId(game2.getGameId());
        round2 = roundDao.addRound(round2);



        List<Round> rounds = roundDao.getAllRounds();

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
    }

    @Test
    public void testUpdateRound() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("2341");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("2389");
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:1:p:2");
        round.setNumExactMatch(1);
        round.setNumPartialMatch(2);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getId());

        assertEquals(round, fromDao);

        round.setGuess("1234");

        roundDao.updateRound(round);

        assertNotEquals(round, fromDao);

        fromDao = roundDao.getRoundById(round.getId());

        assertEquals(round, fromDao);
    }

    @Test
    public void testDeleteRound() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("2341");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("2389");
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:1:p:2");
        round.setNumExactMatch(1);
        round.setNumPartialMatch(2);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        roundDao.deleteRoundById(round.getId());

        Round fromDao = roundDao.getRoundById(round.getId());
        assertNull(fromDao);
    }

}