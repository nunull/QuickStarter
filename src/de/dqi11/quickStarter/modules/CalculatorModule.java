package de.dqi11.quickStarter.modules;

import java.awt.Dimension;
import java.net.ConnectException;

import org.math.plot.Plot2DPanel;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.eval.ComplexEvaluator;
import org.matheclipse.parser.client.eval.ComplexVariable;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class CalculatorModule extends Module {
	private final String KEY = this.toString();
	
	public CalculatorModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(Search search) throws ConnectException {
		if(search.getSearchString().matches("[a-zA-Z]\\([a-zA-Z]\\).*")) {
			return new ModuleAction(KEY, "Draw <b>" + search.getSearchString() + "</b>") {
				@Override
				public ModuleWindow getModuleWindow(Search search) {
					try {
						ComplexEvaluator evaluator = new ComplexEvaluator();
						String variableName = new Character(search.getSearchString().charAt(2)).toString();
						String expression = search.getSearchString().split("=")[1].trim();
						System.out.println(variableName);
						
						ModuleWindow moduleWindow = new ModuleWindow();
						Plot2DPanel plot = new Plot2DPanel();
						plot.setPreferredSize(new Dimension(770, 490));
						plot.plotToolBar.setVisible(false);
						
						double[] x = new double[100];
						double[] y = new double[100];
						for(int i = 0, j = x.length; i < j; i++) {
							evaluator.defineVariable("x", new ComplexVariable(i-50));
							
							x[i] = i-50;
							y[i] = evaluator.evaluate(expression).getReal();
						}
						
						plot.addLinePlot(search.getSearchString(), x, y);
						
						moduleWindow.add(plot);
						
						return moduleWindow;
					} catch(IndexOutOfBoundsException e) {
						return null;
					}
				}
			};
		} else {
			DoubleEvaluator evaluator = new DoubleEvaluator();
			
			try {
				double result = evaluator.evaluate(search.getSearchString());
				
				return new ModuleAction(KEY, search.getSearchString() + " = <b>" + result + "</b>");
			} catch(SyntaxError | ArithmeticException e) {
				return null;
			}
		}
	}
	
}
