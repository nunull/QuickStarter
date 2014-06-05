package de.dqi11.quickStarter.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.JTextPane;
import javax.swing.text.Document;

public class HelpWindow extends Window {
	private boolean initialized = false;
	private Document doc;
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
		
		add(textPane);
	}
	
	@Override
	public void show() {
		init();
		
		super.show();
	}
}
