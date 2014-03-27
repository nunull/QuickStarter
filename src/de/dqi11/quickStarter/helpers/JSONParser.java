package de.dqi11.quickStarter.helpers;

import java.util.Map;

import com.json.parsers.JsonParserFactory;

public class JSONParser {
	private Map<String, Object> jsonMap;
	
	/**
	 * Constructor.
	 * 
	 * @param json The json string to be parsed.
	 */
	@SuppressWarnings("unchecked")
	public JSONParser(String json) {
		this.jsonMap = JsonParserFactory.getInstance().newJsonParser().parseJson(json);
	}
	
	/**
	 * Gets the attribute represented by the given path.
	 * 
	 * @param path The path. (Example: "attribute1.attribute2")
	 * @return The value or null.
	 */
	@SuppressWarnings("rawtypes")
	public String get(String path) {
		try {
			String[] parts = path.split("\\.");
			Object tmp = jsonMap.get(parts[0]);
			
			for(int i = 1; i < parts.length; i++) {
				tmp = ((Map) tmp).get(parts[i]);
			}
			
			return (String)tmp;
		} catch(Exception e) {
			return null;
		}
	}
}
