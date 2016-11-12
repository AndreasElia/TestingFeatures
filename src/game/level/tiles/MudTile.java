package game.level.tiles;

import java.awt.Graphics;

public class MudTile extends Tile {

    private static final long serialVersionUID = 1L;

    public MudTile(int type, int x, int y) {
        super(type, x, y);
        setOffset(12, 4);
        setName("Mud");
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics g) {
        super.render(g);
    }

}
