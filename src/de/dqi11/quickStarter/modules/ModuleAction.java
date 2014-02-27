package de.dqi11.quickStarter.modules;

import de.dqi11.quickStarter.gui.ModuleWindow;

/**
 * Represents an Advice (a possible action for a specific Module-search-string-combination).
 */
public class ModuleAction {
	private String text;
	
	/**
	 * Default constructor.
	 */
	public ModuleAction() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param text The text.
	 */
	public ModuleAction(String text) {
		this.text = text;
	}
	
	/**
	 * Invoke the ModuleAction.
	 */
	public void invoke() {
		
	}
	
	/**
	 * Returns the ModuleWindow of this ModuleAction or null if this is a non-GUI-action.
	 * 
	 * @return A ModuleWindow or null if this is a non-GUI-action.
	 */
	public ModuleWindow getModuleWindow() {
		return null;
	}

	/**
	 * Returns a text describing the represented action.
	 *  
	 * @return The describing text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the describing text.
	 * 
	 * @param text The describing text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
