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
	private String adtCmd = null;
	
	public GoogleSearchModule(MainController mainController) {
		super(mainController);
	}
	
	@Override
	public ModuleAction getModuleAction(final Search search) {
		if(!search.getSearchString().equals("")) {
			if (search.isCommand("img")) adtCmd = "&tbm=isch";
			else adtCmd = null;
			
			return new ModuleAction(KEY, "Google for <b>" + search.getSearchString() + "</b>", new ImageIcon("res/google-logo.png")) {
				@Override
				public void invoke(Search search) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.google.com/#q=" + search.getSearchString().replaceAll(" ", "+") + adtCmd));
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
