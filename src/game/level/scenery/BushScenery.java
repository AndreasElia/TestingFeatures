package game.level.scenery;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.items.BushItem;
import game.items.Item;
import game.items.ItemDrop;
import game.level.Level;

public class BushScenery extends Scenery {

	private static final long serialVersionUID = 1L;

	public BushScenery(int type, int x, int y) {
		super(type, x, y);

		setPassable(false);
		setName("Bush");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.othertiles.getSprite(11), x, y, Images.othertiles.getSprite(1).getWidth() * Globals.scale, Images.othertiles.getSprite(1).getHeight() * Globals.scale, null);
	}

	@Override
	public void onDestroyScenery(Level level, int x, int y) {
		level.setScenery(x, y, null);
	}

	@Override
	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return new ItemDrop[] { new ItemDrop(new BushItem(Item.bush, x, y), 1) };
	}

}
