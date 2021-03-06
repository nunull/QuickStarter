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
package de.dqi11.quickStarter.controller;

import java.awt.HeadlessException;
import java.net.ConnectException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import de.dqi11.quickStarter.gui.HelpWindow;
import de.dqi11.quickStarter.gui.MainWindow;
import de.dqi11.quickStarter.gui.PreferenceWindow;
import de.dqi11.quickStarter.gui.Taskbar;
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
	@SuppressWarnings("unused")
	private Taskbar taskbar;
	private PersitencyController persitencyController;
	private PreferenceWindow prefereceWindow;
	private HelpWindow helpWindow;
	
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
		if(mainWindow != null) {
			mainWindow.toggleApplication();
		}
	}
	
	/**
	 * Initializes the modules.
	 */
	private void initModules() {
		modules = persitencyController.getActiveModules();
		
		// CoreModules have to be added last, since otherwise they won't receive 
		// errors, which were produced by other Modules.
		modules.add(new ErrorCoreModule(this));
	}
	
	/**
	 * Initializes the wrappers for operating-systems.
	 */
	private void initOS() {
		String osName = System.getProperty("os.name");
		
		try {
			if( osName.contains("Windows") ) os = new WinOS();
			else if( osName.contains("Mac") ) os = new MacOS();
		} catch(Exception e) {	
		}
		
		if(os != null) {
			os.addObserver(this);
		}
	}
	
	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			mainWindow = new MainWindow();
			mainWindow.addObserver(this);
			mainWindow.init();
			prefereceWindow = new PreferenceWindow(this);
			helpWindow = new HelpWindow();
			
			// TODO
	//		SwingUtilities.invokeLater(new Runnable() {
	//			
	//			@Override
	//			public void run() {
	//				mainWindow.init();
	//			}
	//		});
			
			taskbar = new Taskbar(this);
		} catch(HeadlessException e) {
			mainWindow = null;
		}
	}
	
	/**
	 * Gets possible actions for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @return a list of Advices (possible actions).
	 */
	public LinkedList<ModuleAction> invoke(Search search) {
		moduleActions = new LinkedList<ModuleAction>();
		
		if(search != null) {
			LinkedList<Module> activeModules = new LinkedList<>();
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
			moduleActions.remove(index + 1);
			
			if(mainWindow != null) {
				mainWindow.updateModuleActions();
			}
			
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
	 * Quits the application.
	 */
	public void quit() {
		shutdown();
		
		// TODO bad style
		System.exit(0);
	}

	/**
	 * Will be called from 
	 * 1. OS-classes, when the visibility of the application should be toggled,
	 * 2. MainWindow, when ModuleActions should be updated.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(mainWindow != null) {
			if(o instanceof OS) {
				mainWindow.toggleApplication();
			} else if(o instanceof MainWindow) {
				mainWindow.setModuleActions(
						invoke(
								new Search(
										mainWindow.getSearchString())));;
			}
		}
	}
	
	/**
	 * Returns all properties of the specific module.
	 * 
	 * @param module The module.
	 * @return All properties as key-value-pairs.
	 */
	public Map<String, String> getModuleProperties(Module module) {
		return persitencyController.getModuleProperties(module);
	}
	
	/**
	 * Returns all properties of the specific module, that are either user-editable via the preferences or not.
	 * 
	 * @param module The module.
	 * @param preferences Specifies if the properties returned are user-editable (true) or not (false).
	 * @return All properties as key-value-pairs.
	 */
	public Map<String, String> getModuleProperties(Module module, boolean preferences) {
		return persitencyController.getModuleProperties(module, preferences);
	}
	
	/**
	 * Returns the specific key if present or null.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @return The value of the key or null if not present.
	 * @see de.dqi11.quickStarter.controller.PersitencyController#getModuleProperty(Module, String)
	 */
	public String getModuleProperty(Module module, String key) {
		return persitencyController.getModuleProperty(module, key);
	}
	
	/**
	 * Returns all Modules.
	 * 
	 * @return A list of Modules.
	 */
	public LinkedList<Module> getModules() {
		return persitencyController.getModules();
	}
	
	/**
	 * Returns whether the given module is active or not.
	 * 
	 * @param module The module.
	 * @return true if the given module is active.
	 */
	public boolean isModuleActive(Module module) {
		return persitencyController.isModuleActive(module);
	}
	
	/**
	 * Sets the state of the given module.
	 * 
	 * @param module The module.
	 * @param active The state.
	 */
	public void setModuleActive(Module module, boolean active) {
		persitencyController.setModuleActive(module, active);
	}

	/**
	 * Saves or updates the value of the specific key.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @param value The value.
	 * @see de.dqi11.quickStarter.controller.PersitencyController#saveOrUpdateModuleProperty(Module, String, String)
	 */
	public void saveOrUpdateModuleProperty(Module module, String key, String value) {
		persitencyController.saveOrUpdateModuleProperty(module, key, value);
	}
	
	/**
	 * Removes the specific key.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @see de.dqi11.quickStarter.controller.PersitencyController#removeModuleProperty(Module, String)
	 */
	public void removeModuleProperty(Module module, String key) {
		persitencyController.removeModuleProperty(module, key);
	}

	/**
	 * Getter.
	 * 
	 * @return true, if an networkError occurred.
	 */
	public boolean isNetworkError() {
		return networkError;
	}

	public OS getOS() {
		return os;
	}

	public void showHelpWindow() {
		helpWindow.show();
	}
	
	public void showPrefenceWindow() {
		prefereceWindow.show();
	}
}
