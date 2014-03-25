package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

/**
 * Represents a module.
 */
public abstract class Module {
	private MainController mainController;
	
	/**
	 * Constructor
	 * @param mainController the MainController
	 */
	public Module (MainController mainController) {
		this.mainController = mainController;
	}
	
	/**
	 * Return a possible action for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @throws An ConnectException, if the module necessarily needs to connect to the Internet and an error occurs.
	 * @return The Advice.
	 */
	public abstract ModuleAction getModuleAction(Search search) throws ConnectException;
	
	/**
	 * Getter.
	 * 
	 * @return the MainController.
	 */
	public MainController getMainController(){
		return mainController;
	}
}
