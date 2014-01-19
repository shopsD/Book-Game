package com.me.bookgame;

import gameEntry.BookGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "GameEngine";
		cfg.useGL20 = false;
		BookGame bg  = new BookGame();
		bg.loadGameSettings("../Game Engine/bin/res/config/config.xml");
		cfg.width = bg.getSettingsVariablesStore().getResWidth();
		cfg.height = bg.getSettingsVariablesStore().getResHeight();
		cfg.fullscreen = bg.getSettingsVariablesStore().getFullScreen();
		cfg.vSyncEnabled = bg.getSettingsVariablesStore().getVsync();
		new LwjglApplication(bg, cfg);
	}
}
