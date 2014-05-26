package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
	
	public PreferenceWindow(MainController mainController) {
		super("Preferences");
		
		this.mainController = mainController;
		this.mainFrame.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				System.out.println("SHOWN");
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				super.componentShown(e);
				System.out.println("HIDDEN");
				//TODO save here
			}
		});
		this.preferencePanels = new LinkedList<>();
		
		initGUI();
		show();
	}
	
	private void initGUI(){
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> list = new JList<>();
		list.setModel(listModel);
		list.setPreferredSize(new Dimension(200, 500));
		list.setVisible(true);
		
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(560, 500));
		
		LinkedList<Module> modules = mainController.getModules();
		for(Module module : modules) {
			listModel.addElement(module.getClass().getSimpleName().replace("Module", ""));
			
			JPanel panel = new JPanel();
			panel.setVisible(false);
			Map<String, String> properties = mainController.getModuleProperties(module, true);
			for(String key : properties.keySet()) {
				String value = properties.get(key);
				
				panel.add(new Label(key));
				panel.add(new Label(value));
			}
			
			preferencePanels.add(panel);
			contentPanel.add(panel);
		}
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(e.getFirstIndex());
				
				for(JPanel panel : preferencePanels) {
					panel.setVisible(false);
				}
				preferencePanels.get(e.getFirstIndex()).setVisible(true);
			}
		});
		
		add(list);
		add(contentPanel);
	}
}
