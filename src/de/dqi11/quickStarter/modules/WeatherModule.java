package de.dqi11.quickStarter.modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
							
							Font defaultFont = window.getDefaultFont();
							Font bigFont = new Font(defaultFont.getName(), defaultFont.getStyle(), 30);
							
							JPanel container = new JPanel();
							
							// Debug
							container.setBackground(Color.BLACK);
							
							String icon = (jsonParser.getArrayList("weather").get(0).get("icon"));
							icon = icon.substring(0, 2) + ".png";
							JLabel iconLabel = new JLabel(new ImageIcon ("res/weather-icons/" + icon));
							container.add(iconLabel);
							
							JPanel panelTable = new JPanel();
							panelTable.setLayout(new GridLayout(0,2));
							panelTable.add(new JLabel(jsonParser.get("name")));
							
							// Creates empty space (needed since there is no real adressing using GridLayout)
							panelTable.add(new JLabel(""));
							
							JLabel label1 = new JLabel(tempFormated + "\u00B0C");
							label1.setPreferredSize(new Dimension(100, 40));
							
							label1.setFont(bigFont);
							panelTable.add(label1);
							
							JLabel humidityTextLabel = new JLabel("humidity in %");
							JLabel humidityLabel = new JLabel(jsonParser.get("main.humidity"));
							JLabel windSpeedTextLabel = new JLabel(("wind speed in mps"));
							JLabel windSpeedLabel = new JLabel(jsonParser.get("wind.speed"));
							JLabel cloudsTextLabel = new JLabel("cloudiness");
							JLabel cloudsLabel = new JLabel(jsonParser.get("clouds.all"));
							JLabel rainTextLabel = new JLabel("rain in 3 hours");
							JLabel rainLabel = new JLabel(jsonParser.get(("rain.3h")));
							
							humidityTextLabel.setFont(defaultFont);
							humidityLabel.setFont(defaultFont);
							windSpeedTextLabel.setFont(defaultFont);
							windSpeedLabel.setFont(defaultFont);
							cloudsTextLabel.setFont(defaultFont);
							cloudsLabel.setFont(defaultFont);
							rainTextLabel.setFont(defaultFont);
							rainLabel.setFont(defaultFont);
							
							// Creates empty space (needed since there is no real adressing using GridLayout)
							panelTable.add(new JLabel());
							
							panelTable.add(humidityTextLabel);
							panelTable.add(humidityLabel);
							panelTable.add(windSpeedTextLabel);
							panelTable.add(windSpeedLabel);
							panelTable.add(cloudsTextLabel);
							panelTable.add(cloudsLabel);
							panelTable.add(rainTextLabel);
							panelTable.add(rainLabel);
							
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
