package de.dqi11.quickStarter;

import java.util.LinkedList;
import java.util.Scanner;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.modules.Advice;

/**
 * The main-class.
 */
public class Main {

	/**
	 * Applications entry-point.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		/*
		 * Some basic tests to get everything initially up and running.
		 */
		MainController main = new MainController();
		
		Scanner terminalInput = new Scanner(System.in);
		System.out.print("Enter a search term: ");
		String search = terminalInput.nextLine();
		terminalInput.close();
		
		LinkedList<Advice> advices = main.invoke(search);
		for(Advice a : advices) {
			System.out.println(a.getText());
		}
		
		main.shutdown();
	}
}
