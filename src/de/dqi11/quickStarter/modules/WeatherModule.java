package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class WeatherModule extends Module {
	private final String KEY = this.toString();
	private SwingWorker<ModuleAction, ModuleAction> worker;

	public WeatherModule(MainController mainController) {
		super(mainController);
		worker = new SwingWorker<ModuleAction, ModuleAction>(){
			@Override
			protected ModuleAction doInBackground() throws Exception {
				return null;
			}
		};
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {

		if (search.isCommand("weather")) {
			try{
				worker.cancel(true);
			}catch(CancellationException e){
				//TODO
			}
			worker = new SwingWorker<ModuleAction, ModuleAction>() {

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String location = search.getParam(0);
					if(location == null) location = "Bremen";
					if(location.split(",").length == 1){
						if (search.getParam(1)!=null) location +=","+search.getParam(1);
						else location += ",de";
					}

					String json = OpenWeatherMapBridge.getJSON(location);
					String text = "";
					JSONParser jsonParser = new JSONParser(json);
					text = jsonParser.get("name") + ": " + jsonParser.get("main.temp") + "°C";
					
					return new ModuleAction(KEY, text);
				}

				@Override
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));

						try {
							getModuleAction(search);
						} catch (ConnectException e1) {
							e1.printStackTrace();
						}
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
