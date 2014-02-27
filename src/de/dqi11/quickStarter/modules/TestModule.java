package de.dqi11.quickStarter.modules;

import de.dqi11.quickStarter.controller.Search;

public class TestModule implements Module {

	@Override
	public ModuleAction getModuleAction(Search search) {
		// Just some test here.
		if(search.equals("test")) {
			return new ModuleAction("Yeah, some test...") {
				@Override
				public void invoke() {
					System.out.println("Do something, TestModule!");
				}
			};
		}
		
		return null;
	}

}
