package me.giantcrack.bpvp.que;

import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/25/2015.
 */
public class QueManager {

    private static QueManager instance = new QueManager();

    private QueManager() {}

    public static QueManager getInstance() {
        return instance;
    }

    public List<Que> ques = new ArrayList<>();

    public Que getQue(Kit k, QueType type) {
        for (Que q : ques) {
            if (q.getKit().getName().equals(k.getName()) && q.getQueType() == type) {
                  return q;
            }
        }
        return null;
    }

    public Que getQue(Player p) {
        for (Que q : ques) {
            if (q.getPlayers().contains(p)) {
                return q;
            }
        }
        return null;
    }

    public void loadUnrankedQues() {
        //Create and load unranked duels
        Unranked ndu = new Unranked(KitManager.getInstance().getKit("NoDebuff"), QueType.UNRANKED);
        ques.add(ndu);

        Unranked du = new Unranked(KitManager.getInstance().getKit("Debuff"), QueType.UNRANKED);
        ques.add(du);

        Unranked a = new Unranked(KitManager.getInstance().getKit("Archer"),QueType.UNRANKED);
        ques.add(a);

        Unranked g = new Unranked(KitManager.getInstance().getKit("Gapple"), QueType.UNRANKED);
        ques.add(g);

        Unranked m = new Unranked(KitManager.getInstance().getKit("MCSG"), QueType.UNRANKED);
        ques.add(m);

        Unranked s = new Unranked(KitManager.getInstance().getKit("Soup"), QueType.UNRANKED);
        ques.add(s);


        //Start all unranked queues
        for (Que q : ques) {
            if (q.getQueType() == QueType.UNRANKED) {
                q.runTaskTimer(Main.getInstance(), 20, 20);
            }
        }
    }

    public void loadRankedQues() {
        Ranked ndr = new Ranked(KitManager.getInstance().getKit("NoDebuff"), QueType.RANKED);
        ques.add(ndr);

        Ranked du = new Ranked(KitManager.getInstance().getKit("Debuff"), QueType.RANKED);
        ques.add(du);

        Ranked a = new Ranked(KitManager.getInstance().getKit("Archer"),QueType.RANKED);
        ques.add(a);

        Ranked g = new Ranked(KitManager.getInstance().getKit("Gapple"), QueType.RANKED);
        ques.add(g);

        Ranked m = new Ranked(KitManager.getInstance().getKit("MCSG"), QueType.RANKED);
        ques.add(m);

        Ranked s = new Ranked(KitManager.getInstance().getKit("Soup"), QueType.RANKED);
        ques.add(s);


        for (Que q : ques) {
            if (q.getQueType() == QueType.RANKED) {
                q.runTaskTimer(Main.getInstance(), 20, 20);
            }
        }
    }


}
