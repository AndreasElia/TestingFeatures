package engine.gfx;

import java.awt.image.BufferedImage;

public class Animation {

    private static int speed = 11;

    public static Animation walkUp = new Animation(new int[] { 0, 1 }, speed);
    public static Animation walkDown = new Animation(new int[] { 2, 3 }, speed);
    public static Animation walkLeft = new Animation(new int[] { 4, 5 }, speed);
    public static Animation walkRight = new Animation(new int[] { 6, 7 }, speed);

    private int[] frames;
    private int fps;
    private int frameCount;
    private int count;
    private int index;
    private int frame;

    public Animation(int[] frames, int fps) {
        this.frames = frames;
        this.fps = fps;
        this.frameCount = frames.length;
    }

    public void play() {
        index++;

        if (index > fps) {
            index = 0;
            nextScreen();
        }
    }

    public void nextScreen() {
        count++;

        if (count >= frameCount) {
            count = 0;
        }

        frame = count;
    }

    public int getFrame() {
        return frame;
    }

    public int index(int frame) {
        return frames[frame];
    }

    public BufferedImage getTexture(int frame) {
        return Images.entitysheet.getSprite(index(frame));
    }

    public void reset() {
        index = 0;
        count = 0;
    }

}
