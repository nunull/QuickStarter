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
		return this.active;
	}
}