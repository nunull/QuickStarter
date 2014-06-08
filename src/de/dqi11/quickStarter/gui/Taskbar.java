/*
 * QuickStarter - Spotlight-like QuickStarter Application.
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Timm Albers, Arne Peschken, Yunus †lker
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
package de.dqi11.quickStarter.gui;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import de.dqi11.quickStarter.controller.MainController;
import de.dqi11.quickStarter.os.OS;

public class Taskbar implements ActionListener, ItemListener {
	private MainController mainController;
	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private BufferedImage trayIconImage;
	private PopupMenu popup;
	private MenuItem helpMenuItem;
	private MenuItem aboutMenuItem;
	private MenuItem toggleShortcutMenuItem;
	private MenuItem preferencesMenuItem;
	private MenuItem quitMenuItem;
	
	public Taskbar(MainController mainController) {
		this.mainController = mainController;
		
		if(SystemTray.isSupported()) {
			try {
				trayIconImage = ImageIO.read(new File("res/tray-icon.png"));
			} catch (IOException e) {
			}
			
			systemTray = SystemTray.getSystemTray();
			trayIcon = new TrayIcon(trayIconImage);
			trayIcon.setImageAutoSize(true);
			
			popup = new PopupMenu();
			helpMenuItem = new MenuItem("Help");
			helpMenuItem.setActionCommand("help");
			
			aboutMenuItem = new MenuItem("About");
			aboutMenuItem.setActionCommand("about");
			
			toggleShortcutMenuItem = new CheckboxMenuItem("Shortcut active", true);
			((CheckboxMenuItem) toggleShortcutMenuItem).addItemListener(this);
			
			preferencesMenuItem = new MenuItem("Preferences");
			preferencesMenuItem.setActionCommand("app.pref");
			
			quitMenuItem = new MenuItem("Quit");
			quitMenuItem.setActionCommand("app.quit");
			
			popup.add(helpMenuItem);
			popup.add(aboutMenuItem);
			popup.add(preferencesMenuItem);
			popup.add(toggleShortcutMenuItem);
			popup.add(quitMenuItem);
			popup.addActionListener(this);
			trayIcon.setPopupMenu(popup);
			
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals(helpMenuItem.getActionCommand())) {
			mainController.showHelpWindow();
		} else if(actionCommand.equals(aboutMenuItem.getActionCommand())) {
			if(Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/nunull/QuickStarter"));
				} catch (IOException e1) {
				} catch (URISyntaxException e1) {
				}
			}
		} else if(actionCommand.equals(quitMenuItem.getActionCommand())) {
			mainController.quit();
		} else if (actionCommand.equals(preferencesMenuItem.getActionCommand())){
			mainController.showPrefenceWindow();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		OS os = mainController.getOS();
		os.setActive(!os.isActive());
		((CheckboxMenuItem) toggleShortcutMenuItem).setState(os.isActive());
	}
}
