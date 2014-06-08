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

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class HelpWindow extends Window {
	private boolean initialized = false;
	private JTextPane textPane;
	
	public HelpWindow() {
		super("Help");
	}
	
	private void init() {
		if(!initialized) {
			initGUI();
			initDoc();
			
			initialized = true;
		}
	}
	
	private void initDoc() {
		String doc = "";
		BufferedReader r;
		
		try {
			r = new BufferedReader(new FileReader("res/help.html"));
			
			while(r.ready()) {
				doc += r.readLine();
			}
		} catch (IOException e1) {
			doc = "<!DOCTYPE html><html><head><title>Help</title><style type=\"text/css\">b{font-family:Arial, sans-serif;}</style></head><body><b>An error occured.</b></body></html>";
		}
		
		textPane.setContentType("text/html");
		textPane.setText(doc);
	}
	
	private void initGUI() {
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(740, 460));
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(740, 460));
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		add(scrollPane);
	}
	
	@Override
	public void show() {
		init();
		
		super.show();
	}
}
