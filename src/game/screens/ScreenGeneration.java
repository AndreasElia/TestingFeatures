package game.screens;

import engine.Main;
import game.Utils;
import game.level.Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ScreenGeneration implements Runnable {

	private Rectangle fullScreenRect;
	private String fullScreenText = "Generating world";
	private String[] dots = { ".", "..", "..." };
	private int timer;
	private int frame;

	public boolean running = true;
	private Thread thread;
	private Level level;

	public ScreenGeneration(Level level) {
		this.level = level;

		setButtons();

		thread = new Thread(this, "Generation");
		thread.start();
	}

	public void tick() {
		timer++;

		if (timer > 30) {
			frame++;
			timer = 0;
		}

		if (frame >= dots.length) {
			frame = 0;
		}

		fullScreenText = "Generating world" + dots[frame];
	}

	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255, 177));
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		g.setFont(Utils.menuButtonFont);

		g.setColor(new Color(0, 0, 0, 177));
		g.fillRect(fullScreenRect.x - (g.getFontMetrics().stringWidth(fullScreenText) / 4), fullScreenRect.y, fullScreenRect.width + (g.getFontMetrics().stringWidth(fullScreenText) / 2), fullScreenRect.height);

		g.setColor(new Color(255, 255, 255));
		g.drawString(fullScreenText, ((Main.WIDTH - g.getFontMetrics().stringWidth(fullScreenText)) / 2), fullScreenRect.y + (g.getFontMetrics().getHeight() + 9));
	}

	public void setButtons() {
		fullScreenRect = new Rectangle((Main.WIDTH / 2) - 65, Main.HEIGHT / 2, 130, 40);
	}

	@Override
	public void run() {
		while (running) {
			level.generateMap();
			level.finishedGeneration = true;
			running = false;
		}
	}

	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
