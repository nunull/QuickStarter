package de.dqi11.quickStarter.modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.ConnectException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private String defaultLocation;
	
	public WeatherModule(MainController mainController) {
		super(mainController);
		
		defaultLocation = mainController.getModuleProperty(this, "city");
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {
		if (search.isCommand("weather")) {
			try {
				worker.cancel(true);
			}catch(NullPointerException | CancellationException e) {
				// Do nothing. It is OK if the worker is not initialized or executed, yet.
			}
			
			// Set up the worker, that will get the necessary data in the background.
			worker = new SwingWorker<ModuleAction, ModuleAction>() {

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String location = search.getParam(0);
					if(location.equals("")) location = defaultLocation;
					if(location.split(",").length == 1) {
						if(search.getParam(1) != null) location += "," + search.getParam(1);
						else location += ",de";
					}

					String json = OpenWeatherMapBridge.getJSON(location);
					jsonParser = new JSONParser(json);
					
					// Debug
					System.out.println(json);
					
					// Get and format the temperature.
					float temp = Float.valueOf(jsonParser.get("main.temp"));
					temp = Math.round(temp);
					tempFormated = String.format("%s", temp);
					if(temp == (int) temp) tempFormated = String.format("%s", (int) temp);
					
					String text = jsonParser.get("name") + ": <b>" + tempFormated + "\u00B0C</b>";
					return new ModuleAction(KEY, text, new ImageIcon("res/weather-icons/weather-icon.png")) {
						@Override
						public ModuleWindow getModuleWindow(Search search) {
							final String city = search.getParam(0).split(",")[0];
							
							ModuleWindow window = new ModuleWindow("Weather " + city);
							
							Font defaultFont = window.getDefaultFont();
							Font bigFont = new Font(defaultFont.getName(), defaultFont.getStyle(), 50);
							
							JPanel container = new JPanel();
							container.setBackground(Color.WHITE);
							
							String icon = (jsonParser.getArrayList("weather").get(0).get("icon"));
							icon = icon.substring(0, 2) + ".png";
							JLabel iconLabel = new JLabel(new ImageIcon ("res/weather-icons/" + icon));
							container.add(iconLabel);
							
							JPanel panelTable = new JPanel();
							GridLayout gridLayout = new GridLayout(0, 2);
							gridLayout.setHgap(50);
							gridLayout.setVgap(10);
							panelTable.setLayout(gridLayout);
							panelTable.setBackground(Color.WHITE);
							
							panelTable.add(new JLabel(jsonParser.get("name")));
							
							// Creates empty space (needed since there is no real adressing using GridLayout)
							panelTable.add(new JLabel(""));
							
							JLabel label1 = new JLabel(tempFormated + "\u00B0C");
							label1.setPreferredSize(new Dimension(140, 40));
							
							label1.setFont(bigFont);
							panelTable.add(label1);
							
							JLabel humidityTextLabel = new JLabel("Humidity");
							JLabel humidityLabel = new JLabel(jsonParser.get("main.humidity") + "%");
							JLabel windSpeedTextLabel = new JLabel(("Windspeed"));
							JLabel windSpeedLabel = new JLabel(jsonParser.get("wind.speed") + "mps");
							JLabel cloudsTextLabel = new JLabel("Clouds");
							JLabel cloudsLabel = new JLabel(jsonParser.get("clouds.all"));
							
							String rain = jsonParser.get("rain.3h");
							JLabel rainTextLabel = new JLabel("Rain");
							JLabel rainLabel = new JLabel(rain + "mm per 3 hours");
							
							humidityTextLabel.setFont(defaultFont);
							humidityLabel.setFont(defaultFont);
							windSpeedTextLabel.setFont(defaultFont);
							windSpeedLabel.setFont(defaultFont);
							cloudsTextLabel.setFont(defaultFont);
							cloudsLabel.setFont(defaultFont);
							rainTextLabel.setFont(defaultFont);
							rainLabel.setFont(defaultFont);
							
							// Creates empty space (needed since there is no real addressing using GridLayout)
							panelTable.add(new JLabel());
							
							panelTable.add(humidityTextLabel);
							panelTable.add(humidityLabel);
							panelTable.add(windSpeedTextLabel);
							panelTable.add(windSpeedLabel);
							panelTable.add(cloudsTextLabel);
							panelTable.add(cloudsLabel);
							if(rain != null) {
								panelTable.add(rainTextLabel);
								panelTable.add(rainLabel);
							}
							
							container.add(panelTable);
								
							window.add(container);
							
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
