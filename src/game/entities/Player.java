package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import engine.Input;
import engine.gfx.Animation;
import engine.gfx.Images;
import game.Globals;
import game.level.Level;

public class Player extends Entity {

	private static final long serialVersionUID = 1L;

	private Input input;

	private boolean mounted = false;

	private int up = 1;
	private int down = 0;
	private int left = 3;
	private int right = 2;

	private int facing = down;

	public Player(Input input, int startX, int startY) {
		setBounds(startX, startY, Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
				Images.entitysheet.getSprite(0).getHeight() * Globals.scale);
		this.input = input;
		this.x = startX;
		this.y = startY;

		setSpeed(4);
	}

	public void tick(Level level) {
		//
	}

	public void render(Graphics g) {
		if (facing == 1) {
			g.drawImage(Animation.walkUp.getTexture(Animation.walkUp.getFrame()), x, y,
					Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
					Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 0) {
			g.drawImage(Animation.walkDown.getTexture(Animation.walkDown.getFrame()), x, y,
					Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
					Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 3) {
			g.drawImage(Animation.walkLeft.getTexture(Animation.walkLeft.getFrame()), x, y,
					Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
					Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else if (facing == 2) {
			g.drawImage(Animation.walkRight.getTexture(Animation.walkRight.getFrame()), x, y,
					Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
					Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		} else {
			g.drawImage(Images.entitysheet.getSprite((facing == 0) ? 10 : (facing == 1) ? 0 : (facing == 3) ? 20 : 30),
					x, y, Images.entitysheet.getSprite(0).getWidth() * Globals.scale,
					Images.entitysheet.getSprite(0).getHeight() * Globals.scale, null);
		}

		g.setColor(new Color(0, 0, 255, 144));
		g.drawRect(x, y, width, height);
	}

}
