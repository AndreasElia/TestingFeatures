package game.level.tiles;

import java.awt.Graphics;

public class WaterTile extends Tile {

	private static final long serialVersionUID = 1L;

	public WaterTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(6, 0);
		setName("Water");
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

}
