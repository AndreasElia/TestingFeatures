package game.level.tiles;

import game.Globals;
import game.items.GrassItem;
import game.items.Item;
import game.items.ItemDrop;
import game.level.Level;

import java.awt.Graphics;

public class GrassTile extends Tile {

	private static final long serialVersionUID = 1L;

	public GrassTile(int type, int x, int y) {
		super(type, x, y);
		setOffset(0, 0);
		setName("Grass");
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

	@Override
	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return new ItemDrop[] { new ItemDrop(new GrassItem(Item.grass, x, y), 1) };
	}

}
