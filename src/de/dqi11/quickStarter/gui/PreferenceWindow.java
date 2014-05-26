package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.modules.Module;

/**
 * A class to show and change the preferences of the program, including the modules.
 */
public class PreferenceWindow extends Window {;
	private MainController mainController;
	private JPanel contentPanel;
	private LinkedList<JPanel> preferencePanels;
	private Map<Module, Map<String, JTextField>> properties;
	
	public PreferenceWindow(final MainController mainController) {
		super("Preferences");
		
		this.mainController = mainController;
		this.mainFrame.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				super.componentShown(e);
				
				for(Module module : properties.keySet()) {
					Map<String, JTextField> moduleProperties = properties.get(module);
					
					for(String key : moduleProperties.keySet()) {
						JTextField textField = moduleProperties.get(key);
						String oldValue = textField.getName();
						String newValue = textField.getText();
						
						if(!oldValue.equals(newValue)) {
							mainController.saveOrUpdateModuleProperty(module, key, newValue);
						}
					}
				}
			}
		});
		this.preferencePanels = new LinkedList<>();
		this.properties = new TreeMap<>();
		
		initGUI();
	}
	
	private void initGUI(){
		DefaultListModel<String> listModel = new DefaultListModel<>();
		final JList<String> list = new JList<>();
		list.setModel(listModel);
		list.setPreferredSize(new Dimension(200, 500));
		list.setVisible(true);
		
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(560, 500));
		contentPanel.setBackground(Color.WHITE);
		
		LinkedList<Module> modules = mainController.getModules();
		for(Module module : modules) {
			listModel.addElement(module.getClass().getSimpleName().replace("Module", ""));
			
			JPanel panel = new JPanel();
			panel.setVisible(false);
			panel.setBackground(Color.WHITE);
			
			Map<String, String> properties = mainController.getModuleProperties(module, true);
			Map<String, JTextField> guiProperties = new TreeMap<>();
			for(String key : properties.keySet()) {
				String value = properties.get(key);
				
				JLabel keyLabel = new JLabel(key);
				keyLabel.setPreferredSize(new Dimension(200, 20));
				JTextField valueField = new JTextField(value);
				valueField.setPreferredSize(new Dimension(340, 20));
				valueField.setName(value);
				
				panel.add(keyLabel);
				panel.add(valueField);
				
				guiProperties.put(key, valueField);
			}
			
			this.properties.put(module, guiProperties);
			
			preferencePanels.add(panel);
			contentPanel.add(panel);
		}
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for(JPanel panel : preferencePanels) {
					panel.setVisible(false);
				}
				preferencePanels.get(list.getSelectedIndex()).setVisible(true);
				
			}
		});
		
		add(list);
		add(contentPanel);
	}
}
