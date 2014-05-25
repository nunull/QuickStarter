package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A wrapper for a Module-window.
 * Can be used by Modules to implement GUI-elements.
 */
public class ModuleWindow extends de.dqi11.quickStarter.gui.Window{
	
	public ModuleWindow() {
		super();
	}
	
	public ModuleWindow(String title) {
		super(title);
	}
	
}
