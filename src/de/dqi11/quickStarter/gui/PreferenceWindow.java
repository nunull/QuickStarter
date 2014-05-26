package de.dqi11.quickStarter.gui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.modules.Module;

/**
 * A class to show and change the preferences of the program, including the modules.
 */
public class PreferenceWindow extends Window {;
	private JCheckBox isActiveChBox;
	private JTabbedPane tabbedPane;
	private MainController mainController;
	private int fontSize = 12;
	
	public PreferenceWindow(String title, MainController mainController) {
		super(title);
		System.out.println("object created");
		this.mainController = mainController;
		tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setFont(DefaultFont.getFont(fontSize));
		
		
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
		fillGUI();
	}
	
	private void fillGUI(){
		int i = 0;//remove me--------------------------------------------------------------------------------------
		for (Module m : mainController.getModules() ){
			JPanel tabContent = new JPanel();
			
			isActiveChBox = new JCheckBox("The module is active"); //no action needed, state of chBox is read when saving
			isActiveChBox.setFont(DefaultFont.getFont(fontSize));
			tabContent.add(isActiveChBox);
			if (i==0) tabContent.add(new JTextField(43));//remove me---------------------------------------------------------
			i = 1; //remove me--------------------------------------------------------------------------------------
			
			Map<String, String> properties = mainController.getModuleProperties(m, true);
			for ( String key : properties.keySet()){
				
				JTextField textField = new JTextField(properties.get(key));
				textField.setActionCommand(key);
				textField.setFont(DefaultFont.getFont(fontSize));
				tabContent.add(textField);
			}
			tabbedPane.add( m.getClass().getSimpleName() ,tabContent);
		}
		tabbedPane.addTab("Hello", new JLabel("World"));
        tabbedPane.addTab("Goodbye", new JLabel("Sunshine"));
		
		this.mainPanel.add(tabbedPane);
	}
}
