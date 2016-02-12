package model;

/** 
 *  Interface for the gizmos
 **/

public interface IGizmo {
	public String getName();
	public String getType();
	public int getX();
	public int getY();
	public abstract IGizmo cloneToPos(int x, int y);
	public abstract void connectionMade();
	public IGizmo getConnection();
	public void connectReaction();
	public void setConnection(IGizmo gizmo);
}
