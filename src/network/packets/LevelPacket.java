package network.packets;

import engine.Main;

public class LevelPacket {

    private int[][] map = new int[Main.mapSize][Main.mapSize];

    public LevelPacket() {
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

}
