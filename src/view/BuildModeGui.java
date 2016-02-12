package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import model.Ball;
import model.IGizmo;
import model.Model;
import controller.AbsorberListener;
import controller.CircleListener;
import controller.ConnectListener;
import controller.BallListener;
import controller.LeftFlipperListener;
import controller.MouseActions;
import controller.RightFlipperListener;
import controller.RotateListener;
import controller.RunListener;
import controller.SquareListener;
import controller.TriangleListener;

public class BuildModeGui implements IgameBoard {

	private Model model;
	private JFrame frame;
	private ActionListener listener;
	private Board board;
	private SquareListener squListener;
	private TriangleListener triListener;
	private CircleListener cirListener;
	private LeftFlipperListener lfListener;
	private RightFlipperListener rfListener;
	private AbsorberListener absListener;
	private RotateListener rotListener;
	private ConnectListener conListener;
	private BallListener ballListener;
	private JLabel selectedImage;
	private JPanel botPanel = new JPanel();
	private int rotation;
	private IGizmo selectedGizmo;
	private MouseActions mouseListener;
	private JTextField fric, fric2, grav, v1, v2;
	private boolean removing, moving, connecting, triangleSet;
	private String gravVal, ballExactXv, ballExactYv, 
	ballXv, ballYv, fricVal, fricVal2;
	private Ball selectedBall;

	public BuildModeGui(Model m) {
		
		model = m;
		listener = new RunListener(m, this);
		squListener = new SquareListener(this, model);
		triListener = new TriangleListener(this);
		cirListener = new CircleListener(this);
		lfListener = new LeftFlipperListener(this);
		rfListener = new RightFlipperListener(this);
		absListener = new AbsorberListener(this);
		rotListener = new RotateListener(this);
		ballListener = new BallListener(this);
		conListener = new ConnectListener(this, model);
		selectedImage = new JLabel("");
		mouseListener = new MouseActions(model, this);

	}

	public void createAndShowGUI() {

		frame = new JFrame("Build Mode");
		frame.setSize(670, 715);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board(500, 500, model);
		board.toggleGridOn();
		model.deleteBall();

		Container cp = frame.getContentPane();

		JPanel mainPanel = new JPanel();

		{
			mainPanel.setLayout(new GridLayout(0, 1));
			{
				JPanel topPanel = new JPanel();
				mainPanel.add(topPanel);
				topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
				topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
						10));
				topPanel.setSize(110, 100);
				{
					JButton playButton = new JButton("Play");
					playButton.addActionListener(listener);
					playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					playButton.setMaximumSize(new Dimension(91, 26));
					topPanel.add(playButton);

					JButton deleteButton = new JButton("Delete");
					deleteButton.addActionListener(listener);
					deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					deleteButton.setMaximumSize(new Dimension(91, 26));
					topPanel.add(deleteButton);

					JButton clearButton = new JButton("Clear Board");
					clearButton.addActionListener(listener);
					clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					clearButton.setMaximumSize(new Dimension(91, 26));
					topPanel.add(clearButton);
					
					JButton loadButton = new JButton("Load");
					loadButton.addActionListener(listener);
					loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					loadButton.setMaximumSize(new Dimension(91, 26));
					topPanel.add(loadButton);
					
					JButton moveButton = new JButton("Move");
					moveButton.addActionListener(listener);
					moveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
					moveButton.setMaximumSize(new Dimension(91, 26));
					topPanel.add(moveButton);

					JLabel toolsPace = new JLabel(" ");
					toolsPace.setAlignmentX(Component.CENTER_ALIGNMENT);
					topPanel.add(toolsPace);
					
					JLabel tools = new JLabel("Tools");
					tools.setAlignmentX(Component.CENTER_ALIGNMENT);
					topPanel.add(tools);

					playButton.setFocusable(false);
					deleteButton.setFocusable(false);
					clearButton.setFocusable(false);
					loadButton.setFocusable(false);
					moveButton.setFocusable(false);
				}

				JPanel iconPanel = new JPanel();
				mainPanel.add(iconPanel);
				iconPanel.setLayout(new GridLayout(4, 2));
				{
					BufferedImage squImg;
					BufferedImage cirImg;
					BufferedImage triImg;
					BufferedImage lfImg;
					BufferedImage rfImg;
					BufferedImage absImg;
					BufferedImage rotImg;
					BufferedImage conImg;

					try {

						squImg = ImageIO.read(getClass().getResource(
								"/resources/squIcon.png"));
						cirImg = ImageIO.read(getClass().getResource(
								"/resources/circleIcon.png"));
						triImg = ImageIO.read(getClass().getResource(
								"/resources/triIcon.png"));
						lfImg = ImageIO.read(getClass().getResource(
								"/resources/LFlipIcon.png"));
						rfImg = ImageIO.read(getClass().getResource(
								"/resources/RFlipIcon.png"));
						absImg = ImageIO.read(getClass().getResource(
								"/resources/absIcon.png"));
						rotImg = ImageIO.read(getClass().getResource(
								"/resources/rotate.png"));
						conImg = ImageIO.read(getClass().getResource(
								"/resources/connect.png"));

						ImageIcon squIcon = new ImageIcon(squImg);
						ImageIcon cirIcon = new ImageIcon(cirImg);
						ImageIcon triIcon = new ImageIcon(triImg);
						ImageIcon lfIcon = new ImageIcon(lfImg);
						ImageIcon rfIcon = new ImageIcon(rfImg);
						ImageIcon absIcon = new ImageIcon(absImg);
						ImageIcon rotIcon = new ImageIcon(rotImg);
						ImageIcon conIcon = new ImageIcon(conImg);

						JButton squButton = new JButton(squIcon);
						squButton.addActionListener(squListener);
						squButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(squButton);

						JButton triButton = new JButton(triIcon);
						triButton.addActionListener(triListener);
						triButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(triButton);

						JButton cirButton = new JButton(cirIcon);
						cirButton.addActionListener(cirListener);
						cirButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(cirButton);

						JButton absButton = new JButton(absIcon);
						absButton.addActionListener(absListener);
						absButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(absButton);

						JButton LFButton = new JButton(lfIcon);
						LFButton.addActionListener(lfListener);
						LFButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(LFButton);

						JButton RFButton = new JButton(rfIcon);
						RFButton.addActionListener(rfListener);
						RFButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(RFButton);

						JButton conButton = new JButton(conIcon);
						conButton.addActionListener(conListener);
						conButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(conButton);

						JButton rotButton = new JButton(rotIcon);
						rotButton.addActionListener(rotListener);
						rotButton.setMaximumSize(new Dimension(30, 13));
						iconPanel.add(rotButton);

						squButton.setFocusable(false);
						cirButton.setFocusable(false);
						triButton.setFocusable(false);
						LFButton.setFocusable(false);
						RFButton.setFocusable(false);
						absButton.setFocusable(false);
						conButton.setFocusable(false);
						rotButton.setFocusable(false);

					} catch (IOException e) {

						e.printStackTrace();
					}
				}
				
				JPanel bp = new JPanel();
				mainPanel.add(bp);
				bp.setLayout(new BoxLayout(bp, BoxLayout.PAGE_AXIS));
				{	
					
					JLabel bs = new JLabel(" ");
					bs.setAlignmentX(Component.LEFT_ALIGNMENT);
					bp.add(bs);
					
					JLabel blab = new JLabel("Ball");
					blab.setAlignmentX(Component.CENTER_ALIGNMENT);
					bp.add(blab);
					
					JLabel bsp = new JLabel(" ");
					bp.add(bsp);
					
					BufferedImage b;
					try {

						b = ImageIO.read(getClass().getResource(
								"/resources/ball.png"));
						
						ImageIcon bIcon = new ImageIcon(b);

						JButton balButton = new JButton(bIcon);
						balButton.addActionListener(ballListener);
						balButton.setMaximumSize(new Dimension(30, 30));
						bp.add(balButton);
						balButton.setAlignmentX(Component.CENTER_ALIGNMENT);
						balButton.setFocusable(false);

					} catch (IOException e) {

						e.printStackTrace();
					}

						v1 = new JTextField();
						bp.add(v1);
						v1.setPreferredSize(new Dimension(91, 20));
						v1.setMaximumSize(v1.getPreferredSize() );
						v1.setAlignmentX(Component.CENTER_ALIGNMENT);
						v1.addFocusListener(new FocusListener() {
							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								ballXv = v1.getText();
							}
						});

						v2 = new JTextField();
						bp.add(v2);
						v2.setPreferredSize(new Dimension(91, 20));
						v2.setMaximumSize(v2.getPreferredSize() );
						v2.setAlignmentX(Component.CENTER_ALIGNMENT);
						v2.addFocusListener(new FocusListener() {

							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								ballYv = v2.getText();
							}
						});
						
						JButton vButton = new JButton("Set Velocity");
						vButton.addActionListener(ballListener);
						vButton.setAlignmentX(Component.CENTER_ALIGNMENT);
						vButton.setMaximumSize(new Dimension(91, 26));
						bp.add(vButton);
					}
			

									
			
		
				botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.PAGE_AXIS));
				botPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
						10));
				mainPanel.add(botPanel);
				{
					JPanel selPanel = new JPanel();
					botPanel.add(selPanel);
					{
						JLabel selected = new JLabel("Selected:");
						selPanel.add(selected);
						selPanel.add(selectedImage);
					}

					JLabel gravity = new JLabel("Gravity");
					gravity.setAlignmentX(Component.CENTER_ALIGNMENT);
					botPanel.add(gravity);

					JPanel GPanel = new JPanel();
					botPanel.add(GPanel);
					GPanel.setLayout(new FlowLayout());
					{
						grav = new JTextField(4);
						grav.setDocument(new JTextFieldLimit(5));
						grav.addFocusListener(new FocusListener() {
							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								gravVal = grav.getText();
							}
						});
						GPanel.add(grav);
						JButton gBut = new JButton("Set");
						gBut.addActionListener(listener);
						GPanel.add(gBut);

					}

					JLabel friction = new JLabel("Friction");
					friction.setAlignmentX(Component.CENTER_ALIGNMENT);
					botPanel.add(friction);

					JPanel FPanel = new JPanel();
					botPanel.add(FPanel);
					FPanel.setLayout(new FlowLayout());
					{
						fric = new JTextField("mu", 3);
						fric.setMaximumSize(fric.getPreferredSize() );
						fric.setDocument(new JTextFieldLimit(5));
						fric.addFocusListener(new FocusListener() {
							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								fricVal = fric.getText();
							}
						});
						FPanel.add(fric);
						
						fric2 = new JTextField("mu2", 3);
						fric2.setMaximumSize(fric2.getPreferredSize() );
						fric2.addFocusListener(new FocusListener() {
							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								fricVal2 = fric2.getText();
							}
						});
						FPanel.add(fric2);
						JButton fBut = new JButton(" Set");
						fBut.addActionListener(listener);
						FPanel.add(fBut);

					}

					JButton backButton = new JButton("Main Menu");
					backButton.addActionListener(listener);
					backButton.setMaximumSize(new Dimension(91, 26));
					botPanel.add(backButton, BorderLayout.PAGE_END);
					backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

					JButton quitButton = new JButton("Quit");
					quitButton.addActionListener(listener);
					quitButton.setMaximumSize(new Dimension(91, 26));
					botPanel.add(quitButton, BorderLayout.PAGE_END);
					quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				}

			}

		}

		cp.add(mainPanel, BorderLayout.LINE_START);
		cp.add(board).addMouseListener(mouseListener);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void updateSelected(ImageIcon img, IGizmo g) {

		selectedBall = null;
		selectedGizmo = null;
		selectedImage.setIcon(img);
		selectedGizmo = g;
	}

	public IGizmo getSelectedGizmo() {
		return selectedGizmo;
	}

	public void rotateTri() {
		rotation = rotation + 90;
		if (rotation > 270) {
			rotation = 0;
		}
	}

	public int getRotation() {
		return rotation;
	}

	public void setTriangle(boolean b) {
		triangleSet = b;
	}

	public boolean isTriSet() {
		return triangleSet;
	}

	public void setGrav(String g) {
		gravVal = g;
	}

	public String getGrav() {
		return gravVal;
	}

	public String getFric() {
		return fricVal;
	}
	
	/*
	 * Methods for getting and setting the ball velocity
	 * These are held to be sent to the listener with the value
	 * obtained being from the text fields
	 */
	public String getBallExactXv(){
		return ballExactXv;
	}
	
	public void setBallExactXv(String x){
		ballExactXv = x;
	}
	
	public String getBallExactYv(){
		return ballExactYv;
	}
	
	public void setBallExactYv(String y){
		ballExactYv = y;
	}

	public String getFric2() {
		return fricVal2;
	}
	
	public void killWindow() {
		frame.dispose();
	}

	public void setRotation(int i) {
		rotation = i;
	}
	
	public void setRemoving(boolean b){
		removing = b;
	}

	public boolean removing() {
		return removing;
	}
	
	public boolean moving(){
		return moving;
	}
	
	public void setMoving(boolean b){
		moving = b;
	}
	
	public void setConnecting(boolean b){
		connecting = b;
	}
	
	public boolean connecting(){
		return connecting;
	}
	
	public String getXv() {
		
		return ballXv;
	}

	public String getYv() {

		return ballYv;
	}
	
	/*
	 * Referenced this JTextFieldLimit code from
	 * http://www.java2s.com/Tutorial/Java/0240__Swing/LimitJTextFieldinputtoamaximumlength.htm
	 */
	
	private class JTextFieldLimit extends PlainDocument {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		@SuppressWarnings("unused")
		JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}


	
	public void updateModel(Model m) {
		model = m;
		model.update();
	}

	public void updateSelectedBall(Ball b) {
		selectedGizmo = null;
		selectedBall = b;
	}
	
	public Ball getSelectedBall() {
		return selectedBall;
	}

}