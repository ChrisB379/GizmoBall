package model;

import physics.Vect;

/**
 * From the Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 **/

public  class CollisionDetails {
	private double tuc;
	private Vect velo;

	public CollisionDetails(double t, Vect v) {
		tuc = t;
		velo = v;
	}
	
	/*
	 * Returns the time until collision
	 */
	public double getTuc() {
		return tuc;
	}
	
	public Vect getVelo() {
		return velo;
	}

}

