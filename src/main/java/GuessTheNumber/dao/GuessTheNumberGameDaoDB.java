package GuessTheNumber.dao;

import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GuessTheNumberGameDaoDB implements GuessTheNumberGameDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            // Column labels are the column names in the database.
            // names: id, answer, finished
            game.setGameId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SELECT_ALL_GAMES, new GuessTheNumberGameDaoDB.GameMapper());
    }


    @Override
    public Game getGameById(int id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE id = ?";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GuessTheNumberGameDaoDB.GameMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    /**
     * Adds the game object to the database and get the appropriate id (auto incremented) from database.
     * The game object with id from db is returned.
     * @param game game object WITOUT the id - if it has, it will be ignored.
     * @return game object with the id from the database.
     */
    @Override
    @Transactional  // 두가지 액션, 양방향 통신. -- 하나가 fail하면 rollback 할 것이다.
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game (answer, finished) VALUES(?, ?)";
        jdbc.update(INSERT_GAME,
                game.getAnswer(),
                game.isFinished());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET answer = ?, finished = ? WHERE id = ?";
        jdbc.update(UPDATE_GAME,
                game.getAnswer(),
                game.isFinished(),
                game.getGameId());
    }

    @Override
    @Transactional
    public void deleteGameById(int id) {
        final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
        jdbc.update(DELETE_GAME, id);
    }
}
