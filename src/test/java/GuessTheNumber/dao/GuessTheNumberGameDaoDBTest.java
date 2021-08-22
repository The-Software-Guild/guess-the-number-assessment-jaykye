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

import java.awt.print.PrinterGraphics;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GuessTheNumberGameDaoDBTest {
    @Autowired
    GuessTheNumberGameDao gameDao;

    @Autowired
    GuessTheNumberRoundDao roundDao;

    @BeforeEach
    public void setUp() {
        // 여기서 따로 establish connection 하기 싫은가보다. 그래서 getAllRounds 사용함 (which internally establish connection.)
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
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("2341");
        game.setFinished(false);

        game = gameDao.addGame(game);
        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
        System.out.println(fromDao.getGameId());
    }

    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setAnswer("2341");
        game1.setFinished(false);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setAnswer("1154");
        game2.setFinished(false);
        game2 = gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();
        assertEquals(games.size(), 2);

        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testUpdateGame() {
        Game game1 = new Game();
        game1.setAnswer("2341");
        game1.setFinished(false);
        game1 = gameDao.addGame(game1);

        game1.setAnswer("9874");
        gameDao.updateGame(game1);

        Game fromDao = gameDao.getGameById(game1.getGameId());

        assertEquals(fromDao, game1);
        assertNotEquals(fromDao.getAnswer(), "2341");
    }

    @Test
    public void testDeleteGame() {
        Game game1 = new Game();
        game1.setAnswer("2341");
        game1.setFinished(false);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setAnswer("1154");
        game2.setFinished(false);
        game2 = gameDao.addGame(game2);

        gameDao.deleteGameById(game1.getGameId());

        List<Game> games = gameDao.getAllGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(game2));
    }
}