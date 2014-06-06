package de.dqi11.quickStarter.modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

/**
 * This module is able to open the default browser to link the use to their search engines.
 */
public class WebSearchModule extends Module {
	private final String KEY = this.toString();
	
	public WebSearchModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.isCommand("wiki") && !search.getParams().equals("")) {
			//TODO Exchange Google-icon with Wikipedia-icon
			return new ModuleAction(KEY, "Wikipedia search for <b>" + search.getParams(), new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
								Desktop.getDesktop().browse(new URI("http://de.wikipedia.org/w/index.php?search=" + search.getParams().replaceAll(" ", "+") + "&title=Spezial%3ASuche&go=Artikel"));
						} catch (IOException | URISyntaxException e) {
							getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));
						}
					}
				}
			};
		} else if(search.getSearchString().startsWith("map ")) {
			return new ModuleAction(KEY, "Show map of <b>" + search.getParams() + "</b>", new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.google.com/maps/search/" + search.getParams().replaceAll(" ", "+").replaceAll("\u00df", "ss")));
						} catch (IOException | URISyntaxException e) {
							getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));
						}
					}
				}
			};
		} else if(search.isCommand("youtube") && !search.getParams().equals("")) {
			//TODO Exchange Google-icon with Youtube-icon
			return new ModuleAction(KEY, "Youtube search for <b>" + search.getParams(), new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
								Desktop.getDesktop().browse(new URI("http://www.youtube.com/results?search_query=" + search.getParams().replaceAll(" ", "+")));
						} catch (IOException | URISyntaxException e) {
							getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));
						}
					}
				}
			};				
		} else {
			return null;
		}
	}
}
