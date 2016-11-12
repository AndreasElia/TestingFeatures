package game.level;

public class Generate implements Runnable {

    public Level level;
    public boolean running = true;
    private Thread thread;

    public Generate(Level level) {
        this.level = level;
        thread = new Thread(this, "Generate");
        thread.start();
    }

    public void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(running);
        if (running) {
            // for (int x = 0; x < level.getLevel().length; x++) {
            // for (int y = 0; y < level.getLevel()[0].length; y++) {
            // // gen
            // }
            // }
            level.generateMap();
            running = false;
        } else {
            stop();
        }
    }

}
