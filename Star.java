import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Star extends Circle {
	
	Random r = new Random();
	
	public Star(Point center, int radius) {
		super(center, radius);
	}

	@Override
	public void paint(Graphics brush, Color color) {
		double centerX = center.x;
		double centerY = center.y;

		brush.setColor(color);
		
		brush.fillOval((int)centerX, (int)centerY, radius/2, radius);
	}

	@Override
	public void move() {
		//ment to be empty
	}

}
