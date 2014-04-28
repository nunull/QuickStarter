package de.dqi11.quickStarter.modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

/**
 * Module for accessing the translate.google.com website
 */
public class GoogleTranslateModule extends Module {
	private final String KEY = this.toString();
	
	public GoogleTranslateModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.isCommand("translate") && !search.getParams().equals("")) {
			return new ModuleAction(KEY, "Translate " + search.getParams() + " on Google") {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("https://translate.google.com/#auto/en/" + search.getParams().replaceAll(" ", "+")));
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
