package de.dqi11.quickStarter.gui;

import java.awt.Font;
import java.io.FileInputStream;

public final class DefaultFont {
	
	public static Font getFont(int fontSize){
		Font defaultFont;
		try {
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Light.ttf"));
			defaultFont =  defaultFont.deriveFont((float) fontSize);
		} catch (Exception e) {
			defaultFont = new Font("Arial", Font.PLAIN, fontSize);
		}
		return defaultFont;
	}
	
	public static Font getFont(){
		return DefaultFont.getFont(20);
	}
}
