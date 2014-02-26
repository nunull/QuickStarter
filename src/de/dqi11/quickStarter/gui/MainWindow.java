package de.dqi11.quickStarter.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.dqi11.quickStarter.modules.ModuleAction;

/**
 * Represents the main-application window, which 
 * shows the search-field and Advices.
 */
public class MainWindow extends Observable {
	private final int WIDTH = 300;
	private final int TEXTFIELD_HEIGHT = 50;
	private final int ADVICESLIST_MAXHEIGHT = 200;
	private boolean visible;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JTextField textField;
	private DefaultListModel moduleActionsListModel;
	private KeyListener keyListener;
	private DocumentListener documentListener;
	private LinkedList<ModuleAction> moduleActions;
	
	
	/**
	 * Constructor.
	 */
	public MainWindow() {
		visible = false;
		
		initListeners();
		initMainFrame();
		initMainPanel();
		initTextField();
		initAdvicesPanel();
	}
	
	/**
	 * Initializes the actions.
	 */
	public void initListeners() {
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// escape-key
				if(e.getKeyCode() == 27) toggleApplication();
			}
		};
		
		documentListener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		};
	}
	
	/**
	 * Initializes the mainFrame.
	 */
	public void initMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setUndecorated(true);
		mainFrame.setSize(WIDTH, TEXTFIELD_HEIGHT + ADVICESLIST_MAXHEIGHT);
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
		textField.setPreferredSize(new Dimension(WIDTH, TEXTFIELD_HEIGHT));
		textField.addKeyListener(keyListener);
		textField.getDocument().addDocumentListener(documentListener);
		
		mainPanel.add(textField);
	}
	
	/**
	 * Initializes the advicesPanel;
	 */
	public void initAdvicesPanel() {
		moduleActionsListModel = new DefaultListModel();
		
		JList advicesList = new JList(moduleActionsListModel);
		advicesList.setPreferredSize(new Dimension(WIDTH, ADVICESLIST_MAXHEIGHT));
		
		mainPanel.add(advicesList);
	}
	
	/**
	 * Updates the shown advices.
	 */
	public void updateModuleActions() {
		moduleActionsListModel.clear();
		
		for(ModuleAction moduleAction : moduleActions) {
			moduleActionsListModel.addElement(moduleAction);
		}
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
	
	/**
	 * Sets the Advices and updates the GUI.
	 * 
	 * @param advices The List of Advices.
	 */
	public void setModuleActions(LinkedList<ModuleAction> moduleActions) {
		this.moduleActions = moduleActions;
		updateModuleActions();
	}
}
