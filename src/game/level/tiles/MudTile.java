package game.level.tiles;

import game.Globals;
import game.items.DirtItem;
import game.items.Item;
import game.items.ItemDrop;
import game.level.Level;

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

	@Override
	public void onDestroyTile(Level level, int x, int y) {
		level.setTile(x, y, new DirtTile(Tile.dirt, x * Globals.tileSize, y * Globals.tileSize));
	}

	@Override
	public ItemDrop[] onDestroy(Level level, int x, int y) {
		// Multiple
		return new ItemDrop[] { new ItemDrop(new DirtItem(Item.dirt, x, y), 1) };
		// return null;
	}

}
