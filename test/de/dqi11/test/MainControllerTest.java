package de.dqi11.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

public class MainControllerTest {
	MainController mainController;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mainController = new MainController();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link de.dqi11.quickStarter.controller.MainController#invoke(de.dqi11.quickStarter.controller.Search)}.
	 */
	@Test
	public void testInvoke() {
		assertEquals(0, mainController.invoke(null).size());
		assertEquals(0, mainController.invoke(new Search("")).size());
		assertEquals(2, mainController.invoke(new Search("test")).size());
	}

	@Test
	public void testIsNetworkError() {
		assertEquals(false, mainController.isNetworkError());
		mainController.invoke(new Search());
		assertEquals(true, mainController.isNetworkError());
	}
}
