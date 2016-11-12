package engine.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Images {

    private static HashMap<String, BufferedImage> images;

    public static SpriteSheet tilesheet;
    public static SpriteSheet othertiles;
    public static SpriteSheet entitysheet;

    public Images() {
        images = new HashMap<String, BufferedImage>();

        tilesheet = new SpriteSheet("tiles", 8, 8);
        othertiles = new SpriteSheet("othertiles", 16, 16);
        entitysheet = new SpriteSheet("entities", 14, 18);
    }

    public static BufferedImage getImage(String text) {
        return images.get(text);
    }

}
