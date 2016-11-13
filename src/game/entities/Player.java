package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import engine.Input;
import game.Globals;
import game.level.Level;

public class Player extends Entity {

    private static final long serialVersionUID = 1L;

    private Input input;

    private boolean mounted = false;

    private int up = 1;
    private int down = 0;
    private int left = 3;
    private int right = 2;

    private int facing = down;

    public Player(Input input, int startX, int startY) {
        setBounds(startX, startY, 16 * Globals.scale, 16 * Globals.scale);
        this.input = input;
        this.x = startX;
        this.y = startY;

        setSpeed(4);
    }

    public void tick(Level level) {
        //
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 255, 144));
        g.drawRect(x, y, width, height);
    }

}
