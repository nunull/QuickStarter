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
package de.dqi11.quickStarter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.ImageObserver;

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

	private static final long serialVersionUID = 6641728081199830661L;
	private final int ICON_SIZE = 50;
	private Font font;
	
	public ModuleActionListCellRenderer(Font font) {
		this.font = font;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Component getListCellRendererComponent(final JList list, Object value, int index, boolean selected, boolean expanded) {
		ModuleAction moduleAction = (ModuleAction) value;
		Class[] interfaces = moduleAction.getClass().getInterfaces();
		boolean warning = false;
		for(int i = 0; i < interfaces.length; i++) {
			if(interfaces[i].getName().equals("de.dqi11.quickStarter.gui.Warning")) {
				warning = true;
			}
		}
		
		ImageIcon icon = (ImageIcon) moduleAction.getIcon();
		JLabel label = new JLabel();
		if(icon != null) {
			if(icon.getIconWidth() != ICON_SIZE || icon.getIconHeight() != ICON_SIZE) {
				icon.setImage(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
			} else {
				icon.setImageObserver(new ImageObserver() {
					
					@Override
					public boolean imageUpdate(Image img, int infoflags, int x, int y,
							int width, int height) {
						
						list.repaint();
						if(infoflags > 16) img.flush();
						
						return true;
					}
				});
			}
			
			label.setIcon(icon);
			setIcon(icon);
		}
		
		label.setText("<html>" + moduleAction.getText() + "</html>");
		label.setFont(font);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(280, 60));
		
		Border line = BorderFactory.createLineBorder(Color.BLACK);
		Border empty = new EmptyBorder(5, 20, 5, 0);
		CompoundBorder border = new CompoundBorder(line, empty);
		label.setBorder(border);
		
		if(warning) {
			label.setForeground(Color.RED);
		} else {
			label.setForeground(Color.WHITE);
		}
		
		if(selected) {
			label.setBackground(new Color(1f, 1f, 1f, 0.1f));
		} else {
			label.setBackground(new Color(1f, 1f, 1f, 0f));
		}
		
		return label;
	}
}
