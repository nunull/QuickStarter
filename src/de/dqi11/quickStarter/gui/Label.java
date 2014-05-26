package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;

import javax.swing.JLabel;

public class Label extends JLabel {

	private static final long serialVersionUID = -9214836264225982745L;
	private int fontSize;
	private boolean bold;
	private Font defaultFont;
	private Font boldFont;
	
	public Label() {
		super();
		this.fontSize = 20;
		this.bold = false;
		
		initFonts();
		initLabel();
	}
	
	public Label(String text) {
		super(text);
		this.fontSize = 20;
		this.bold = false;
		
		initFonts();
		initLabel();
	}
	
	public Label(String text, boolean bold) {
		super(text);
		this.fontSize = 20;
		this.bold = bold;
		
		initFonts();
		initLabel();
	}
	
	public Label(String text, boolean bold, int fontSize) {
		super(text);
		this.fontSize = fontSize;
		this.bold = bold;
		
		initFonts();
		initLabel();
	}
	
	/**
	 * Initializes all fonts.
	 */
	private void initFonts() {
		// Initialize default font.
		try {
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Light.ttf"));
			defaultFont =  defaultFont.deriveFont((float) fontSize);
		} catch (Exception e) {
			defaultFont = new Font("Arial", Font.PLAIN, fontSize);
		}
		
		// Initialize bold font.
		try {
			boldFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Bold.ttf"));
			boldFont =  boldFont.deriveFont((float) fontSize);
		} catch (Exception e) {
			boldFont = new Font("Arial", Font.BOLD, fontSize);
		}
	}
	
	private void initLabel() {
		setPreferredSize(new Dimension(160, 60));
		setForeground(Color.BLACK);
		
		if(!bold) {
			setFont(defaultFont);
		} else {
			setFont(boldFont);
		}
	}
}
