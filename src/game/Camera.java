package game;

import java.awt.Rectangle;

public class Camera extends Rectangle {

    private static final long serialVersionUID = 1L;

    private Rectangle original;

    public void setBounds(int x, int y, int width, int height) {
        original = new Rectangle(x, y, width, height);
        super.setBounds(x, y, width, height);
        moveWith();
    }

    public void moveWith() {
        x = original.x - Game.cX;
        y = original.y - Game.cY;
    }

    public void setX(double d) {
        original.x += d;
    }

    public void setY(double y) {
        original.y += y;
    }

    public Rectangle getBounds() {
        return original;
    }

}