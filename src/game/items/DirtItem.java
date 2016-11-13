package game.items;

import java.awt.Graphics;

import engine.gfx.Images;
import game.Globals;
import game.level.tiles.MudTile;
import game.level.tiles.Tile;

public class DirtItem extends Item {

	private static final long serialVersionUID = 1L;

	public DirtItem(int type, int x, int y) {
		super(type, x, y);

		setUpdates(false);
		setName("Dirt Chunks");
		setInfo("For turning dirt holes into mud");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(Images.itemsheet.getSprite(1), x, y, Globals.itemSize * Globals.scale, Globals.itemSize * Globals.scale, null);
	}

	public void onItemLeftClick() {
	}

	public void onItemRightClick() {
	}

	public Tile getTile() {
		return new MudTile(Tile.mud, 0, 0);
	}

}
