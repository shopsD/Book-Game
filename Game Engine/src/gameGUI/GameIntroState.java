package gameGUI;

import gameEntry.BookGame;

import org.newdawn.slick.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameIntroState implements Screen{

	private int loading = 0;
	Graphics loadingGraphics = new Graphics();
	SpriteBatch batch = new SpriteBatch();
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
		batch.begin();
		
		loadingGraphics.fillRoundRect(Math.round(svs.getResWidth()/10), Math.round(svs.getResHeight()/2), (loading * 3400)/(svs.getResWidth()/4), 40, 5);
		if(loading == 100){
			bookGame.setScreen(mmf);
		}
		loading++;
		batch.end();
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.dispose();
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
