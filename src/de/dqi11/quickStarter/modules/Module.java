package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.LinkedList;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

/**
 * Represents a module.
 */
public abstract class Module implements Comparable<Module> {
	private MainController mainController;
	private LinkedList<Module> exceptions;
	private boolean active;
	private String key;
	
	/**
	 * Constructor
	 * @param mainController the MainController
	 */
	public Module (MainController mainController) {
		this.mainController = mainController;
		this.exceptions = new LinkedList<>();
		this.active = true;
		this.key = this.toString();
	}
	
	/**
	 * Return a possible action for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @throws An ConnectException, if the module necessarily needs to connect to the Internet and an error occurs.
	 * @return The Advice.
	 */
	public abstract ModuleAction getModuleAction(final Search search) throws ConnectException;
	
	/**
	 * Getter.
	 * 
	 * @return the MainController.
	 */
	public MainController getMainController(){
		return mainController;
	}
	
	/**
	 * Adds an exception.
	 * 
	 * @param exception The exception.
	 */
	public void addException(Module exception) {
		exceptions.add(exception);
	}
	
	/**
	 * Getter.
	 * 
	 * @return the exceptions.
	 */
	public LinkedList<Module> getExceptions() {
		return exceptions;
	}
	
	/**
	 * Setter
	 * 
	 * @param exceptions The exceptions.
	 */
	public void setExceptions(LinkedList<Module> exceptions) {
		this.exceptions = exceptions;
	}
	
	/**
	 * Returns if the Module is active.
	 *  
	 * @return true, if the Module is active, false otherwise.
	 */
	public boolean isActive() {
		return active;
	}
	
	/*
	 * Returns the key of the module.
	 * 
	 * @return the key.
	 */
	public String getKey() {
		return key;
	}

	@Override
	public int compareTo(Module o) {
		return this.getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
	}
}
