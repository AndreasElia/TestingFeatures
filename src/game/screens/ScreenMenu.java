package game.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import engine.Input;
import engine.Main;
import game.Utils;
import game.screens.ScreenManager.State;

public class ScreenMenu {

    private Input input;
    private ScreenManager screen;

    private Rectangle startRect;
    private String startText = "Play";

    private Rectangle optionsRect;
    private String optionsText = "Options";

    private Rectangle exitRect;
    private String exitText = "Exit";

    public ScreenMenu(Input input, ScreenManager screen) {
        this.input = input;
        this.screen = screen;

        setButtons();
    }

    public void tick() {
        if (startRect.contains(input.getMouse()) && input.isMouseLeft()) {
            screen.setState(State.GAME);
            screen.freshGame();
            input.setMouseLeft(false);
        }

        if (optionsRect.contains(input.getMouse()) && input.isMouseLeft()) {
            screen.setState(State.OPTIONS);
            input.setMouseLeft(false);
        }

        if (exitRect.contains(input.getMouse()) && input.isMouseLeft()) {
            System.exit(0);
            input.setMouseLeft(false);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 255, 255, 177));
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        g.setFont(Utils.menuButtonFont);

        // start button
        g.setColor(new Color(0, 0, 0, 177));
        g.fillRect(startRect.x, startRect.y, startRect.width, startRect.height);

        g.setColor(new Color(255, 255, 255));
        g.drawString(startText, ((Main.WIDTH - g.getFontMetrics().stringWidth(startText)) / 2),
                startRect.y + (g.getFontMetrics().getHeight() + 9));

        // options button
        g.setColor(new Color(0, 0, 0, 177));
        g.fillRect(optionsRect.x, optionsRect.y, optionsRect.width, optionsRect.height);

        g.setColor(new Color(255, 255, 255));
        g.drawString(optionsText, ((Main.WIDTH - g.getFontMetrics().stringWidth(optionsText)) / 2),
                optionsRect.y + (g.getFontMetrics().getHeight() + 9));

        // exit button
        g.setColor(new Color(0, 0, 0, 177));
        g.fillRect(exitRect.x, exitRect.y, exitRect.width, exitRect.height);

        g.setColor(new Color(255, 255, 255));
        g.drawString(exitText, ((Main.WIDTH - g.getFontMetrics().stringWidth(exitText)) / 2),
                exitRect.y + (g.getFontMetrics().getHeight() + 9));
    }

    public void setButtons() {
        startRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2) - 50, 130, 40);
        optionsRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2), 130, 40);
        exitRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2) + 50, 130, 40);
    }

}
