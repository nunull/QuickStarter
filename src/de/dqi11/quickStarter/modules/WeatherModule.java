package de.dqi11.quickStarter.modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.ConnectException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.OpenWeatherMapBridge;

public class WeatherModule extends Module {
	private final String KEY = this.toString();
	private SwingWorker<ModuleAction, ModuleAction> worker;
	private JSONParser jsonParser;
	private String tempFormated;
	
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
			try {
				worker.cancel(true);
			}catch(CancellationException e) {
			}
			
			worker = new SwingWorker<ModuleAction, ModuleAction>() {

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String location = search.getParam(0);
					if(location == null) location = "Bremen";
					if(location.split(",").length == 1) {
						if(search.getParam(1) != null) location += "," + search.getParam(1);
						else location += ",de";
					}

					String json = OpenWeatherMapBridge.getJSON(location);
					String text = "";
					System.out.println(json);
					jsonParser = new JSONParser(json);
					
					float temp = Float.valueOf(jsonParser.get("main.temp"));
					temp = Math.round(temp);
					
					tempFormated = String.format("%s", temp);
					if(temp == (int) temp) tempFormated = String.format("%s", (int) temp);
					
					text = jsonParser.get("name") + ": <b>" + tempFormated + "\u00B0C</b>";
					
					return new ModuleAction(KEY, text, new ImageIcon("res/weather-icon-tmp.png")) {
						@Override
						public ModuleWindow getModuleWindow(Search search) {
							ModuleWindow window = new ModuleWindow();
							
							JPanel panel = new JPanel();
							
							panel.setBackground(Color.BLACK);
							String icon = (jsonParser.getArrayList("weather").get(0).get("icon"));
							icon = icon.substring(0, 2) + ".png";
							JLabel label = new JLabel(new ImageIcon ("res/weather-icons/" + icon));
							panel.add(label);
							
							JPanel panelTable = new JPanel();
							panelTable.setLayout(new GridLayout(0,2));
							panelTable.add(new JLabel(jsonParser.get("name")));
							panelTable.add(new JLabel(""));
							JLabel label1 = new JLabel("temperature");
							label1.setPreferredSize(new Dimension(80, 20));
							panelTable.add(label1);
							panelTable.add(new JLabel(tempFormated));
							panelTable.add(new JLabel("humidity in %"));
							panelTable.add(new JLabel(jsonParser.get("main.humidity")));
							panelTable.add(new JLabel(("wind speed in mps")));
							panelTable.add(new JLabel(jsonParser.get("wind.speed")));
							panelTable.add(new JLabel("cloudiness"));
							panelTable.add(new JLabel(jsonParser.get("clouds.all")));
							panelTable.add(new JLabel("rain in 3 hours"));
							panelTable.add(new JLabel(jsonParser.get(("rain.3h"))));
							panel.add(panelTable);
								
							window.add(panel);
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
