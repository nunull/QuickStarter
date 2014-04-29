package de.dqi11.quickStarter.modules;

import de.dqi11.quickStarter.controller.MainController;

/**
 * Represents a CoreModule, which is a static part of the software (can not be removed).
 */
public abstract class CoreModule extends Module {	
	
	public CoreModule(MainController mainController) {
		super(mainController);
	}
}
