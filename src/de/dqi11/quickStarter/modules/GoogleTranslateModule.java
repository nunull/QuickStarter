package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

public class GoogleTranslateModule extends Module {
	private final String KEY = this.toString();

	public GoogleTranslateModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.isCommand("translate")) {
			String text = ""; 
			
			SwingWorker<ModuleAction, ModuleAction> worker = new SwingWorker<ModuleAction, ModuleAction>(){

				@Override
				protected ModuleAction doInBackground() throws Exception {
					//connect to google and translate the text
					return new ModuleAction(KEY, "TODO");
				}
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));
					}
					
					super.done();
				}
			};
			worker.execute();
			
			return new LoadingModuleAction(KEY, "translating " + text);
		}
		
		return null;
	}
	
}
