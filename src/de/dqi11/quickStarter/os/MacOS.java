package de.dqi11.quickStarter.os;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Wrapper for Mac OSX.
 */
public class MacOS extends OS implements NativeKeyListener {
	
	private boolean altKeyPressed = false;

	public MacOS() {
		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.getInstance().addNativeKeyListener(this);
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void shutdown() {
		GlobalScreen.unregisterNativeHook();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == 16) altKeyPressed = true;
		else if(e.getKeyCode() == 32 && altKeyPressed) toggleApp();
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(e.getKeyCode() == 16) altKeyPressed = false;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
