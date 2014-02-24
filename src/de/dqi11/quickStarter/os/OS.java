package de.dqi11.quickStarter.os;

import java.util.Observable;

/**
 * Wrapper for operating-system-specific tools.
 */
public abstract class OS extends Observable {
	
	/**
	 * Toggles the visibility of the application (opens or closes the input-field).
	 * Notifies all Observers.
	 */
	public void toggleApp() {
		setChanged();
		notifyObservers();
	}
}
