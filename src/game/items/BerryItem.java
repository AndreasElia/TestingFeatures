package game.items;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.level.tiles.Tile;

public class BerryItem extends Item {

	private static final long serialVersionUID = 1L;

	public BerryItem(int type, int x, int y) {
		super(type, x, y);

		setUpdates(false);
		setName("Berries");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.itemsheet.getSprite(9), x, y, Globals.itemSize * Globals.scale, Globals.itemSize * Globals.scale, null);
	}

	public void onItemLeftClick() {
	}

	public void onItemRightClick() {
	}

	public Tile getTile() {
		return null;
	}

}
