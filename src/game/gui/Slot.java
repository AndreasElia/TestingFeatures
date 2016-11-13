package game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import engine.Input;
import game.Utils;
import game.items.Item;

public class Slot extends Rectangle {

	private static final long serialVersionUID = 1L;

	private Input input;
	private int slotSize = 34;
	private Item currentItem;
	private int currentAmount;
	private boolean active = false;
	private Container container;

	public Slot(Input input, int x, int y) {
		setBounds(x, y, slotSize, slotSize);

		this.input = input;
	}

	public void tick() {
		if (currentAmount <= 0) {
			currentItem = null;
		}

		if (currentItem != null && currentItem.isUpdates()) {
			currentItem.tick();
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(x, y, slotSize, slotSize);

		if (active) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(x, y - 5, slotSize, slotSize + 5);
		}

		if (currentItem != null) {
			currentItem.render(g);

			g.setFont(Utils.debugFont);
			g.setColor(new Color(255, 255, 255, 144));
			g.drawString("" + currentAmount, x + slotSize - g.getFontMetrics().stringWidth("" + currentAmount) - 2, y + slotSize - 3);
		}
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

}
