package game.level;

import game.Globals;
import game.level.tiles.Tile;

import java.util.Random;

public class Generator {

	public static int scale = 1;
	public int s = Globals.chunkSize;
	public int w = s;
	public int h = s;
	public int[][] map = new int[w][h];
	public boolean generated = false;
	public static Random random = new Random();
	public double[][] values = new double[w][h];

	public Generator(int featureSize) {
		values = new double[w][h];

		for (int x = 0; x < w; x += featureSize) {
			for (int y = 0; y < w; y += featureSize) {
				setSample(x, y, random.nextFloat() * 2 - 1);
			}
		}

		int stepSize = featureSize;
		double scale = 1.0 / w;
		double scaleMod = 1;

		while (stepSize > 1) {
			int halfStep = stepSize / 2;

			for (int x = 0; x < w; x += stepSize) {
				for (int y = 0; y < w; y += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}

			for (int x = 0; x < w; x += stepSize) {
				for (int y = 0; y < w; y += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}

			stepSize /= 2;
			scale *= (scaleMod + 0.8);
			scaleMod *= 0.3;
		}
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1))][(y & (h - 1))];
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1))][(y & (h - 1))] = value;
	}

	public int[][] createTopMap() {
		Generator mnoise1 = new Generator(16);
		Generator mnoise2 = new Generator(16);
		Generator mnoise3 = new Generator(16);

		Generator noise1 = new Generator(32);
		Generator noise2 = new Generator(32);

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				double val = Math.abs(noise1.values[x][y] - noise2.values[x][y]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[x][y] - mnoise2.values[x][y]);
				mval = Math.abs(mval - mnoise3.values[x][y]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;

				if (xd < 0)
					xd = -xd;
				if (yd < 0)
					yd = -yd;

				double dist = xd >= yd ? xd : yd;

				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 20;

				if (val < -0.5) {
					map[x][y] = Tile.water;
				} else if (val > 0.5 && mval < -1.5) {
					map[x][y] = Tile.mud;
				} else {
					map[x][y] = Tile.grass;
				}
			}
		}

		return map;
	}

}
