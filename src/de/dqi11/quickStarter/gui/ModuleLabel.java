package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class ModuleLabel extends JLabel {

	private static final long serialVersionUID = -9214836264225982745L;
	
	public ModuleLabel() {
		super();
		
		initLabel();
	}
	
	public ModuleLabel(String text) {
		super(text);
		
		initLabel();
	}
	
	private void initLabel() {
		setPreferredSize(new Dimension(380, 60));
		setForeground(Color.WHITE);
	}
}
