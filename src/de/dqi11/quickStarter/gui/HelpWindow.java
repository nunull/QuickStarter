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
