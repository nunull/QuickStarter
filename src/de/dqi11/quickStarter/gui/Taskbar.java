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
		
		if(actionCommand.equals(aboutMenuItem.getActionCommand())) {
			if(Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/nunull/QuickStarter"));
				} catch (IOException e1) {
				} catch (URISyntaxException e1) {
				}
			}
		} else if(actionCommand.equals(quitMenuItem.getActionCommand())) {
			mainController.quit();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		OS os = mainController.getOS();
		os.setActive(!os.isActive());
		((CheckboxMenuItem) toggleShortcutMenuItem).setState(os.isActive());
	}
}
