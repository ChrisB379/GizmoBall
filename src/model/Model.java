package model;

import java.util.ArrayList;
import java.util.Observable;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * The base of this class was given in the Murray Wood Demonstration of MVC and
 * MIT Physics Collisions 2014 It has been modified so that it now deals with
 * the collisions between a ball and bumpers, absorbers and flippers. It also
 * adds a score each time a bumper is hit
 * 
 */
public class Model extends Observable {

	private ArrayList<Line> lines;
	private ArrayList<GizmoCircle> gizmoCircles;
	private ArrayList<GizmoSquare> gizmoSquares;
	private ArrayList<GizmoTriangle> gizmoTriangles;
	private ArrayList<GizmoAbsorber> absorbers;
	private ArrayList<GizmoLeftFlipper> lflippers;
	private ArrayList<GizmoRightFlipper> rflippers;
	private Ball ball;
	private Walls gws;
	private boolean absorbing;
	private boolean squarebumped;
	private boolean trianglebumped;
	private boolean circlebumped;
	private boolean ballExists = true;
	private GizmoAbsorber currentAbsorber;
	private GizmoSquare currentSquare;
	private GizmoTriangle currentTriangle;
	private GizmoCircle currentCircle;
	private int score;
	private int abKey, lfKey, rfKey;
	private Grid grid = new Grid();
	private Cell selectedCell;
	private Cell movingCell;
	private Cell connectingCell;
	private String file;
	private float gravity;
	private float friction;
	private float friction2;
	@SuppressWarnings("unused")
	private double xV, xY;

	public Model() {

		// Ball position (12.5,12.5) in pixels. Ball velocity (100, 100) pixels
		// per
		// tick
		ball = new Ball(12.5, 12.5, 100, 100);
		gravity = 20f;
		friction = 0.025f;
		friction2 = 0.025f;
		// Wall size 500 x 500 pixels
		gws = new Walls(0, 0, 500, 500);
		// Lines added in Main
		lines = new ArrayList<Line>();
		gizmoSquares = new ArrayList<GizmoSquare>();
		gizmoTriangles = new ArrayList<GizmoTriangle>();
		absorbers = new ArrayList<GizmoAbsorber>();
		gizmoCircles = new ArrayList<GizmoCircle>();
		lflippers = new ArrayList<GizmoLeftFlipper>();
		rflippers = new ArrayList<GizmoRightFlipper>();
		score = 0;
	}

	public void moveBall() {

		setBallGravity(gravity);
		setBallFriction(friction, friction2);

		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
		if (ball != null && !ball.stopped()) {

			if (!ball.absorbed()) {
				addPhysics();
			}
			
			/*
			 * rotating the right flippers
			 * Booleans are used to check if the flippers have rotated or not
			 * Once the rotation total hits 90 then rotating is set to false 
			 * to prevent it going any further.
			 * 
			 * Otherwise if it is greater than 0 i.e it has been rotated
			 * the rotate back methods are called to get it back to the start
			 * 
			 * f.getRotateBack() as a way to allow us to keep the flipper rotated at
			 * 90 degrees for as long as we hold the key down
			 */
			for (GizmoRightFlipper f : rflippers) {
				if (f.getRotating()) {
					f.rotateFlipper();
					if (f.getTotalRotation() >= 90) {
						f.setRotation(false);
					}
				} else if (f.getTotalRotation() > 0 && f.getRotateBack()) {
					f.rotateFlipperBack();
					if (f.getTotalRotation() == 0)
						f.setRotateBack(false);
				}
			}

			/*
			 * Rotating the left flippers
			 * Same as above
			 */
			for (GizmoLeftFlipper f : lflippers) {
				if (f.getRotating()) {
					f.rotateFlipper();
					if (f.getTotalRotation() >= 90) {
						f.setRotation(false);
					}
				} else if (f.getTotalRotation() > 0 && f.getRotateBack()) {
					f.rotateFlipperBack();
					if (f.getTotalRotation() == 0)
						f.setRotateBack(false);
				}
			}
			/*
			 * Collision Details
			 * Deals with if a certain gizmo has been bumped which relats to connections
			 * Also checks the absorber collision
			 */
			CollisionDetails cd = timeUntilCollision();
			double tuc = cd.getTuc();
			if (tuc > moveTime) {
				// No collision ...
				ball = movelBallForTime(ball, moveTime);
			} else if (absorbing) {
				// A ball is currently in an absorber...
				ball = movelBallForTime(ball, tuc);
				currentAbsorber.absorb(ball);
				if (currentAbsorber.getConnection() != null && tuc != 0)
					currentAbsorber.connectionMade();
			} else if (squarebumped) {
				// A ball is currently in a square bumper...
				ball = movelBallForTime(ball, tuc);
				ball.setVelo(cd.getVelo());
				if (currentSquare.getConnection() != null && tuc != 0)
					currentSquare.connectionMade();
				score += 1000;

			} else if (trianglebumped) {
				// A ball is currently in a triangle bumper...
				ball = movelBallForTime(ball, tuc);
				ball.setVelo(cd.getVelo());
				if (currentTriangle.getConnection() != null && tuc != 0)
					currentTriangle.connectionMade();
				score += 1500;

			} else if (circlebumped) {
				// A ball is currently in a circle bumper...
				ball = movelBallForTime(ball, tuc);
				ball.setVelo(cd.getVelo());
				if (currentCircle.getConnection() != null && tuc != 0)
					currentCircle.connectionMade();
				score += 500;

			} else {
				// We've got a collision in tuc
				ball = movelBallForTime(ball, tuc);
				// Post collision velocity ...
				ball.setVelo(cd.getVelo());
			}
		}

		// Notify observers ... redraw updated view
		this.setChanged();
		this.notifyObservers();

	}

	public void update() {

		this.setChanged();
		this.notifyObservers();
	}

	private Ball movelBallForTime(Ball ball, double time) {

		double newX = 0.0;
		double newY = 0.0;
		double xVel = ball.getVelo().x();
		double yVel = ball.getVelo().y();
		newX = ball.getExactX() + (xVel * time);
		newY = ball.getExactY() + (yVel * time);
		ball.setExactX(newX);
		ball.setExactY(newY);
		return ball;
	}

	private CollisionDetails timeUntilCollision() {

		// Find Time Until Collision and also, if there is a collision, the new
		// speed vector.
		// Create a physics.Circle from Ball
		Circle ballCircle = ball.getCircle();
		Vect ballVelocity = ball.getVelo();
		Vect newVelo = new Vect(0, 0);

		// Now find shortest time to hit a vertical line or a wall line
		double shortestTime = Double.MAX_VALUE;
		double time = 0.0;

		// Time to collide with 4 walls
		ArrayList<LineSegment> lss = gws.getLineSegments();
		for (LineSegment line : lss) {
			time = Geometry.timeUntilWallCollision(line, ballCircle,
					ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
				absorbing = false;
				squarebumped = false;
				trianglebumped = false;
				circlebumped = false;
			}
		}

		// Time to collide with any horizontal lines
		for (Line line : lines) {
			LineSegment ls = line.getLineSeg();
			time = Geometry
					.timeUntilWallCollision(ls, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
				ball.release();
			}

			// Time to collide with the start point of the line
			Circle c = line.getStartCircle();
			time = Geometry.timeUntilCircleCollision(c, ballCircle,
					ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectCircle(c.getCenter(),
						new Vect(ball.getExactX(), ball.getExactY()),
						ball.getVelo(), 1.0);
				absorbing = false;
				squarebumped = false;
				trianglebumped = false;
				circlebumped = false;
			}

			// Time to collide with the end point of the line
			Circle c2 = line.getEndCircle();
			time = Geometry.timeUntilCircleCollision(c2, ballCircle,
					ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectCircle(c2.getCenter(),
						new Vect(ball.getExactX(), ball.getExactY()),
						ball.getVelo(), 1.0);
				absorbing = false;
				squarebumped = false;
				trianglebumped = false;
				circlebumped = false;
			}
		}

		// SQUARES
		// Time to collide with squares (including each corner represented
		// by a circle)
		for (GizmoSquare sq : gizmoSquares) {
			ArrayList<Circle> circ = sq.getCircles();
			ArrayList<LineSegment> lineseg = sq.getLineSeg();

			for (Circle c : circ) {
				time = Geometry.timeUntilCircleCollision(c, ballCircle,
						ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectCircle(c.getCenter(), new Vect(
							ball.getExactX(), ball.getExactY()),
							ball.getVelo(), 1.0);
					absorbing = false;
					squarebumped = true;
					trianglebumped = false;
					circlebumped = false;
					currentSquare = sq;
				}
			}
			for (LineSegment ls : lineseg) {
				time = Geometry.timeUntilWallCollision(ls, ballCircle,
						ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
					absorbing = false;
					squarebumped = true;
					trianglebumped = false;
					circlebumped = false;
					currentSquare = sq;
				}
			}
		}

		// TRIANGLES

		// Time to collide with each side of the triangle
		for (GizmoTriangle tr : gizmoTriangles) {
			ArrayList<LineSegment> lineseg = tr.getLineSeg();
			ArrayList<Circle> circ = tr.getCircles();
			for (LineSegment ls : lineseg) {
				time = Geometry.timeUntilWallCollision(ls, ballCircle,
						ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
					absorbing = false;
					squarebumped = false;
					trianglebumped = true;
					circlebumped = false;
					currentTriangle = tr;
				}
			}
			for (Circle c : circ) {
				time = Geometry.timeUntilCircleCollision(c, ballCircle,
						ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectCircle(c.getCenter(), new Vect(
							ball.getExactX(), ball.getExactY()),
							ball.getVelo(), 1.0);
					absorbing = false;
					squarebumped = false;
					trianglebumped = true;
					circlebumped = false;
					currentTriangle = tr;
				}
			}
		}

		// CIRCLE
		// Time to collide with any circle at centre (x,y)
		for (GizmoCircle gc : gizmoCircles) {
			Circle c = gc.getCircle();
			time = Geometry.timeUntilCircleCollision(c, ballCircle,
					ballVelocity);

			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectCircle(c.getCenter(),
						new Vect(ball.getExactX(), ball.getExactY()),
						ball.getVelo(), 1.0);
				absorbing = false;
				squarebumped = false;
				trianglebumped = false;
				circlebumped = true;
				currentCircle = gc;
			}
		}

		// Time to collide with left flippers
		for (GizmoLeftFlipper f : lflippers) {
			if (f.getRotating()) {
				ArrayList<LineSegment> lineseg = f.getLineSeg();
				ArrayList<Circle> circ = f.getCircles();
				for (LineSegment ls : lineseg) {
					time = Geometry.timeUntilRotatingWallCollision(ls,
							new Vect(f.getX(), f.getY()),
							Math.toRadians(-1080), ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectRotatingWall(ls,
								new Vect(f.getX(), f.getY()),
								Math.toRadians(-1080), ballCircle,
								ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}

				for (Circle c : circ) {
					time = Geometry.timeUntilRotatingCircleCollision(c,
							new Vect(f.getX(), f.getY()),
							Math.toRadians(-1080), ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectRotatingCircle(c,
								new Vect(f.getX(), f.getY()), 18.849,
								ballCircle, ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}
			} else {
				ArrayList<LineSegment> lineseg = f.getLineSeg();
				ArrayList<Circle> circ = f.getCircles();

				for (LineSegment ls : lineseg) {
					time = Geometry.timeUntilWallCollision(ls, ballCircle,
							ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry
								.reflectWall(ls, ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}

				for (Circle c : circ) {
					time = Geometry.timeUntilCircleCollision(c, ballCircle,
							ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectCircle(c.getCenter(),
								new Vect(ball.getExactX(), ball.getExactY()),
								ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}
			}
		}

		// Time to collide with right flippers
		for (GizmoRightFlipper f : rflippers) {
			if (f.getRotating()) {
				ArrayList<LineSegment> lineseg = f.getLineSeg();
				ArrayList<Circle> circ = f.getCircles();
				for (LineSegment ls : lineseg) {
					time = Geometry.timeUntilRotatingWallCollision(ls,
							new Vect(f.getX(), f.getY()), Math.toRadians(1080),
							ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectRotatingWall(ls,
								new Vect(f.getX(), f.getY()),
								Math.toRadians(1080), ballCircle,
								ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}

				for (Circle c : circ) {
					time = Geometry.timeUntilRotatingCircleCollision(c,
							new Vect(f.getX(), f.getY()), Math.toRadians(1080),
							ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectRotatingCircle(c,
								new Vect(f.getX(), f.getY()),
								Math.toRadians(1080), ballCircle,
								ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}
			} else {
				ArrayList<LineSegment> lineseg = f.getLineSeg();
				ArrayList<Circle> circ = f.getCircles();

				for (LineSegment ls : lineseg) {
					time = Geometry.timeUntilWallCollision(ls, ballCircle,
							ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry
								.reflectWall(ls, ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}

				for (Circle c : circ) {
					time = Geometry.timeUntilCircleCollision(c, ballCircle,
							ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						newVelo = Geometry.reflectCircle(c.getCenter(),
								new Vect(ball.getExactX(), ball.getExactY()),
								ball.getVelo(), 0.95);
						absorbing = false;
						squarebumped = false;
						trianglebumped = false;
						circlebumped = false;
					}
				}
			}
		}
		// Collision with absorbers
		for (GizmoAbsorber ab : absorbers) {
			ArrayList<LineSegment> lineseg = ab.getLineSeg();

			for (LineSegment ls : lineseg) {
				time = Geometry.timeUntilWallCollision(ls, ballCircle,
						ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					currentAbsorber = ab;
					absorbing = true;
					squarebumped = false;
					trianglebumped = false;
					circlebumped = false;
				}
			}
		}

		return new CollisionDetails(shortestTime, newVelo);
	}

	public Ball getBall() {
		return ball;
	}

	// Horizontal Lines
	public ArrayList<Line> getlines() {
		return lines;
	}

	public void addLine(Line l) {
		lines.add(l);
	}

	// Squares
	public void addSquare(GizmoSquare gizmoSquare) {
		gizmoSquares.add(gizmoSquare);
	}

	public ArrayList<GizmoSquare> getSquares() {
		return gizmoSquares;
	}

	// GizmoCircles
	public ArrayList<GizmoCircle> getGizmoCircles() {
		return gizmoCircles;
	}

	public void addGizmoCircle(GizmoCircle c) {
		gizmoCircles.add(c);
	}

	public void setBallSpeed(double x, double y) {
		ball.setVelo(new Vect(x, y));
	}

	public void addTriangle(GizmoTriangle t) {
		gizmoTriangles.add(t);
	}

	public ArrayList<GizmoTriangle> getTriangles() {
		return gizmoTriangles;
	}

	public void addAbsorber(GizmoAbsorber ab) {
		absorbers.add(ab);
	}

	public ArrayList<GizmoAbsorber> getAbsorbers() {
		return absorbers;
	}

	public void releaseBallFromAbsorber() {
		currentAbsorber.release(ball);
	}

	public void loadBall(Ball ball2) {
		ball = ball2;
	}

	// Left Flippers
	public void addLeftFlipper(GizmoLeftFlipper f) {
		lflippers.add(f);
	}

	public ArrayList<GizmoLeftFlipper> getLeftFlippers() {
		return lflippers;
	}

	/*
	 * Flippers rotate at an angular velocity of 1080 per second The ball moves
	 * every 0.05 seconds meaning there can only be 54 degrees of rotation per
	 * movement The rest methods simply rotate by the further 36 degrees to
	 * complete the 90 rotation
	 */

	public void setLeftRotation() {
		for (GizmoLeftFlipper f : lflippers) {
			f.setRotation(true);
		}
	}

	public void setRightRotation() {
		for (GizmoRightFlipper f : rflippers) {
			f.setRotation(true);
		}
	}

	public void addRightFlipper(GizmoRightFlipper f) {
		rflippers.add(f);
	}

	public ArrayList<GizmoRightFlipper> getRightFlippers() {
		return rflippers;
	}

	/*
	 * Method used to add gizmos into the build mode Is used by the mouse
	 * listener to do so
	 */

	public void addGizmos(IGizmo g) {
		if (g.getType().equals("Square")) {
			gizmoSquares.add((GizmoSquare) g);
			grid.cellAt(g.getX() / 25, g.getY() / 25).addGizmo(g);
		} else if (g.getType().equals("Triangle")) {
			gizmoTriangles.add((GizmoTriangle) g);
			grid.cellAt(g.getX() / 25, g.getY() / 25).addGizmo(g);
		} else if (g.getType().equals("Circle")) {
			gizmoCircles.add((GizmoCircle) g);
			grid.cellAt(g.getX() / 25, g.getY() / 25).addGizmo(g);
		} else if (g.getType().equals("LeftFlipper")) {
			lflippers.add((GizmoLeftFlipper) g);
			grid.cellAt(g.getX() / 25, g.getY() / 25).addGizmo(g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25) + 1).setOccupied(true);
		} else if (g.getType().equals("RightFlipper")) {
			rflippers.add((GizmoRightFlipper) g);
			grid.cellAt(g.getX() / 25, g.getY() / 25).addGizmo(g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25) + 1).setOccupied(true);
		} else if (g.getType().equals("Absorber")) {
			GizmoAbsorber addedAbsorber = (GizmoAbsorber) g;
			boolean safe = true;
			for (int i = g.getX() / 25; i < 20; i++) {
				if (grid.cellAt(i, g.getY() / 25).isOccupied())
					safe = false;
			}
			if (safe) {
				absorbers.add(addedAbsorber);
				for (int i = g.getX() / 25; i < 20; i++) {
					grid.cellAt(i, g.getY() / 25).setOccupied(true);
				}
			}
		}

	}

	/*
	 * Used to remove gizmos from the build mode Also used by the mouse listener
	 * to do so
	 */

	public void removeGizmo(IGizmo g) {

		if (g.getType().equals("Square")) {
			gizmoSquares.remove(g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25)).setOccupied(false);
		} else if (g.getType().equals("Triangle")) {
			gizmoTriangles.remove((GizmoTriangle) g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25)).setOccupied(false);
		} else if (g.getType().equals("Circle")) {
			gizmoCircles.remove((GizmoCircle) g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25)).setOccupied(false);
		} else if (g.getType().equals("LeftFlipper")) {
			lflippers.remove((GizmoLeftFlipper) g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25)).setOccupied(false);
			grid.cellAt(g.getX() / 25, (g.getY() / 25) + 1).setOccupied(false);
		} else if (g.getType().equals("RightFlipper")) {
			rflippers.remove((GizmoRightFlipper) g);
			grid.cellAt(g.getX() / 25, (g.getY() / 25)).setOccupied(false);
			grid.cellAt(g.getX() / 25, (g.getY() / 25) + 1).setOccupied(false);
		} else if (g.getType().equals("Absorber")) {
			absorbers.remove(g);
			for (int i = g.getX() / 25; i < 20; i++) {
				grid.cellAt(i, g.getY() / 25).setOccupied(false);
			}
		}
	}

	/*
	 * Removes all gizmos from the board
	 */
	public void clearBoard() {
		gizmoCircles.clear();
		gizmoSquares.clear();
		gizmoTriangles.clear();
		lflippers.clear();
		rflippers.clear();
		absorbers.clear();
	}
	
	/*
	 * Deletes the ball
	 */
	public void deleteBall() {
		ball = null;
		ballExists = false;
	}

	/*
	 * Checking if a ball has been added and exists
	 */
	public boolean ballExists() {
		if (ballExists) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setBallExists() {
		ballExists = true;
	}

	/*
	 * Sets the wall size for the main menu where
	 * there is a mini board with a ball bouncing across the screen
	 */
	public void setWallSize() {
		gws = new Walls(0, 0, 480, 50);
	}

	public Grid getGrid() {
		return grid;
	}

	/*
	 * Set and get the selected cells related in build mode
	 */
	public void setSelectedCell(Cell c) {
		selectedCell = c;
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	/*
	 * Returns the users score
	 */
	public int getScore() {
		return score;
	}
	
	/*
	 * Setting the friction and Gravity
	 * Adding the physics to the ball
	 * Allowing the user to set ball velocity
	 */
	public void setBallFriction(float f, float f2) {
		ball.setFriction(f, f2);
	}

	public void setModelFriction(float f, float f2) {

		friction = f;
		friction2 = f2;
	}
	
	public void setModelGravity(float f) {
		gravity = f;
	}

	private void setBallGravity(float f) {
		ball.setGravity(f);

	}

	public float getGravity() {
		return gravity;
	}
	
	public void setModelxV(double x) {
		xV = x;
	}
	
	public void setModelxY(double x) {
		xY = x;
	}

	public float getFriction() {
		return friction;
	}
	
	public float getFriction2() {
		return friction2;
	}

	public void addPhysics() {
		ball.addFriction();
		ball.addGravity();
	}
	
	/*
	 * Flipper methods to make them rotate back to 0 degrees
	 * and not sick at 90
	 */
	public void setGoBackLeft() {
		for (GizmoLeftFlipper f : lflippers) {
			f.setRotateBack(true);
		}
	}

	public void setGoBackRight() {
		for (GizmoRightFlipper f : rflippers) {
			f.setRotateBack(true);
		}
	}
	

	/*
	 * Used for build mode
	 * Sets the cell that a gizmo is being moved to
	 */
	public void setMovingCell(Cell c) {
		movingCell = c;
	}

	public Cell getMovingCell() {
		return movingCell;
	}

	public boolean movingCellIsSet() {
		if (movingCell != null)
			return true;
		else
			return false;
	}
	
	/*
	 * Gets and sets the cell a gizmo 
	 * is being connected to
	 */
	public void setConnectingCell(Cell c) {
		connectingCell = c;
	}

	public Cell getConnectingCell() {
		return connectingCell;
	}

	public void makeBall() {
		ball = new Ball(12.5, 12.5, 1000, 1000);
	}

	

	/*
	 * Load passes in a string for filename to allow the restart method to load the correct file
	 */
	public String getFile(){
		return file;
	}
	
	public void setFile(String f) {
		file = f;

	}

}
