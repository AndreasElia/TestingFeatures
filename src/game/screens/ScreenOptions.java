package game.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import engine.Input;
import engine.Main;
import game.Utils;
import game.screens.ScreenManager.State;

public class ScreenOptions {

    private Input input;
    private ScreenManager screen;

    // private String resolution = "1024x576";
    private boolean fullScreen = false;

    private Rectangle fullScreenRect;
    private String fullScreenText = "Full Screen: " + fullScreen;

    private Rectangle backRect;
    private String backText = "Back";

    public ScreenOptions(Input input, ScreenManager screen) {
        this.input = input;
        this.screen = screen;

        setButtons();
    }

    public void tick() {
        fullScreenText = "Full Screen: " + fullScreen;

        if (fullScreenRect.contains(input.getMouse()) && input.isMouseLeft()) {
            fullScreen = !fullScreen;
            fullScreenText = "Full Screen: " + fullScreen;
            ScreenManager.setButtons();
            input.setMouseLeft(false);
        }

        if (backRect.contains(input.getMouse()) && input.isMouseLeft()) {
            saveOptions();
            screen.setState(State.MENU);
            input.setMouseLeft(false);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 255, 255, 177));
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        g.setFont(Utils.menuButtonFont);

        // start button
        g.setColor(new Color(0, 0, 0, 177));
        g.fillRect(fullScreenRect.x - (g.getFontMetrics().stringWidth(fullScreenText) / 4), fullScreenRect.y,
                fullScreenRect.width + (g.getFontMetrics().stringWidth(fullScreenText) / 2), fullScreenRect.height);

        g.setColor(new Color(255, 255, 255));
        g.drawString(fullScreenText, ((Main.WIDTH - g.getFontMetrics().stringWidth(fullScreenText)) / 2),
                fullScreenRect.y + (g.getFontMetrics().getHeight() + 9));

        // back button
        g.setColor(new Color(0, 0, 0, 177));
        g.fillRect(backRect.x, backRect.y, backRect.width, backRect.height);

        g.setColor(new Color(255, 255, 255));
        g.drawString(backText, ((Main.WIDTH - g.getFontMetrics().stringWidth(backText)) / 2),
                backRect.y + (g.getFontMetrics().getHeight() + 9));
    }

    public void setButtons() {
        fullScreenRect = new Rectangle((Main.WIDTH / 2) - 65, Main.HEIGHT / 2, 130, 40);
        backRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2) + 100, 130, 40);
    }

    public void saveOptions() {
        try {
            FileOutputStream fileOut = new FileOutputStream("options.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(fullScreen);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadOptions() {

    }

}
