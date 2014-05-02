package de.dqi11.quickStarter.modules;

import javax.swing.ImageIcon;

public class LoadingModuleAction extends ModuleAction {
	
	public LoadingModuleAction(String key) {
		super(key, "Loading...", new ImageIcon("res/loading.gif"));
	}
	
	public LoadingModuleAction(String key, String text) {
		super(key, text, new ImageIcon("res/loading.gif"));
	}
}
