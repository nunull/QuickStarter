package de.dqi11.test.gui;

import static org.junit.Assert.*;

import org.junit.Test;

import de.dqi11.quickStarter.gui.DefaultFont;

public class DefaultFontTest {

	@Test
	public void testGetFontInt() {
		assertNotEquals(null, DefaultFont.getFont(20));
	}

	@Test
	public void testGetFont() {
		assertNotEquals(null, DefaultFont.getFont());
	}

}
