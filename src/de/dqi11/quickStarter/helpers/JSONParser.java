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
package de.dqi11.quickStarter.helpers;

import java.util.ArrayList;
import java.util.Map;

import com.json.parsers.JsonParserFactory;

public class JSONParser {
	private Map<String, Object> jsonMap;
	
	/**
	 * Constructor.
	 * 
	 * @param json The JSON string to be parsed.
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
	@SuppressWarnings("unchecked")
	public ArrayList<JSONParser> getArrayList(String path) {
		ArrayList<Object> raw = (ArrayList<Object>) getRaw(path);
		ArrayList<JSONParser> parsed = new ArrayList<JSONParser>();
		
		for(Object obj : raw) {
			parsed.add(new JSONParser((Map<String, Object>) obj));
		}
		
		return parsed;
	}
}
