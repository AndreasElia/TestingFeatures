package game.level.tiles;

import game.Globals;
import game.level.Level;

import java.awt.Color;
import java.awt.Graphics;

public class RedstoneTile extends Tile {

	private static final long serialVersionUID = 1L;

	public RedstoneTile(int type, int x, int y) {
		super(type, x, y);

		updates = true;
	}

	public void tick() {
	}

	@Override
	public void onUpdate(Level level, int x, int y) {
		if (level.getTile(x - 1, y).getType() == Tile.redstone && level.getTile(x, y).getPowerState() == PowerStates.POWERED) {
			level.getTile(x - 1, y).setPowerState(PowerStates.POWERED);
		} else if (level.getTile(x + 1, y).getType() == Tile.redstone && level.getTile(x, y).getPowerState() == PowerStates.POWERED) {
			level.getTile(x + 1, y).setPowerState(PowerStates.POWERED);
		} else if (level.getTile(x, y - 1).getType() == Tile.redstone && level.getTile(x, y).getPowerState() == PowerStates.POWERED) {
			level.getTile(x, y - 1).setPowerState(PowerStates.POWERED);
		} else if (level.getTile(x, y + 1).getType() == Tile.redstone && level.getTile(x, y).getPowerState() == PowerStates.POWERED) {
			level.getTile(x, y + 1).setPowerState(PowerStates.POWERED);
		}

		if ((level.getTile(x - 1, y) instanceof PowerTile)) {
			level.getTile(x - 1, y).onUpdate(level, x - 1, y);
			setPowerState(PowerStates.POWERED);
		} else if ((level.getTile(x + 1, y) instanceof PowerTile)) {
			level.getTile(x + 1, y).onUpdate(level, x + 1, y);
			setPowerState(PowerStates.POWERED);
		} else if ((level.getTile(x, y - 1) instanceof PowerTile)) {
			level.getTile(x, y - 1).onUpdate(level, x, y - 1);
			setPowerState(PowerStates.POWERED);
		} else if ((level.getTile(x, y + 1) instanceof PowerTile)) {
			level.getTile(x, y + 1).onUpdate(level, x, y + 1);
			setPowerState(PowerStates.POWERED);
		}

		// draw |
		if ((level.getTile(x, y - 1).getType() == Tile.redstone) && !(level.getTile(x - 1, y).getType() == Tile.redstone) && !(level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWN;
		}

		// draw |
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && !(level.getTile(x - 1, y).getType() == Tile.redstone) && !(level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWN;
		}

		// draw -
		else if (!(level.getTile(x, y + 1).getType() == Tile.redstone) && !(level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.LEFTRIGHT;
		}

		// draw -
		else if (!(level.getTile(x, y + 1).getType() == Tile.redstone) && !(level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.LEFTRIGHT;
		}

		// draw +
		else if ((level.getTile(x - 1, y).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone) && (level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x, y + 1).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.PLUS;
		}

		// draw |-
		else if ((level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWNRIGHT;
		}

		// draw -|
		else if ((level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWNLEFT;
		}

		// draw _|_
		else if ((level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPLEFTRIGHT;
		}

		// draw -|-
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.DOWNLEFTRIGHT;
		}

		// draw |_
		else if ((level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPRIGHT;
		}

		// draw _|
		else if ((level.getTile(x, y - 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPLEFT;
		}

		// draw |-
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.DOWNRIGHT;
		}

		// draw -|
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.DOWNLEFT;
		}

		// draw |-
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x + 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWNRIGHT;
		}

		// draw -|
		else if ((level.getTile(x, y + 1).getType() == Tile.redstone) && (level.getTile(x - 1, y).getType() == Tile.redstone)) {
			redstoneState = RedstoneStates.UPDOWNLEFT;
		}

		// draw .
		else {
			redstoneState = RedstoneStates.DOT;
		}
	}

	public void render(Graphics g) {
		if (getPowerState() == PowerStates.POWERED) {
			g.setColor(new Color(255, 255, 255));
		} else {
			g.setColor(new Color(177, 13, 13));
		}

		// draw |
		if (redstoneState == RedstoneStates.UPDOWN) {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize);
		}

		// draw -
		else if (redstoneState == RedstoneStates.LEFTRIGHT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize, 4);
		}

		// draw +
		else if (redstoneState == RedstoneStates.PLUS) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize);
		}

		// draw |-
		else if (redstoneState == RedstoneStates.UPDOWNRIGHT) {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize);
		}

		// draw -|
		else if (redstoneState == RedstoneStates.UPDOWNLEFT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize);
		}

		// draw _|_
		else if (redstoneState == RedstoneStates.UPLEFTRIGHT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize / 2 + 2);
		}

		// draw -|-
		else if (redstoneState == RedstoneStates.DOWNLEFTRIGHT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, Globals.tileSize / 2 + 2);
		}

		// draw |_
		else if (redstoneState == RedstoneStates.UPRIGHT) {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize / 2 + 2);
		}

		// draw _|
		else if (redstoneState == RedstoneStates.UPLEFT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y, 4, Globals.tileSize / 2 + 2);
		}

		// draw |-
		else if (redstoneState == RedstoneStates.DOWNRIGHT) {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, Globals.tileSize / 2 + 2);
		}

		// draw -|
		else if (redstoneState == RedstoneStates.DOWNLEFT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, Globals.tileSize / 2 + 2);
		}

		// draw |-
		else if (redstoneState == RedstoneStates.UPDOWNRIGHT) {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, Globals.tileSize / 2 + 2);
		}

		// draw -|
		else if (redstoneState == RedstoneStates.UPDOWNLEFT) {
			g.fillRect(x, y + (Globals.tileSize / 2) - 2, Globals.tileSize / 2 + 2, 4);
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, Globals.tileSize / 2 + 2);
		}

		// draw .
		else {
			g.fillRect(x + (Globals.tileSize / 2) - 2, y + (Globals.tileSize / 2) - 2, 4, 4);
		}
	}

}
