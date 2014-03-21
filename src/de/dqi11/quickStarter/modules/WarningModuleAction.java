package de.dqi11.quickStarter.modules;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.dqi11.quickStarter.gui.Warning;

public class WarningModuleAction extends ModuleAction implements Warning {
	/**
	 * Default constructor.
	 */
	public WarningModuleAction() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param text The text.
	 */
	public WarningModuleAction(String text) {
		super(text);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param text The text.
	 * @param label An icon to be shown.
	 */
	public WarningModuleAction(String text, Icon icon) {
		super(text, icon);
	}
}
