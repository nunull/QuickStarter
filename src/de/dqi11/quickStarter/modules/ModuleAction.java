package de.dqi11.quickStarter.modules;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

/**
 * Represents a ModuleAction (a possible action for a specific Module-search-string-combination).
 */
public class ModuleAction {
	private String key;
	private String text;
	private Icon icon;
	
	/**
	 * Default constructor.
	 * 
	 * @param key identifying the specific module belonging to this ModuleAction.
	 */
	public ModuleAction(String key) {
		this.key = key;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param key identifying the specific module belonging to this ModuleAction.
	 * @param text The text.
	 */
	public ModuleAction(String key, String text) {
		super();
		this.key = key;
		this.text = text;
		this.icon = new ImageIcon("res/empty-icon.png");
	}
	
	/**
	 * Constructor.
	 * 
	 * @param key identifying the specific module belonging to this ModuleAction.
	 * @param text The text.
	 * @param label An icon to be shown.
	 */
	public ModuleAction(String key, String text, Icon icon) {
		super();
		this.key = key;
		this.text = text;
		this.icon = icon;
	}
	
	/**
	 * Invoke the ModuleAction.
	 */
	public void invoke(Search search) {
		// Implement in overrided method.
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			return this.getKey().equals( ((ModuleAction)obj).getKey() );
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Returns the ModuleWindow of this ModuleAction or null if this is a non-GUI-action.
	 * 
	 * @return A ModuleWindow or null if this is a non-GUI-action.
	 */
	public ModuleWindow getModuleWindow(Search search) {
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
	
	/**
	 * Returns a label representing the action.
	 * 
	 * @return The label.
	 */
	public Icon getIcon() {
		return icon;
	}
	
	@Override
	public String toString() {
		return text;
	}
//TODO javadoc
	public String getKey() {
		return key;
	}
//TODO javadoc
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
}
