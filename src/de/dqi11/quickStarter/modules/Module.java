package de.dqi11.quickStarter.modules;

/**
 * Represents a module.
 */
public interface Module {
	/**
	 * Return a possible action for the given search-term.
	 * 
	 * @param search The specific search-term.
	 * @return The Advice.
	 */
	public ModuleAction getModuleAction(String search);
}
