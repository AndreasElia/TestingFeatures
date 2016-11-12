package game.level.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import engine.gfx.Images;
import engine.gfx.SpriteSheet;
import game.Camera;
import game.Globals;
import game.level.Chunk;

public abstract class Tile extends Camera {

    private static final long serialVersionUID = 1L;

    public static final int grass = 0;
    public static final int water = 3;
    public static final int mud = 11;

    protected int type;
    protected String name;

    protected boolean ticks = false;
    protected boolean updates = false;
    protected boolean passable = true;
    protected boolean unbreakable = false;

    protected int transitionId = 0;
    protected int sheetOffsetX = 0;
    protected int sheetOffsetY = 0;

    protected SpriteSheet tileSheet;
    protected BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    protected boolean imageSet = false;

    public Tile(int type, int x, int y) {
        setBounds(x, y, Globals.tileSize, Globals.tileSize);

        this.type = type;
    }

    public void tick() {
    }

    public void render(Graphics g) {
        if (!isImageSet() && getOffsetX() >= 0 && getOffsetY() >= 0) {
            Graphics gmerge = img.getGraphics();
            int[] tiles = TilePresets.getTiles(getTransitionId());
            int x1 = 0;
            int y1 = 0;

            for (int i = 0; i < 4; i++) {
                // 7, 8, 13, 14 = middle 4 tiles so...

                gmerge.drawImage(getTileSheet().getSprite(tiles[i]), (x1 * 8), (y1 * 8), 8, 8, null);

                x1 += 1;

                if (x1 == 2) {
                    x1 = 0;
                    y1 += 1;
                }
            }
            gmerge.dispose();
            setImageSet(true);
        }

        g.drawImage(img, x, y, width, height, null);
    }

    public void onUpdate(Chunk chunk, int x, int y) {
    }

    public void onDestroyTile(Chunk chunk, int chunkX, int chunkY, int x, int y) {
    }

    public int getType() {
        return type;
    }

    public boolean isTicks() {
        return ticks;
    }

    public void setTicks(boolean ticks) {
        this.ticks = ticks;
    }

    public boolean isUpdates() {
        return updates;
    }

    public void setUpdates(boolean updates) {
        this.updates = updates;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setOffset(int x, int y) {
        this.sheetOffsetX = x;
        this.sheetOffsetY = y;
        setTileSheet(new SpriteSheet(Images.tilesheet.getSheet().getSubimage(x * 8, y * 8, 48, 32), 8, 8));
    }

    public int getOffsetX() {
        return sheetOffsetX;
    }

    public int getOffsetY() {
        return sheetOffsetY;
    }

    public boolean isImageSet() {
        return imageSet;
    }

    public void setImageSet(boolean imageSet) {
        this.imageSet = imageSet;
    }

    public SpriteSheet getTileSheet() {
        return tileSheet;
    }

    public void setTileSheet(SpriteSheet tileSheet) {
        this.tileSheet = tileSheet;
    }

    public int getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(int transitionId) {
        this.transitionId = transitionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

}
