package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import de.dqi11.quickStarter.modules.ModuleAction;

public class ModuleActionListCellRenderer extends DefaultListCellRenderer {

	private final int ICON_SIZE = 50;
	private Font font;
	
	public ModuleActionListCellRenderer(Font font) {
		this.font = font;
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean expanded) {
		ModuleAction moduleAction = (ModuleAction) value;
		ImageIcon icon = (ImageIcon) moduleAction.getIcon();
		JLabel label = new JLabel();
		if(icon != null) {
			icon.setImage(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
			label.setIcon(icon);
		}
		
		label.setText(moduleAction.getText());
		label.setFont(font);
		label.setPreferredSize(new Dimension(300, 50));
		
		Border line = BorderFactory.createLineBorder(Color.BLACK);
		Border empty = new EmptyBorder(0, 10, 0, 0);
		CompoundBorder border = new CompoundBorder(line, empty);
		label.setBorder(border);
		
		if(!selected) {
			label.setForeground(Color.WHITE);
		} else {
			label.setForeground(Color.YELLOW);
		}
		
		return label;
	}
}