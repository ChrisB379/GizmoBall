package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Cell;
import model.GizmoAbsorber;
import model.Ball;
import model.GizmoLeftFlipper;
import model.GizmoCircle;
import model.Grid;
import model.Model;
import model.Line;
import model.GizmoRightFlipper;
import model.GizmoSquare;
import model.GizmoTriangle;

public class Board extends JPanel implements Observer, IgameBoard {

	private static final long serialVersionUID = 1L;
	private int width, height;
	private Model gm;
	private Grid grid;
	private boolean showGrid;

	public Board(int w, int h, Model m) {
		
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		grid = gm.getGrid();
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setDoubleBuffered(true);// Swing does the double buffering smoothes
										
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (showGrid)
			drawGrid(g);


		// Draw all the horizontal lines
		for (Line hl : gm.getlines()) {
			g2.fillRect(hl.getX(), hl.getY(), hl.getWidth(), 1);
		}

		// Draw all squares
		for (GizmoSquare sq : gm.getSquares()) {
			g2.setColor(sq.getColour());
			g2.fillRect(sq.getX(), sq.getY(), sq.getWidth(), sq.getWidth());
		}

		// Draw all triangles
		for (GizmoTriangle tr : gm.getTriangles()) {
			g2.setColor(tr.getColour());
			int[] xp = { tr.getP1().x, tr.getP2().x, tr.getP3().x };
			int[] yp = { tr.getP1().y, tr.getP2().y, tr.getP3().y };
			g2.fillPolygon(xp, yp, 3);
		}

		for (GizmoCircle gc : gm.getGizmoCircles()) {
			g2.setColor(gc.getColour());
			g2.fillOval((int) (gc.getXPos()), (int) (gc.getYPos()),
					(int) (2 * gc.getRadius()), (int) (2 * gc.getRadius()));
		}

		// Draw all Absorbers
		for (GizmoAbsorber ab : gm.getAbsorbers()) {
			g2.setColor(ab.getColour());
			g2.fillRect(ab.getX(), ab.getY(), ab.getWidth(), ab.getHeight());
		}

		for (GizmoLeftFlipper flip : gm.getLeftFlippers()) {

			g2.setColor(flip.getColour());
			// Big circle
			g2.fillOval(flip.getP1().x - flip.getBigRad(),
					(flip.getP1().y - flip.getBigRad()),
					(2 * flip.getBigRad()), (2 * flip.getBigRad()));

			// Small Circle
			g2.fillOval(flip.getP2().x - flip.getSmallRad(),
					(flip.getP2().y - flip.getSmallRad()),
					(2 * flip.getSmallRad()), (2 * flip.getSmallRad()));


			// Polygon connecting the circles
			int[] xp = { flip.getP3().x, flip.getP4().x, flip.getP6().x,
					flip.getP5().x };
			int[] yp = { flip.getP3().y, flip.getP4().y, flip.getP6().y,
					flip.getP5().y };
			g2.fillPolygon(xp, yp, 4);

		}

		// Draw all right flippers


		for (GizmoRightFlipper rflip : gm.getRightFlippers()) {
			g2.setColor(rflip.getColour());
			// Big circle
			g2.fillOval(rflip.getP1().x - rflip.getBigRad(),
					(rflip.getP1().y - rflip.getBigRad()),
					(2 * rflip.getBigRad()), (2 * rflip.getBigRad()));

			// Small Circle
			g2.fillOval(rflip.getP2().x - rflip.getSmallRad(),
					(rflip.getP2().y - rflip.getSmallRad()),
					(2 * rflip.getSmallRad()), (2 * rflip.getSmallRad()));

			// Polygon connecting the circles
			int[] xp = { rflip.getP3().x, rflip.getP4().x, rflip.getP6().x,
					rflip.getP5().x };
			int[] yp = { rflip.getP3().y, rflip.getP4().y, rflip.getP6().y,
					rflip.getP5().y };

			g2.fillPolygon(xp, yp, 4);

		}

		Ball b = gm.getBall();
		if (b != null) {
			g2.setColor(b.getColour());
			int x = (int) (b.getExactX() - b.getRadius());
			int y = (int) (b.getExactY() - b.getRadius());
			int width = (int) (2 * b.getRadius());
			g2.fillOval(x, y, width, width);
		}
		if (gm.getSelectedCell() != null)
			highlightSelectedCell(g);

	}

	public void drawGrid(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Cell cell;

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				cell = grid.cellAt(i, j);
				g2.setColor(Color.gray);
				g2.fill(cell.getRectangle());
			}
		}

	}

	public void highlightSelectedCell(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.blue);
		g2.draw(new Rectangle(gm.getSelectedCell().getPos().x, gm.getSelectedCell().getPos().y
				,24, 24));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	@Override
	public void createAndShowGUI() {
		// TODO Auto-generated method stub

	}

	public void toggleGridOff() {
		showGrid = false;
	}

	public void toggleGridOn() {
		showGrid = true;
	}

}
