/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus Uelker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
	private int ID;
	
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
