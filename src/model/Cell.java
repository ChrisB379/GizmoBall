package model;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Stores the values of a cell in the grid for build mode.
 * The Cell contains whether a it is occupied or not, this 
 * allows gizmos to be added and removed
 * 
 **/

public class Cell {
	
	private IGizmo gizmo;
	private boolean occupied = false;
	private Rectangle rectangle;
	
	public Cell(Rectangle r){
		rectangle = r;
	}
	
	/*
	 * Checks if a cell is occupied
	 * If it is then a gizmo cannot be added
	 * If it is not then a gizmo can be added
	 */
	public boolean addGizmo(IGizmo g){
		if(!occupied){
		gizmo = g;
		return true;
		} else return false;
	}
	
	/*
	 * Checks if a cell is occupied
	 * If it is then gizmo inside it can be removed
	 * If it is not then there is nothing to remove
	 */
	public boolean removeGizmo(){
		if(occupied){
		gizmo = null;
		occupied = false;
		return true;
		} else return false;
	}
	
	public IGizmo getGizmo(){
		return gizmo;
	}
	
	/*
	 * Setting and getting occupied once a gizmo
	 * has been added/removed from a cell
	 */
	
	public void setOccupied(boolean b){
		occupied = b;
	}
	
	public boolean isOccupied(){
		return occupied;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public Point getPos(){
		return new Point((int) rectangle.getX(),(int) rectangle.getY());
	}

}
