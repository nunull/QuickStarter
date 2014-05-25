package de.dqi11.quickStarter.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.dqi11.quickStarter.controller.PersitencyController;
import de.dqi11.quickStarter.modules.Module;

/**
 * A class to show and change the preferences of the program, including the modules.
 */
public class PreferenceWindow extends Window {
	private PersitencyController persistencyController;
	private JCheckBox isActiveChBox;
	private LinkedList<JTextField> properties;
	
	public PreferenceWindow() {
		super();
	}
	
	public PreferenceWindow(String title, PersitencyController persistencyController) {
		super(title);
		System.out.println("object created");
		this.persistencyController = persistencyController;
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		for (Module m : this.persistencyController.getModules() ){
			JPanel tabContent = new JPanel();
			isActiveChBox = new JCheckBox("The module is active"); //no action needed, state of chBox is read when saving
			tabContent.add(isActiveChBox);
			
			Object o = new Object();
			
			//TODO replace the linkedList with a query to the persistencycontroller
			//TODO interface to persistency will contain content and key
			for (String property : new LinkedList<String>()){ //loading the gui-content from persistency
				JTextField textField = new JTextField(property);
				textField.setActionCommand("key");//remove the "", (declare and initialize the variable key)
				properties.add(textField);
			}
			tabbedPane.add( m.getClass().getSimpleName() ,tabContent);
		}
		tabbedPane.addTab("Hello", new JLabel("World"));
        tabbedPane.addTab("Goodbye", new JLabel("Sunshine"));
		
		tabbedPane.setVisible(true);
		this.mainPanel.add(tabbedPane);
		
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
		
	}
}
