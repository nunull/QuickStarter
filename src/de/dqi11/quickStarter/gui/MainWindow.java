package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.ModuleAction;

/**
 * Represents the main-application window, which 
 * shows the search-field and Advices.
 */
public class MainWindow extends Observable {
	private final int WIDTH = 500;
	private final int TEXTFIELD_HEIGHT = 50;
	private final int ADVICESLIST_MAXHEIGHT = 450;
	private boolean visible;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JTextField textField;
	private JLabel errorLabel;
	private JList<ModuleAction> advicesList;
	private DefaultListModel<ModuleAction> moduleActionsListModel;
	private KeyListener keyListener;
	private DocumentListener documentListener;
	private LinkedList<ModuleAction> moduleActions;
	private Font defaultFont;
	private Font boldFont;
	
	/**
	 * Constructor.
	 */
	public MainWindow() {
		visible = false;
	}
	
	public void init() {
		initListeners();
		initFonts();
		initMainFrame();
		initMainPanel();
		initTextField();
		initErrorLabel();
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) toggleApplication();
				// enter-key
				else if(e.getKeyCode() == KeyEvent.VK_ENTER) invokeSelectedModuleAction();
				// down-arrow-key
				else if(e.getKeyCode() == KeyEvent.VK_DOWN) selectNext();
				// up-arrow-key
				else if(e.getKeyCode() == KeyEvent.VK_UP) selectPrevious();
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
	 * Initializes all fonts.
	 */
	private void initFonts() {
		// Initialize default font.
		try {
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Light.ttf"));
			defaultFont =  defaultFont.deriveFont(20f);
		} catch (Exception e) {
			defaultFont = new Font("Arial", Font.PLAIN, 20);
		}
		
		// Initialize bold font.
		try {
			boldFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Bold.ttf"));
			boldFont =  boldFont.deriveFont(20f);
		} catch (Exception e) {
			boldFont = new Font("Arial", Font.BOLD, 20);
		}
	}
	
	/**
	 * Initializes the mainFrame.
	 */
	private void initMainFrame() {
		mainFrame = new JFrame();
		
		mainFrame.setUndecorated(true);
		mainFrame.setSize(WIDTH, TEXTFIELD_HEIGHT + ADVICESLIST_MAXHEIGHT);
//		mainFrame.setShape(new RoundRectangle2D.Double(10, 10, 100, 100, 50, 50));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setOpacity(0.8f);
		mainFrame.setBackground(Color.BLACK);
	}
	
	/**
	 * Initializes the mainPanel.
	 */
	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainFrame.setContentPane(mainPanel);
	}
	
	/**
	 * Initializes the textField.
	 */
	private void initTextField() {
		textField = new JTextField();
		
		Border line = BorderFactory.createLineBorder(Color.BLACK);
		Border empty = new EmptyBorder(0, 10, 0, 0);
		CompoundBorder border = new CompoundBorder(line, empty);
		textField.setBorder(border);
		textField.setPreferredSize(new Dimension(WIDTH, TEXTFIELD_HEIGHT));
		textField.setFont(boldFont);
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.WHITE);
		textField.setCaretColor(Color.WHITE);
		
		textField.addKeyListener(keyListener);
		textField.getDocument().addDocumentListener(documentListener);
		
		mainPanel.add(textField);
	}
	
	private void initErrorLabel() {
		errorLabel = new JLabel();
		
		mainPanel.add(errorLabel);
	}
	
	/**
	 * Initializes the advicesPanel;
	 */
	private void initModuleActionsPanel() {
		moduleActionsListModel = new DefaultListModel<ModuleAction>();

		advicesList = new JList<ModuleAction>(moduleActionsListModel);
		advicesList.setCellRenderer(new ModuleActionListCellRenderer(defaultFont));

		advicesList.setPreferredSize(new Dimension(WIDTH, ADVICESLIST_MAXHEIGHT));
		advicesList.setOpaque(false);
		
		mainPanel.add(advicesList);
	}
	
	/**
	 * Updates the shown module actions.
	 */
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
		textField.setText("");
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
		return advicesList.getSelectedValue();
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
