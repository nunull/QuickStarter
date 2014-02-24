package de.dqi11.quickStarter.os;

import java.util.Observable;

/**
 * Wrapper for operating-system-specific tools.
 */
public abstract class OS extends Observable {
	
	/**
	 * Toggles the visibility of the application (opens or closes the input-field).
	 * Notifies all Observers and should be called from within the specific instances
	 * of OS (WinOS and MacOS) when the global keyboard-shortcuts are pressed. 
	 */
	public void toggleApp() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Called when the applications exits.
	 * Can be used to shut global keyboard-listeners down.
	 */
	public abstract void shutdown();
}