/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus Uelker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.dqi11.quickStarter.modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.helpers.JSONParser;
import de.dqi11.quickStarter.modules.bridges.GlosbeBridge;

public class TranslateModule extends Module {
	private final String KEY = this.toString();
	private String defaultSrcLanguage;
	private String systemLanguage = Locale.getDefault().getCountry();

	public TranslateModule(MainController mainController) {
		super(mainController);
		defaultSrcLanguage = mainController.getModuleProperty(this, "defaultSrcLanguage");
		systemLanguage = Locale.getDefault().getCountry();
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {
		if(search.isCommand("translate")) {
			String text = ""; 
			
			SwingWorker<ModuleAction, ModuleAction> worker = new SwingWorker<ModuleAction, ModuleAction>(){

				@Override
				protected ModuleAction doInBackground() throws Exception {
					String from, to, phrase;
					
					phrase = search.getParam(0); 
					if (phrase.equals("")) 
						return new ModuleAction(KEY, "Type the word to translate");
					
					from = search.getParam(1);
					if (from.equals("")) from = defaultSrcLanguage;
					to = search.getParam(2);
					if (to.equals("")) to = Locale.getDefault().getLanguage();
					
					JSONParser parser = new JSONParser( GlosbeBridge.getJSON(from, to, phrase) );
					
					return new ModuleAction(KEY, parser.getArrayList("tuc").get(0).get("phrase.text")){
						@Override
						public void invoke(Search search) {
							if(Desktop.isDesktopSupported()) {
								try {
									if(systemLanguage != null)
										Desktop.getDesktop().browse(new URI("https://translate.google.com/#auto/" + systemLanguage + "/" + search.getParams().replaceAll(" ", "+")));
									else
										Desktop.getDesktop().browse(new URI("https://translate.google.com/#auto/en/" + search.getParams().replaceAll(" ", "+")));
								} catch (IOException | URISyntaxException e) {
									getMainController().updateModule(new WarningModuleAction(KEY, "An error occured."));
								}
							}
						}
					};
				}
				
				protected void done() {
					try {
						getMainController().updateModule(get());
					} catch (InterruptedException | ExecutionException e) {
						getMainController().updateModule(new WarningModuleAction(KEY, "No translation found."));
					}
					
					super.done();
				}
			};
			worker.execute();
			
			return new LoadingModuleAction(KEY, "Translating " + text);
		}
		
		return null;
	}

	public String getDefaultSrcLanguage() {
		return defaultSrcLanguage;
	}
	
}
