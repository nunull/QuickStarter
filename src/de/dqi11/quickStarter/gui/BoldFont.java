package de.dqi11.quickStarter.gui;

import java.awt.Font;
import java.io.FileInputStream;

public class BoldFont {

	public static Font getFont(int fontSize){
		Font boldFont;
		try {
			boldFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/ubuntu/Ubuntu-Bold.ttf"));
			boldFont =  boldFont.deriveFont((float) fontSize);
		} catch (Exception e) {
			boldFont = new Font("Arial", Font.BOLD, fontSize);
		}
		return boldFont;
	}
	
	public static Font getFont(){
		return BoldFont.getFont(20);
	}
}
