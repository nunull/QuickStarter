package de.dqi11.quickStarter.modules;

import de.dqi11.quickStarter.controller.MainController;

/**
 * Represents a CoreModule, which is a static part of the software (can not be removed).
 */
public abstract class CoreModule implements Module {
	private MainController controller;
	
	/**
	 * Constructor.
	 * 
	 * @param controller the MainController.
	 */
	public CoreModule(MainController controller) {
		this.controller = controller;
	}

	/**
	 * Getter.
	 * 
	 * @return the MainController.
	 */
	public MainController getController() {
		return controller;
	}
}
