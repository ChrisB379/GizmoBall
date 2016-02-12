package model;
import java.awt.Color;
import java.util.ArrayList;

import physics.LineSegment;
import physics.Vect;

/**
 * Stores the values of an absorber such as its height, width, colour etc
 * When a ball hits the absorber, the absorber stops the ball and holds
 * it at the right hand corner. When the ball is released which occurs
 * when a key is pressed the ball shoots straight up from the absorber.
 *
 */

public class GizmoAbsorber extends Gizmo implements IGizmo {

	//Top left corner of the square (x,y)
	//Bottom right corner of the square (x,y)
	private int rx;
	private int by;	
	private int width;
	private int height;
	private Line l1, l2, l3, l4;	
	private Color colour;
	private boolean keyBindSet = false;
	private char boundKey;

	public GizmoAbsorber(String n, int x, int y, int w, int h){

		super(n, x, y);
		rx = x+w;
		by = y+h;
		width = w;
		height = h;
		type = "Absorber";
		//top horizontal line
		l1 = new Line(x, y, rx, y, w);

		//bottom horizontal line
		l2 = new Line(x, by, rx, by, w);

		//left vertical line
		l3 = new Line(x,y, x, by, w);

		//right vertical line
		l4 = new Line(rx,y, rx, by, w);

		colour = Color.MAGENTA;

	}

	public ArrayList<LineSegment> getLineSeg(){
		ArrayList<LineSegment> lineSegs = new ArrayList<LineSegment>();
		lineSegs.add(l1.getLineSeg());
		lineSegs.add(l2.getLineSeg());
		lineSegs.add(l3.getLineSeg());
		lineSegs.add(l4.getLineSeg());
		return lineSegs;
	}
	
	/*
	 * Method for setting the ball when it has been absorbed
	 */
	public void absorb(Ball b){
		b.setPosition((xpos + width) - 12.5, ypos - b.getRadius());
		b.absorb();
		b.setVelo(new Vect(0, 0));
	}

	/*
	 * Method for setting the ball when a key is pressed
	 * to released the ball from the absorber
	 */
	public void release(Ball b){
		b.release();
		b.setVelo(new Vect(0, -1250));
	}
	
	/*
	 * General Getters and Setters for the Absorber
	 * X and Y position
	 * Width and Height
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

	public int getHeight(){
		return height;
	}

	public Color getColour(){
		return colour;
	}
	
	/*
	 * Used to move the absorber into the build move through cloning
	 */
	public GizmoAbsorber cloneToPos(int x,int y){
		System.out.println(x);
		GizmoAbsorber a =  new GizmoAbsorber("A" + String.valueOf(x)+String.valueOf(y), x, y, (500-x), height);
		if(isKeyBound()) {
			a.setKeyBind();
			a.setBoundKey(boundKey);

		}
		return a;
	}

	public void connectReaction(){
		if (colour == Color.magenta){
			colour = Color.pink;
		} else colour = Color.magenta;	
	}
	
	/*
	 * Setters and Getters for binding a key
	 * to the absorber
	 */
	public void setKeyBind() {
		keyBindSet = true;
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

	public void setKeyBindOff() {
		keyBindSet = false;
		
	}



}
