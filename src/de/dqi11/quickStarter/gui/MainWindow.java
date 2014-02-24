package de.dqi11.quickStarter.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.dqi11.quickStarter.modules.Advice;

/**
 * Represents the main-application window, which 
 * shows the search-field and Advices.
 */
public class MainWindow extends Observable {
	private final int TEXTFIELD_WIDTH = 300;
	private final int TEXTFIELD_HEIGHT = 50;
	private boolean visible;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JTextField textField;
	private KeyListener keyListener;
	private LinkedList<Advice> advices;
	
	/**
	 * Constructor.
	 */
	public MainWindow() {
		visible = false;
		
		initListeners();
		initMainFrame();
		initMainPanel();
		initTextField();
	}
	
	/**
	 * Initializes the actions.
	 */
	public void initListeners() {
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// escape-key
				if(e.getKeyCode() == 27) {
					toggleApplication();
				} else {
					// TODO
					setChanged();
					notifyObservers();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	/**
	 * Initializes the mainFrame.
	 */
	public void initMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setUndecorated(true);
		mainFrame.setSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the mainPanel.
	 */
	public void initMainPanel() {
		mainPanel = new JPanel();
		mainFrame.setContentPane(mainPanel);
	}
	
	/**
	 * Initializes the textField.
	 */
	public void initTextField() {
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT));
		textField.addKeyListener(keyListener);
		mainPanel.add(textField);
	}
	
	/**
	 * Toggles the visibility of the application.
	 */
	public void toggleApplication() {
		visible = !visible;
		mainFrame.setVisible(visible);
	}
	
	/**
	 * Returns the current search-string.
	 * 
	 * @return The search-string.
	 */
	public String getSearchString() {
		return textField.getText();
	}
	
	public void setAdvices(LinkedList<Advice> advices) {
		this.advices = advices;
	}
}
