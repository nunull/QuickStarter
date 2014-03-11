package de.dqi11.quickStarter.modules;

import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.modules.bridges.GoogleBridge;

public class GoogleSearchModule implements Module {

	private GoogleBridge googleBridge;
	
	public GoogleSearchModule() {
		googleBridge = new GoogleBridge();
	}
	
	@Override
	public ModuleAction getModuleAction(Search search) {
		// TODO Auto-generated method stub
		if(!search.getSearchString().equals("")) {
			return new ModuleAction("Google for \"" + search.getSearchString() + "\"", new ImageIcon("res/google-logo.png")) {
				
				@Override
				public void invoke(Search search) {
					
				}
			};
		} else {
			return null;
		}
	}

}