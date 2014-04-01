package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import javax.swing.JButton;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class TestModule extends Module {
	private final String KEY = this.toString();

	public TestModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		// TODO Auto-generated method stub
		return new ModuleAction(KEY, "Test") {
			public ModuleWindow getModuleWindow(Search search) {
				ModuleWindow moduleWindow = new ModuleWindow();
				moduleWindow.add(new JButton("Test"));
				
				return moduleWindow;
			}
		};
	}

}
