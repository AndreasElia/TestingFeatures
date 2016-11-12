package game;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.Random;

import game.entities.Entity;
import game.level.tiles.Tile;

public class Utils {

	public static Font plainFont = new Font("Tahoma", Font.PLAIN, 11);
	public static Font debugFont = new Font("Tahoma", Font.BOLD, 11);
	public static Font menuButtonFont = new Font("Tahoma", Font.BOLD, 12);

	public static int randInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public static boolean randomBoolean() {
		return new Random().nextBoolean();
	}

	public static double randomDouble() {
		return new Random().nextDouble();
	}

	public static double distanceTo(Entity one, Entity two) {
		double xd = (one.getX() + one.getWidth() / 2) - (two.getX() + two.getWidth() / 2);
		double yd = (one.getY() + one.getHeight() / 2) - (two.getY() + two.getHeight() / 2);
		return Math.sqrt(xd * xd + yd * yd);
	}

	public static double distanceTo(Tile one, Entity two) {
		double xd = (one.getBounds().getX() + one.getWidth() / 2) - (two.getBounds().getX() + two.getWidth() / 2);
		double yd = (one.getBounds().getY() + one.getHeight() / 2) - (two.getBounds().getY() + two.getHeight() / 2);
		return Math.sqrt(xd * xd + yd * yd);
	}

	public static double distanceTo(Rectangle one, Rectangle two) {
		double xd = (one.getBounds().getX() + one.getWidth() / 2) - (two.getBounds().getX() + two.getWidth() / 2);
		double yd = (one.getBounds().getY() + one.getHeight() / 2) - (two.getBounds().getY() + two.getHeight() / 2);
		return Math.sqrt(xd * xd + yd * yd);
	}

}
