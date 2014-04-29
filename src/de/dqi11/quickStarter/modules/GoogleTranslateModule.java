package de.dqi11.quickStarter.modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

/**
 * Module for accessing the translate.google.com website
 */
public class GoogleTranslateModule extends Module {
	private final String KEY = this.toString();
	private String systemLanguage;
	
	public GoogleTranslateModule(MainController mainController) {
		super(mainController);
		systemLanguage = Locale.getDefault().getCountry();
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.isCommand("translate") && !search.getParams().equals("")) {
			return new ModuleAction(KEY, "Translate " + search.getParams() + " on Google", new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
							if(systemLanguage != null)
								Desktop.getDesktop().browse(new URI("https://translate.google.com/#auto/" + systemLanguage + "/" + search.getParams().replaceAll(" ", "+")));
							else
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
