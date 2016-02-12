package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import view.PlayGui;

/**
 * 
 * Takes in a file which contains a list of gizmos and their postitions For
 * example Triangle T1 0 7 this then gets parsed through the load game and is
 * put in the correct position
 * 
 */

public class Load implements Iload {

	private boolean isBuildMode = false;
	Model model;
	ArrayList<IGizmo> gizmos = new ArrayList<IGizmo>();

	@Override
	public boolean loadGame(String filename) {

		model = new Model();
		model.setFile(filename);
		gizmos.clear();

		File file = new File(filename);

		BufferedReader reader = null;
		/*
		 * A try catch statement for reading the text file
		 * Checks the first letter of a word at the start of a line
		 * to determine what the command is
		 * e.g S with be a square ,Ci will be a circle
		 * Co will be a connect
		 */
		try {

			reader = new BufferedReader(new FileReader(file));
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {

				String[] splitLine = currentLine.split("\\s+");

				if (splitLine[0].charAt(0) == 'S') {

					String name = splitLine[1];
					int x = Integer.parseInt(splitLine[2]) * 25;
					int y = Integer.parseInt(splitLine[3]) * 25;
					int w = 25;
					GizmoSquare sqr = new GizmoSquare(name, x, y, w);
					gizmos.add(sqr);

				}

				else if (splitLine[0].charAt(0) == 'C'
						&& splitLine[0].charAt(1) == 'i') {

					String name = splitLine[1];
					int x = Integer.parseInt(splitLine[2]) * 25;
					int y = Integer.parseInt(splitLine[3]) * 25;
					double r = 12;
					GizmoCircle circle = new GizmoCircle(name, x, y, r);
					gizmos.add(circle);
				}

				else if (splitLine[0].charAt(0) == 'T') {
					String name = splitLine[1];
					int x = Integer.parseInt(splitLine[2]) * 25;
					int y = Integer.parseInt(splitLine[3]) * 25;
					int w = 25;
					GizmoTriangle tri = new GizmoTriangle(name, x, y, w);
					gizmos.add(tri);
				}

				else if ((splitLine[0].charAt(0) == 'R')
						&& (splitLine[0].charAt(1) == 'o')) {
					for (IGizmo giz : gizmos) {
						if (giz.getName().equals(splitLine[1])) {
							((GizmoTriangle) giz).rotateAroundCentre(90);

						}
					}

				}

				else if (splitLine[0].charAt(0) == 'B') {
					double x = Double.parseDouble(splitLine[2]) * 25;
					double y = Double.parseDouble(splitLine[3]) * 25;
					double xv = Double.parseDouble(splitLine[4]);
					double yv = Double.parseDouble(splitLine[5]);
					Ball ball = new Ball(x, y, xv, yv);
					model.loadBall(ball);

				}

				else if (splitLine[0].charAt(0) == 'A') {
					String name = splitLine[1];
					int x = Integer.parseInt(splitLine[2]) * 25;
					int y = Integer.parseInt(splitLine[3]) * 25;
					int x2 = Integer.parseInt(splitLine[4]) * 25;
					int y2 = Integer.parseInt(splitLine[5]) * 25;
					int height = y2 - y;
					int width = x2 - x;
					GizmoAbsorber abs = new GizmoAbsorber(name, x, y, width,
							height);
					gizmos.add(abs);
				}

				else if (splitLine[0].charAt(0) == 'R'
						&& splitLine[0].charAt(1) == 'i') {
					String name = splitLine[1];
					int x = (Integer.parseInt(splitLine[2]) * 25 + 10) + 25;
					int y = Integer.parseInt(splitLine[3]) * 25 + 10;
					GizmoRightFlipper rflip = new GizmoRightFlipper(name, x, y);
					gizmos.add(rflip);
				}


				else if (splitLine[0].charAt(0) == 'L') {
					String name = splitLine[1];
					int x = Integer.parseInt(splitLine[2]) * 25 + 10;
					int y = Integer.parseInt(splitLine[3]) * 25 + 10;
					GizmoLeftFlipper lflip = new GizmoLeftFlipper(name, x, y);
					gizmos.add(lflip);
				}

				else if(splitLine[0].charAt(0) == 'C' &&
						splitLine[0].charAt(1) == 'o'){
					for (IGizmo g: gizmos){
						if(g.getName().equals(splitLine[1])){
							for(IGizmo gc: gizmos){
								if(gc.getName().equals(splitLine[2]))
								g.setConnection(gc);
							}
						}
					}
				}

				else if(splitLine[0].charAt(0) == 'C' &&
						splitLine[0].charAt(1) == 'o'){
					for (IGizmo g: gizmos){
						if(g.getName().equals(splitLine[1])){
							for(IGizmo gc: gizmos){
								if(gc.getName().equals(splitLine[2]))
								g.setConnection(gc);
							}
						}
					}
				}


				else if (splitLine[0].charAt(0) == 'G') {
					float f = Float.parseFloat(splitLine[1]);
					model.setModelGravity(f);
				}

				else if (splitLine[0].charAt(0) == 'F') {
					float f = Float.parseFloat(splitLine[1]);
					float f2 = Float.parseFloat(splitLine[2]);
					model.setModelFriction(f,f2);
				}
				
				else if (splitLine[0].charAt(0) == 'K') {
					
					for (IGizmo giz : gizmos) {
						if (giz.getName().equals(splitLine[1])) {
							if(giz.getType().equals("LeftFlipper")){
							((GizmoLeftFlipper) giz).setKeyBind();
							((GizmoLeftFlipper) giz).setBoundKey(splitLine[2].charAt(0));
							}
							
							if(giz.getType().equals("RightFlipper")){
							((GizmoRightFlipper) giz).setKeyBind();
							((GizmoRightFlipper) giz).setBoundKey(splitLine[2].charAt(0));
							}
							
							if(giz.getType().equals("Absorber")){
								((GizmoAbsorber) giz).setKeyBind();
								((GizmoAbsorber) giz).setBoundKey(splitLine[2].charAt(0));
								
							}
						}
					}
				}

			}

			for (IGizmo gizmo : gizmos) {
				model.addGizmos(gizmo);
			}


			if(!isBuildMode) {
				PlayGui play = new PlayGui(model);
				play.createAndShowGUI();
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {

				if (reader != null)
					reader.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
	
	public void setBuildMode() {
		isBuildMode = true;
	}
	
	public Model getModel() {
		return model;
	}

}