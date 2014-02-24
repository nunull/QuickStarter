package de.dqi11.quickStarter.controller;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import de.dqi11.quickStarter.gui.MainWindow;
import de.dqi11.quickStarter.modules.Advice;
import de.dqi11.quickStarter.modules.Module;
import de.dqi11.quickStarter.modules.TestModule;
import de.dqi11.quickStarter.os.MacOS;
import de.dqi11.quickStarter.os.OS;
import de.dqi11.quickStarter.os.WinOS;

/**
 * The main-controller. Brings everything together.
 */
public class MainController implements Observer {
	private LinkedList<Module> modules;
	private OS winOS;
	private OS macOS;
	private MainWindow mainWindow;
	
	/**
	 * Constructor.
	 */
	public MainController() {
		this.modules = new LinkedList<>();
		
		initModules();
		initOS();
		initGUI();
		
		/*
		 * Just a small test.
		 */
		mainWindow.toggleApplication();
	}
	
	/**
	 * Initializes the modules.
	 */
	public void initModules() {
		// TODO this.modules.push(new Module())
		modules.push(new TestModule());
	}
	
	/**
	 * Initializes the wrappers for operating-systems.
	 */
	public void initOS() {
		winOS = new WinOS();
		macOS = new MacOS();
		
		winOS.addObserver(this);
		macOS.addObserver(this);
	}
	
	/**
	 * Initializes the GUI.
	 */
	public void initGUI() {
		mainWindow = new MainWindow();
		mainWindow.addObserver(this);
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
	 * Shuts global (OS-wide) shortcut-handlers down. 
	 */
	public void shutdown() {
		winOS.shutdown();
		macOS.shutdown();
	}

	/**
	 * Will be called from OS-classes, when the visibility of the application should be toggled.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof OS) {
			mainWindow.toggleApplication();
		} else if(o instanceof MainWindow) {
			mainWindow.setAdvices(invoke(mainWindow.getSearchString()));;
		}
	}
}
