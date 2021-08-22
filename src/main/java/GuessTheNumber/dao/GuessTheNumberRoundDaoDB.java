package GuessTheNumber.dao;

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
public class GuessTheNumberRoundDaoDB implements GuessTheNumberRoundDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            // Column labels are the column names in the database.
            // names: id, guess, timeOfGuess, result, gameId
            round.setId(rs.getInt("id"));
            round.setTimeOfGuess((rs.getTimestamp("timeOfGuess").toLocalDateTime()));
            // parse the result to integers.
            String result = rs.getString("result");
            round.setResult(result);
            String[] tokens = result.split(":");
            round.setNumExactMatch(Integer.parseInt(tokens[1]));
            round.setNumPartialMatch(Integer.parseInt(tokens[3]));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }

    @Override
    public List<Round> getAllRounds() {  // Probably not very useful. Disable it?
        final String SELECT_ALL_ROUNDS = "SELECT * FROM gameround";
        return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }

    @Override
    public Round getRoundById(int id) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM gameround WHERE id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional  // 두가지 액션, 양방향 통신. -- 하나가 fail하면 rollback 할 것이다.
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO gameround(guess, timeOfGuess, result, gameId) VALUES(?, ?, ?, ?)";
        jdbc.update(INSERT_ROUND,
                round.getGuess(),
                round.getTimeOfGuess(),
                round.getResult(),
                round.getGameId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);
        return round;
    }

    @Override
    public void updateRound(Round round) {
        final String UPDATE_ROUND = "UPDATE gameround SET guess = ?, timeOfGuess = ?, result = ?, gameId = ? WHERE id = ?";
        jdbc.update(UPDATE_ROUND,
                round.getGuess(),
                round.getTimeOfGuess(),
                round.getResult(),
                round.getGameId(),
                round.getId());
    }

    @Override
    @Transactional
    public void deleteRoundById(int id) {
        final String DELETE_ROUND = "DELETE FROM gameround WHERE id = ?";
        jdbc.update(DELETE_ROUND, id);
    }
}
