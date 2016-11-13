package game.level.tiles;

import java.awt.Graphics;

public class DirtTile extends Tile {

	private static final long serialVersionUID = 1L;

	public DirtTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(6, 4);
		setName("Dirt");
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

}
