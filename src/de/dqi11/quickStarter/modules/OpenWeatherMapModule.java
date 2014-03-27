package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class OpenWeatherMapModule extends Module {

	private final String KEY = this.toString();
	
	public OpenWeatherMapModule(MainController mainController) {
		super(mainController);
	}
	
	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if (search.partEquals(0, "weather")){
			SwingWorker<ModuleAction, ModuleAction> worker = new SwingWorker<ModuleAction, ModuleAction>() {
				
				@Override
				protected ModuleAction doInBackground() throws Exception {
					String json = OpenWeatherMapBridge.getJSON("Bremen,de", 2000);
					String text = "";
					JSONParser jsonParser = new JSONParser(json);
					text = jsonParser.get("name") + ": " + jsonParser.get("main.temp") + "¡C";
					
					return new ModuleAction(KEY, text);
				}
				
				@Override
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
			
			return new LoadingModuleAction(KEY, "Loading weather...");
		}
		
		return null;
	}

}
