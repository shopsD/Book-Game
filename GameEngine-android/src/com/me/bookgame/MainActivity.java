package com.me.bookgame;

import gameEntry.BookGame;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        cfg.useCompass = false;
		cfg.useAccelerometer = false;
        
        BookGame bg = new BookGame();
        bg.getSettingsVariablesStore().setMusicVolume(1.0f);
        bg.getSettingsVariablesStore().setSoundVolume(1.0f);
        initialize(bg, cfg);
    }
}