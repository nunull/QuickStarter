package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class OpenWeatherMapModule extends Module {

	private final static String KEY = "1";
	
	public OpenWeatherMapModule(MainController mainController) {
		super(mainController);
	}

	private String jsonTxt;
	
	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		
		if (search.partEquals(0, "weather")){
			
			SwingWorker worker = new SwingWorker<ModuleAction, ModuleAction>() {

				@Override
				protected ModuleAction doInBackground() throws Exception {
					jsonTxt = OpenWeatherMapBridge.getJSON("Bremen,de", 2000);
					
					return new ModuleAction(KEY, jsonTxt);
				}
				@Override
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					super.done();
				}
			};
			
			worker.execute();
			
			return new LoadingModuleAction(KEY, "Loading weather...");
			//TODO evtl ne grafik oder so beim ausloesen der acion darstellen
//			return new ModuleAction(this.toString(), "hier wird die temperatur etc stehen-TODO");
		}
		return null;
	}

}
