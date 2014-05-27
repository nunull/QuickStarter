package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

public class ErrorCoreModule extends CoreModule {

	public ErrorCoreModule(MainController controller) {
		super(controller);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(getMainController().isNetworkError() && !search.getSearchString().equals("")) {
			return new WarningModuleAction("A network error occured and some modules can not be displayed. Please check your network connection and retry.");
		} else {
			return null;
		}
	}
}
