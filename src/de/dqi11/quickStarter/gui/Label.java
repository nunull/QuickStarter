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
		defaultFont = DefaultFont.getFont(fontSize);
		
		// Initialize bold font.
		boldFont = BoldFont.getFont(fontSize);
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
	
	@Override
	public String toString() {
		return getText();
	}
}
