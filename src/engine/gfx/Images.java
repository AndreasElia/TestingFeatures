package engine.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Images {

	private static HashMap<String, BufferedImage> images;

	public static SpriteSheet tilesheet;
	public static SpriteSheet itemsheet;
	public static SpriteSheet othertiles;
	public static SpriteSheet entitysheet;
	public static SpriteSheet containersheet;

	public Images() {
		images = new HashMap<String, BufferedImage>();

		tilesheet = new SpriteSheet("tiles", 8, 8);
		itemsheet = new SpriteSheet("itemsheet", 16, 16);
		othertiles = new SpriteSheet("othertiles", 16, 16);
		entitysheet = new SpriteSheet("entities", 14, 18);
		containersheet = new SpriteSheet("guisheet", 8, 8);
	}

	public static BufferedImage getImage(String text) {
		return images.get(text);
	}

}
