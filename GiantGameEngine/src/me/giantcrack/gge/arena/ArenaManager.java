package me.giantcrack.gge.arena;

import me.giantcrack.gge.exceptions.ArenaException;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface ArenaManager {

    List<? extends Arena> getArenas();

    void createArena(String name) throws ArenaException;

    <T extends Arena> T getArena(String name);

    void deleteArena(String name) throws ArenaException;

    void setUpArena(String name) throws ArenaException;

    void loadArenasFromFile();

}
