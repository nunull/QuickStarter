package de.dqi11.quickStarter.controller;

/**
 * Represents a specific search.
 */
public class Search {
	private String searchString;
	
	/**
	 * Default constructor.
	 */
	public Search() {
	}
	
	/**
	 * Constructor.
	 * 
	 * @param searchString A specific search-string.
	 */
	public Search(String searchString) {
		this.searchString = searchString;
	}
	
	/**
	 * Returns true if the search has the specific command (first part of the search-string).
	 * 
	 * @param command The specific command.
	 * @return true if the search has the specific command, false otherwise.
	 */
	public boolean isCommand(String command) {
		return partEquals(0, command);
	}
	
	/**
	 * Checks if the part equals the specific String.
	 * 
	 * @param index The index of the part.
	 * @param part The part.
	 * @return True, if the part equals the specific String, false otherwise.
	 */
	public boolean partEquals(int index, String part) {
		String[] parts = searchString.split(" ");
		
		try {
			return parts[index].equals(part);
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean equals(String s) {
		return searchString.equals(s);
	}
}
