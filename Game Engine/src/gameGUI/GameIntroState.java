package gameGUI;

import gameEntry.BookGame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class GameIntroState implements Screen{

	private int loading = 0;
	Graphics loadingGraphics = new Graphics();
	MainMenuFrame mmf;
	SettingsVariablesStore svs;
	SettingsFiles sf;
	private BookGame bookGame;
	public GameIntroState(SettingsFiles sf, SettingsVariablesStore svs,BookGame bookGame) {
		this.sf = sf;
		this.svs = svs;
		this.bookGame = bookGame;
	}

	@Override
	public void render(float delta) {
		//increase the length of the loading bar
		loadingGraphics.fillRoundRect(Math.round(svs.getResWidth()/10), Math.round(svs.getResHeight()/2), (loading * 3400)/(svs.getResWidth()/4), 40, 5);
		if(loading == 100){
			//bookGame.setScreen(mmf);
		}
		loading++;
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
