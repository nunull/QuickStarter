package de.dqi11.quickStarter.controller;

import java.net.ConnectException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import de.dqi11.quickStarter.gui.MainWindow;
import de.dqi11.quickStarter.modules.ErrorCoreModule;
import de.dqi11.quickStarter.modules.ModuleAction;
import de.dqi11.quickStarter.modules.Module;
import de.dqi11.quickStarter.os.MacOS;
import de.dqi11.quickStarter.os.OS;
import de.dqi11.quickStarter.os.WinOS;

/**
 * The main-controller. Brings everything together.
 */
public class MainController implements Observer {
	private boolean networkError = false;
	private LinkedList<Module> modules;
	private LinkedList<ModuleAction> moduleActions;
	private OS os;
	private MainWindow mainWindow;
	private PersitencyController persitencyController;
	
	/**
	 * Constructor.
	 */
	public MainController() {
		this.modules = new LinkedList<Module>();
		this.moduleActions = new LinkedList<ModuleAction>();
		this.persitencyController = new PersitencyController(this);
		
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
		modules = persitencyController.getModules();
		
		// CoreModules have to be added last, since otherwise they won't receive 
		// errors, which were produced by other Modules.
		modules.add(new ErrorCoreModule(this));
	}
	
	/**
	 * Initializes the wrappers for operating-systems.
	 */
	public void initOS() {
		if( System.getProperty("os.name").contains("Windows") ) os = new WinOS();
		else os = new MacOS();
		os.addObserver(this);
	}
	
	/**
	 * Initializes the GUI.
	 */
	public void initGUI() {
		mainWindow = new MainWindow();
		mainWindow.addObserver(this);
		mainWindow.init();
		
		// TODO
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				mainWindow.init();
//			}
//		});
	}
	
	/**
	 * Gets possible actions for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @return a list of Advices (possible actions).
	 */
	public LinkedList<ModuleAction> invoke(Search search) {
		LinkedList<Module> activeModules = new LinkedList<>();
		moduleActions = new LinkedList<ModuleAction>();
		networkError = false;
		
		for(Module m : modules) {
			try {
				ModuleAction moduleAction = m.getModuleAction(search);
				if(moduleAction != null) {
					activeModules.add(m);
					moduleActions.add(moduleAction);
				}
			} catch(ConnectException e) {
				networkError = true;
			}
		}
		
		for(Module m : activeModules) {
			LinkedList<Module> exceptions = m.getExceptions();
			
			for(Module exception : exceptions) {
				if(activeModules.contains(exception)) {
					for(ModuleAction moduleAction : moduleActions) {
						if(moduleAction.getKey().equals(m.getKey())) {
							moduleActions.remove(moduleAction);
							break;
						}
					}
				}
			}
		}
		
		return moduleActions;
	}
	
	/**
	 * Updates the ModuleAction from the specific module.
	 *  
	 * @param modulAction The new ModuleAction.
	 * @return true if replacement was successful, false otherwise.
	 */
	public boolean updateModule(ModuleAction modulAction) {
		try {
			int index = moduleActions.lastIndexOf(modulAction);
			moduleActions.add(index, modulAction);
			moduleActions.remove(index+1);
			
			mainWindow.updateModuleActions();
			return true;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * Shuts global (OS-wide) shortcut-handlers down. 
	 */
	public void shutdown() {
		os.shutdown();
	}

	/**
	 * Will be called from 
	 * 1. OS-classes, when the visibility of the application should be toggled,
	 * 2. MainWindow, when ModuleActions should be updated.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof OS) {
			mainWindow.toggleApplication();
		} else if(o instanceof MainWindow) {
			mainWindow.setModuleActions(
					invoke(
							new Search(
									mainWindow.getSearchString())));;
		}
	}

	/**
	 * Getter.
	 * 
	 * @return true, if an networkError occurred.
	 */
	public boolean isNetworkError() {
		return networkError;
	}
}
