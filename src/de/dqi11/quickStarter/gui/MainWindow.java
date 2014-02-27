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

import de.dqi11.quickStarter.controller.Search;
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
	@SuppressWarnings("rawtypes")
	private JList advicesList;
	@SuppressWarnings("rawtypes")
	private DefaultListModel moduleActionsListModel;
	private KeyListener keyListener;
	private DocumentListener documentListener;
	private LinkedList<ModuleAction> moduleActions;
	
	/**
	 * Constructor.
	 */
	public MainWindow() {
		visible = false;
	}
	
	public void init() {
		initListeners();
		initMainFrame();
		initMainPanel();
		initTextField();
		initModuleActionsPanel();
	}
	
	/**
	 * Initializes the actions.
	 */
	private void initListeners() {
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// Nothing to do here.
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// Nothing to do here.
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// escape-key
				if(e.getKeyCode() == 27) toggleApplication();
				// enter-key
				else if(e.getKeyCode() == 10) invokeSelectedModuleAction();
				// down-arrow-key
				else if(e.getKeyCode() == 40) selectNext();
				// up-arrow-key
				else if(e.getKeyCode() == 38) selectPrevious();
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
				// Nothing to do here.
			}
		};
	}
	
	/**
	 * Initializes the mainFrame.
	 */
	private void initMainFrame() {
		mainFrame = new JFrame();
		
		mainFrame.setUndecorated(true);
		mainFrame.setSize(WIDTH, TEXTFIELD_HEIGHT + ADVICESLIST_MAXHEIGHT);
		mainFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the mainPanel.
	 */
	private void initMainPanel() {
		mainPanel = new JPanel();
		mainFrame.setContentPane(mainPanel);
	}
	
	/**
	 * Initializes the textField.
	 */
	private void initTextField() {
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(WIDTH, TEXTFIELD_HEIGHT));
		textField.addKeyListener(keyListener);
		textField.getDocument().addDocumentListener(documentListener);
		
		mainPanel.add(textField);
	}
	
	/**
	 * Initializes the advicesPanel;
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initModuleActionsPanel() {
		moduleActionsListModel = new DefaultListModel();
		
		advicesList = new JList(moduleActionsListModel);
		
		advicesList.setPreferredSize(new Dimension(WIDTH, ADVICESLIST_MAXHEIGHT));
		
		mainPanel.add(advicesList);
	}
	
	/**
	 * Updates the shown advices.
	 */
	@SuppressWarnings("unchecked")
	public void updateModuleActions() {
		moduleActionsListModel.clear();
		
		for(ModuleAction moduleAction : moduleActions) {
			moduleActionsListModel.addElement(moduleAction);
		}
		
		selectFirst();
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
	 * Selects the first ModuleAction.
	 */
	public void selectFirst() {
		advicesList.setSelectedIndex(0);
	}
	
	/**
	 * Selects the next ModuleAction.
	 */
	public void selectNext() {
		int index = advicesList.getSelectedIndex()+1;
		advicesList.setSelectedIndex(index > advicesList.getLastVisibleIndex() ? advicesList.getLastVisibleIndex() : index);
	}
	
	/**
	 * Selects the previous ModuleAction.
	 */
	public void selectPrevious() {
		int index = advicesList.getSelectedIndex()-1;
		advicesList.setSelectedIndex(index < 0 ? 0 : index);
	}
	
	public int getSelectedIndex() {
		return advicesList.getSelectedIndex();
	}
	
	public ModuleAction getSelectedModuleAction() {
		return (ModuleAction)advicesList.getSelectedValue();
	}
	
	public void invokeSelectedModuleAction() {
		Search search = new Search(getSearchString());
		ModuleAction moduleAction = getSelectedModuleAction();
		
		if(moduleAction != null) {
			ModuleWindow moduleWindow = moduleAction.getModuleWindow(search);
			
			if(moduleWindow == null) getSelectedModuleAction().invoke(search);
			else System.out.println("Show ModuleWindow.");
		}
	}
	
	/**
	 * Sets the ModuleActions and updates the GUI.
	 * 
	 * @param moduleActions The List of ModuleActions.
	 */
	public void setModuleActions(LinkedList<ModuleAction> moduleActions) {
		this.moduleActions = moduleActions;
		updateModuleActions();
	}
}
