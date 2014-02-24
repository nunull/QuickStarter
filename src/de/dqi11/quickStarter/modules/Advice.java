package de.dqi11.quickStarter.modules;

/**
 * Represents an Advice (a possible action for a specific Module-search-string-combination).
 */
public class Advice {
	private String text;
	
	/**
	 * Default constructor.
	 */
	public Advice() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param text The text.
	 */
	public Advice(String text) {
		this.text = text;
	}
	
	// TODO return type
	public void getTooltip() {
		
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
