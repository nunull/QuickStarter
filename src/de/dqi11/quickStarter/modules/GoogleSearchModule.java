package de.dqi11.quickStarter.modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

public class GoogleSearchModule extends Module {
	private final String KEY = this.toString();
	
	public GoogleSearchModule(MainController mainController) {
		super(mainController);
	}
	
	@Override
	public ModuleAction getModuleAction(final Search search) {
		if(!search.getSearchString().equals("")) {
			return new ModuleAction(KEY, "Google for " + search.getSearchString(), new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.google.com/#q=" + search.getSearchString().replaceAll(" ", "+")));
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
