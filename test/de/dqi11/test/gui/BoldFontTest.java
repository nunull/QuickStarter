package de.dqi11.test.gui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dqi11.quickStarter.gui.BoldFont;

public class BoldFontTest {
	
	@Test
	public void testGetFontInt() {
		assertNotEquals(null, BoldFont.getFont(20));
	}

	@Test
	public void testGetFont() {
		assertNotEquals(null, BoldFont.getFont());
	}

}
