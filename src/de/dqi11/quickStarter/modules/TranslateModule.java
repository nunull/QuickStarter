package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.GlosbeBridge;

public class TranslateModule extends Module {
	private final String KEY = this.toString();

	public TranslateModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {
		if(search.isCommand("translate")) {
			String text = ""; 
			
			SwingWorker<ModuleAction, ModuleAction> worker = new SwingWorker<ModuleAction, ModuleAction>(){

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String from, to, phrase;
					
					phrase = search.getParam(0);
					from = search.getParam(1);
					if (from==null) from = "eng";
					to = search.getParam(2);
					if (to==null) to = Locale.getDefault().getLanguage();
					
					JSONParser parser = new JSONParser( GlosbeBridge.getJSON(from, to, phrase) );
					System.out.println( parser.getArrayList("tuc").get(0).get("phrase.text") );
					
					return new ModuleAction(KEY, "TODO");
				}
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
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
