package me.giantcrack.bpvp.duels;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

/**
 * Created by markvolkov on 8/27/15.
 */
public class DuelBoard {

    private Duel d;

    private Scoreboard board;

    private ScoreboardManager scoreboardManager;

    public DuelBoard(Duel d) {
        this.d = d;
        this.scoreboardManager = Bukkit.getScoreboardManager();
        this.board = this.scoreboardManager.getNewScoreboard();
        Team team  = this.board.registerNewTeam("team1");
        Objective objective = this.board.registerNewObjective("test","dummy");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);

    }

}
