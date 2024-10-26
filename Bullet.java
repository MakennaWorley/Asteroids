import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle {
	
	public static final int RADIUS = 10;
	double rotation;
	
	public Bullet(Point center, double rotation) {
		super(center, RADIUS); // define RADIUS in Bullet class
		this.rotation = rotation;
		}

	@Override
	public void paint(Graphics brush, Color color) {
		double centerX = center.x;
		double centerY = center.y;

		brush.setColor(color);
		
		brush.fillOval((int)centerX, (int)centerY, RADIUS, RADIUS/2);

	}

	@Override
	public void move() {
		int scalar = 8;
		
		center.y += Math.sin(Math.toRadians(rotation))*scalar;
		center.x += Math.cos(Math.toRadians(rotation))*scalar;
	}
	
	public boolean outOfBounds() {
		if (center.x < 0) {
			return true;
		} else if (center.x > 800) {
			return true;
		}

		if (center.y < 0) {
			return true;
		} else if (center.y > 600) {
			return true;
		}
		
		return false;
	}
	
	public Point getCenter() {
		return center;
	}

}
