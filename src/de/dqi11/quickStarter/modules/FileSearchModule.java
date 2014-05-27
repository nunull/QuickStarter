package de.dqi11.quickStarter.modules;

import java.io.File;
import java.io.FilenameFilter;
import java.net.ConnectException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.Label;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class FileSearchModule extends Module {
	private final String KEY = this.toString();
	private final int MAX_FOLDER_ITERATIONS = 2;
	private final int MAX_RESULTS_PER_FOLTER = 15;

	public FileSearchModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {
		if(!search.getSearchString().equals("")) {
			SwingWorker<ModuleAction, ModuleAction> worker = new SwingWorker<ModuleAction, ModuleAction>() {
	
				@Override
				protected ModuleAction doInBackground() throws Exception {
					
					return new ModuleAction(KEY, "Find <b>" + search.getSearchString() + "</b>") {
						@Override
						public ModuleWindow getModuleWindow(final Search search) {
							ModuleWindow moduleWindow = new ModuleWindow();
							
							File dir = new File(System.getProperty("user.home"));
							LinkedList<File> results = findFiles(dir, new FilenameFilter() {
								
								@Override
								public boolean accept(File dir, String name) {
									return name.contains(search.getSearchString());
								}
							});
							
							for(File file : results) {
								moduleWindow.add(new Label(file.getName().replace("%20", " ")));
							}
							
							return moduleWindow;
						}
					};
				}
				
				@Override
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			worker.execute();
			
			return new LoadingModuleAction(KEY);
		} else {
			return null;
		}
	}
	
	public LinkedList<File> findFiles(File dir, FilenameFilter filter) {
		return findFiles(dir, filter, 0);
	};
	
	private LinkedList<File> findFiles(File dir, FilenameFilter filter, int iteration) {
		if(dir != null && dir.isDirectory() && !dir.isHidden()) {
			LinkedList<File> results = new LinkedList<>();
			File[] files = dir.listFiles();
			File[] resultsArray = dir.listFiles(filter);
			
			for(int i = 0, j = (resultsArray.length < MAX_RESULTS_PER_FOLTER ? resultsArray.length : MAX_RESULTS_PER_FOLTER); i < j; i++) {
				if(!resultsArray[i].isHidden() && !resultsArray[i].getName().startsWith(".")) {
					results.add(resultsArray[i]);
				}
			}
			
			if(files != null) {
				for(int i = 0, j = files.length; i < j; i++) {
					File file = files[i];
					
					if(file != null && file.isDirectory() && iteration < MAX_FOLDER_ITERATIONS) {
						LinkedList<File> tmp = findFiles(file, filter, iteration + 1);
						if(tmp != null) results.addAll(tmp);
					}
				}
			}
			
			return results;
		}
		
		return null;
	}
}