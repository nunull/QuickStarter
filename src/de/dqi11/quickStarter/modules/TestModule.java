package de.dqi11.quickStarter.modules;

public class TestModule implements Module {

	@Override
	public Advice getAdvice(String search) {
		// TODO Just some test here.
		return new Advice("Yeah, some test...");
	}

}
