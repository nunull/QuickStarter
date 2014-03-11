package de.dqi11.quickStarter.gui;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import de.dqi11.quickStarter.modules.ModuleAction;

public class ModuleActionListCellRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean expanded) {
		ModuleAction moduleAction = (ModuleAction) value;
		ImageIcon icon = (ImageIcon)moduleAction.getIcon();
		if(icon != null) {
			icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		}
		
		JLabel label = new JLabel();
		label.setIcon(icon);
		label.setText(moduleAction.getText());
		
		return label;
	}
}
