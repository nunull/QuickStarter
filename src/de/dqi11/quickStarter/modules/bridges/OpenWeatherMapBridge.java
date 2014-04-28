package de.dqi11.quickStarter.modules.bridges;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;


public class OpenWeatherMapBridge implements Bridge {
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=b1e761a9aea5ddf17d383c0d3f3f1c56&q=";
	
	public static String getJSON(String url) throws ConnectException {
		return HttpBridge.getContent(BASE_URL + url);
	}
	
	
}
