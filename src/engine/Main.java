package engine;

import engine.gfx.Images;
import game.level.tiles.TilePresets;
import game.screens.ScreenManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static int WIDTH = 1024;
    public static int HEIGHT = WIDTH / 16 * 9;
    private static Dimension size = new Dimension(WIDTH - 10, HEIGHT - 10);

    private static boolean isFullScreen = false;

    private boolean running = false;
    private Thread thread;
    private static JFrame frame;

    private static Input input;
    private static ScreenManager screen;

    private static String NAME = "TestingFeatures";
    private static int FPS = 0;
    private static int TPS = 0;

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

        frame = new JFrame(NAME);
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

}
