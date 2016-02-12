package model;
/**
 * 
 * Interface for load
 *
 */
public interface Iload {
	public boolean loadGame(String filename);

	public void setBuildMode();

	public Model getModel();
}
