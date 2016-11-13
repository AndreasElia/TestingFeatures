package game.level.tiles;

import java.awt.Graphics;

public class StoneWallTile extends Tile {

	private static final long serialVersionUID = 1L;

	public StoneWallTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(6, 8);
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

}
