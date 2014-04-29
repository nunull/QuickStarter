package de.dqi11.quickStarter.modules.bridges;

import java.net.ConnectException;
import java.text.MessageFormat;

public class GlosbeBridge {
	private static final String BASE_URL = "http://glosbe.com/gapi/translate?from={0}&dest={1}&format=json&phrase={2}&page=1&pretty=true";
	
	public static String getJSON(String from, String to, String text) throws ConnectException{
		String url = MessageFormat.format(BASE_URL, new Object[]{from, to, text});
		return HTTPBridge.getContent(url);
	}
}
