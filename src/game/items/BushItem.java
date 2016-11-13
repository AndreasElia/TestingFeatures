package game.items;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.level.scenery.BushScenery;
import game.level.scenery.Scenery;

public class BushItem extends Item {

	private static final long serialVersionUID = 1L;

	public BushItem(int type, int x, int y) {
		super(type, x, y);

		setUpdates(false);
		setName("Bush");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.itemsheet.getSprite(8), x, y, Globals.itemSize * Globals.scale, Globals.itemSize * Globals.scale, null);
	}

	public void onItemLeftClick() {
	}

	public void onItemRightClick() {
	}

	@Override
	public Scenery getScenery() {
		return new BushScenery(Scenery.bush, 0, 0);
	}

}
