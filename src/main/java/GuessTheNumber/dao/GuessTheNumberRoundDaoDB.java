package GuessTheNumber.dao;

import GuessTheNumber.model.Round;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GuessTheNumberRoundDaoDB implements GuessTheNumberRoundDao {
    @Override
    public List<Round> getAllRounds() {
        return null;
    }

    @Override
    public Round getRoundById(int id) {
        return null;
    }

    @Override
    public Round addRound(Round round) {
        return null;
    }

    @Override
    public void updateRound(Round round) {

    }

    @Override
    public void deleteRoundById(Round round) {

    }
}
