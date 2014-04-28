package de.dqi11.quickStarter.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Container for all persistency-functionality.
 */
public class PersitencyController {
	private final String CONFIG_FILE_PATH = "config.xml";
	private Document configDocument;
	
	public PersitencyController() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			configDocument = builder.parse(new File(CONFIG_FILE_PATH));
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LinkedList<String> getModuleClassNames() {
		NodeList modules = configDocument.getElementsByTagName("module");
		LinkedList<String> moduleClassNames = new LinkedList<String>();
		
		for(int i = 0, j = modules.getLength(); i < j; i++) {
			NodeList childs = modules.item(i).getChildNodes();
			
			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);
				
				if(child.getNodeName().equals("class")) {
					moduleClassNames.push(child.getTextContent());
				}
			}
		}
		
		return moduleClassNames;
	}
}
