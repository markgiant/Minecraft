package me.giantcrack.gge.gui;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by markvolkov on 8/7/15.
 */
public class Menu {

    private String name;
    private Set<Button> buttons;

    public Menu(String name) {
        this.name = name;
        this.buttons = new HashSet<>();
    }
    public Menu() {
        this.name = null;
        this.buttons = new HashSet<>();
    }

    public void removeAllButtons() {
        this.buttons = new HashSet<>();
    }

    public void addButton(Button b) {
        buttons.add(b);
    }

    public void removeButton(Button b) {
        if (buttons.contains(b)) {
            buttons.remove(b);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Button> getButtons() {
        return buttons;
    }

    public void setButtons(Set<Button> buttons) {
        this.buttons = buttons;
    }

}
