package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import model.Model;

/**
 * 
 * Saves the game board gizmos
 * 
 */
public class Save implements Isave {

	Model model;
	ArrayList<GizmoSquare> gizmoSquares;
	ArrayList<GizmoCircle> gizmoCircles;
	ArrayList<GizmoAbsorber> gizmoAbsorber;
	ArrayList<GizmoLeftFlipper> gizmoLeftFlippers;
	ArrayList<GizmoRightFlipper> gizmoRightFlippers;
	ArrayList<GizmoTriangle> gizmoTriangles;

	public Save() {
	}

	@Override
	public void makeSave(String filename, Model m) {

		model = m;
		ArrayList<GizmoSquare> gizmoSquares = model.getSquares();
		ArrayList<GizmoCircle> gizmoCircles = model.getGizmoCircles();
		ArrayList<GizmoAbsorber> gizmoAbsorber = model.getAbsorbers();
		ArrayList<GizmoLeftFlipper> gizmoLeftFlippers = model.getLeftFlippers();
		ArrayList<GizmoRightFlipper> gizmoRightFlippers = model
				.getRightFlippers();
		ArrayList<GizmoTriangle> gizmoTriangles = model.getTriangles();

		/*
		 * Formatter for saving the file
		 * If the user doesnt specify a file format
		 * then .txt is added to the end, if they already added .txt
		 * themselves then the format is made blank to prevent the file being called
		 * "name.txt".txt
		 */
		String format = ".txt";

		if (filename.contains(".txt")) {
			format = "";
		}

		File file = new File(filename + format);

		PrintWriter writer;
		/*
		 * Used a print write each array of gizmos is fed in and wrote into the text file
		 * Using the given format
		 */
		try {
			writer = new PrintWriter(file, "UTF-8");
			

			
			for(GizmoSquare sqr : gizmoSquares) {
				
				writer.print("Square ");

				writer.print(sqr.getName() + " ");

				int xpos = sqr.getX();
				xpos = xpos / 25;
				writer.print(xpos + " ");

				int ypos = sqr.getY();
				ypos = ypos / 25;
				writer.println(ypos);

			}

			for (GizmoCircle circle : gizmoCircles) {

				writer.print("Circle ");

				writer.print(circle.getName() + " ");

				int xpos = (int) circle.getXPos();
				xpos = (xpos) / 25;
				writer.print(xpos + " ");

				int ypos = (int) circle.getYPos();
				ypos = (ypos) / 25;
				writer.println(ypos);

			}

			for (GizmoAbsorber absorber : gizmoAbsorber) {

				writer.print("Absorber ");

				writer.print(absorber.getName() + " ");

				int x = absorber.getX();
				x = x / 25;
				int y = absorber.getY();
				y = y / 25;
				int h = absorber.getHeight();
				int w = absorber.getWidth();
				int y2 = h / 25 + y;
				int x2 = w / 25 + x;

				writer.print(x + " ");
				writer.print(y + " ");
				writer.print(x2 + " ");
				writer.println(y2);
				
				if(absorber.isKeyBound()) {
					writer.print("KeyConnect ");
					writer.print(absorber.getName() + " ");
					writer.println(absorber.getBoundKey());
				}

			}

			for (GizmoLeftFlipper lflip : gizmoLeftFlippers) {

				writer.print("LeftFlipper ");

				writer.print(lflip.getName() + " ");

				int x = lflip.getX();
				x = (x - 10) / 25;
				int y = lflip.getY();
				y = (y - 10) / 25;

				writer.print(x + " ");
				writer.println(y + " ");

				
				if(lflip.isKeyBound()) {
					writer.print("KeyConnect ");
					writer.print(lflip.getName() + " ");
					writer.println(lflip.getBoundKey());
				}			
				
			}

			for (GizmoRightFlipper rflip : gizmoRightFlippers) {

				writer.print("RightFlipper ");

				writer.print(rflip.getName() + " ");

				int x = rflip.getX();
				x = ((x - 35) / 25);
				int y = rflip.getY();
				y = (y - 10) / 25;

				writer.print(x + " ");
				writer.println(y + " ");
				
				if(rflip.isKeyBound()) {
					writer.print("KeyConnect ");
					writer.print(rflip.getName() + " ");
					writer.println(rflip.getBoundKey());
				}
				

			}

			for (GizmoTriangle triangle : gizmoTriangles) {

				writer.print("Triangle ");
				writer.print(triangle.getName() + " ");

				int x = triangle.getX();
				x = x / 25;
				int y = triangle.getY();
				y = y / 25;

				writer.print(x + " ");
				writer.println(y + " ");

				if (triangle.getRot() == 90) {
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
				} else if (triangle.getRot() == 180) {
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
				} else if (triangle.getRot() == 270) {
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
					writer.print("Rotate ");
					writer.println(triangle.getName() + " ");
				}

			}

			for (GizmoSquare sqr : gizmoSquares) {

				if (sqr.getConnection() != null) {
					writer.print("Connect ");

					writer.print(sqr.getName() + " ");

					writer.println(sqr.getConnection().getName());

				}

			}

			for (GizmoCircle circle : gizmoCircles) {

				if (circle.getConnection() != null) {
					writer.print("Connect ");

					writer.print(circle.getName() + " ");

					writer.println(circle.getConnection().getName());

				}

			}

			for (GizmoAbsorber absorber : gizmoAbsorber) {

				if (absorber.getConnection() != null) {
					writer.print("Connect ");

					writer.print(absorber.getName() + " ");

					writer.println(absorber.getConnection().getName());

				}

			}

			for (GizmoLeftFlipper lflip : gizmoLeftFlippers) {

				if (lflip.getConnection() != null) {
					writer.print("Connect ");

					writer.print(lflip.getName() + " ");

					writer.println(lflip.getConnection().getName());

				}

			}

			for (GizmoRightFlipper rflip : gizmoRightFlippers) {

				if (rflip.getConnection() != null) {
					writer.print("Connect ");

					writer.print(rflip.getName() + " ");

					writer.println(rflip.getConnection().getName());

				}

			}

			for (GizmoTriangle triangle : gizmoTriangles) {

				if (triangle.getConnection() != null) {
					writer.print("Connect ");

					writer.print(triangle.getName() + " ");

					writer.println(triangle.getConnection().getName());

				}
			}

			if (model.ballExists()) {
				writer.print("Ball ");
				writer.print("B ");

				double x = model.getBall().getExactX() / 25;
				double y = model.getBall().getExactY() / 25;
				double xv = model.getBall().getVelo().x();
				double yv = model.getBall().getVelo().y();

				writer.print(x + " ");
				writer.print(y + " ");
				writer.print(xv + " ");
				writer.println(yv + " ");
			} else if(!model.ballExists()){
				writer.print("Ball ");
				writer.print("B ");

				double x = 1.0;
				double y = 11.0;
				double xv = 0.0;
				double yv = 0.0;

				writer.print(x + " ");
				writer.print(y + " ");
				writer.print(xv + " ");
				writer.println(yv + " ");
			}

			
			writer.println("Gravity " + model.getGravity());
			writer.print("Friction " + model.getFriction());
			writer.println(" " + model.getFriction2());
	
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}