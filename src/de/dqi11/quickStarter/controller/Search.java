package de.dqi11.quickStarter.controller;

/**
 * Represents a specific search.
 */
public class Search {
	private String searchString = "";
	
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
	 * Returns the command or an empty string, if there is not any.
	 * 
	 * @return the command.
	 */
	public String getCommand() {
		String[] parts = searchString.split(" ");
		
		try {
			return parts[0];
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
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
	
	/**
	 * Returns the parameters of the search.
	 * 
	 * @return The parameters (separated by spaces).
	 */
	public String getParams() {
		return searchString.replaceFirst(getCommand() + " ", "");
	}
	
	/**
	 * Returns the parameter at the given index.
	 * 
	 * @param index The index.
	 * @return The parameter or an empty string if the index is out of bounds.
	 */
	public String getParam(int index) {
		String[] params = searchString.split(" ");
		
		try {
			return params[index+1];
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * Checks whether the search-string equals the given string.
	 * 
	 * @param s The String to check.
	 * @return true if the strings are equal, false otherwise.
	 */
	public boolean equals(String s) {
		return searchString.equals(s);
	}
	
	/**
	 * Returns the specific search string.
	 * 
	 * @return The search string.
	 */
	public String getSearchString() {
		return searchString;
	}
}
