package game.entities;

import game.Camera;
import game.items.Item;
import game.level.Level;

import java.awt.Graphics;
import java.util.List;

public abstract class Entity extends Camera {

	private static final long serialVersionUID = 1L;

	private double speed;

	public Entity() {
	}

	public void tick() {
	}

	public void tick(Level level) {
	}

	public void tick(List<Item> itemDrops) {
	}

	public abstract void render(Graphics g);

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
