package game.level.tiles;

import game.Globals;
import game.level.Level;

import java.awt.Color;
import java.awt.Graphics;

public class LampTile extends Tile {

	private static final long serialVersionUID = 1L;

	public LampTile(int type, int x, int y) {
		super(type, x, y);

		updates = true;
	}

	public void tick() {
	}

	@Override
	public void onUpdate(Level level, int x, int y) {
		if (this instanceof LampTileOff) {
			if (level.getTile(x - 1, y) instanceof PowerTile || level.getTile(x + 1, y) instanceof PowerTile || level.getTile(x, y - 1) instanceof PowerTile || level.getTile(x, y + 1) instanceof PowerTile) {
				level.setTile(x, y, new LampTileOn(Tile.lampOn, x * Globals.tileSize, y * Globals.tileSize));
			}
		} else if (this instanceof LampTileOn) {
			if (!(level.getTile(x - 1, y) instanceof PowerTile) && !(level.getTile(x + 1, y) instanceof PowerTile) && !(level.getTile(x, y - 1) instanceof PowerTile) && !(level.getTile(x, y + 1) instanceof PowerTile)) {
				level.setTile(x, y, new LampTileOff(Tile.lampOff, x * Globals.tileSize, y * Globals.tileSize));
			}
		}
	}

	public void render(Graphics g) {
		if (this instanceof LampTileOn) {
			g.setColor(new Color(227, 206, 102));
		} else {
			g.setColor(new Color(179, 105, 55));
		}
		g.fillRect(x + (Globals.tileSize / 2) - 5, y + (Globals.tileSize / 2) - 5, 10, 10);
	}

}
