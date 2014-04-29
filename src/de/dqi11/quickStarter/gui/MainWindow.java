package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.ModuleAction;

/**
 * Represents the main-application window, which 
 * shows the search-field and ModuleActions.
 */
public class MainWindow extends Observable {
	private final int WIDTH = 500;
	private final int TEXTFIELD_HEIGHT = 60;
	private final int ADVICESLIST_MAXHEIGHT = 450;
	private boolean visible;
	private JDialog mainDialog;
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
	private Thread updateHeightThread;
	
	/**
	 * Constructor.
	 */
	public MainWindow() {
		visible = false;
	}
	
	/**
	 * Initializes the whole GUI.
	 */
	public void init() {
		initListeners();
		initFonts();
		initMainDialog();
		initMainPanel();
		initTextField();
		initErrorLabel();
		initModuleActionsPanel();
		updateHeight();
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					toggleApplication();
					
				// enter-key
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					invokeSelectedModuleAction();
					
				// down-arrow-key
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.consume();
					selectNext();
					
				// up-arrow-key
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					e.consume();
					selectPrevious();
				}
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
	private void initMainDialog() {
		mainDialog = new JDialog();
		
		mainDialog.setUndecorated(true);
		mainDialog.setSize(WIDTH-20, TEXTFIELD_HEIGHT);
//		mainFrame.setShape(new RoundRectangle2D.Double(10, 10, 100, 100, 50, 50));
		mainDialog.setLocationRelativeTo(null);
		mainDialog.setLocation(mainDialog.getLocation().x, 100);
		mainDialog.setOpacity(0.8f);
		mainDialog.setBackground(Color.BLACK);
	}
	
	/**
	 * Initializes the mainPanel.
	 */
	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainDialog.setContentPane(mainPanel);
	}
	
	/**
	 * Initializes the textField.
	 */
	private void initTextField() {
		textField = new JTextField();
		
		Border line = BorderFactory.createLineBorder(Color.BLACK);
		Border empty = new EmptyBorder(0, 80, 0, 0);
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
		updateHeight();
	}
	
	/**
	 * Updates the window-height.
	 */
	private void updateHeight() {
		final int startHeight = mainDialog.getHeight();
		final int endHeight = moduleActionsListModel.getSize() * TEXTFIELD_HEIGHT + TEXTFIELD_HEIGHT + 10;
		
		if(updateHeightThread != null) {
			updateHeightThread.stop();
		}
		
		if(startHeight != endHeight) {
			updateHeightThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					int direction = 1;
					if(startHeight > endHeight) direction = -1;
					int speed = 45;
					
					for(int i = startHeight; i < endHeight && direction > 0 || i > endHeight && direction < 0; i += direction * speed) {
						final int currentHeight = i;
						
						try {
							SwingUtilities.invokeAndWait(new Runnable() {
								
								@Override
								public void run() {
									mainDialog.setSize(mainDialog.getWidth(), currentHeight);
									mainDialog.repaint();
								}
							});
						} catch (InvocationTargetException e1) {
						} catch (InterruptedException e1) {
						}
						
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
						}
					}
					
					try {
						SwingUtilities.invokeAndWait(new Runnable() {
							
							@Override
							public void run() {
								mainDialog.setSize(mainDialog.getWidth(), endHeight);
								mainDialog.repaint();
								
							}
						});
					} catch (InvocationTargetException e) {
					} catch (InterruptedException e) {
					}
				}
			});
			updateHeightThread.start();
		}
	}
	
	/**
	 * Toggles the visibility of the application.
	 */
	public void toggleApplication() {
		visible = !visible;
		
		mainDialog.setVisible(visible);
		if(visible) {
			mainDialog.setAlwaysOnTop(true);
			mainDialog.toFront();
			mainDialog.requestFocus();
			mainDialog.setAlwaysOnTop(false);
		}
		textField.setText("");
		textField.requestFocus();
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
	
	/**
	 * Returns the index of the selected ModuleAction.
	 * 
	 * @return the index
	 */
	public int getSelectedIndex() {
		return advicesList.getSelectedIndex();
	}
	
	/**
	 * Returns the selected ModuleAction.
	 * 
	 * @return the selected ModuleAction.
	 */
	public ModuleAction getSelectedModuleAction() {
		return advicesList.getSelectedValue();
	}
	
	/**
	 * Invokes the currently selected ModuleAction.
	 */
	public void invokeSelectedModuleAction() {
		Search search = new Search(getSearchString());
		ModuleAction moduleAction = getSelectedModuleAction();
		
		if(moduleAction != null) {
			ModuleWindow moduleWindow = moduleAction.getModuleWindow(search);
			
			toggleApplication();
			if(moduleWindow == null) {
				moduleAction.invoke(search);
			} else {
				moduleWindow.show();
			}
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
