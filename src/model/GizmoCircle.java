package model;

import java.awt.Color;

import physics.Circle;
import physics.Vect;

/**
 * Stores the variables for a circle bumper such as its height, width, colour etc.
 **/

public class GizmoCircle extends Gizmo implements IGizmo {

	private Vect centre;
	private double radius;

	private Color colour;

	// x, y coordinates radius
	public GizmoCircle(String n, int x, int y, double rad) {
		super(n, x, y);

		colour = Color.GREEN;
		radius = rad;
		centre = new Vect(x+12.5, y+12.5);
		type = "Circle";
	}

	public void setGizmoCircle(int x, int y, double rad) {
		xpos = x;
		ypos = y;
		colour = Color.GREEN;
		radius = rad;
		centre = new Vect(x+12.5, y+12.5);
	}
	
	/*
	 * General Getterss for the Circles
	 * Centre
	 * X and Y position
	 * Radius
	 * Colour
	 * Returning the circle itself
	 */
	public Vect getCentre() {
		return centre;
	}
	
	public double getXPos(){
		return xpos;
	}

	public double getYPos(){
		return ypos;
	}
	
	public double getRadius() {
		return radius;
	}

	public Circle getCircle() {
		return new Circle(centre.x(), centre.y(), radius);
	}
	
	public Color getColour(){
		return colour;
	}
	
	/*
	 * Clones the circle into the build mode view based on
	 * the mouse clicked co-ordinated
	 */
	public GizmoCircle cloneToPos(int x, int y){
		return new GizmoCircle("C" + String.valueOf(x)+String.valueOf(y), (int) x, (int) y, radius);
	}
	
	/*
	 * How a circle reacts if it is connected to by another
	 * gizmo
	 * It will change colour from orange to gree or vice versa
	 */
	public void connectReaction() {
		if (colour == Color.orange){
			colour = Color.green;
		} else colour = Color.ORANGE;	
	}


	
}
