package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JFrame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import engine.gfx.Images;
import game.level.tiles.TilePresets;
import game.screens.ScreenManager;
import network.handlers.PacketHandler;

public class Main extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static int WIDTH = 1024;
    public static int HEIGHT = WIDTH / 16 * 9;
    private static Dimension size = new Dimension(WIDTH - 10, HEIGHT - 10);

    private static boolean isFullScreen = false;
    private boolean running = false;

    private Thread thread;

    private static Client client;
    private static JFrame frame;
    private static Input input;
    private static ScreenManager screen;

    private static int FPS = 0;
    private static int TPS = 0;

    public static int auth;
    public static int currentId;
    public static int serverID = 0;

    public static Hashtable<Integer, Integer> ids = new Hashtable<Integer, Integer>();

    public Main() {
        input = new Input();

        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addMouseWheelListener(input);

        screen = new ScreenManager(input);

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        new Images();
        new TilePresets();
    }

    public static void main(String[] args) {
        Main main = new Main();

        frame = new JFrame("TestingFeatures");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(main);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Images.othertiles.getSprite(0));
        frame.setVisible(true);

        main.start();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        serverID = Main.getClient().getID();

        screen.tick();
    }

    public void render() {
        BufferStrategy strategy = getBufferStrategy();

        if (strategy == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = strategy.getDrawGraphics();

        g.setColor(new Color(0x1F282D));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        screen.render(g);

        g.dispose();
        strategy.show();
    }

    @Override
    public void run() {
        startClient();

        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            boolean shouldRender = true;

            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (unprocessed >= 1) {
                ticks++;
                tick();
                unprocessed -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                setFPS(frames);
                setTPS(ticks);
                ticks = 0;
                frames = 0;
            }
        }
    }

    public void startClient() {
        client = new Client();

        Kryo kryo = client.getKryo();

        register(kryo);

        client.start();

        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                new PacketHandler(connection, object);
            }
        });
    }

    public static void register(Kryo kryo) {
        kryo.register(java.util.Hashtable.class);
    }

    public static boolean isFullScreen() {
        return isFullScreen;
    }

    public static int getFPS() {
        return FPS;
    }

    public static void setFPS(int fPS) {
        FPS = fPS;
    }

    public static int getTPS() {
        return TPS;
    }

    public static void setTPS(int tPS) {
        TPS = tPS;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Main.client = client;
    }

}
