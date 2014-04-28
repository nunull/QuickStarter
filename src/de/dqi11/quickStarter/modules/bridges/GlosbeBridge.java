package de.dqi11.quickStarter.modules.bridges;

import java.net.ConnectException;

public class GlosbeBridge {
	private static final String BASE_URL = "http://glosbe.com/gapi/translate?from={0}&dest={1}&format=json&phrase={2}&page=1&pretty=true";
	
	public static String getJSON(String url) throws ConnectException{
		return HttpBridge.getContent(BASE_URL + url);
	}
}
