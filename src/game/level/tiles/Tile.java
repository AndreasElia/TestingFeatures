package game.level.tiles;

import engine.gfx.Images;
import engine.gfx.SpriteSheet;
import game.Camera;
import game.Globals;
import game.items.ItemDrop;
import game.level.Level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile extends Camera {

	private static final long serialVersionUID = 1L;

	// Scenery values range from 0 - 127
	public static final int grass = 0;
	public static final int stone = 1;
	public static final int power = 2;
	public static final int water = 3;
	public static final int redstone = 4;
	public static final int lampOn = 5;
	public static final int lampOff = 6;
	public static final int dirt = 7;
	public static final int snow = 8;
	public static final int stoneWall = 9;
	public static final int stoneFloor = 10;
	public static final int mud = 11;

	protected int type;
	protected String name;

	protected boolean ticks = false;
	protected boolean updates = false;
	protected boolean interactive = false;
	protected boolean canInteract = true;
	protected boolean passable = true;

	private int transitionId = 0;
	protected int sheetOffsetX = 0;
	protected int sheetOffsetY = 0;

	protected enum PowerStates {
		UNPOWERED, POWERED
	}

	protected enum RedstoneStates {
		DOT, LEFTRIGHT, UPDOWN, UPRIGHT, UPLEFT, PLUS, UPDOWNRIGHT, UPDOWNLEFT, UPLEFTRIGHT, DOWNLEFTRIGHT, DOWNRIGHT, DOWNLEFT
	}

	protected RedstoneStates redstoneState = RedstoneStates.DOT;
	protected PowerStates powerState = PowerStates.UNPOWERED;

	protected SpriteSheet tileSheet;
	protected BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
	protected boolean imageSet = false;

	public Tile(int type, int x, int y) {
		setBounds(x, y, Globals.tileSize, Globals.tileSize);

		this.type = type;
	}

	public void tick() {
	}

	public void render(Graphics g) {
		if (!isImageSet() && getOffsetX() >= 0 && getOffsetY() >= 0) {
			Graphics gmerge = img.getGraphics();
			int[] tiles = TilePresets.getTiles(getTransitionId());
			int x1 = 0;
			int y1 = 0;

			for (int i = 0; i < 4; i++) {
				// 7, 8, 13, 14 = middle 4 tiles so...

				gmerge.drawImage(getTileSheet().getSprite(tiles[i]), (x1 * 8), (y1 * 8), 8, 8, null);

				x1 += 1;

				if (x1 == 2) {
					x1 = 0;
					y1 += 1;
				}
			}
			gmerge.dispose();
			setImageSet(true);
		}

		g.drawImage(img, x, y, width, height, null);

		// g.setColor(Color.WHITE);
		// g.drawString("" + transitionId, x, y+g.getFontMetrics().getHeight());
	}

	public void onUpdate(Level level, int x, int y) {
	}

	public void onDestroyTile(Level level, int x, int y) {
	}

	public ItemDrop[] onDestroy(Level level, int x, int y) {
		return null;
	}

	public int getType() {
		return type;
	}

	public RedstoneStates getRedstoneState() {
		return redstoneState;
	}

	public void setRedstoneState(RedstoneStates redstoneState) {
		this.redstoneState = redstoneState;
	}

	public PowerStates getPowerState() {
		return powerState;
	}

	public void setPowerState(PowerStates powerState) {
		this.powerState = powerState;
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
		setTileSheet(new SpriteSheet(Images.tilesheet.getSheet().getSubimage(x * 8, y * 8, 48, 32), 8, 8));
	}

	public int getOffsetX() {
		return sheetOffsetX;
	}

	public int getOffsetY() {
		return sheetOffsetY;
	}

	public boolean isImageSet() {
		return imageSet;
	}

	public void setImageSet(boolean imageSet) {
		this.imageSet = imageSet;
	}

	public SpriteSheet getTileSheet() {
		return tileSheet;
	}

	public void setTileSheet(SpriteSheet tileSheet) {
		this.tileSheet = tileSheet;
	}

	public int getTransitionId() {
		return transitionId;
	}

	public void setTransitionId(int transitionId) {
		this.transitionId = transitionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
