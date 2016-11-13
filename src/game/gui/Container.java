package game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.gfx.Images;
import game.Globals;

public class Container extends Rectangle {

	private static final long serialVersionUID = 1L;

	private BufferedImage container;
	private boolean containerMerged;

	public Container(int x, int y, int width, int height) {
		setBounds(x, y, width - 10, height - 10);

		if ((width & 1) != 0) {
			width = round(width, 8);
		}

		if ((height & 1) != 0) {
			height = round(height, 8);
		}

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		container = new BufferedImage(width - 8, height - 8, BufferedImage.TYPE_INT_RGB);
		containerMerged = false;
	}

	private int round(int num, int multipleOf) {
		return (int) (Math.floor((num + multipleOf / 2) / multipleOf) * multipleOf);
	}

	public void render(Graphics g) {
		if (!containerMerged) {
			Graphics gmerge = container.getGraphics();

			gmerge.setColor(Color.WHITE);
			gmerge.fillRect(x, y, width, height);

			int x1 = -4;
			int y1 = -4;

			// top row
			gmerge.drawImage(Images.containersheet.getSprite(0), x1, y1, 8 * Globals.scale, 8 * Globals.scale, null);
			gmerge.drawImage(Images.containersheet.getSprite(1), x1 + (8 * Globals.scale), y1, width - (16 * Globals.scale), 8 * Globals.scale, null);
			gmerge.drawImage(Images.containersheet.getSprite(2), x1 + width - (8 * Globals.scale), y1, 8 * Globals.scale, 8 * Globals.scale, null);

			// middle row
			gmerge.drawImage(Images.containersheet.getSprite(3), x1, y1 + (8 * Globals.scale), 8 * Globals.scale, height - (16 * Globals.scale), null);
			gmerge.drawImage(Images.containersheet.getSprite(4), x1 + (8 * Globals.scale), y1 + (8 * Globals.scale), width - (16 * Globals.scale), height - (16 * Globals.scale), null);
			gmerge.drawImage(Images.containersheet.getSprite(5), x1 + width - (8 * Globals.scale), y1 + (8 * Globals.scale), 8 * Globals.scale, height - (16 * Globals.scale), null);

			// bottom row
			gmerge.drawImage(Images.containersheet.getSprite(6), x1, y1 + height - (8 * Globals.scale), 8 * Globals.scale, 8 * Globals.scale, null);
			gmerge.drawImage(Images.containersheet.getSprite(7), x1 + (8 * Globals.scale), y1 + height - (8 * Globals.scale), width - (16 * Globals.scale), 8 * Globals.scale, null);
			gmerge.drawImage(Images.containersheet.getSprite(8), x1 + width - (8 * Globals.scale), y1 + height - (8 * Globals.scale), 8 * Globals.scale, 8 * Globals.scale, null);

			gmerge.dispose();
			containerMerged = true;
		}

		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);

		g.drawImage(container, x, y, width, height, null);
	}

}
