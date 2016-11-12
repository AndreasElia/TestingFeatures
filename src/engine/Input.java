package engine;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Hashtable;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static Point mouse = new Point(0, 0);

    private boolean mouseLeft;
    private boolean mouseRight;

    private static Hashtable<String, Boolean> keys = new Hashtable<String, Boolean>();
    private static Hashtable<String, Integer> keysHeld = new Hashtable<String, Integer>();

    private int wheelRotation;

    public Input() {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        wheelRotation = event.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        mouse.setLocation(event.getX(), event.getY());
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            setMouseLeft(true);
        } else if (event.getButton() == MouseEvent.BUTTON3) {
            setMouseRight(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            setMouseLeft(false);
        } else if (event.getButton() == MouseEvent.BUTTON3) {
            setMouseRight(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keys.put(KeyEvent.getKeyText(event.getKeyCode()), true);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keys.put(KeyEvent.getKeyText(event.getKeyCode()), false);
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    public Point getMouse() {
        return mouse;
    }

    public boolean isMouseLeft() {
        return mouseLeft;
    }

    public void setMouseLeft(boolean mouseLeft) {
        this.mouseLeft = mouseLeft;
    }

    public boolean isMouseRight() {
        return mouseRight;
    }

    public void setMouseRight(boolean mouseRight) {
        this.mouseRight = mouseRight;
    }

    private boolean getKeyI(String key) {
        return (getKeys().get(key) == null) ? false : getKeys().get(key);
    }

    public boolean getKey(String key) {
        if (getKeyI(key) == true) {
            int heldnum = getHeld(key) + 1;
            keysHeld.put(key, heldnum);
        } else {
            keysHeld.put(key, -1);
        }

        if (getKeys().get(key) == null) {
            return false;
        }

        return getKeys().get(key);
    }

    public static int getHeld(String key) {
        if (keysHeld.get(key) == null) {
            return -1;
        }

        return keysHeld.get(key);
    }

    public boolean isJustHeld(String key) {
        if (keysHeld.get(key) == null) {
            return false;
        }

        if (keysHeld.get(key) == 0) {
            return true;
        }

        return false;
    }

    public Hashtable<String, Boolean> getKeys() {
        return keys;
    }

    public int getKeysSize() {
        return keys.size();
    }

    public int getWheelRotation() {
        return wheelRotation;
    }

    public void setWheelRotation(int wheelRotation) {
        this.wheelRotation = wheelRotation;
    }

}
