package game.screens;

import java.awt.Graphics;

import engine.Input;
import game.Game;

public class ScreenManager {

    private Input input;

    public enum State {
        MENU, OPTIONS, GAME
    }

    private State state = State.MENU;

    private static ScreenMenu menu;
    private static ScreenOptions options;
    private Game game;

    public ScreenManager(Input input) {
        this.input = input;

        menu = new ScreenMenu(input, this);
        options = new ScreenOptions(input, this);
    }

    public void tick() {
        switch (state) {
        case MENU:
            menu.tick();
            break;
        case OPTIONS:
            options.tick();
            break;
        case GAME:
            game.tick();
            break;
        }
    }

    public void render(Graphics g) {
        switch (state) {
        case MENU:
            menu.render(g);
            break;
        case OPTIONS:
            options.render(g);
            break;
        case GAME:
            game.render(g);
            break;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void freshGame() {
        game = new Game(input, this);
    }

    public static void setButtons() {
        menu.setButtons();
        options.setButtons();
    }

}
