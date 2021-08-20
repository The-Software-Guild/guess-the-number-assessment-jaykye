package GuessTheNumber.dao;

import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GuessTheNumberGameDaoDB implements GuessTheNumberGameDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rn = new Round();
            // Column labels are the column names in the database.
            rn.setId(rs.getInt("id"));
//            rn.setName(rs.getString("name"));
//            rn.setDescription(rs.getString("description"));
            return rn;
        }
    }

    @Override
    public List<Game> getAllGames() {
        return null;
    }

    @Override
    public Game getGameById(int id) {
        return null;
    }

    @Override
    public Game addGame(Game game) {
        return null;
    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void deleteGameById(Game game) {

    }
}
