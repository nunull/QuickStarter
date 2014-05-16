package de.dqi11.quickStarter.modules;

import java.net.ConnectException;

import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;

public class CalculatorModule extends Module {
	private final String KEY = this.toString();
	
	public CalculatorModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		DoubleEvaluator evaluator = new DoubleEvaluator();
		try {
			double result = evaluator.evaluate(search.getSearchString());
			
			return new ModuleAction(KEY, search.getSearchString() + " = " + result);
		} catch(SyntaxError | ArithmeticException e) {
			return null;
		}
	}
	
}
