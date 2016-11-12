package engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage sheet;
	private BufferedImage[] sprites;
	private int spriteWidth;
	private int spriteHeight;

	public SpriteSheet(String name, int sw, int sh) {
		this.spriteWidth = sw;
		this.spriteHeight = sh;

		try {
			sheet = ImageIO.read(SpriteSheet.class.getResource("/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sprites = new BufferedImage[(sheet.getWidth() / spriteWidth) * (sheet.getHeight() / spriteHeight)];

		int tx = 0, ty = 0;
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = sheet.getSubimage(tx * spriteWidth, ty * spriteHeight, spriteWidth, spriteHeight);
			tx++;
			if (tx == (sheet.getWidth() / spriteWidth)) {
				tx = 0;
				ty++;
			}
		}
	}

	public SpriteSheet(BufferedImage img, int sw, int sh) {
		this.spriteWidth = sw;
		this.spriteHeight = sh;

		this.sheet = img;

		sprites = new BufferedImage[(sheet.getWidth() / spriteWidth) * (sheet.getHeight() / spriteHeight)];

		int tx = 0, ty = 0;
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = sheet.getSubimage(tx * spriteWidth, ty * spriteHeight, spriteWidth, spriteHeight);
			tx++;
			if (tx == (sheet.getWidth() / spriteWidth)) {
				tx = 0;
				ty++;
			}
		}
	}

	public BufferedImage getSprite(int x) {
		return sprites[x];
	}

	public int getWidth() {
		return sheet.getWidth();
	}

	public int getHeight() {
		return sheet.getHeight();
	}

	public int getCount() {
		return (sheet.getWidth() / spriteWidth * sheet.getHeight() / spriteHeight);
	}

	public BufferedImage getSheet() {
		return sheet;
	}

}
