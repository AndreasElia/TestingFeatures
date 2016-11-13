package game.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {

	private static final long serialVersionUID = 1L;

	private int rr = 255;
	private int rg = 0;
	private int rb = 0;

	public Bullet(int x, int y, int angle) {
		this.x = 100;
		this.y = 50;

		setSpeed(1);
	}

	public void tick() {
	}

	public void render(Graphics g) {
		nextRGB();

		g.setColor(new Color(rr, rg, rb));
		g.fillRect(x, y, 8, 8);
	}

	private void nextRGB() {
		if (rr == 255 && rg < 255 && rb == 0) {
			rg++;
		} else if (rg == 255 && rr > 0 && rb == 0) {
			rr--;
		} else if (rg == 255 && rb < 255 && rr == 0) {
			rb++;
		} else if (rb == 255 && rg > 0 && rr == 0) {
			rg--;
		} else if (rb == 255 && rr < 255 && rg == 0) {
			rr++;
		} else if (rr == 255 && rb > 0 && rg == 0) {
			rb--;
		}
	}

}
