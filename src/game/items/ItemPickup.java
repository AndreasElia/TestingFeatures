package game.items;

import game.Camera;
import game.Utils;

import java.awt.Color;
import java.awt.Graphics;

public class ItemPickup extends Camera {

	private static final long serialVersionUID = 1L;

	private String item;
	private int timer;
	private boolean removalRequired = false;

	/*
	 * Make it so that each item pickup appears, disappears, and then the next one appears from array
	 */

	public ItemPickup(String string, int x, int y) {
		setBounds(x, y, 100, 30);

		this.item = string;
		this.x = x;
		this.y = y;
	}

	public void tick() {
		timer++;

		if (timer > 100) {
			setRemovalRequired(true);
			timer = 0;
		}
	}

	public void render(Graphics g) {
		g.setFont(Utils.debugFont);
		g.setColor(new Color(0, 0, 0, 144));
		g.drawString("" + item, x, y);
	}

	public boolean isRemovalRequired() {
		return removalRequired;
	}

	public void setRemovalRequired(boolean removalRequired) {
		this.removalRequired = removalRequired;
	}

}
