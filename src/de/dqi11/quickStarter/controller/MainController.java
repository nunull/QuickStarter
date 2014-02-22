package de.dqi11.quickStarter.controller;

import java.util.LinkedList;

import de.dqi11.quickStarter.modules.Advice;
import de.dqi11.quickStarter.modules.Module;

/**
 * The main-controller. Brings everything together.
 */
public class MainController {
	private LinkedList<Module> modules;
	
	/**
	 * Constructor.
	 */
	public MainController() {
		this.modules = new LinkedList<>();
	}
	
	/**
	 * Initializes the modules.
	 */
	public void initModules() {
		// TODO this.modules.push(new Module())
	}
	
	/**
	 * Gets possible actions for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @return a list of Advices (possible actions).
	 */
	public LinkedList<Advice> invoke(String search) {
		LinkedList<Advice> advices = new LinkedList<>();
		
		for(Module m : modules) {
			Advice advice = m.getAdvice(search);
			if(advice != null) {
				advices.push(advice);
			}
		}
		return advices;
	}
	
	/**
	 * Wrapper for future use, i.e. shutting global (os-wide) shurtcut-handlers down. 
	 */
	public void shutdown() {
		
	}
}
