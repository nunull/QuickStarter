package de.dqi11.test.controller;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;

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
		assertEquals(!isInternetReachable(), mainController.isNetworkError());
	}
	
	public static boolean isInternetReachable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.getContent();
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
