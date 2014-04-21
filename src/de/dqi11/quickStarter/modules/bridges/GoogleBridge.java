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

import de.dqi11.quickStarter.helpers.JSONParser;

public class GoogleBridge {
	private static final String CSE_ID = "009242032891967575886:naymd7huzoe";
	private static final String API_KEY = "AIzaSyB-3Zp6o_ebiL7fJ_niVtf_T_1ttcRys8g";
	private static final String BASE_URL = "https://www.googleapis.com/customsearch/v1?";
	
	public static JSONParser getSearch(String query) throws ConnectException {
		String url = BASE_URL + "key=" + API_KEY + "&cx=" + CSE_ID + "&q=" + query;
		try {
			System.out.println(url);
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(2000);
			c.setReadTimeout(2000);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				System.out.println("200");
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				
				return new JSONParser(sb.toString());
			}
		} catch(SocketTimeoutException e) {
			throw new ConnectException();
		} catch(UnknownHostException e) {
			throw new ConnectException();
		} catch(MalformedURLException ex) {
			throw new ConnectException();
	    } catch (IOException ex) {
	    	throw new ConnectException();
	    }
		
	    return null;
	}
}
