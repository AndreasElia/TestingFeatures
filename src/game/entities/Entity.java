package game.entities;

import java.awt.Graphics;

import game.Camera;
import game.level.Level;

public abstract class Entity extends Camera {

	private static final long serialVersionUID = 1L;

	private double speed;

	public Entity() {
	}

	public void tick() {
	}

	public void tick(Level level) {
	}

	public abstract void render(Graphics g);

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
