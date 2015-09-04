package me.giantcrack.gge.arena;

/**
 * Created by markvolkov on 8/6/15.
 */
public class Arena extends ArenaBase {

    protected String name;

    public Arena(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }
}
