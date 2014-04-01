package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A wrapper for a Module-window.
 * Can be used by Modules to implement GUI-elements.
 */
public class ModuleWindow {
	private final int WIDTH = 800;
	private final int HEIGHT = 500;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private Font defaultFont;
	private Font boldFont;
	
	public ModuleWindow() {
		initFonts();
		initMainFrame();
		initMainPanel();
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
		mainFrame.setSize(WIDTH-20, HEIGHT);
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
	 * Appends the specific component to the end of this container.
	 * 
	 * @param component The Component.
	 * @see JPanel
	 */
	public void add(Component component) {
		mainPanel.add(component);
	}
	
	/**
	 * Shows the window.
	 */
	public void show() {
		mainFrame.setVisible(true);
	}
}
