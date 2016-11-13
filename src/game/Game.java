package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Input;
import engine.Main;
import game.entities.Player;
import game.items.ItemDrop;
import game.items.ItemPickup;
import game.level.Level;
import game.screens.ScreenManager;
import game.screens.ScreenManager.State;

public class Game {

	private Input input;
	private ScreenManager screen;
	private static Level level;
	private static Player player;

	private boolean paused = false;
	private Rectangle resumeRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2), 130, 40);
	private String resumeText = "Resume";

	private Rectangle exitRect = new Rectangle((Main.WIDTH / 2) - 65, (Main.HEIGHT / 2) + 50, 130, 40);
	private String exitText = "Back to Menu";

	// cX and cY are to keep track of the Camera position
	public static int cX;
	public static int cY;

	// pX and pY are for the initial (safe) spawn location
	public static int pX;
	public static int pY;

	// playerSet is to check if the spawn location was set
	private boolean playerSet = false;

	public static List<ItemDrop> itemDrops = new ArrayList<ItemDrop>();
	public static List<ItemPickup> itemPickups = new ArrayList<ItemPickup>();

	// other colours: 145,65,31, 212,180,74, 179,105,55

	public Game(Input input, ScreenManager screen) {
		this.input = input;
		this.screen = screen;

		level = new Level(input);
	}

	public void tick() {
		if (level.screenGen.running) {
			level.screenGen.tick();
		}

		if (level.finishedGeneration) {
			if (input.getKey("F1") && input.isJustHeld("F1")) {
				level = new Level(input);
			}

			if (input.getKey("F3") && input.isJustHeld("F3")) {
				Globals.debug = !Globals.debug;
			}

			if (input.getKey("Escape") && input.isJustHeld("Escape") && player.isInventoryOpen()) {
				player.setInventoryOpen(false);
			}

			if (input.getKey("Escape") && input.isJustHeld("Escape") && !player.isInventoryOpen()) {
				paused = !paused;
			}

			if (!playerSet) {
				player = new Player(input, pX, pY);
				playerSet = true;
			}

			if (input.getKey("E") && input.isJustHeld("E")) {
				player.setInventoryOpen(!player.isInventoryOpen());
			}

			if (!paused && player != null && !player.isInventoryOpen()) {
				if (!paused) {
					level.tick();
				}

				player.tick(level);

				for (int e = 0; e < itemDrops.size(); e++) {
					itemDrops.get(e).getItem().moveWith();
					itemDrops.get(e).getItem().tick();

					if (itemDrops.get(e).getItem().intersects(player)) {
						itemPickups.add(new ItemPickup(itemDrops.get(e).getItem().getName() + (itemDrops.get(e).getAmount() > 1 ? " +" + itemDrops.get(e).getAmount() : ""), itemDrops.get(e).getItem().getBounds().x, itemDrops.get(e).getItem().getBounds().y));
						player.getHotbar().addItem(itemDrops.get(e));
						itemDrops.remove(e);
					}
				}

				for (int e = 0; e < itemPickups.size(); e++) {
					itemPickups.get(e).moveWith();
					itemPickups.get(e).tick();

					if (itemPickups.get(e).isRemovalRequired()) {
						itemPickups.remove(e);
					}
				}

				// Camera
				for (int x = (Game.cX / Globals.tileSize); x < (Game.cX / Globals.tileSize) + (Main.WIDTH / Globals.tileSize) + 2; x++) {
					for (int y = (Game.cY / Globals.tileSize); y < (Game.cY / Globals.tileSize) + (Main.HEIGHT / Globals.tileSize) + 2; y++) {
						if (x >= 0 && y >= 0 && x < Level.mapSize && y < Level.mapSize) {
							if (level.getLevel()[x][y] != null) {
								level.getLevel()[x][y].moveWith();
							}
						}
					}
				}

				for (int x = (Game.cX / Globals.tileSize); x < (Game.cX / Globals.tileSize) + (Main.WIDTH / Globals.tileSize) + 2; x++) {
					for (int y = (Game.cY / Globals.tileSize); y < (Game.cY / Globals.tileSize) + (Main.HEIGHT / Globals.tileSize) + 2; y++) {
						if (x >= 0 && y >= 0 && x < Level.mapSize && y < Level.mapSize) {
							if (level.getScenery()[x][y] != null) {
								level.getScenery()[x][y].moveWith();
							}
						}
					}
				}
			}

			if (paused) {
				if (resumeRect.contains(input.getMouse()) && input.isMouseLeft()) {
					paused = !paused;
					input.setMouseLeft(false);
				}

				if (exitRect.contains(input.getMouse()) && input.isMouseLeft()) {
					screen.setState(State.MENU);
					input.setMouseLeft(false);
				}
			}
		}
	}

	public void render(Graphics g) {
		if (level.screenGen.running) {
			level.screenGen.render(g);
		}

		if (level.finishedGeneration) {
			level.render(g);

			for (int i = 0; i < itemDrops.size(); i++) {
				itemDrops.get(i).getItem().render(g);
			}

			if (player != null) {
				player.render(g);
			}

			for (int e = 0; e < itemPickups.size(); e++) {
				itemPickups.get(e).render(g);
			}

			if (paused) {
				g.setColor(new Color(255, 255, 255, 177));
				g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

				g.setFont(Utils.menuButtonFont);

				// resume button
				g.setColor(new Color(0, 0, 0, 177));
				g.fillRect(resumeRect.x, resumeRect.y, resumeRect.width, resumeRect.height);

				g.setColor(new Color(255, 255, 255));
				g.drawString(resumeText, ((Main.WIDTH - g.getFontMetrics().stringWidth(resumeText)) / 2), resumeRect.y + 24);

				// exit button
				g.setColor(new Color(0, 0, 0, 177));
				g.fillRect(exitRect.x, exitRect.y, exitRect.width, exitRect.height);

				g.setColor(new Color(255, 255, 255));
				g.drawString(exitText, ((Main.WIDTH - g.getFontMetrics().stringWidth(exitText)) / 2), exitRect.y + 24);
			}

			if (Globals.debug) {
				g.setColor(new Color(0, 0, 0, 177));
				g.fillRect(10, 10, 240, 110);

				g.setColor(Color.WHITE);
				g.setFont(Utils.debugFont);
				g.drawString("DEBUG MODE (F3 - toggle)", 16, 24);
				g.drawString("Dev. v" + Globals.version, 16, 38);
				g.drawString("FPS/TPS: " + Main.getFPS() + "/" + Main.getTPS(), 16, 52);
				g.drawString("Entities: " + "n/a", 16, 66);
				g.drawString("Biome: " + "n/a", 16, 80);
				g.drawString("Tile: " + level.getTile((int) (input.getMouse().x + Game.cX) / Globals.tileSize, (int) (input.getMouse().y + Game.cY) / Globals.tileSize).getName() + ", Transition ID: " + level.getTile((int) (input.getMouse().x + Game.cX) / Globals.tileSize, (int) (input.getMouse().y + Game.cY) / Globals.tileSize).getTransitionId(), 16, 94);
				g.drawString("Scenery: " + ((level.getScenery((int) (input.getMouse().x + Game.cX) / Globals.tileSize, (int) (input.getMouse().y + Game.cY) / Globals.tileSize) == null) ? "Empty" : level.getScenery((int) (input.getMouse().x + Game.cX) / Globals.tileSize, (int) (input.getMouse().y + Game.cY) / Globals.tileSize).getName()), 16, 108);
			}
		}
	}

	public static boolean addItemDrop(ItemDrop[] item) {
		if (item != null) {
			for (ItemDrop i : item) {
				itemDrops.add(i);
			}

			return true;
		}

		return false;
	}

	public static void removeDrop(ItemDrop item) {
		for (int i = 0; i < itemDrops.size(); i++) {
			if (itemDrops.get(i) == item) {
				itemDrops.remove(i);
			}
		}
	}

	public static Player getPlayer() {
		return player;
	}

}
