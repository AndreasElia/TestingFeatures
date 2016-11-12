package game.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import engine.Input;
import game.Camera;
import game.Globals;
import game.Utils;
import game.level.tiles.GrassTile;
import game.level.tiles.MudTile;
import game.level.tiles.Tile;

public class Chunk extends Camera {

	private static final long serialVersionUID = 1L;

	private Input input;

	public Tile[][] tiles;
	public BufferedImage img;
	public boolean imageSet = false;

	public Chunk(Input input, int chunkX, int chunkY) {
		setBounds(x, y, Globals.chunkSize * Globals.tileSize, Globals.chunkSize * Globals.tileSize);

		this.input = input;

		tiles = new Tile[Globals.chunkSize][Globals.chunkSize];
		img = new BufferedImage(Globals.chunkSize * Globals.tileSize, Globals.chunkSize * Globals.tileSize, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < Globals.chunkSize; i++) {
			for (int j = 0; j < Globals.chunkSize; j++) {
				if (Utils.randInt(0, 10) <= 1) {
					tiles[i][j] = new MudTile(Tile.mud, chunkX + i * Globals.tileSize, chunkY + j * Globals.tileSize);
				} else {
					tiles[i][j] = new GrassTile(Tile.grass, chunkX + i * Globals.tileSize, chunkY + j * Globals.tileSize);
				}
			}
		}

		for (int i = 0; i < Globals.chunkSize; i++) {
			for (int j = 0; j < Globals.chunkSize; j++) {
				tiles[i][j].setTransitionId(getId(i, j));
			}
		}
	}

	public void tick(int chunkX, int chunkY) {
		for (int i = 0; i < Globals.chunkSize; i++) {
			for (int j = 0; j < Globals.chunkSize; j++) {
				if (i >= 0 && j >= 0 && i < Globals.chunkSize && j < Globals.chunkSize) {
					if (getTile(i, j) != null && getTile(i, j).isTicks()) {
						getTile(i, j).tick();
					}

					if (getTile(i, j) != null && getTile(i, j).contains(input.getMouse()) && input.isMouseLeft()) {
						getTile(i, j).onDestroyTile(this, chunkX, chunkY, i, j);
						getTile(i, j).setTransitionId(getId(i, j));
						getTile(i, j).setImageSet(false);
						updateNeighbourTransitions(i, j);

						input.setMouseLeft(false);
					}

					if (getTile(i, j) != null && getTile(i, j).contains(input.getMouse()) && input.isMouseRight()) {
						input.setMouseRight(false);
					}
				}
			}
		}
	}

	public void render(Graphics g, int chunkX, int chunkY) {
		g.setColor(Color.RED);

		for (int i = 0; i < Globals.chunkSize; i++) {
			for (int j = 0; j < Globals.chunkSize; j++) {
				tiles[i][j].render(g);
				g.drawRect((chunkX * (Globals.chunkSize * Globals.tileSize)) + i * Globals.tileSize, (chunkY * (Globals.chunkSize * Globals.tileSize)) + j * Globals.tileSize, Globals.tileSize, Globals.tileSize);
			}
		}
	}

	private int getId(int i, int j) {
		int result = 0;

		Tile current = getTile(i, j);

		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;

		boolean topleft = false;
		boolean topright = false;
		boolean bottomleft = false;
		boolean bottomright = false;

		if (j > 0 && typeCheck(getTile(i, j - 1), current))
			up = true;
		if (j < Globals.chunkSize - 1 && typeCheck(getTile(i, j + 1), current))
			down = true;
		if (i >= 1 && typeCheck(getTile(i - 1, j), current))
			left = true;
		if (i < Globals.chunkSize - 1 && typeCheck(getTile(i + 1, j), current))
			right = true;

		if (j >= 1 && i >= 1 && typeCheck(getTile(i - 1, j - 1), current))
			topleft = true;
		if (j >= 1 && i < Globals.chunkSize - 1 && typeCheck(getTile(i + 1, j - 1), current))
			topright = true;
		if (i >= 1 && j < Globals.chunkSize - 1 && typeCheck(getTile(i - 1, j + 1), current))
			bottomleft = true;
		if (j < Globals.chunkSize - 1 && i < Globals.chunkSize - 1 && typeCheck(getTile(i + 1, j + 1), current))
			bottomright = true;

		if (up)
			result += 16;
		if (down)
			result += 64;
		if (left)
			result += 128;
		if (right)
			result += 32;

		if (topleft && up && left)
			result += 8;
		if (topright && up && right)
			result += 1;
		if (bottomleft && down && left)
			result += 4;
		if (bottomright && down && right)
			result += 2;

		return result;
	}

	private boolean typeCheck(Tile t, Tile t2) {
		if (t != null && t2 != null) {
			if (t.getType() == t2.getType()) {
				return true;
			}
			return false;
		}
		return false;
	}

	public void updateNeighbours(int x, int y) {
		if (x > 0 && x < (Globals.chunkSize * Globals.chunkCount) - 1 && y > 0 && y < (Globals.chunkSize * Globals.chunkCount) - 1) {
			if (getTile(x, y).isUpdates()) {
				getTile(x, y).onUpdate(this, x, y);
			}

			if (getTile(x - 1, y).isUpdates()) {
				getTile(x - 1, y).onUpdate(this, x - 1, y);
			}

			if (getTile(x + 1, y).isUpdates()) {
				getTile(x + 1, y).onUpdate(this, x + 1, y);
			}

			if (getTile(x, y - 1).isUpdates() && y > 0) {
				getTile(x, y - 1).onUpdate(this, x, y - 1);
			}

			if (getTile(x, y + 1).isUpdates()) {
				getTile(x, y + 1).onUpdate(this, x, y + 1);
			}
		}
	}

	private void updateNeighbourTransitions(int x, int y) {
		// non-corners
		if (x > 0) {
			tiles[x - 1][y].setTransitionId(getId(x - 1, y));
			tiles[x - 1][y].setImageSet(false);
		}

		if (x < (Globals.chunkSize - 1)) {
			tiles[x + 1][y].setTransitionId(getId(x + 1, y));
			tiles[x + 1][y].setImageSet(false);
		}

		if (y > 0) {
			tiles[x][y - 1].setTransitionId(getId(x, y - 1));
			tiles[x][y - 1].setImageSet(false);
		}

		if (y < (Globals.chunkSize - 1)) {
			tiles[x][y + 1].setTransitionId(getId(x, y + 1));
			tiles[x][y + 1].setImageSet(false);
		}

		// corners
		if (x > 0 && y > 0) {
			tiles[x - 1][y - 1].setTransitionId(getId(x - 1, y - 1));
			tiles[x - 1][y - 1].setImageSet(false);
		}

		if (x < (Globals.chunkSize - 1) && y > 0) {
			tiles[x + 1][y - 1].setTransitionId(getId(x + 1, y - 1));
			tiles[x + 1][y - 1].setImageSet(false);
		}

		if (x > 0 && y < (Globals.chunkSize - 1)) {
			tiles[x - 1][y + 1].setTransitionId(getId(x - 1, y + 1));
			tiles[x - 1][y + 1].setImageSet(false);
		}

		if (x < (Globals.chunkSize - 1) && y < (Globals.chunkSize - 1)) {
			tiles[x + 1][y + 1].setTransitionId(getId(x + 1, y + 1));
			tiles[x + 1][y + 1].setImageSet(false);
		}
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tile type) {
		tiles[x][y] = type;
	}

	public Tile[][] getLevel() {
		return tiles;
	}

}
