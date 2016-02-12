package model;

import physics.Circle;
import physics.LineSegment;

/**
 * Edited version of Murray Wood Demonstration of MVC and MIT Physics Collisions 2014 VerticalLine class
 * Now puts smaller circles on the end of every line for collisions
 */

public class Line implements IGizmo{

	private int xpos;
	private int ypos;
	private int x2pos;
	private int y2pos;
	private int width;
	private LineSegment ls;
	private Circle c,c2;

	public Line(int x, int y, int x2, int y2, int w) {
		xpos = x;
		ypos = y;
		x2pos = x2;
		y2pos = y2;
		width = w;
		ls = new LineSegment(x, y, x2, y2);
		c = new Circle(x, y, 0);
		c2 = new Circle(x+w, y +w, 0);
	}
	
	public void setLine(int x, int y, int x2, int y2, int w){
		xpos = x;
		ypos = y;
		x2pos = x2;
		y2pos = y2;
		width = w;
		ls = new LineSegment(x, y, x2, y2);
		c = new Circle(x, y, 0);
		c2 = new Circle(x+w, y +w, 0);
	}

	public LineSegment getLineSeg() {
		return ls;
	}
	
	/*
	 * Getters for the circles on the 
	 * start and end of the line
	 */
	
	//Circle at position x
	public Circle getStartCircle(){
		return c;
	}
	
	//Circle at position x+w
	public Circle getEndCircle(){
		return c2;
	}
	
	/*
	 * General Getters for lines
	 * X and Y position of start and end of the line
	 * Width
	 */
	public int getX() {
		return xpos;
	}

	public int getY() {
		return ypos;
	}
	
	public int getX2() {
		return x2pos;
	}

	public int getY2() {
		return y2pos;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGizmo cloneToPos(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionMade() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectReaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConnection(IGizmo gizmo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGizmo getConnection() {
		// TODO Auto-generated method stub
		return null;
	}


}
