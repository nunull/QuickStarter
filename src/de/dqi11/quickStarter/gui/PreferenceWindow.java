/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus Uelker
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.modules.Module;

/**
 * A class to show and change the preferences of the program, including modules.
 */
public class PreferenceWindow extends Window {;
	private MainController mainController;
	private JPanel contentPanel;
	private LinkedList<JPanel> preferencePanels;
	private Map<Module, Map<String, JTextField>> properties;
	private Map<Module, JRadioButton> moduleStates;
	
/**
 * Constructor.
 * 
 * @param mainController
 */
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
					boolean isActive = moduleStates.get(module).isSelected();
					
					System.out.println(module.getClass().getSimpleName() + ":" + isActive);
					mainController.setModuleActive(module, isActive);
					
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
		this.moduleStates = new TreeMap<>();
		
		initGUI();
	}
	
	private void initGUI(){
		DefaultListModel<String> listModel = new DefaultListModel<>();
		final JList<String> list = new JList<>();
		
		
		
		LinkedList<Module> modules = mainController.getModules();
		for(Module module : modules) {
			listModel.addElement(module.getClass().getSimpleName().replace("Module", ""));
						
			contentPanel = new JPanel();
			contentPanel.setPreferredSize(new Dimension(560, 300));
			contentPanel.setVisible(false);
			contentPanel.setBackground(Color.WHITE);

			list.setModel(listModel);
			list.setPreferredSize(new Dimension(200, 500));
			list.setVisible(true);
			
			JRadioButton isActiveButton = new JRadioButton("Active", mainController.isModuleActive(module));
						
			isActiveButton.setPreferredSize(new Dimension(540, 20));
			this.moduleStates.put(module, isActiveButton);
			
			Map<String, String> properties = mainController.getModuleProperties(module, true);
			Map<String, JTextField> guiProperties = new TreeMap<>();
			for(String key : properties.keySet()) {
				String value = properties.get(key);
				
				JLabel keyLabel = new JLabel(key);
				keyLabel.setPreferredSize(new Dimension(200, 20));
				JTextField valueField = new JTextField(value);
				valueField.setPreferredSize(new Dimension(340, 20));
				valueField.setName(value);
				
				contentPanel.add(keyLabel);
				contentPanel.add(valueField);
				
				guiProperties.put(key, valueField);
			}
			
			
			this.properties.put(module, guiProperties);
			
			add(list);
			add(contentPanel);
			contentPanel.add(isActiveButton);
			
			preferencePanels.add(contentPanel);
		}
		
		list.setSelectedIndex(0);
		preferencePanels.get(0).setVisible(true);
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for(JPanel panel : preferencePanels) {
					panel.setVisible(false);
				}
				preferencePanels.get(list.getSelectedIndex()).setVisible(true);
			}
		});
		
	}
}
