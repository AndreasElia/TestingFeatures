package engine.gfx;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private Color colour;

    public Particle(int x, int y, int dx, int dy, int size, int life, Color colour) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.colour = colour;
    }

    public void tick() {
        x += dx;
        y += dy;

        life--;
    }

    public void render(Graphics g) {
        g.setColor(colour);
        g.fillRect(x - (size / 2), y - (size / 2), size, size);
    }

    public int getLife() {
        return life;
    }

}
