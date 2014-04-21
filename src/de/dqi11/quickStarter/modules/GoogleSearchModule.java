package de.dqi11.quickStarter.modules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.GoogleBridge;

public class GoogleSearchModule extends Module {
	private final String KEY = this.toString();
	private GoogleBridge googleBridge;
	
	public GoogleSearchModule(MainController mainController) {
		super(mainController);
		googleBridge = new GoogleBridge();
	}
	
	@Override
	public ModuleAction getModuleAction(final Search search) {
		if(!search.getSearchString().equals("")) {
			return new ModuleAction(KEY, "Google for " + search.getSearchString()) {
				@Override
				public ModuleWindow getModuleWindow(final Search search) {
					final ModuleWindow moduleWindow = new ModuleWindow();

					SwingWorker<JPanel, ModuleAction> worker = new SwingWorker<JPanel, ModuleAction>() {
						@Override
						protected JPanel doInBackground() throws Exception {
							final JSONParser jsonParser = GoogleBridge.getSearch(search.getSearchString());
							JPanel panel = new JPanel();
							
							ArrayList<JSONParser> items = jsonParser.getArrayList("items");

							for(JSONParser item : items) {
								panel.add(new JButton(item.get("title") + " (" + item.get("link") + ")"));
							}
							
							return panel;
						}
						
						@Override
						protected void done() {
							try {
								moduleWindow.add(get());
							} catch (InterruptedException | ExecutionException e) {
								moduleWindow.add(new JButton(e.toString()));
							}
						}
					};
					worker.execute();
					
					return moduleWindow;
				}
			};
		} else {
			return null;
		}
	}

}
