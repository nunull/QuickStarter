package de.dqi11.test.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.PersitencyController;

public class PersistencyControllerTest {
	private PersitencyController persitencyController;
	
	@Before
	public void setUp() throws Exception {
		persitencyController = new PersitencyController(new MainController());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetModules() {
		assertNotEquals(0, persitencyController.getModules().size());
	}

}
