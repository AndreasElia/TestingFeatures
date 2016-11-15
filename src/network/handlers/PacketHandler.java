package network.handlers;

import com.esotericsoftware.kryonet.Connection;

import network.packets.LevelPacket;
import network.packets.NewUserPacket;

public class PacketHandler {

    public PacketHandler(Connection conn, Object object) {
        if (object instanceof NewUserPacket) {
            new NewUserHandler(conn, (NewUserPacket) object);
        } else if (object instanceof LevelPacket) {
            new LevelHandler(conn, (LevelPacket) object);
        }
    }

}
