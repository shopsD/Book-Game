package gameGUI;

import gameEntry.BookGame;
import gameEntry.SettingsFiles;
import gameEntry.SettingsVariablesStore;





import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameIntroState implements Screen{

	private static int loading = 0;
	private static ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static SpriteBatch batch = new SpriteBatch();
	private static MainMenuScreen mmf;
	private static SettingsVariablesStore svs;
	private static SettingsFiles sf;
	private static BookGame bookGame;

	public GameIntroState(SettingsFiles sf, SettingsVariablesStore svs,BookGame bookGame) {
		GameIntroState.sf = sf;
		GameIntroState.svs = svs;
		GameIntroState.bookGame = bookGame;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		//increase the length of the loading bar
		shapeRenderer.rect(Math.round(svs.getResWidth()/10), 40,(loading * 3400)/(svs.getResWidth()/4) , Math.round(svs.getResHeight()/10));
		shapeRenderer.end();
		if(loading == 100){
			//transition to main menu
			GameIntroState.mmf = new MainMenuScreen(sf, svs, this, bookGame);
			bookGame.setScreen(GameIntroState.mmf);
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
	}



	@Override
	public void hide() {

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
