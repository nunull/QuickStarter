package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class OpenWeatherMapModule extends Module {

	public OpenWeatherMapModule(MainController mainController) {
		super(mainController);
	}

	private String jsonTxt;
	
	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		
		if (search.partEquals(0, "weather")){
			jsonTxt = OpenWeatherMapBridge.getJSON("Bremen,de", 2000);
			System.out.println(jsonTxt);
			
			//TODO evtl ne grafik oder so beim auslösen der acion darstellen
			return new ModuleAction(this.toString(), "hier wird die temperatur etc stehen-TODO");
		}
		return null;
	}

}
