package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;

/**
 * Stores the variables for the right flipper such as its height, width, colour
 * etc The flippers for our gizmoball game are different from the MIT ones, they
 * are made up of two circles and two lines to give it an unique shape.
 * 
 */

public class GizmoRightFlipper extends Gizmo implements IGizmo {

	private int xPos;
	private int yPos;
	private int width;
	private int bigRadius;
	private int smallRadius;
	private Line topLine;
	private Line botLine;
	private GizmoCircle bigCircle, smallCircle;
	private Point p1, p2, p3, p4, p5, p6, centre;
	private Color colour;
	private int totalRotation = 0;
	private boolean rotateBack;
	private boolean rotating;
	private boolean keyBindSet = false;
	private char boundKey;

	public GizmoRightFlipper(String n, int x, int y) {
		super(n, x, y);
		type = "RightFlipper";

		// Flipper pointing down
		p1 = new Point(x, y); // big circle
		p2 = new Point(x, y + 30); // small circle
		p3 = new Point(x - 7, y); // left side line
		p4 = new Point(x - 4, y + 30); // left side line 2nd co-ord
		p5 = new Point(x + 7, y); // right side line first co-ord
		p6 = new Point(x + 4, y + 30); // right side line second co-ord
		centre = p1;

		xPos = x;
		yPos = y;
		width = 40;
		bigRadius = 7;
		smallRadius = 4;
		colour = Color.ORANGE;

		bigCircle = new GizmoCircle("c1", x, y, 10);
		smallCircle = new GizmoCircle("c2", x, y + 45, 5);
		topLine = new Line(x - 10, y, x - 5, y + 45, 30);
		botLine = new Line(x + 10, y, x + 5, y + 45, 30);
	}

	// Collision
	public ArrayList<LineSegment> getLineSeg() {
		ArrayList<LineSegment> lineSegs = new ArrayList<LineSegment>();
		lineSegs.add(topLine.getLineSeg());
		lineSegs.add(botLine.getLineSeg());
		return lineSegs;
	}

	public ArrayList<Circle> getCircles() {
		ArrayList<Circle> circ = new ArrayList<Circle>();
		circ.add(new Circle(p1, 7));
		circ.add(new Circle(p2, 4));
		// putting circles on the end of the lines
		circ.add(new Circle(p3, 0));
		circ.add(new Circle(p4, 0));
		circ.add(new Circle(p5, 0));
		circ.add(new Circle(p6, 0));
		return circ;
	}
	
	/*
	 * General getters for the flippers
	 * X and Y
	 * Width and Height
	 * Big and small circles radius
	 * Colour
	 */
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getBigRad() {
		return bigRadius;
	}

	public int getSmallRad() {
		return smallRadius;
	}
	
	public Color getColour() {
		return colour;
	}


	/*
	 * Getters for returning points
	 */
	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public Point getP3() {
		return p3;
	}

	public Point getP4() {
		return p4;
	}

	public Point getP5() {
		return p5;
	}

	public Point getP6() {
		return p6;
	}

	public void rotateAroundCentre(double angle) {
		// System.out.println("At the start " + getP6());

		ArrayList<Point> points = new ArrayList<Point>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		points.add(p6);
		for (Point point : points) {
			double[] pt = { point.x, point.y };
			AffineTransform.getRotateInstance(Math.toRadians(angle), centre.x,
					centre.y).transform(pt, 0, pt, 0, 1); // specifying to use
															// this double[] to
															// hold coords
			double newX = pt[0];
			double newY = pt[1];
			point.setLocation(newX, newY);
		}
		colour = Color.ORANGE;

		// think this is useless
		bigCircle.setGizmoCircle(p1.x, p1.y, 10);
		smallCircle.setGizmoCircle(p2.x, p2.y, 5);
		topLine.setLine(p3.x, p3.y, p4.x, p4.y, width);
		botLine.setLine(p5.x, p5.y, p6.x, p6.y, width);
	}

	public void rotateFlipper() {
		rotateAroundCentre(15);
		totalRotation = totalRotation + 15;
	}

	public void rotateFlipperBack() {
		rotateAroundCentre(-15);
		totalRotation = totalRotation - 15;
	}


	/*
	 * Methods for rotating the flipper
	 * Booleans to set its been rotated
	 * or to set it should now rotate back
	 */
	public void setRotation(boolean b) {
		rotating = b;
	}

	public void setRotateBack(boolean b) {
		rotateBack = b;
	}



	public int getTotalRotation() {
		return totalRotation;
	}

	public boolean getRotating() {
		return rotating;
	}

	public boolean getRotateBack() {
		return rotateBack;
	}

	/*
	 * Cloning to add the flipper into build mode
	 * as it is created outside of the screen and
	 * moved in
	 */
	public GizmoRightFlipper cloneToPos(int x, int y) {
		GizmoRightFlipper f = new GizmoRightFlipper("RF" + String.valueOf(x)
				+ String.valueOf(y), x + 12, y + 10);
		if (isKeyBound()) {
			f.setKeyBind();
			f.setBoundKey(boundKey);

		}
		return f;
	}

	/*
	 * How the flipper reacts when connected to another gizmo
	 */
	@Override
	public void connectReaction() {
		if (totalRotation >= 90) {
			rotating = false;
			rotateBack = true;
		} else {
			rotating = true;
			rotateBack = false;
		}

	}
	
	/*
	 * Getters and Setters for key binding
	 * the flipper to use selected keys
	 */
	public void setKeyBind() {
		keyBindSet = true;
	}

	public boolean isKeyBound() {
		if (keyBindSet) {
			return true;
		} else {
			return false;
		}
	}

	public char getBoundKey() {
		return boundKey;
	}

	public void setBoundKey(char c) {
		boundKey = c;
	}

	public void setKeyBindOff() {
		keyBindSet = false;
		
	}

}
