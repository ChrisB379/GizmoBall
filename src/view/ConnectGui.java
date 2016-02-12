package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.GizmoAbsorber;
import model.GizmoLeftFlipper;
import model.GizmoRightFlipper;
import model.IGizmo;
import model.Model;
import controller.ConnectGuiListener;
import controller.RunListener;

/* GUI for the pause menu */

public class ConnectGui {
	
	private RunListener listener;
	private ConnectGuiListener clistener;
	private JFrame mainFrame;
	private JPanel panel;
	private Model model;
	private BuildModeGui gui;
	private String s, s2;
	private JTextField input;
	private JLabel g;
	
	public ConnectGui(Model m, BuildModeGui g) {
		
		gui = g;
		model = m;
		listener = new RunListener(model, gui);
		clistener = new ConnectGuiListener(this,model);
		s2 = (" ");
	}

	public void createAndShowGUI() {
				
		mainFrame = new JFrame("CONNECT");
		mainFrame.setBounds(100, 100, 100, 100);
		mainFrame.setPreferredSize(new Dimension(250, 250));
	    {
	    	panel = new JPanel();
	    	mainFrame.getContentPane().add(panel, BorderLayout.NORTH);
	    	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    	panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
					10));
	    	{
	    		
	    		JPanel connectPanel = new JPanel();
	    		connectPanel.setLayout(new BoxLayout(connectPanel, BoxLayout.PAGE_AXIS));
	    		panel.add(connectPanel);
	    		{
	    			JLabel conLabel = new JLabel("Connect");
	    			conLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    			connectPanel.add(conLabel);
	    			
	    			JPanel jp = new JPanel();
	    			jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
	    			connectPanel.add(jp);
	    			{
			    		JLabel f = new JLabel("To add a connection click");
			    		f.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		JLabel ff = new JLabel("a gizmo then click the gizmo");
			    		ff.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		JLabel fff = new JLabel("you would like to connect to.");
			    		fff.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		g = new JLabel(s2);
			    		g.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		jp.add(f);
			    		jp.add(ff);
			    		jp.add(fff);
			    		
			    		JButton cB = new JButton("Connect");
			    		cB.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		cB.addActionListener(clistener);
			    		cB.setSize(150, 40);
			    		jp.add(cB);
			    		
			    		JButton cB2 = new JButton("Disconnect");
			    		cB2.setAlignmentX(Component.CENTER_ALIGNMENT);
			    		cB2.addActionListener(clistener);
			    		cB2.setSize(150, 40);
			    		jp.add(cB2);
			    		
			    		jp.add(g);
	    			}
		    		
	    		}	
	    		
	    		JPanel keyConPanel = new JPanel();
	    		panel.add(keyConPanel);
	    		JLabel kConLabel = new JLabel("Key Connections");
	    		kConLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    		keyConPanel.setLayout(new BoxLayout(keyConPanel, BoxLayout.PAGE_AXIS));
    			keyConPanel.add(kConLabel);
    			JLabel sp = new JLabel(" ");
    			keyConPanel.add(sp);
    			
	    		{
	    			JPanel topPanel = new JPanel();
		    		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));	
		    		keyConPanel.add(topPanel);
		    		{	
		    			JLabel j1 = new JLabel("Connect Key: ");
		    			topPanel.add(j1);
		    			input = new JTextField(1);
		    			input.setDocument(new JTextFieldLimit(1));
						input.addFocusListener(new FocusListener() {
							@Override
							public void focusGained(FocusEvent arg0) {
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								s = input.getText();
								
								char[] x = s.toCharArray();
								char boundKey = x[0];
								IGizmo g = model.getSelectedCell().getGizmo();
								if(g.getType().equals("LeftFlipper")) {
									((GizmoLeftFlipper) g).setKeyBind(); 
									((GizmoLeftFlipper) g).setBoundKey(boundKey);
								}
								else if(g.getType().equals("RightFlipper")) {
									((GizmoRightFlipper) g).setKeyBind(); 
									((GizmoRightFlipper) g).setBoundKey(boundKey);
								}
								else if(g.getType().equals("Absorber")){
									((GizmoAbsorber) g).setKeyBind(); 
									((GizmoAbsorber) g).setBoundKey(boundKey);
								}
							}
						});
		    		
		    			topPanel.add(input);
		    			
		    			JButton button4 = new JButton("Bind");
			    		button4.addActionListener(listener);
			    		button4.setSize(150, 40);
			    		topPanel.add(button4);
			    		
			    		
		    		}
		    		
		    		JButton button6 = new JButton("Key Disconnect");
		    		button6.setAlignmentX(Component.CENTER_ALIGNMENT);
		    		button6.addActionListener(listener);
		    		button6.setSize(150, 40);
		    		keyConPanel.add(button6);
	    		}
		    		
		    		JButton button5 = new JButton("Close");
		    		button5.setAlignmentX(Component.CENTER_ALIGNMENT);
		    		button5.addActionListener(clistener);
		    		button5.setSize(150, 40);
		    		panel.add(button5);
	    	}
	    }
		
	    mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
	
	public void killWindow() {
        mainFrame.dispose(); 

	}
	
	public void update() {
		g.setText("CONNECTED!");
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

}