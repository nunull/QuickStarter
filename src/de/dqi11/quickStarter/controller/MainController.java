package de.dqi11.quickStarter.controller;

import java.util.LinkedList;

import de.dqi11.quickStarter.modules.Advice;
import de.dqi11.quickStarter.modules.Module;

public class MainController {
	private LinkedList<Module> modules;
	
	public MainController() {
		this.modules = new LinkedList<>();
	}
	
	public void initModules() {
		// TODO this.modules.push(new Module())
	}
	
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
}
