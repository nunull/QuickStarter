package de.dqi11.quickStarter.modules.bridges;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class OpenWeatherMapBridge implements Bridge {
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	
	public static String getJSON(String url, int timeout) throws ConnectException {
		try{
			URL u = new URL(BASE_URL + url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}
		} catch(UnknownHostException e) {
			throw new ConnectException();
		} catch(MalformedURLException ex) {
			ex.printStackTrace();
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
		
	    return null;
	}
}
