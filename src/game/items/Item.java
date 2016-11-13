package game.items;

import game.Camera;
import game.Globals;
import game.level.scenery.Scenery;
import game.level.tiles.Tile;

import java.awt.Graphics;

public abstract class Item extends Camera {

	private static final long serialVersionUID = 1L;

	public static final int grass = 0;
	public static final int dirt = 1;
	public static final int log = 5;
	public static final int stone = 6;
	public static final int cherry = 7;
	public static final int bush = 8;
	public static final int berry = 9;

	protected int type;
	protected String name;
	protected String info;
	protected Tile tile;

	protected boolean updates = false;

	public Item(int type, int x, int y) {
		setBounds(x, y, Globals.itemSize, Globals.itemSize);

		this.type = type;
		this.x = x;
		this.y = y;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void onItemLeftClick();

	public abstract void onItemRightClick();

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isUpdates() {
		return updates;
	}

	public void setUpdates(boolean updates) {
		this.updates = updates;
	}

	public Tile getTile() {
		return null;
	}

	public Scenery getScenery() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
