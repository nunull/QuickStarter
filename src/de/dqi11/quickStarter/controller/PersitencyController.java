package de.dqi11.quickStarter.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.dqi11.quickStarter.modules.Module;

/**
 * Container for all persistency-functionality.
 */
public class PersitencyController {
	private final String CONFIG_FILE_PATH = "config.xml";
	private MainController mainController;
	private Document configDocument;
	
	public PersitencyController(MainController mainController) {
		this.mainController = mainController;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			configDocument = builder.parse(new File(CONFIG_FILE_PATH));
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
	
	public LinkedList<Module> getModules() {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		LinkedList<Module> modules = new LinkedList<>();
		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] moduleExceptions = new LinkedList[modulesXML.getLength()];
		
		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			moduleExceptions[i] = new LinkedList<>();
		}
		
		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();
			String currentClassName = "";
			
			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);
				
				if(child.getNodeName().equals("class")) {
					currentClassName = child.getTextContent();
				} else if(child.getNodeName().equals("exceptions")) {
					NodeList exceptions = child.getChildNodes();
					
					for(int x = 0, y = exceptions.getLength(); x < y; x++) {
						Node exception = exceptions.item(x);
						
						if(exception.getNodeName().equals("exception")) {
							moduleExceptions[i].add(new Integer(exception.getTextContent()));
						}
					}
				}
			}
			
			try {
				Module module = (Module) Class.
						forName("de.dqi11.quickStarter.modules." + currentClassName).
						getDeclaredConstructor(MainController.class).
						newInstance(mainController);
				
				modules.add(module);
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (ClassNotFoundException e) {
			} catch (DOMException e) {
			}
		}
		
		for(int i = 0, j = moduleExceptions.length; i < j; i++) {
			LinkedList<Integer> currentModuleExceptions = moduleExceptions[i];
			
			for(Integer id : currentModuleExceptions) {
				modules.get(i).addException(modules.get(id));
			}
		}
		
		return modules;
	}
}
