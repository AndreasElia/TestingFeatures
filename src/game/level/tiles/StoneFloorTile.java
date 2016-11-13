package game.level.tiles;

import java.awt.Graphics;

public class StoneFloorTile extends Tile {

	private static final long serialVersionUID = 1L;

	public StoneFloorTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(12, 8);
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

}
