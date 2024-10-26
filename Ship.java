import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	boolean up = false;
	boolean left = false;
	boolean right = false;
	boolean shoot = false;
	boolean hasShot = false;
	
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	@Override
	public void paint(Graphics brush, Color color) {
		Point[] stuff = getPoints();
		int size = stuff.length;
		int[] xA = new int[size];
		int[] yA = new int[size];
		brush.setColor(color);
		
		for (int i = 0; i < size; i++) {
			double tempX = stuff[i].x;
			double tempY = stuff[i].y;
			xA[i] = (int)tempX;
			yA[i] = (int)tempY;
		}
		
		brush.fillPolygon(xA, yA, size);
	}
	
	@Override
	public void move() {
		int scalar = 5;
		int rot = 3;
		
		if (up == true) { //goes forward from nose
			position.y += Math.sin(Math.toRadians(rotation))*scalar;
			position.x += Math.cos(Math.toRadians(rotation))*scalar;
		}
		
		if (left == true) { //rotates left
			this.rotate(-rot);
		}
		
		if (right == true) { //rotates right
			this.rotate(rot);
		}
		
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
	
	@Override
	public void keyPressed(KeyEvent press) {
		int key = press.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			shoot = true;
			if (shoot == true && hasShot == false) {
				Bullet bullet = new Bullet(getPoints()[0], rotation);
				bullets.add(bullet);
				hasShot = true;
			}
			shoot = false;
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent release) {
		int key = release.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			hasShot = false;
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent something) {
		return;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}
