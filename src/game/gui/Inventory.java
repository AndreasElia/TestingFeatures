package game.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Input;
import engine.Main;
import game.items.BushItem;
import game.items.GrassItem;
import game.items.Item;

public class Inventory extends Rectangle {

	private static final long serialVersionUID = 1L;

	private Input input;

	private int slotSize = 34;

	private List<Slot> slots = new ArrayList<Slot>();

	private int slotSpacing = 40;
	private int slotsX;
	private int slotsY;
	private int maxItems;

	private Container container;
	private Container tooltip;

	public Inventory(Input input, int slotsX, int slotsY) {
		setBounds(x, y, slotsX * slotSize, slotsY * slotSize);

		this.input = input;

		this.slotsX = slotsX;
		this.slotsY = slotsY;
		this.setMaxItems(slotsX * slotsY);

		for (int x = 0; x < slotsX; x++) {
			for (int y = 0; y < slotsY; y++) {
				slots.add(new Slot(input, Main.WIDTH / 2 - x * slotSpacing - 40, Main.HEIGHT / 2 - y * slotSpacing + 18));
			}
		}

		container = new Container(Main.WIDTH / 2 - 224, Main.HEIGHT / 2 - 120, 448, 240);

		slots.get(0).setCurrentItem(new GrassItem(Item.grass, Main.WIDTH / 2 - 0 * slotSpacing - 40, Main.HEIGHT / 2 - 0 * slotSpacing + 18));
		slots.get(0).setCurrentAmount(20);

		slots.get(1).setCurrentItem(new BushItem(Item.bush, (Main.WIDTH / 2) - (0 * slotSpacing) - 40, Main.HEIGHT / 2 - 1 * slotSpacing + 18));
		slots.get(1).setCurrentAmount(20);
	}

	public void tick() {
		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).tick();
		}
	}

	public void render(Graphics g) {
		container.render(g);

		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).render(g);
		}
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public int getSlotSpacing() {
		return slotSpacing;
	}

}
