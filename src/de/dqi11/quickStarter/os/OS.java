package de.dqi11.quickStarter.os;

import java.util.Observable;

/**
 * Wrapper for operating-system-specific tools.
 */
public abstract class OS extends Observable {
	private boolean active = true;
	
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

	/**
	 * Starts / stops the listening-process of the shortcut.
	 * 
	 * @param active true if the shortcut should be listened, else if not
	 */
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Return whether the shortcut is active or not.
	 * 
	 * @return true if the shortcut is active
	 */
	public boolean isActive() {
		return active;
	}
}