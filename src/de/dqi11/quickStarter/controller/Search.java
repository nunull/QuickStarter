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
		searchString = "";
	}
	
	/**
	 * Constructor.
	 * 
	 * @param searchString A specific search-string.
	 */
	public Search(String searchString) {
		this.searchString = "";
		if(searchString != null) {
			this.searchString = searchString.trim();
		}
	}
	
	/**
	 * Returns true if the search has the specific command (first part of the search-string).
	 * 
	 * @param command The specific command.
	 * @return true if the search has the specific command, false otherwise.
	 */
	public boolean isCommand(String command) {
		command = command == null ? "" : command;
		
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
		s = s == null ? "" : s;
		
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
