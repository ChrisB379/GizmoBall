package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import physics.LineSegment;
import physics.Circle;

/**
 * Stores the variables for a triangle bumper such as its height, width, colour
 * etc. Also allows the triangle bumper to be rotated.
 * 
 */

public class GizmoTriangle extends Gizmo implements IGizmo {

	private Point p1, p2, p3, centre;
	private Line l1, l2, l3, l4;
	private int width;
	private Color colour;
	private double rot;

	public GizmoTriangle(String n, int x, int y, int w) {
		super(n, x, y);
		rot = 0;
		type = "Triangle";
		p1 = new Point(xpos, ypos);
		p2 = new Point(xpos + w, ypos);
		p3 = new Point(xpos, ypos + w);
		centre = new Point(x + (w / 2), y + (w / 2));
		width = w;

		// top horizontal line
		l1 = new Line(xpos, ypos, xpos, ypos + w, w);

		// bottom horizontal line
		l2 = new Line(xpos, ypos + w, xpos + w, ypos, w);

		// left vertical line
		l3 = new Line(xpos + w, ypos, xpos, ypos, w);
		colour = Color.YELLOW;

	}

	public ArrayList<LineSegment> getLineSeg() {
		ArrayList<LineSegment> lineSegs = new ArrayList<LineSegment>();
		lineSegs.add(l1.getLineSeg());
		lineSegs.add(l2.getLineSeg());
		lineSegs.add(l3.getLineSeg());
		return lineSegs;
	}

	public ArrayList<Circle> getCircles() {
		ArrayList<Circle> circ = new ArrayList<Circle>();
		circ.add(new Circle(p1, 0));
		circ.add(new Circle(p2, 0));
		circ.add(new Circle(p3, 0));

		return circ;
	}

	/*
	 * Returns the points of the triangle
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

	public int getWidth() {
		return width;
	}

	/**
	 * Rotates the Triangle around its corresponding centre point.
	 * 
	 * @param angle
	 *            The angle of the rotation.
	 */

	public void rotateAroundCentre(double angle) {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
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
		l1.setLine(p1.x, p1.y, p2.x, p2.y, width);
		l2.setLine(p2.x, p2.y, p3.x, p3.y, width);
		l3.setLine(p3.x, p3.y, p1.x, p1.y, width);
		rot = rot + angle;
		if (rot > 270) {
			rot = 0;
		}
	}

	public Color getColour() {
		return colour;
	}

	public void changeColour() {
		colour = Color.BLUE;
	}

	public Line getL4() {
		return l4;
	}

	public void setL4(Line l4) {
		this.l4 = l4;
	}
	
	/*
	 * Cloning a triangle into build mode
	 * Also takes into account the rotation of the triangle
	 * to ensure it is added at the right rotation angle
	 */
	public GizmoTriangle cloneToPos(int x, int y) {
		if (rot == 90) {
			GizmoTriangle tr = new GizmoTriangle("T" + String.valueOf(x)+String.valueOf(y), x, y, width);
			tr.rotateAroundCentre(90);
			return tr;
		}
		if (rot == 180) {
			GizmoTriangle tr = new GizmoTriangle("T" + String.valueOf(x)+String.valueOf(y), x, y, width);
			tr.rotateAroundCentre(180);
			return tr;
		}
		if (rot == 270) {
			GizmoTriangle tr = new GizmoTriangle("T" + String.valueOf(x)+String.valueOf(y), x, y, width);
			tr.rotateAroundCentre(270);
			return tr;
		} else {
			return new GizmoTriangle("T" + String.valueOf(x)+String.valueOf(y), x, y, width);
		}
	}

	public double getRot() {
		return rot;
	}

	/*
	 * How a triangle reacts when connected to by another gizmo
	 */
	public void connectReaction() {
		if (colour == Color.yellow) {
			colour = Color.red;
		} else
			colour = Color.yellow;
	}

}
