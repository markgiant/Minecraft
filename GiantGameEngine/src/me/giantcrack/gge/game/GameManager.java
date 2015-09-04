package me.giantcrack.gge.game;

import me.giantcrack.gge.exceptions.GameException;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface GameManager {

    List<? extends Game> getGames();

    void createGame(String name, int id) throws GameException;

    Game getGame(String name, int id);

    void removeGame(String name, int id) throws GameException;
}
