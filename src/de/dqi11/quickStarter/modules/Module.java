package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import de.dqi11.quickStarter.controller.Search;

/**
 * Represents a module.
 */
public interface Module {
	/**
	 * Return a possible action for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @throws An ConnectException, if the module necessarily needs to connect to the Internet and an error occurs.
	 * @return The Advice.
	 */
	public ModuleAction getModuleAction(Search search) throws ConnectException;
}
