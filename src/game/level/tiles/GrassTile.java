package game.level.tiles;

import java.awt.Graphics;

import game.Globals;
import game.level.Chunk;

public class GrassTile extends Tile {

    private static final long serialVersionUID = 1L;

    public GrassTile(int type, int x, int y) {
        super(type, x, y);

        setOffset(0, 0);
        setName("Grass");
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void onDestroyTile(Chunk chunk, int chunkX, int chunkY, int x, int y) {
        int xPos = (chunkX * (Globals.chunkSize * Globals.tileSize)) + x * Globals.tileSize;
        int yPos = (chunkY * (Globals.chunkSize * Globals.tileSize)) + y * Globals.tileSize;

        chunk.setTile(x, y, new WaterTile(Tile.water, xPos, yPos));
    }

}
