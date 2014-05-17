package de.dqi11.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.dqi11.quickStarter.controller.Search;

public class SearchTest {
	private Search search;

	@Test
	public void testEquals() {
		search = new Search(null);
		assertEquals(true, search.equals(null));
		assertEquals(true, search.equals(""));
		
		search = new Search("");
		assertEquals(true, search.equals(""));
		
		search = new Search("test");
		assertEquals(true, search.equals("test"));
	}
	
	@Test
	public void testIsCommand() {
		search = new Search(null);
		assertEquals(true, search.isCommand(""));
		assertEquals(true, search.isCommand(null));
		
		search = new Search("abc def");
		assertEquals(true, search.isCommand("abc"));
	}

	@Test
	public void testGetCommand() {
		search = new Search("");
		assertEquals("", search.getCommand());
		
		search = new Search("abc");
		assertEquals("abc", search.getCommand());
		
		search = new Search("abc def");
		assertEquals("abc", search.getCommand());
		
		search = new Search(null);
		assertEquals("", search.getCommand());
	}

	@Test
	public void testPartEquals() {
		search = new Search("");
		assertEquals(true, search.partEquals(0, ""));
		
		search = new Search("abc");
		assertEquals(true, search.partEquals(0, "abc"));
	}

	@Test
	public void testGetParams() {
		search = new Search("");
		assertEquals("", search.getParams());
		
		search = new Search(null);
		assertEquals("", search.getParams());
		
		search = new Search("a b c");
		assertEquals("b c", search.getParams());
	}

	@Test
	public void testGetParam() {
		search = new Search("");
		assertEquals("", search.getParam(0));
		
		search = new Search("abc def");
		assertEquals("def", search.getParam(0));
		
		search = new Search(" def");
		assertEquals("", search.getParam(0));
	}

	@Test
	public void testGetSearchString() {
		search = new Search(null);
		assertEquals("", search.getSearchString());
		
		search = new Search("abc");
		assertEquals("abc", search.getSearchString());
	}

}
