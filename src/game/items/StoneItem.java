package game.items;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.level.tiles.StoneTile;
import game.level.tiles.Tile;

public class StoneItem extends Item {

	private static final long serialVersionUID = 1L;

	public StoneItem(int type, int x, int y) {
		super(type, x, y);

		setUpdates(false);
		setName("Stone Chunks");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.itemsheet.getSprite(6), x, y, Globals.itemSize * Globals.scale, Globals.itemSize * Globals.scale, null);
	}

	public void onItemLeftClick() {
	}

	public void onItemRightClick() {
	}

	public Tile getTile() {
		return new StoneTile(Tile.stone, 0, 0);
	}

}
