package GuessTheNumber.dao;

import GuessTheNumber.model.Game;

import java.util.List;

public interface GuessTheNumberGameDao {
    // Want to do CRUD operation.
    List<Game> getAllGames();
    Game getGameById(int id);
    Game addGame(Game game);
    void updateGame(Game game);
    void deleteGameById(int id);
}
