package GuessTheNumber.dao;

import GuessTheNumber.model.Round;

import java.util.List;

public interface GuessTheNumberRoundDao {
    // Want to do CRUD operation.
    List<Round> getAllRounds();
    Round getRoundById(int id);
    Round addRound(Round round);
    void updateRound(Round round);
    void deleteRoundById(Round round);
}
