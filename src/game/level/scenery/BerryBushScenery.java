package game.level.scenery;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.Utils;
import game.items.BerryItem;
import game.items.Item;
import game.items.ItemDrop;
import game.level.Level;

public class BerryBushScenery extends BushScenery {

	private static final long serialVersionUID = 1L;

	public BerryBushScenery(int type, int x, int y) {
		super(type, x, y);

		setPassable(false);
		setName("Berry Bush");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.othertiles.getSprite(21), x, y, Images.othertiles.getSprite(1).getWidth() * Globals.scale, Images.othertiles.getSprite(1).getHeight() * Globals.scale, null);
	}

	@Override
	public void onDestroyScenery(Level level, int x, int y) {
		level.setScenery(x, y, new BushScenery(Scenery.bush, x * Globals.tileSize, y * Globals.tileSize));
	}

	@Override
	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return new ItemDrop[] { new ItemDrop(new BerryItem(Item.berry, x, y), Utils.randInt(2, 8)) };
	}

}
