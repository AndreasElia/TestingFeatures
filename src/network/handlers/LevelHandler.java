package network.handlers;

import com.esotericsoftware.kryonet.Connection;

import game.level.Level;
import network.packets.LevelPacket;

public class LevelHandler {

    public LevelHandler(Connection conn, LevelPacket object) {
        Level.topMap = object.getMap();
    }

}
