/**
 * Asteroid.java
 * 
 * Class that represents an Asteroid object
 */
import java.awt.Color;
import java.awt.Graphics;

public class Asteroid extends Polygon {

	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	@Override
	public void paint(Graphics brush, Color color) {
		Point[] pts = getPoints();
		int[] xpts = new int[pts.length];
		int[] ypts = new int[pts.length];
		int npts = pts.length;

		for (int i = 0; i < npts; i++) {
			xpts[i] = (int)pts[i].x;
			ypts[i] = (int)pts[i].y;
		}

		brush.setColor(color);
		brush.drawPolygon(xpts, ypts, npts);
		brush.fillPolygon(xpts, ypts, npts);

	}

	@Override
	public void move() {
		int scalar = 2;
		
		position.x += Math.cos(Math.toRadians(rotation))*scalar;
		position.y += Math.sin(Math.toRadians(rotation))*scalar;

		// TO-DO
		/**
		 * Have asteroid move back on the screen once they go off the screen.
		 *
		 * You will do this by checking the value of position.x and position.y
		 * and determine if they are outside of the bounds of the screen
		 * specified by Asteroids.SCREEN_WIDTH and Asteroids.SCREEN_HEIGHT
		 * If so, reposition the x and/or y coordinates.
		 * 
		 * i.e. if an asteroid moves off the right-side of the screen
		 * have it re-appear on the left side of the screen.
		 */

		if (position.x < 0) {
			position.x = position.x + 800;
		} else if (position.x > 800) {
			position.x = position.x - 800;
		}

		if (position.y < 0) {
			position.y = position.y + 600;
		} else if (position.y > 600) {
			position.y = position.y - 600;
		}
	}
}

