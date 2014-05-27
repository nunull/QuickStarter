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
		// TODO Auto-generated method stub
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
