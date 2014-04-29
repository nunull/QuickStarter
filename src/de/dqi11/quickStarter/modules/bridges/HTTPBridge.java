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

public class HTTPBridge {
	private static final int TIMEOUT = 30000;
	
	public static String getContent(String url) throws ConnectException {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(TIMEOUT);
			c.setReadTimeout(TIMEOUT);
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
