package game.level.tiles;

import game.Globals;
import game.level.Level;

import java.awt.Color;
import java.awt.Graphics;

public class PowerTile extends Tile {

	private static final long serialVersionUID = 1L;

	public PowerTile(int type, int x, int y) {
		super(type, x, y);
		
		setTicks(true);
	}

	public void tick() {

	}

	public void onUpdate(Level level, int x, int y) {
	}

	public void render(Graphics g) {
		g.setColor(new Color(249, 168, 134));
		g.fillRect(x + (Globals.tileSize / 2) - 3, y + (Globals.tileSize / 2) - 3, 6, 6);
	}

}
