package de.dqi11.quickStarter.modules;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.dqi11.quickStarter.gui.Warning;

public class WarningModuleAction extends ModuleAction implements Warning {

	public WarningModuleAction(String key, String text, Icon icon) {
		super(key, text, icon);
	}

	public WarningModuleAction(String key, String text) {
		super(key, text);
	}

	public WarningModuleAction(String key) {
		super(key);
	}

}
