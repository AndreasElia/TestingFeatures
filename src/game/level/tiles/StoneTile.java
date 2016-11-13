package game.level.tiles;

import game.Globals;
import game.level.Level;

import java.awt.Graphics;

public class StoneTile extends Tile {

	private static final long serialVersionUID = 1L;

	public StoneTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(0, 4);
		setName("Stone");
		setPassable(false);
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void onDestroyTile(Level level, int x, int y) {
		level.setTile(x, y, new MudTile(Tile.mud, x * Globals.tileSize, y * Globals.tileSize));
	}

}
