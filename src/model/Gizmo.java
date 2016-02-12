package model;

/**
 * 
 * Stores the values of Gizmos, this is the super class for all gizmos.
 *
 */

public abstract class Gizmo {

	protected int xpos, ypos, height, width;
	protected String name;
	protected String type;

	protected IGizmo connection;

	
	public Gizmo(String n, int x, int y) {
		
		xpos = x;
		ypos = y;
		name = n;
		
	}
	
	/*
	 * Getters for the gizmos name and what type it is
	 * i.e circle, square etc
	 */
	public String getName() { return name; }
	public String getType() { return type; }
	
	/*
	 * Gets the x and y co-ords of the gizmo
	 */
	public int getX() { return xpos; }
	public int getY() { return ypos; }
	
	/*
	 * Setting and getting connections
	 */

	public void setConnection(IGizmo g){
		connection = g;
	}
	
	public void connectionMade(){
		connection.connectReaction();
	}
	
	public IGizmo getConnection(){
		return connection;
	}

	
	/*
	 * Deals with moving the gizmo into the board where 
	 * the mouse click happens
	 */
	public abstract IGizmo cloneToPos(int x, int y);
	
	public abstract void connectReaction();

	
}
