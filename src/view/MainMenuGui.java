package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Model;
import controller.MainMenuBallListener;
import controller.MainMenuRunListener;

/* GUI for the main menu. */

public class MainMenuGui  implements IgameBoard  {
	
	private MainMenuRunListener listener;
	private JFrame mainFrame;
	private BuildBoard board;
	private JPanel panel;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel bigText;
	private Model model;
	@SuppressWarnings("unused")
	private MainMenuBallListener gListener;
	
	public MainMenuGui() {
		// RunListener catches all GUI events. In reality might have many listeners.
		listener = new MainMenuRunListener(this);
		model = new Model();
		gListener = new MainMenuBallListener(model, this);
	}

	public void createAndShowGUI() {
				
		mainFrame = new JFrame("GIZMOBALL");
		mainFrame.setPreferredSize(new Dimension(500, 300));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new BuildBoard(280, 50, model);
		model.setWallSize();
		model.setBallSpeed(200, 200);
	    
	    {
	    	panel = new JPanel();
	    	mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
	    	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    	{
	    		bigText = new JPanel();
	    		panel.add(bigText);
	    		{
	    			BufferedImage titleImage;
					try {
						
						titleImage = ImageIO.read(getClass().getResource("/resources/Title.png"));
	
	    			JLabel imageLabel = new JLabel(new ImageIcon(titleImage));
	    			
	    			bigText.add(imageLabel);
	    			
					} catch (IOException e) {
						
						e.printStackTrace();
					}
	    		}
	    	}
	    	{
	    		panel1 = new JPanel();
	    		panel.add(panel1);
	    		panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));
	    		{
		    		JButton button1 = new JButton("Play Now");
		    		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		    		button1.addActionListener(listener);
		    		button1.setSize(150, 40);
		    		panel1.add(button1);
	
		    		JButton button2 = new JButton("Build Mode");
		    		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		    		button2.addActionListener(listener);
		    		button2.setSize(150, 40);
		    		panel1.add(button2);
	    		}
	    	}
	    	{
	    		panel2 = new JPanel();
	    		panel.add(panel2);
	    		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
	    		{
	    			board.setAlignmentX(Component.CENTER_ALIGNMENT);
	    			panel2.add(board);
	    			JButton button3 = new JButton("Quit");
	    			button3.setAlignmentX(Component.CENTER_ALIGNMENT);
	    			button3.addActionListener(listener);
	    			button3.setSize(150, 40);
	    			panel2.add(button3);
	    			
	    		}
	    	}
	    }

		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
	
	public void killWindow() {
        mainFrame.dispose(); 

	}

}