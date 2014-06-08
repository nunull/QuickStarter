/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus †lker
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
package de.dqi11.quickStarter.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.dqi11.quickStarter.modules.Module;

/**
 * Container for all persistency-functionality (based on a XML file).
 */
public class PersitencyController {
	private final String CONFIG_FILE_PATH = "config.xml";
	private MainController mainController;
	private Document configDocument;

	/**
	 * Constructor.
	 * 
	 * @param mainController The main-controller.
	 */
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
	 * Returns all Modules.
	 * 
	 * @return A list of Modules, which are registered in the XML file.
	 */
	public LinkedList<Module> getModules() {
		return getModules(false);
	}
	
	/**
	 * Return all currently active Modules.
	 * 
	 * @return A list of active Modules, which are registered in the XML file.
	 */
	public LinkedList<Module> getActiveModules(){
		return getModules(true);
	}
	
	/**
	 * Returns whether the given module is active or not.
	 * 
	 * @param module The module.
	 * @return true if the given module is active.
	 */
	public boolean isModuleActive(Module module) {
		LinkedList<Module> allModules = getModules();
		LinkedList<Module> activeModules = getActiveModules();
		
		
		for(Module m : allModules) {
			if(module.getClass().getSimpleName().equals(m.getClass().getSimpleName())) {
				for(Module activeModule : activeModules) {
					if(module.getClass().getSimpleName().equals(activeModule.getClass().getSimpleName())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Sets the state of the given module.
	 * 
	 * @param module The module.
	 * @param active The state.
	 */
	public void setModuleActive(Module module, boolean active) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");

		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();

			for(int n = 0, m = childs.getLength(); n < m; n++) {
				Node child = childs.item(n);

				if(child.getNodeName().equals("class") && 
							child.getTextContent().equals(module.getClass().getSimpleName())) {
					Node isActive = modulesXML.item(i).getAttributes().getNamedItem("active");
					
					if(isActive != null) {
						isActive.setNodeValue(active ? "true" : "false");
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
	 * Returns the modules saved in XML.
	 * 
	 * @param onlyActivated true to get a list with activated Modules, false to get all Modules
	 * @return a list of Modules (depending on the parameter)
	 */
	private LinkedList<Module> getModules(boolean onlyActivated){
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		LinkedList<Module> modules = new LinkedList<>();
		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] moduleExceptions = new LinkedList[modulesXML.getLength()];

		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			moduleExceptions[i] = new LinkedList<>();
		}

		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			Node moduleItem = modulesXML.item(i);
			if( !onlyActivated || 
						(moduleItem.getAttributes().getNamedItem("active") != null && 
						moduleItem.getAttributes().getNamedItem("active").getTextContent().equals("true") )) {
				NodeList childs = moduleItem.getChildNodes();
				String currentClassName = "";
				int currentID = -1;

				for(int n = 0, m = childs.getLength(); n < m; n++) {
					Node child = childs.item(n);

					if(child.getNodeName().equals("class")) {
						currentClassName = child.getTextContent();
					} else if(child.getNodeName().equals("exceptions")) {
						NodeList exceptions = child.getChildNodes();

						for(int x = 0, y = exceptions.getLength(); x < y; x++) {
							Node exception = exceptions.item(x);

							if(exception.getNodeName().equals("exception")) {
								moduleExceptions[currentID].add(new Integer(exception.getTextContent()));
							}
						}
					} else if(child.getNodeName().equals("id")) {
						try {
							currentID = Integer.parseInt(child.getTextContent());
						} catch(NumberFormatException e) {
						}
					}
				}

				try {
					Module module = (Module) Class.
							forName("de.dqi11.quickStarter.modules." + currentClassName).
							getDeclaredConstructor(MainController.class).
							newInstance(mainController);
//					modules.add(module);
					
					module.setID(currentID);
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
		}
		
		for(int i = 0, j = moduleExceptions.length; i < j; i++) {
			LinkedList<Integer> currentModuleExceptions = moduleExceptions[i];

			for(Integer id : currentModuleExceptions) {
				Module module = findModuleByID(i, modules);
				Module exception = findModuleByID(id, modules);
				
				try {
					module.addException(exception);
				} catch(NullPointerException e) {
				}
			}
		}

		return modules;
	}
	
	/**
	 * Finds and returns a module by its ID.
	 * 
	 * @param ID The ID.
	 * @param module The list of modules to search in.
	 * @return The found module.
	 */
	private Module findModuleByID(int ID, LinkedList<Module> modules) {
		for(Module m : modules) {
			if(m.getID() == ID) return m;
		}
		
		return null;
	}

	/**
	 * Returns all properties of the specific module.
	 * 
	 * @param module The module.
	 * @return All properties as key-value-pairs.
	 */
	public Map<String, String> getModuleProperties(Module module) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		Map<String, String> properties = new TreeMap<String, String>();

		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();

			for(int a = 0, b = childs.getLength(); a < b; a++) {
				if(childs.item(a).getNodeName().equals("class") && module.getClass().getSimpleName().equals(childs.item(a).getTextContent())) {
					for(int n = 0, m = childs.getLength(); n < m; n++) {
						Node child = childs.item(n);
						
						

						if(child.getNodeName().equals("properties")) {
							NodeList propertyNodes = child.getChildNodes();

							for(int x = 0, y = propertyNodes.getLength(); x < y; x++) {
								Node property = propertyNodes.item(x);

								properties.put(property.getNodeName(), property.getTextContent());
							}
						}
					}
				}
			}

			

		}

		return properties;
	}

	/**
	 * Returns all properties of the specific module, that are either user-editable via the preferences or not.
	 * 
	 * @param module The module.
	 * @param preferences Specifies if the properties returned are user-editable (true) or not (false).
	 * @return All properties as key-value-pairs.
	 */
	public Map<String, String> getModuleProperties(Module module, boolean preferences) {
		NodeList modulesXML = configDocument.getElementsByTagName("module");
		Map<String, String> properties = new TreeMap<String, String>();

		for(int i = 0, j = modulesXML.getLength(); i < j; i++) {
			NodeList childs = modulesXML.item(i).getChildNodes();

			for(int c = 0, d = childs.getLength(); c < d; c++) {
				if(childs.item(c).getNodeName().equals("class") && module.getClass().getSimpleName().equals(childs.item(c).getTextContent())) {
					for(int n = 0, m = childs.getLength(); n < m; n++) {
						Node child = childs.item(n);

						if(child.getNodeName().equals("properties")) {
							NodeList propertyNodes = child.getChildNodes();

							for(int x = 0, y = propertyNodes.getLength(); x < y; x++) {
								Node property = propertyNodes.item(x);
								NamedNodeMap attributes = property.getAttributes();
								boolean hasPrefAttribute = false;
								boolean valid = false;
								
								if(attributes == null) return properties;
								for(int a = 0, b = attributes.getLength(); a < b; a++) {
									Node attribute = attributes.item(a);
									String nodeName = attribute.getNodeName();
									String textContent = attribute.getTextContent();

									if(nodeName.equals("pref")) {
										hasPrefAttribute = true;

										if(textContent.equals("true") && preferences ||
												textContent.equals("false") && !preferences) {

											valid = true;
										}
									}
								}

								if(!hasPrefAttribute && !preferences) {
									valid = true;
								}

								if(valid) {
									properties.put(property.getNodeName(), property.getTextContent());
								}
							}
						}
					}
				}
			}
			
			
		}

		return properties;
	}

	/**
	 * Returns the specific key if present or null.
	 * 
	 * @param module The module.
	 * @param key The key.
	 * @return The value of the key or null if not present.
	 */
	public String getModuleProperty(Module module, String key) {
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
	public void saveOrUpdateModuleProperty(Module module, String key, String value) {
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
	public void removeModuleProperty(Module module, String key) {
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
