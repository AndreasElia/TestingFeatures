package game.level.scenery;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.Utils;
import game.items.CherryItem;
import game.items.Item;
import game.items.ItemDrop;
import game.level.Level;

public class CherryBushScenery extends BushScenery {

	private static final long serialVersionUID = 1L;

	public CherryBushScenery(int type, int x, int y) {
		super(type, x, y);

		setPassable(false);
		setName("Cherry Bush");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.othertiles.getSprite(1), x, y, Images.othertiles.getSprite(1).getWidth() * Globals.scale, Images.othertiles.getSprite(1).getHeight() * Globals.scale, null);
	}

	@Override
	public void onDestroyScenery(Level level, int x, int y) {
		level.setScenery(x, y, new BushScenery(Scenery.bush, x * Globals.tileSize, y * Globals.tileSize));
	}

	@Override
	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return new ItemDrop[] { new ItemDrop(new CherryItem(Item.cherry, x, y), Utils.randInt(2, 8)) };
	}

}
