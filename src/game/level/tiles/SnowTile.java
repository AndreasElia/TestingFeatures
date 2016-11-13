package game.level.tiles;

import java.awt.Graphics;

public class SnowTile extends Tile {

	private static final long serialVersionUID = 1L;

	public SnowTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(0, 8);
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

}
