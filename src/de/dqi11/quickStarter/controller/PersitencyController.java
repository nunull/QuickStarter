package de.dqi11.quickStarter.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	
	/**
	 * Returns all currently active Modules.
	 * 
	 * @return A list of Modules.
	 */
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
	
	/**
	 * Returns the specific key if present or null.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @return The value of the key or null if not present.
	 */
	public String getModuleKey(Module module, String key) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		
		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();
			
			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);
				
				if(child.getNodeName().equals("properties")) {
					NodeList properties = child.getChildNodes();
					
					for(int x = 0, y = properties.getLength(); x < y; x++) {
						Node property = properties.item(x);
						
						if(property.getNodeName().equals(key)) {
							return property.getTextContent();
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Saves or updates the value of the specific key.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @param value The value.
	 */
	public void saveOrUpdateModuleKey(Module module, String key, String value) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		
		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();
			String currentClassName = "";
			boolean hasProperties = false;
			
			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);
				
				if(child.getNodeName().equals("class")) {
					currentClassName = child.getTextContent();
				} else if(child.getNodeName().equals("properties")) {
					hasProperties = true;
				}
			}
			
			if(currentClassName.equals(module.getClass().getSimpleName())) {
				if(!hasProperties) {
					modulesXML.item(i).appendChild(configDocument.createElement("properties"));
				}
				
				childs = modulesXML.item(i).getChildNodes();
				for(int n = 0, m = childs.getLength(); n < m; n++) {
					Node child = childs.item(n);
					
					if(child.getNodeName().equals("properties")) {
						NodeList properties = child.getChildNodes();
						
						boolean hasProperty = false;
						
						for(int x = 0, y = properties.getLength(); x < y; x++) {
							Node property = properties.item(x);
							
							if(property.getNodeName().equals(key)) {
								hasProperty = true;
								
								property.setTextContent(value);
							}
						}
						
						if(!hasProperty) {
							Element newProperty = configDocument.createElement(key);
							newProperty.setTextContent(value);
							
							child.appendChild(newProperty);
						}
					}
				}
			}
		}
		
		DOMSource source = new DOMSource(configDocument);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult streamResult = new StreamResult(CONFIG_FILE_PATH);
			transformer.transform(source, streamResult);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}
	
	/**
	 * Removes the specific key.
	 * 
	 * @param module The module.
	 * @param key The key.
	 */
	public void removeModuleKey(Module module, String key) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		
		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();
			
			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);
				
				if(child.getNodeName().equals("properties")) {
					NodeList properties = child.getChildNodes();
					
					for(int x = 0, y = properties.getLength(); x < y; x++) {
						Node property = properties.item(x);
						
						if(property.getNodeName().equals(key)) {
							property.getParentNode().removeChild(property);
							
							break;
						}
					}
				}
			}
		}
		
		DOMSource source = new DOMSource(configDocument);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult streamResult = new StreamResult(CONFIG_FILE_PATH);
			transformer.transform(source, streamResult);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}
}
