package model;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * Defines a 20x20 grid for build mode
 *
 */

public class Grid {
	
	private Cell[][] grid = new Cell[20][20];
	
	public Grid(){
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				grid[i][j] = new Cell(new Rectangle(i*25, j*25, 23, 23));
			}
		}
	}
	
	/*
	 * Returns a specific cell
	 */
	public Cell cellAt(int x, int y){
		return grid[x][y];
	}
	
	/*
	 * Used to return the grid
	 */
	public Cell[][] getGrid(){
		return grid;
	}
	
	public Cell cellContaining(int x, int y){
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				if(grid[i][j].getRectangle().contains(new Point(x,y))){
					return grid[i][j];
				}
			}
	}
		return null;
	}

}
