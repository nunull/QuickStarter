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
 * Module for accessing the Wikipedia.com website
 */
public class WikiSearchModule extends Module {
	private final String KEY = this.toString();
//	private String systemLanguage;
	
	public WikiSearchModule(MainController mainController) {
		super(mainController);
//		systemLanguage = Locale.getDefault().getCountry();
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
		} else {
			return null;
		}
	}
}