package de.dqi11.quickStarter.helpers;

import java.util.ArrayList;
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
	 * Constructor.
	 * 
	 * @param jsonMap The previously parsed JSONMap.
	 */
	public JSONParser(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	/**
	 * Gets the attribute represented by the given path.
	 * 
	 * @param path The path. (Example: "attribute1.attribute2")
	 * @param obj The object to work with
	 * @return The value.
	 */
	@SuppressWarnings("rawtypes")
	private Object getRaw(String path, Map<String, Object> obj) {
		try {
			String[] parts = path.split("\\.");
			Object tmp = obj.get(parts[0]);
			
			for(int i = 1; i < parts.length; i++) {
				tmp = ((Map) tmp).get(parts[i]);
			}
			
			return tmp;
		} catch(Exception e) {
			return null;
		}
	}
	
	private Object getRaw(String path) {
		return getRaw(path, jsonMap);
	}
	
	/**
	 * Gets the attribute represented by the given path.
	 * 
	 * @param path The path. (Example: "attribute1.attribute2")
	 * @return The value or null.
	 */
	public String get(String path) {
		return (String)getRaw(path);
	}
	
	/**
	 * Gets the attribute represented by the given path.
	 * 
	 * @param path The path. (Example: "attribute1.attribute2")
	 * @return The value or null.
	 */
	public ArrayList<JSONParser> getArrayList(String path) {
		ArrayList<Object> raw = (ArrayList<Object>) getRaw(path);
		ArrayList<JSONParser> parsed = new ArrayList<JSONParser>();
		
		for(Object obj : raw) {
			parsed.add(new JSONParser((Map<String, Object>) obj));
		}
		
		return parsed;
	}
}
