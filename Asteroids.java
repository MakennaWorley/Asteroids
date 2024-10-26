/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.*;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	static int counter = 0;
	
	private Ship ship;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	
	ArrayList<Asteroid> offScreenA = new ArrayList<Asteroid>();
	ArrayList<Asteroid> collisionWith = new ArrayList<Asteroid>();
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> offScreen = new ArrayList<Bullet>();
	
	Star[] randomStars = new Star[50];
	Point[] ptsShip = new Point[4];
	
	Random r = new Random();

	int lives = 5;
	int numAsteroid = 0;
	int showEnd;
	boolean colSA = false;
	boolean lostLife = false;
	boolean stop1 = false;
	boolean stop2 = false;
	boolean win = false;
	
	Color color;
	
	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(15,60,30);
		randomStars = createStars(50,15);
		
		ship = createShip();
		this.addKeyListener(ship);
	}
	
	private Ship createShip() {
		Point origin = new Point(200.,300.);
		ptsShip[0] = new Point(400.,300);
		ptsShip[1] = new Point(350.,285.);
		ptsShip[2] = new Point(340.,300.);
		ptsShip[3] = new Point(350,315);
		int startingRotation = 0;
		
		return new Ship(ptsShip, origin, startingRotation);
	}
	
	// Create a certain number of stars with a given max radius
	 public Star[] createStars(int numberOfStars, int maxRadius) {
	 	Star[] stars = new Star[numberOfStars];
	 	for(int i = 0; i < numberOfStars; ++i) {
	 		Point center = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);

	 		int radius = (int) (Math.random() * maxRadius);
	 		if(radius < 1) {
	 			radius = 1;
	 		}
	 		stars[i] = new Star(center, radius);
	 	}

	 	return stars;
	 }

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);
		numAsteroid = numberOfAsteroids;

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	public void paint(Graphics brush) {
		bullets = ship.getBullets();
		color = Color.GRAY;
		
		brush.setColor(Color.BLACK);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.WHITE);
		brush.drawString("Counter is " + counter,10,10);
		brush.drawString("Number of asteroids is " + numAsteroid,10,30);
		brush.drawString("Number of lives is " + lives,10,50);
		brush.drawString("Number of bullets is " + bullets.size(),10,70);

		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.LIGHT_GRAY);
			asteroid.move();
			if (asteroid.collision(ship) == true) {
				colSA = true;
				color = Color.RED;
				if (colSA = true && lostLife == false) {
					if (lives > 0 ) {
						lives = lives - 1;
					}
					
					if (lives <= 0 && stop1 == false) {
						stop1 = true;
						stop2 = true;
						showEnd = counter + 10;
					}
					
					lostLife = true;
					collisionWith.add(asteroid);
				}
			}
			if (asteroid.collision(ship) == false && collisionWith.contains(asteroid)) { //spotty sometimes
				collisionWith.remove(asteroid);
				colSA = false;
				lostLife = false;
			}
		}
		
		/**
		 * The above for loop (known as a "for each" loop)
		 * is equivalent to what is shown below.
		 */

		/**
		for (int i = 0; i < randomAsteroids.size(); i++) {
			randomAsteroids.get(i).paint(brush, Color.white);
			randomAsteroids.get(i).move();

		}
		*/
		
		for (Star star : randomStars) {
			if (r.nextInt(100) < 10) {
				star.paint(brush,Color.BLACK);
			} else {
				star.paint(brush,Color.WHITE);
			}
		}
		
		/*for (int i = 0; i < ship.getBullets().size(); i++) {
			Bullet b = ship.getBullets().get(i);
			b.paint(brush,Color.BLUE);
			b.move();
		}*/
		
		for (Bullet bullet : bullets) {
			bullet.paint(brush, Color.BLUE);
			bullet.move();
			if (bullet.outOfBounds() == true) {
				offScreen.add(bullet);
			}
		}
		
		for (Asteroid asteroid : randomAsteroids) {
			for (Bullet bullet : bullets) {
				if (asteroid.contains(bullet.getCenter()) == true) {
					offScreen.add(bullet);
					offScreenA.add(asteroid);
				}
			}
		}
		
		for (Bullet bullet : offScreen) {
			bullets.remove(bullet);
		}
		
		for (Asteroid asteroid : offScreenA) {
			randomAsteroids.remove(asteroid);
			numAsteroid = randomAsteroids.size();
			
			if (numAsteroid == 0 && stop1 == false) {
				stop1 = true;
				stop2 = true;
				win = true;
				showEnd = counter + 10;
			}
		}
		
		ship.paint(brush, color);
		ship.move();
		
		if (showEnd <= counter && stop2 == true) {
			if (win == true) { //not printing winning screen!
				brush.setColor(Color.BLACK);
				brush.fillRect(0,0,width,height);
				brush.setColor(Color.WHITE);
				brush.drawString("You have won",400,300);
			} else {
				brush.setColor(Color.BLACK);
				brush.fillRect(0,0,width,height);
				brush.setColor(Color.RED);
				brush.drawString("You have lost",400,300);
			}
				
			stopGame();
		}
	}
	
	public void stopGame() {
		on = false;
	}
	
	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		
		a.repaint();
	}
}