package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import engine.Input;
import engine.Main;
import engine.gfx.Animation;
import engine.gfx.Images;
import game.Game;
import game.Globals;
import game.Utils;
import game.gui.Container;
import game.gui.Hotbar;
import game.gui.Inventory;
import game.items.Item;
import game.level.Level;

public class Player extends Entity {

	private static final long serialVersionUID = 1L;

	private Input input;

	private boolean mounted = false;

	private int up = 1;
	private int down = 0;
	private int left = 3;
	private int right = 2;

	private int facing = down;

	private Hotbar hotbar;
	private int[] hotbarKeys = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private int hotbarSlot;
	private Item mouseItem;
	private int mouseItemAmount;

	private Inventory inventory;
	private boolean inventoryOpen = false;
	private Container container;

	public Player(Input input, int startX, int startY) {
		setBounds(startX, startY, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale);
		this.input = input;
		this.x = startX;
		this.y = startY;

		setSpeed(4);

		hotbar = new Hotbar(input, 9);
		inventory = new Inventory(input, 5, 4);
	}

	public void tick(Level level) {
		hotbar.tick();

		if (input.getWheelRotation() < 0) {
			changeHotbarSlot(hotbarSlot++);
			input.setWheelRotation(0);
		} else if (input.getWheelRotation() > 0) {
			changeHotbarSlot(hotbarSlot--);
			input.setWheelRotation(0);
		}

		for (int hb = 0; hb < hotbarKeys.length; hb++) {
			if (input.getKey(Integer.toString(hotbarKeys[hb])) && input.isJustHeld(Integer.toString(hotbarKeys[hb]))) {
				changeHotbarSlot(hotbarKeys[hb] - 1);
			}
		}

		if (inventoryOpen) {
			inventory.tick();
		}

		/*
		 * NOT WORKING BECAUSE IT IS INTERACTING WITH TILES UNDER THE HOTBAR
		 */
		for (int i = 0; i < hotbar.getSlotCount(); i++) {
			if (hotbar.getSlots().get(i).contains(input.getMouse()) && input.isMouseLeft()) {
				System.out.println(123);
				hotbar.getSlots().get(i).setCurrentItem(null);
				hotbar.getSlots().get(i).setCurrentAmount(0);
				input.setMouseLeft(false);
			}
		}

		int collisionScaleUp = -1;
		int collisionScaleDown = 1;

		boolean flag = !mounted && input.getKey("W") && Game.cY > 0 && y > 0 && y < (Main.HEIGHT / 2) - (height / 2) && level.getTile((int) ((x + Game.cX) / Globals.tileSize), (int) (((y + Game.cY) + collisionScaleUp) / Globals.tileSize)).isPassable();
		boolean flag2 = !mounted && input.getKey("S") && Game.cY < (Level.mapSize * Globals.tileSize) - Main.HEIGHT && y > (Main.HEIGHT / 2) - (height / 2) && level.getTile((int) ((x + Game.cX) / Globals.tileSize), (int) ((y + Game.cY + height + collisionScaleDown) / Globals.tileSize)).isPassable();

		boolean flag3 = !mounted && input.getKey("W") && y > 0 && level.getTile((int) ((x + Game.cX) / Globals.tileSize), (int) ((y + Game.cY + collisionScaleUp) / Globals.tileSize)).isPassable();
		boolean flag4 = !mounted && input.getKey("S") && y < Main.HEIGHT - (height / 2) && level.getTile((int) ((x + Game.cX) / Globals.tileSize), (int) ((y + Game.cY + height + collisionScaleDown) / Globals.tileSize)).isPassable();

		if (flag) {
			Game.cY -= getSpeed();
			facing = up;
			Animation.walkUp.play();
		} else if (flag2) {
			Game.cY += getSpeed();
			facing = down;
			Animation.walkDown.play();
		} else {
			if (flag3) {
				y -= getSpeed();
				facing = up;
				Animation.walkUp.play();
			} else if (flag4) {
				y += getSpeed();
				facing = down;
				Animation.walkDown.play();
			}
		}

		boolean flag5 = !mounted && input.getKey("A") && Game.cX > 0 && x > 0 && x < (Main.WIDTH / 2) - (width / 2) && level.getTile((int) ((x + Game.cX + collisionScaleUp) / Globals.tileSize), (int) ((y + Game.cY) / Globals.tileSize)).isPassable();
		boolean flag6 = !mounted && input.getKey("D") && Game.cX < (Level.mapSize * Globals.tileSize) - Main.WIDTH && x > (Main.WIDTH / 2) - (width / 2) && level.getTile((int) ((x + Game.cX + width + collisionScaleDown) / Globals.tileSize), (int) ((y + Game.cY) / Globals.tileSize)).isPassable();

		boolean flag7 = !mounted && input.getKey("A") && x > 0 && level.getTile((int) ((x + Game.cX + collisionScaleUp) / Globals.tileSize), (int) ((y + Game.cY) / Globals.tileSize)).isPassable();
		boolean flag8 = !mounted && input.getKey("D") && x < Main.WIDTH - (width / 2) && level.getTile((int) ((x + Game.cX + width + collisionScaleDown) / Globals.tileSize), (int) ((y + Game.cY) / Globals.tileSize)).isPassable();

		if (flag5) {
			Game.cX -= getSpeed();
			facing = left;
			Animation.walkLeft.play();
		} else if (flag6) {
			Game.cX += getSpeed();
			facing = right;
			Animation.walkRight.play();
		} else {
			if (flag7) {
				x -= getSpeed();
				facing = left;
				Animation.walkLeft.play();
			} else if (flag8) {
				x += getSpeed();
				facing = right;
				Animation.walkRight.play();
			}
		}
	}

	private void changeHotbarSlot(int i) {
		if (hotbarSlot >= hotbar.getSlotCount()) {
			hotbarSlot = 0;
		} else if (hotbarSlot < 0) {
			hotbarSlot = hotbar.getSlotCount();
		}
		hotbar.setActiveSlot(i);
	}

	public void render(Graphics g) {
		if (facing == 1) {
			g.drawImage(Animation.walkUp.getTexture(Animation.walkUp.getFrame()), x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 0) {
			g.drawImage(Animation.walkDown.getTexture(Animation.walkDown.getFrame()), x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 3) {
			g.drawImage(Animation.walkLeft.getTexture(Animation.walkLeft.getFrame()), x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 2) {
			g.drawImage(Animation.walkRight.getTexture(Animation.walkRight.getFrame()), x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else {
			g.drawImage(Images.entitysheet.getSprite((facing == 0) ? 10 : (facing == 1) ? 0 : (facing == 3) ? 20 : 30), x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale, Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		}

		hotbar.render(g);

		if (inventoryOpen) {
			inventory.render(g);

			g.setFont(Utils.debugFont);

			for (int i = 0; i < hotbar.getSlotCount(); i++) {
				if (hotbar.getSlots().get(i).contains(input.getMouse()) && hotbar.getSlots().get(i).getCurrentItem() != null) {
					int strWid = g.getFontMetrics().stringWidth(hotbar.getSlots().get(i).getCurrentItem().getName());
					container = new Container(input.getMouse().x + 5, input.getMouse().y - 30, 20 + strWid, 30);
					container.render(g);
					g.setColor(new Color(44, 44, 44));
					g.drawString(hotbar.getSlots().get(i).getCurrentItem().getName(), container.x + 10, container.y + 16);

					if (isInventoryOpen() && input.isMouseLeft()) {
						mouseItem = hotbar.getSlots().get(i).getCurrentItem();
						mouseItemAmount = hotbar.getSlots().get(i).getCurrentAmount();

						hotbar.getSlots().get(i).setCurrentItem(null);
						hotbar.getSlots().get(i).setCurrentAmount(0);

						input.setMouseLeft(false);
					}
				}

				if (hotbar.getSlots().get(i).contains(input.getMouse()) && hotbar.getSlots().get(i).getCurrentItem() == null && isInventoryOpen() && input.isMouseLeft() && mouseItem != null && mouseItemAmount > 0) {
					mouseItem.setBounds(i * hotbar.getSlotSpacing() + ((Main.WIDTH / 2) - ((hotbar.getSlotCount() * hotbar.getSlotSpacing()) / 2)) + 4, Main.HEIGHT - hotbar.getSlotSpacing(), mouseItem.width, mouseItem.height);
					hotbar.getSlots().get(i).setCurrentItem(mouseItem);
					hotbar.getSlots().get(i).setCurrentAmount(mouseItemAmount);

					mouseItem = null;
					mouseItemAmount = 0;

					input.setMouseLeft(false);
				}
			}

			for (int i = 0; i < inventory.getMaxItems(); i++) {
				if (inventory.getSlots().get(i).contains(input.getMouse()) && inventory.getSlots().get(i).getCurrentItem() != null) {
					int strWid = g.getFontMetrics().stringWidth(inventory.getSlots().get(i).getCurrentItem().getName());
					container = new Container(input.getMouse().x + 5, input.getMouse().y - 30, 20 + strWid, 30);
					container.render(g);
					g.setColor(new Color(44, 44, 44));
					g.drawString(inventory.getSlots().get(i).getCurrentItem().getName(), container.x + 10, container.y + 16);

					if (isInventoryOpen() && input.isMouseLeft()) {
						mouseItem = inventory.getSlots().get(i).getCurrentItem();
						mouseItemAmount = inventory.getSlots().get(i).getCurrentAmount();

						inventory.getSlots().get(i).setCurrentItem(null);
						inventory.getSlots().get(i).setCurrentAmount(0);

						input.setMouseLeft(false);
					}
				}

				if (inventory.getSlots().get(i).contains(input.getMouse()) && inventory.getSlots().get(i).getCurrentItem() == null && isInventoryOpen() && input.isMouseLeft() && mouseItem != null && mouseItemAmount > 0) {
					mouseItem.setBounds(Main.WIDTH / 2 - x * inventory.getSlotSpacing() - 40, Main.HEIGHT / 2 - y * inventory.getSlotSpacing() + 18, mouseItem.width, mouseItem.height);
					inventory.getSlots().get(i).setCurrentItem(mouseItem);
					inventory.getSlots().get(i).setCurrentAmount(mouseItemAmount);

					mouseItem = null;
					mouseItemAmount = 0;

					input.setMouseLeft(false);
				}
			}
		}

		g.setColor(new Color(0, 0, 255, 144));
		g.drawRect(x, y, width, height);
	}

	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}

	public Hotbar getHotbar() {
		return hotbar;
	}

}
