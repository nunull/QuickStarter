package de.dqi11.quickStarter.modules;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.controller.Search;
import de.dqi11.quickStarter.gui.ModuleWindow;

public class FileSearchModule extends Module {
	private final String KEY = this.toString();
	private final int MAX_FOLDER_ITERATIONS = 1;
	private final int MAX_RESULTS_PER_FOLTER = 15;

	public FileSearchModule(MainController mainController) {
		super(mainController);
	}

	@Override
	public ModuleAction getModuleAction(final Search search) throws ConnectException {
		if(!search.getSearchString().equals("")) {
					
			return new ModuleAction(KEY, "Find <b>" + search.getSearchString() + "</b>") {
				@Override
				public ModuleWindow getModuleWindow(final Search search) {
					final ModuleWindow moduleWindow = new ModuleWindow("File search: " + search.getSearchString());
					
					SwingWorker<JList<File>, JList<File>> worker = new SwingWorker<JList<File>, JList<File>>() {

						@Override
						protected JList<File> doInBackground()
								throws Exception {
							
							final JList<File> list = new JList<>();
							DefaultListModel<File> listModel = new DefaultListModel<>();
							list.setModel(listModel);
							
							File dir = new File(System.getProperty("user.home"));
							LinkedList<File> results = findFiles(dir, new FilenameFilter() {
								
								@Override
								public boolean accept(File dir, String name) {
									return name.contains(search.getSearchString());
								}
							});
							
							for(File file : results) {
								listModel.addElement(file);
							}
							
							list.addKeyListener(new KeyAdapter() {
								
								@Override
								public void keyPressed(KeyEvent e) {
									if(e.getKeyCode() == KeyEvent.VK_ENTER) {
										File file = list.getSelectedValue();
										
										if(Desktop.isDesktopSupported()) {
											try {
												Desktop.getDesktop().open(file);
											} catch (IOException e1) {
											}
										}
									}
								}
							});
							
							list.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mousePressed(MouseEvent e) {
									int index = list.locationToIndex(e.getPoint());
									File file = list.getModel().getElementAt(index);
									
									if(e.getClickCount() == 2 && Desktop.isDesktopSupported()) {
										try {
											Desktop.getDesktop().open(file);
										} catch (IOException e1) {
										}
									}
								}
							});

							list.setSelectedIndex(0);
							list.setBackground(Color.WHITE);
							
							return list;
						}
						
						@Override
						protected void done() {
							try {
								JScrollPane scrollPane = new JScrollPane(get());
								scrollPane.setPreferredSize(new Dimension(770, 470));
								scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
								
								moduleWindow.add(scrollPane);
							} catch (InterruptedException e) {
							} catch (ExecutionException e) {
							}
							
							super.done();
						}
					};
					worker.execute();
					
					return moduleWindow;
				}
			};
		} else {
			return null;
		}
	}
	
	public LinkedList<File> findFiles(File dir, FilenameFilter filter) {
		return findFiles(dir, filter, 0);
	};
	
	private LinkedList<File> findFiles(File dir, FilenameFilter filter, int iteration) {
		if(dir != null && dir.isDirectory() && dir.canRead() && !dir.isHidden()) {
			LinkedList<File> results = new LinkedList<>();
			File[] files = dir.listFiles();
			File[] resultsArray = dir.listFiles(filter);
			
			if(resultsArray != null) {
				for(int i = 0, j = (resultsArray.length < MAX_RESULTS_PER_FOLTER ? resultsArray.length : MAX_RESULTS_PER_FOLTER); i < j; i++) {
					if(!resultsArray[i].isHidden() && !resultsArray[i].getName().startsWith(".")) {
						results.add(resultsArray[i]);
					}
				}
				
				if(files != null) {
					for(int i = 0, j = files.length; i < j; i++) {
						File file = files[i];
						
						if(file != null && file.isDirectory() && file.canRead() && iteration < MAX_FOLDER_ITERATIONS) {
							LinkedList<File> tmp = findFiles(file, filter, iteration + 1);
							if(tmp != null) results.addAll(tmp);
						}
					}
				}
				
				return results;
			}
		}
		
		return null;
	}
}