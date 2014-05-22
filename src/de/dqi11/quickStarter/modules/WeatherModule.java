package de.dqi11.quickStarter.modules;

import java.net.ConnectException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class WeatherModule extends Module {
	private final String KEY = this.toString();
	private SwingWorker<ModuleAction, ModuleAction> worker;
	private String defaultLocation;

	public WeatherModule(MainController mainController) {
		super(mainController);
		
		// Unnecessary
		worker = new SwingWorker<ModuleAction, ModuleAction>(){
			@Override
			protected ModuleAction doInBackground() throws Exception {
				return null;
			}
		};
		
		defaultLocation = mainController.getModuleKey(this, "city");
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {

		if (search.isCommand("weather")) {
			try {
				worker.cancel(true);
			}catch(CancellationException e) {
			}
			
			worker = new SwingWorker<ModuleAction, ModuleAction>() {

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String location = search.getParam(0);
					if(location == null) location = defaultLocation;
					if(location.split(",").length == 1) {
						if(search.getParam(1) != null) location += "," + search.getParam(1);
						else location += ",de";
					}

					String json = OpenWeatherMapBridge.getJSON(location);
					String text = "";
					JSONParser jsonParser = new JSONParser(json);
					
					float temp = Float.valueOf(jsonParser.get("main.temp"));
					temp = Math.round(temp);
					
					String tempFormated = String.format("%s", temp);
					if(temp == (int) temp) tempFormated = String.format("%s", (int) temp);
					
					text = jsonParser.get("name") + ": <b>" + tempFormated + "\u00B0C</b>";
					
					return new ModuleAction(KEY, text, new ImageIcon("res/weather-icon-tmp.png")) {
						@Override
						public ModuleWindow getModuleWindow(Search search) {
							ModuleWindow window = new ModuleWindow();
							window.add(new JButton("test"));
							return window;
						}
					};
				}

				@Override
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						getMainController().updateModule(new WarningModuleAction(KEY, "An error occured.", new ImageIcon("res/weather-icon-tmp.png")));

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
