/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus †lker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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
