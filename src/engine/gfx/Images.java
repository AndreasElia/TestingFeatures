package engine.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Images {

    private static HashMap<String, BufferedImage> images;

    public static SpriteSheet tilesheet;
    public static SpriteSheet othertiles;

    public Images() {
        images = new HashMap<String, BufferedImage>();

        tilesheet = new SpriteSheet("tiles", 8, 8);
        othertiles = new SpriteSheet("othertiles", 16, 16);
    }

    public static BufferedImage getImage(String text) {
        return images.get(text);
    }

}
