/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus †lker
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
