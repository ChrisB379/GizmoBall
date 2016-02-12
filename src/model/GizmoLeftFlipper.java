package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;

/**
 * Stores the variables for the left flipper such as its height, width, colour etc
 * The flippers for our gizmoball game are different from the MIT ones,
 * they are made up of two circles and two lines to give it an unique shape.
 *
 */

public class GizmoLeftFlipper extends Gizmo implements IGizmo{

	private int width;
	private int bigRadius;
	private int smallRadius;
	private Line topLine;
	private Line botLine;
	private GizmoCircle bigCircle, smallCircle;
	private Point p1, p2, p3, p4, p5, p6, centre;
	private Color colour;
	private boolean rotating;
	private int totalRotation;
	private boolean rotateBack;
	private boolean keyBindSet = false;
	private char boundKey;

	public GizmoLeftFlipper(String n, int x, int y) {

		super(n, x, y);
		type = "LeftFlipper";

		// flipper pointing straight down
		p1 = new Point(xpos, ypos); // big circle
		p2 = new Point(xpos, ypos + 30); // small circle
		p3 = new Point(xpos - 7, ypos); // top line
		p4 = new Point(xpos - 4, ypos + 30); // top line 2nd coord
		p5 = new Point(xpos + 7, ypos); // bottom line first coord
		p6 = new Point(xpos + 4, ypos + 30); // bottom line second coord
		centre = p1;

		width = 40;
		bigRadius = 7;
		smallRadius = 4;
		colour = Color.ORANGE;

		bigCircle = new GizmoCircle("c1", x, y, 7);
		smallCircle = new GizmoCircle("c2", x, y + 30, 4);
		topLine = new Line(xpos - 7, ypos, xpos - 4, ypos + 30, 20);
		botLine = new Line(xpos + 7, ypos, xpos + 4, ypos + 30, 20);

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
		circ.add(new Circle(p1, 10));
		circ.add(new Circle(p2, 5));
		// putting circles on the end of the lines
		circ.add(new Circle(p3, 0));
		circ.add(new Circle(p4, 0));
		circ.add(new Circle(p5, 0));
		circ.add(new Circle(p6, 0));
		return circ;
	}
	/*
	 * Returns general getters for flippers
	 * X and Y position of circles
	 * Width
	 * Radius of big and small circles
	 * Position of smaller circle
	 * Colour
	 */
	public int getX() {
		return xpos;
	}

	public int getY() {
		return ypos;
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
	
	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	/*
	 * Returns the points
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
		bigCircle.setGizmoCircle(p1.x, p1.y, 7);
		smallCircle.setGizmoCircle(p2.x, p2.y, 4);
		topLine.setLine(p3.x, p3.y, p4.x, p4.y, width);
		botLine.setLine(p5.x, p5.y, p6.x, p6.y, width);
	}
	
	/*
	 * Methods for rotating flippers
	 */
	public void rotateFlipper() {
		rotateAroundCentre(-15);
		totalRotation = totalRotation + 15;
	}

	public void rotateFlipperBack() {
		rotateAroundCentre(15);
		totalRotation = totalRotation - 15;
	}

	
	public void setRotation(boolean b){
		rotating = b;
	}
	
	public void setRotateBack(boolean b){
		rotateBack = b;
	}


	public boolean getRotateBack(){
		return rotateBack;
	}
	
	public int getTotalRotation(){
		return totalRotation;
	}
	
	public boolean getRotating() {
		return rotating;
	}

	/*
	 * Used to clone the flipper into the build mode as it is first
	 * made outside of the screen
	 */
	public GizmoLeftFlipper cloneToPos(int x, int y) {
		GizmoLeftFlipper f = new GizmoLeftFlipper("LF" + String.valueOf(x)+String.valueOf(y), x + 12, y + 10);
		if(isKeyBound()) {
			f.setKeyBind();
			f.setBoundKey(boundKey);
		}
		return f;
	}
	
	/*
	 * How flippers react when they are connected to a gizmo
	 */
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
	 * Getters and Setters for binding keys to
	 * the flippers
	 */
	public void setKeyBind() {
		keyBindSet = true;
	}
	
	public void setKeyBindOff() {
		keyBindSet = false;
	}
	
	public boolean isKeyBound() {
		if(keyBindSet) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public char getBoundKey() {
		return boundKey;
	}
	
	public void setBoundKey(char c) {
		boundKey = c;
	}


}
