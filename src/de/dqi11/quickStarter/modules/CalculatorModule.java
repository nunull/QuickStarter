package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
	//TODO Delete unused imports
import javax.swing.*;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class CalculatorModule extends Module {
	private final String KEY = this.toString();
	
	public CalculatorModule(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.getSearchString().indexOf("calc") == 0) {
			return new ModuleAction(KEY, "Calculator") {
				@Override
				public ModuleWindow getModuleWindow(Search search) {
					// TODO Auto-generated method stub
					ModuleWindow moduleWindow = new ModuleWindow();
					moduleWindow.add (new JButton());
					return moduleWindow;
				}
			};
		} else {
			return null;
		}
	}

}
