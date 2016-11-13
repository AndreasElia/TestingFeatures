package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import engine.Input;
import engine.Main;
import game.entities.Player;
import game.level.Level;
import game.screens.ScreenManager;
import game.screens.ScreenManager.State;

public class Game {

    private Input input;
    private ScreenManager screen;
    private static Level level;
    private static Player player;

    private boolean paused = false;
    private Rectangle resumeRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2), 130, 40);
    private String resumeText = "Resume";

    private Rectangle exitRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2) + 50, 130, 40);
    private String exitText = "Back to Menu";

    // cX and cY are to keep track of the Camera position
    public static int cX;
    public static int cY;

    // pX and pY are for the initial (safe) spawn location
    public static int pX;
    public static int pY;

    // playerSet is to check if the spawn location was set
    private boolean playerSet = false;

    // other colours: 145,65,31, 212,180,74, 179,105,55

    public Game(Input input, ScreenManager screen) {
        this.input = input;
        this.screen = screen;

        level = new Level(input);
    }

    public void tick() {
        if (level.screenGen.running) {
            level.screenGen.tick();
        }

        if (level.finishedGeneration) {
            if (input.getKey("F1") && input.isJustHeld("F1")) {
                level = new Level(input);
            }

            if (input.getKey("F3") && input.isJustHeld("F3")) {
                Globals.debug = !Globals.debug;
            }

            if (input.getKey("Escape") && input.isJustHeld("Escape")) {
                paused = !paused;
            }

            if (!playerSet) {
                player = new Player(input, pX, pY);
                playerSet = true;
            }

            if (!paused && player != null) {
                if (!paused) {
                    level.tick();
                }

                player.tick(level);

                // Camera
                for (int x = (Game.cX / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize)); x < (Game.cX
                        / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize))
                        + (Main.WIDTH / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize)) + 2; x++) {
                    for (int y = (Game.cY / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize)); y < (Game.cY
                            / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize))
                            + (Main.HEIGHT / ((Globals.chunkSize * Globals.chunkCount) * Globals.tileSize)) + 2; y++) {
                        if (x >= 0 && y >= 0 && x < (Globals.chunkSize * Globals.chunkCount)
                                && y < (Globals.chunkSize * Globals.chunkCount)) {
                            if (level.getChunks()[x][y] != null) {
                                level.getChunks()[x][y].moveWith();
                            }
                        }
                    }
                }
            }

            if (paused) {
                if (resumeRect.contains(input.getMouse()) && input.isMouseLeft()) {
                    paused = !paused;
                    input.setMouseLeft(false);
                }

                if (exitRect.contains(input.getMouse()) && input.isMouseLeft()) {
                    screen.setState(State.MENU);
                    input.setMouseLeft(false);
                }
            }
        }
    }

    public void render(Graphics g) {
        if (level.screenGen.running) {
            level.screenGen.render(g);
        }

        if (level.finishedGeneration) {
            level.render(g);

            if (player != null) {
                // player.render(g);
            }

            if (paused) {
                g.setColor(new Color(255, 255, 255, 177));
                g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

                g.setFont(Utils.menuButtonFont);

                // resume button
                g.setColor(new Color(0, 0, 0, 177));
                g.fillRect(resumeRect.x, resumeRect.y, resumeRect.width, resumeRect.height);

                g.setColor(new Color(255, 255, 255));
                g.drawString(resumeText, ((Main.WIDTH - g.getFontMetrics().stringWidth(resumeText)) / 2),
                        resumeRect.y + 24);

                // exit button
                g.setColor(new Color(0, 0, 0, 177));
                g.fillRect(exitRect.x, exitRect.y, exitRect.width, exitRect.height);

                g.setColor(new Color(255, 255, 255));
                g.drawString(exitText, ((Main.WIDTH - g.getFontMetrics().stringWidth(exitText)) / 2), exitRect.y + 24);
            }

            if (Globals.debug) {
                g.setColor(new Color(0, 0, 0, 177));
                g.fillRect(10, 10, 240, 110);

                g.setColor(Color.WHITE);
                g.setFont(Utils.debugFont);
                g.drawString("DEBUG MODE (F3 - toggle)", 16, 24);
                g.drawString("Dev. v" + Globals.version, 16, 38);
                g.drawString("FPS/TPS: " + Main.getFPS() + "/" + Main.getTPS(), 16, 52);
                g.drawString("Entities: " + "n/a", 16, 66);
            }
        }
    }

    public static Player getPlayer() {
        return player;
    }

}
