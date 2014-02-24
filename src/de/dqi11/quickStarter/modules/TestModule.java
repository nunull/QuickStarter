package de.dqi11.quickStarter.modules;

public class TestModule implements Module {

	@Override
	public ModuleAction getModuleAction(String search) {
		// TODO Just some test here.
		return new ModuleAction("Yeah, some test...");
	}

}
