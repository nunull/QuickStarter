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
package de.dqi11.quickStarter.os;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * Wrapper for Microsoft Windows.
 */
public class WinOS extends OS implements HotkeyListener{

	public WinOS(){
		if (!JIntellitype.checkInstanceAlreadyRunning("MainWindow")){
			JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, (int)' ');
			JIntellitype.getInstance().addHotKeyListener(this);
		}
	}
	@Override
	public void shutdown() {
		JIntellitype.getInstance().cleanUp();
	}
	
	@Override
	public void onHotKey(int arg0) {
		toggleApp();
	}
	
	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		
		JIntellitype.getInstance().unregisterHotKey(1);
		if (active) JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, (int)' ');
	}
	
	
}
