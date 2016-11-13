package game.level.scenery;

import game.Camera;
import game.Globals;
import game.items.ItemDrop;
import game.level.Level;

import java.awt.Graphics;

public abstract class Scenery extends Camera {

	private static final long serialVersionUID = 1L;

	// Scenery values range from 128 - 256
	public static final int bush = 128;
	public static final int cherryBush = 129;
	public static final int berryBush = 130;

	protected int type;
	protected String name;

	protected boolean ticks = false;
	protected boolean updates = false;
	protected boolean interactive = false;
	protected boolean canInteract = true;
	protected boolean passable = true;

	protected int transitionId = 0;
	protected int sheetOffsetX = 0;
	protected int sheetOffsetY = 0;

	public Scenery(int type, int x, int y) {
		setBounds(x, y, Globals.tileSize, Globals.tileSize);

		this.type = type;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public void onUpdate(Level level, int x, int y) {
	}

	public void onClick(Level level, int x, int y) {
	}

	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return null;
	}

	public void onDestroyScenery(Level level, int x, int y) {
	}

	public int getType() {
		return type;
	}

	public boolean isTicks() {
		return ticks;
	}

	public void setTicks(boolean ticks) {
		this.ticks = ticks;
	}

	public boolean isUpdates() {
		return updates;
	}

	public void setUpdates(boolean updates) {
		this.updates = updates;
	}

	public boolean isInteractive() {
		return interactive;
	}

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	public boolean isCanInteract() {
		return canInteract;
	}

	public void setCanInteract(boolean canInteract) {
		this.canInteract = canInteract;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setOffset(int x, int y) {
		this.sheetOffsetX = x;
		this.sheetOffsetY = y;
	}

	public int getOffsetX() {
		return sheetOffsetX;
	}

	public int getOffsetY() {
		return sheetOffsetY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
