package de.dqi11.quickStarter.modules;

import javax.swing.ImageIcon;

import de.dqi11.quickStarter.controller.Search;

public class TestModule implements Module {

	@Override
	public ModuleAction getModuleAction(Search search) {
		// Just some test here.
		if(search.isCommand("test")) {
			return new ModuleAction("Yeah, some test...") {
				@Override
				public void invoke(Search search) {
					System.out.println(search.getParam(0));
				}
			};
		}
		
		return null;
	}

}
