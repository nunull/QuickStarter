package de.dqi11.test.helpers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.helpers.JSONParser;

public class JSONParserTest {
	
	private final String JSON = "{\"aString\":\"text\",\"anArray\":[{\"name\":\"first\"},{\"name\":\"second\"}],\"anObject\":{\"first\":1,\"second\":2}}";
	private JSONParser jsonParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jsonParser = new JSONParser(JSON);
	}
	
	@Test
	public void testGet() {
		assertEquals("text", jsonParser.get("aString"));
		assertEquals("1", jsonParser.get("anObject.first"));
		assertEquals("2", jsonParser.get("anObject.second"));
	}

	@Test
	public void testGetArrayList() {
		ArrayList<JSONParser> arrayList = jsonParser.getArrayList("anArray");
		
		assertEquals(2, arrayList.size());
		assertEquals("first", arrayList.get(0).get("name"));
		assertEquals("second", arrayList.get(1).get("name"));
	}
}
