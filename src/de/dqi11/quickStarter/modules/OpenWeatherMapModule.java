package de.dqi11.quickStarter.modules;

import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class OpenWeatherMapModule implements Module {

	private String jsonTxt;
	
	@Override
	public ModuleAction getModuleAction(Search search) {
		
		jsonTxt = OpenWeatherMapBridge.getJSON("http://api.openweathermap.org/data/2.5/weather?q=" + "Bremen,de", 2000);
		System.out.println(jsonTxt);
		
		//TODO evtl ne grafik oder so beim auslösen der acion darstellen
		return new ModuleAction("hier wird die temperatur etc stehen-TODO");
	}

}
