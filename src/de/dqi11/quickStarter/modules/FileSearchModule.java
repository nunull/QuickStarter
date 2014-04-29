package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class FileSearchModule extends Module {
	private final String KEY = this.toString();
	private static ModuleWindow moduleWindow;

	public FileSearchModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(!search.equals("")) {
			moduleWindow = new ModuleWindow();
			
			return new ModuleAction(KEY, "Locate " + search.getSearchString()) {
				
				@Override
				public ModuleWindow getModuleWindow(Search search) {
					SwingWorker<JPanel, JPanel> worker = new SwingWorker<JPanel, JPanel>() {

						@Override
						protected JPanel doInBackground() throws Exception {
							return null;
						}
						
						@Override
						protected void done() {
							try {
								moduleWindow.add(get());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
