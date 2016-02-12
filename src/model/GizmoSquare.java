package model;
import java.awt.Color;
import java.util.ArrayList;

import physics.LineSegment;
import physics.Circle;

/**
 * Stores the variables for a square bumper such as its height, width, colour etc.
 *
 */

public class GizmoSquare extends Gizmo implements IGizmo{

	//Top left corner of the square (x,y)
	//Bottom right corner of the square (x,y)
	private int bottomRx;
	private int bottomRy;
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private int width;
	private Line l1, l2, l3, l4;
	private Color colour;
	@SuppressWarnings("unused")
	private String name;


	public GizmoSquare(String n, int x,int y, int w){

		super(n, x, y);
		setBottomRx(x+w);
		setBottomRy(y+w);
		width = w;
		name = n;
		type = "Square";
		//top horizontal line
		l1 = new Line(x, y, x+w, y, w);

		//bottom horizontal line
		l2 = new Line(x, y+w, x+w, y+w,w);

		//left vertical line
		l3 = new Line(x,y, x, y+w, w);

		//right vertical line
		l4 = new Line(x+w,y, x+w, y+w, w);

		circles.add( new Circle(xpos, ypos, 0));
		circles.add( new Circle(xpos, ypos + width, 0));
		circles.add( new Circle(xpos + width, ypos, 0));
		circles.add( new Circle(xpos + width, ypos + width, 0));

		colour = Color.RED;
		


	}

	public ArrayList<LineSegment> getLineSeg(){
		ArrayList<LineSegment> lineSegs = new ArrayList<LineSegment>();
		lineSegs.add(l1.getLineSeg());
		lineSegs.add(l2.getLineSeg());
		lineSegs.add(l3.getLineSeg());
		lineSegs.add(l4.getLineSeg());
		return lineSegs;
	}


	public ArrayList<Circle> getCircles(){
		return circles;
	}

	/*
	 * General Getters and Setters for squares
	 * X and Y position
	 * Width
	 * Colour
	 */
	public int getX(){
		return xpos;
	}

	public int getY(){
		return ypos;
	}

	public int getWidth(){
		return width;
	}

	public Color getColour(){
		return colour;
	}

	public int getBottomRx() {
		return bottomRx;
	}

	public void setBottomRx(int bottomRx) {
		this.bottomRx = bottomRx;
	}

	public int getBottomRy() {
		return bottomRy;
	}

	public void setBottomRy(int bottomRy) {
		this.bottomRy = bottomRy;
	}

	/*
	 * Used to clone squares into the build mode
	 */
	public GizmoSquare cloneToPos(int x, int y){
		return new GizmoSquare("S" + String.valueOf(x)+String.valueOf(y), x, y, width);
	}
	
	/*
	 * How a square reacts when connected to another gizmo
	 */
	public void connectReaction(){
		if (colour == Color.red){
			colour = Color.blue;
		} else colour = Color.red;	
	}

}
