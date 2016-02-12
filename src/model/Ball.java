package model;

import java.awt.Color;

import physics.Circle;
import physics.Vect;

/**
 * Defines the variables of a ball, this class is an edited version of the 
 * Murray Wood Demonstration of MVC and MIT Physics Collisions 2014. 
 * It now stores whether a ball has been absorbed and released, 
 * also has gravity and friction applied to the ball.
 * 
 */

public class Ball {

	private Vect velocity;
	private Vect center;
	private double radius;
	private double xpos;
	private double ypos;
	private Color colour;
	private boolean stopped;
	private boolean absorbed;
	private float mu,mu2;
	private float gravity;

	// x, y coordinates and x,y velocity
	public Ball(double x, double y, double xv, double yv) {
		xpos = x; // Centre coordinates
		ypos = y;
		colour = Color.BLUE;
		velocity = new Vect(xv, yv);
		radius = 6.25;
		stopped = false;
		center = new Vect(x, y);
		gravity = 20f;
	}

	public Vect getVelo() {
		return velocity;
	}

	public void setVelo(Vect v) {
		velocity = v;
	}
	
	public Vect getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	public double getExactX() {
		return xpos;
	}

	public double getExactY() {
		return ypos;
	}

	public void setExactX(double x) {
		xpos = x;
	}

	public void setExactY(double y) {
		ypos = y;
	}

	public void stop() {
		stopped = true;
	}

	public void start() {
		stopped = false;
	}

	public boolean stopped() {
		return stopped;
	}
	
	/*
	 * Methods related to checking if the ball is in the absorber
	 */
	public void absorb() {
		absorbed = true;
	}

	public void release() {
		absorbed = false;
	}

	public boolean absorbed() {
		return absorbed;
	}

	public Color getColour() {
		return colour;
	}
	
	public void setPosition(double x, double y){
		xpos = x;
		ypos = y;
	}
	
	/*
	 * Getters and setters for gravity
	 * along with the actual calculation for it
	 */
	
	public void addGravity(){
		double yv = velocity.y() + Math.pow(gravity, 2) * 0.05;
		velocity = new Vect(velocity.x(), yv);
	}
	
	public void setGravity(float g){
		gravity = g;
	}
	
	public float getGravity(){
		return gravity;
	}
	
	/*
	 * Getters and setters for friction
	 * along with the actual calculation for it
	 */
	
	public void addFriction(){

		velocity = velocity.times((1 - mu * 0.05 - (mu / 20) * Math.sqrt(velocity.dot(velocity)) * 0.05));
	}
	
	public void setFriction(float f, float f2){
		mu = f;
		mu2 = f2;
	}
	
	public float getMu(){
		return this.mu;
	}

	public float getMu2(){
		return this.mu2;
	}
	
	/*
	 * Used for creating the ball in the build mode view
	 */
	
	public Ball cloneToPos(double x, double y){
		return new Ball(x, y, velocity.x(), velocity.y());
	}


}
