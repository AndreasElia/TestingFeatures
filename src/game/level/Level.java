package game.level;

import java.awt.Graphics;

import engine.Input;
import engine.Main;
import game.Game;
import game.Globals;
import game.Utils;
import game.level.scenery.BerryBushScenery;
import game.level.scenery.CherryBushScenery;
import game.level.scenery.Scenery;
import game.level.tiles.DirtTile;
import game.level.tiles.GrassTile;
import game.level.tiles.StoneTile;
import game.level.tiles.Tile;
import game.level.tiles.WaterTile;
import game.screens.ScreenGeneration;

public class Level {

	private Input input;

	public static int mapSize = Globals.mapSize;

	public Tile[][] level = new Tile[mapSize][mapSize];
	public Scenery[][] scenery = new Scenery[mapSize][mapSize];

	public ScreenGeneration screenGen;
	public boolean finishedGeneration = false;

    private int[][] topMap; // set from server

	public Level(Input input) {
		this.input = input;

		screenGen = new ScreenGeneration(this);
	}

	public void tick() {
		for (int x = (Game.cX / Globals.tileSize); x < (Game.cX / Globals.tileSize) + (Main.WIDTH / Globals.tileSize) + 2; x++) {
			for (int y = (Game.cY / Globals.tileSize); y < (Game.cY / Globals.tileSize) + (Main.HEIGHT / Globals.tileSize) + 2; y++) {
				if (x >= 0 && y >= 0 && x < mapSize && y < mapSize) {
					if (getTile(x, y) != null && getTile(x, y).isTicks()) {
						getTile(x, y).tick();
					}

					if (getScenery(x, y) != null) {
						getScenery(x, y).tick();
					}

					if (getScenery(x, y) != null && getScenery(x, y).contains(input.getMouse()) && input.isMouseLeft()) {
						Game.addItemDrop(getScenery(x, y).onDestroy(this, x * Globals.tileSize, y * Globals.tileSize));
						getScenery(x, y).onDestroyScenery(this, x, y);

						input.setMouseLeft(false);
					}

					if (getTile(x, y) != null && getTile(x, y).contains(input.getMouse()) && input.isMouseLeft()) {
						Game.addItemDrop(getTile(x, y).onDestroy(this, x * Globals.tileSize, y * Globals.tileSize));
						getTile(x, y).onDestroyTile(this, x, y);
						getTile(x, y).setTransitionId(getId(x, y));
						getTile(x, y).setImageSet(false);
						updateNeighbourTransitions(x, y);

						input.setMouseLeft(false);
					}

					if (getTile(x, y) != null && getTile(x, y).contains(input.getMouse()) && input.isMouseRight()) {
						if (getScenery(x, y) == null && Game.getPlayer().getHotbar().getSelectedItemTile() != null && Game.getPlayer().getHotbar().getSelectedItemAmount() > 0) {
							Tile scenery = Game.getPlayer().getHotbar().getSelectedItemTile();
							scenery.setBounds(getTile(x, y).getBounds());
							scenery.setTransitionId(getId(x, y));
							setTile(x, y, scenery);
							updateNeighbourTransitions(x, y);
							Game.getPlayer().getHotbar().setSelectedItemAmount(Game.getPlayer().getHotbar().getSelectedItemAmount() - 1);
						}

						if (Game.getPlayer().getHotbar().getSelectedItemScenery() != null && Game.getPlayer().getHotbar().getSelectedItemAmount() > 0) {
							Scenery scenery = Game.getPlayer().getHotbar().getSelectedItemScenery();
							scenery.setBounds(getTile(x, y).getBounds());
							setScenery(x, y, scenery);
							Game.getPlayer().getHotbar().setSelectedItemAmount(Game.getPlayer().getHotbar().getSelectedItemAmount() - 1);
						}
					}
				}
			}
		}

		if (screenGen.running) {
			screenGen.tick();
		}
	}

	public void render(Graphics g) {
		if (finishedGeneration) {
			for (int x = (Game.cX / Globals.tileSize); x < (Game.cX / Globals.tileSize) + (Main.WIDTH / Globals.tileSize) + 2; x++) {
				for (int y = (Game.cY / Globals.tileSize); y < (Game.cY / Globals.tileSize) + (Main.HEIGHT / Globals.tileSize) + 2; y++) {
					if (x >= 0 && y >= 0 && x < mapSize && y < mapSize) {
						if (getTile(x, y) != null) {
							getTile(x, y).render(g);
						}

						if (getScenery(x, y) != null) {
							getScenery(x, y).render(g);
						}
					}
				}
			}
		}

		if (screenGen.running) {
			screenGen.render(g);
		}
	}

	public void generateMap() {
		int[][] map = topMap;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				switch (map[i][j]) {
				case Tile.grass:
					level[i][j] = new GrassTile(Tile.grass, i * Globals.tileSize, j * Globals.tileSize);
					break;
				case Tile.dirt:
					level[i][j] = new DirtTile(Tile.dirt, i * Globals.tileSize, j * Globals.tileSize);
					break;
				case Tile.stone:
					level[i][j] = new StoneTile(Tile.stone, i * Globals.tileSize, j * Globals.tileSize);
					break;
				case Scenery.bush:
					level[i][j] = new GrassTile(Tile.grass, i * Globals.tileSize, j * Globals.tileSize);
					if (Utils.randomBoolean()) {
						scenery[i][j] = new CherryBushScenery(Scenery.cherryBush, i * Globals.tileSize, j * Globals.tileSize);
					} else {
						scenery[i][j] = new BerryBushScenery(Scenery.berryBush, i * Globals.tileSize, j * Globals.tileSize);
					}
					break;
				default:
					level[i][j] = new WaterTile(Tile.water, i * Globals.tileSize, j * Globals.tileSize);
					break;
				}
				getTile(i, j).setTransitionId(getId(i, j));
			}
		}

		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				level[i][j].setTransitionId(getId(i, j));

				// spawn the camera at grass tile
				if (level[i][j] != null && level[i][j].getType() == Tile.grass && (scenery[i][j] != null && scenery[i][j].isPassable())) {
					if (Utils.randInt(7, 100) <= 7) {
						Game.cX = (i * Globals.tileSize) - (Main.WIDTH / 2);
						Game.cY = (j * Globals.tileSize) - (Main.HEIGHT / 2);
						Game.pX = ((Main.WIDTH / 2) - 16);
						Game.pY = ((Main.HEIGHT / 2) - 16);
						setTile(i, j, new StoneTile(Tile.stone, i * Globals.tileSize, j * Globals.tileSize));
					}
				}
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

		if (j >= 1 && typeCheck(getTile(i, j - 1), current))
			up = true;
		if (j < mapSize - 1 && typeCheck(getTile(i, j + 1), current))
			down = true;
		if (i >= 1 && typeCheck(getTile(i - 1, j), current))
			left = true;
		if (i < mapSize - 1 && typeCheck(getTile(i + 1, j), current))
			right = true;

		if (j >= 1 && i >= 1 && typeCheck(getTile(i - 1, j - 1), current))
			topleft = true;
		if (j >= 1 && i < mapSize - 1 && typeCheck(getTile(i + 1, j - 1), current))
			topright = true;
		if (i >= 1 && j < mapSize - 1 && typeCheck(getTile(i - 1, j + 1), current))
			bottomleft = true;
		if (j < mapSize - 1 && i < mapSize - 1 && typeCheck(getTile(i + 1, j + 1), current))
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
		if (x > 0 && x < mapSize - 1 && y > 0 && y < mapSize - 1) {
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
			level[x - 1][y].setTransitionId(getId(x - 1, y));
			level[x - 1][y].setImageSet(false);
		}

		if (x < (mapSize - 1)) {
			level[x + 1][y].setTransitionId(getId(x + 1, y));
			level[x + 1][y].setImageSet(false);
		}

		if (y > 0) {
			level[x][y - 1].setTransitionId(getId(x, y - 1));
			level[x][y - 1].setImageSet(false);
		}

		if (y < (mapSize - 1)) {
			level[x][y + 1].setTransitionId(getId(x, y + 1));
			level[x][y + 1].setImageSet(false);
		}

		// corners
		if (x > 0 && y > 0) {
			level[x - 1][y - 1].setTransitionId(getId(x - 1, y - 1));
			level[x - 1][y - 1].setImageSet(false);
		}

		if (x < (mapSize - 1) && y > 0) {
			level[x + 1][y - 1].setTransitionId(getId(x + 1, y - 1));
			level[x + 1][y - 1].setImageSet(false);
		}

		if (x > 0 && y < (mapSize - 1)) {
			level[x - 1][y + 1].setTransitionId(getId(x - 1, y + 1));
			level[x - 1][y + 1].setImageSet(false);
		}

		if (x < (mapSize - 1) && y < (mapSize - 1)) {
			level[x + 1][y + 1].setTransitionId(getId(x + 1, y + 1));
			level[x + 1][y + 1].setImageSet(false);
		}
	}

	public Tile getTile(int x, int y) {
		return level[x][y];
	}

	public void setTile(int x, int y, Tile type) {
		level[x][y] = type;
	}

	public Tile[][] getLevel() {
		return level;
	}

	public Scenery getScenery(int x, int y) {
		return scenery[x][y];
	}

	public Scenery[][] getScenery() {
		return scenery;
	}

	public void setScenery(int x, int y, Scenery type) {
		scenery[x][y] = type;
	}

}
