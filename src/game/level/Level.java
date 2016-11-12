package game.level;

import java.awt.Graphics;

import engine.Input;
import game.Globals;
import game.screens.ScreenGeneration;

public class Level {

    private Input input;

    public Chunk[][] chunks;

    public Generator gen;
    public ScreenGeneration screenGen;
    public boolean finishedGeneration = false;

    public Level(Input input) {
        this.input = input;

        chunks = new Chunk[Globals.chunkCount][Globals.chunkCount];

        screenGen = new ScreenGeneration(this);
    }

    public void tick() {
        for (int x = 0; x < Globals.chunkCount; x++) {
            for (int y = 0; y < Globals.chunkCount; y++) {
                if (chunks[x][y] != null) {
                    chunks[x][y].tick(x, y);
                }
            }
        }

        if (screenGen.running) {
            screenGen.tick();
        }
    }

    public void render(Graphics g) {
        if (finishedGeneration) {
            for (int x = 0; x < Globals.chunkCount; x++) {
                for (int y = 0; y < Globals.chunkCount; y++) {
                    if (chunks[x][y] != null) {
                        chunks[x][y].render(g, x, y);
                    }
                }
            }
        }

        if (screenGen.running) {
            screenGen.render(g);
        }
    }

    public void generateMap() {
        for (int x = 0; x < Globals.chunkCount; x++) {
            for (int y = 0; y < Globals.chunkCount; y++) {
                chunks[x][y] = new Chunk(input, x * (Globals.chunkSize * Globals.tileSize),
                        y * (Globals.chunkSize * Globals.tileSize));
            }
        }
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }

}
