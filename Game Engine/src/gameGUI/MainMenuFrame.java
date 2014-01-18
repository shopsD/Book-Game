package gameGUI;

import java.io.IOException;

import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;






import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gameController.DataController;
import gameEntry.BookGame;

public class MainMenuFrame implements Screen {

	JPanel mainMenuPanel = new JPanel();
	SettingsFiles sf;
	MainWindowFrame mwf;
	SettingsFrame sfm;
	DataController dc;
	BottomDisplayPanel bdp;
	CreateCharacterFrame ccf;
	private volatile boolean gamePaused = false; // to be  used for pausing the game
	
	/**
	 * Width of a button
	 */
	private static final int BUTTON_WIDTH = 200;
	/**
	 * Height of the buttons
	 */
	private static final int BUTTON_HEIGHT = 45;

	
	/**
	 * Position of the button on the X Axis
	 */
	private static int buttonXPos;
	/**
	 * Position of the button on the Y Axis
	 */
	private static int buttonYPos;
	
	/**
	 * Space between buttons
	 */
	private static final int BUTTON_SPACING = 20;
	/**
	 * Position of text relative to the buttons background image on the x axis
	 */
	private static final int BUTTON_TEXT_CENTER_X = 45;
	/**
	 * Position of text relative to the buttons background image on the y axis
	 */
	private static final int BUTTON_TEXT_CENTER_Y = 5;
	private static Color textColour = Color.black;
	private UnicodeFont ttf;
	
	private SpriteBatch menuGraphics = new SpriteBatch();
	private static Texture button_image = null;
	private static Texture button_selected = null;

	private Audio main_menu_music;
	private Audio main_menu_button_hover;
	
	private GameIntroState gis;
	private SettingsVariablesStore svs;
	private BookGame bookGame;
	
	public MainMenuFrame(SettingsFiles sf, SettingsVariablesStore svs, GameIntroState gis, BookGame bookGame) throws LWJGLException{
		//first line of GUI to run
		this.sf = sf;
		this.gis = gis;
		this.svs = svs;
		this.bookGame = bookGame;
		//passes info to the settings frame
		SettingsFrame sfm = new SettingsFrame(sf, svs, this);
		sfm.displaySettingsWindow(); // displays the settings window
		MainWindowFrame mwf = new MainWindowFrame(sf, sfm, this, svs,/* dc,*/ ccf);
		//updates variables
		this.sfm = sfm;
		this.mwf = mwf;

		bdp = mwf.getBottomDisplayPanel();
		new Stage(svs.getResWidth(),svs.getResHeight(),svs.getFullScreen());
	}

	/**
	 * Creates buttons for the main menu
	 */
	private void createButtons (){
		buttonYPos = 0;
		int x = Mouse.getX();
		int y = Mouse.getY();

		//Loop to create buttons
		//Buttons are created from the bottom to the top
		for(int i =1; i <= 6; i++){
			buttonYPos += (BUTTON_HEIGHT + BUTTON_SPACING);
			//Check if mouse is over a button
			if (x > buttonXPos && x < (buttonXPos + BUTTON_WIDTH) && y < buttonYPos && y > (buttonYPos -  BUTTON_HEIGHT)){
				menuGraphics.draw(button_selected, buttonXPos,(svs.getResHeight() - buttonYPos), BUTTON_WIDTH, BUTTON_HEIGHT);
				textColour = Color.blue;
				if(!main_menu_button_hover.isPlaying()){
					main_menu_button_hover.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if(Mouse.isButtonDown(0)){
					switch(i){
					case 1:
						System.exit(0);

					case 2:
						System.out.println("Credits");
						break;
					case 3:
						System.out.println("Help");
						break;

					case 4:
						System.out.println("Settings");
						break;

					case 5:
						System.out.println("Load Game");

						break;

					case 6:
						ccf = new CreateCharacterFrame(this);

						break;
					}
				}
			}
			else{
				//Used to start displaying buttons from the bottom of the screen
				menuGraphics.draw(button_image,buttonXPos,(svs.getResHeight() - buttonYPos), BUTTON_WIDTH, BUTTON_HEIGHT);
				textColour = Color.black;
			}

			//Draw text
			switch(i){
			case 1:
				createButtonText(buttonXPos,buttonYPos, "Quit Game");
				break;

			case 2:
				createButtonText(buttonXPos,buttonYPos, "Credits");
				break;

			case 3:
				createButtonText(buttonXPos,buttonYPos, "Help");
				break;

			case 4:
				createButtonText(buttonXPos,buttonYPos, "Settings");
				break;

			case 5:
				createButtonText(buttonXPos,buttonYPos, "Load Game");
				break;

			case 6:
				createButtonText(buttonXPos,buttonYPos, "Start Game");
				break;
			}
		}
	}

	public void createButtonText(int xpos, int ypos, String text){
		ttf.drawString((xpos + BUTTON_TEXT_CENTER_X), ((svs.getResHeight() - ypos ) + BUTTON_TEXT_CENTER_Y), text, textColour);
	}

	public void startGameThreads(){
		//setting player starting stats
		dc.setPlayerName(ccf.getPlayerName());
		dc.setPlayerTechnique(ccf.getPlayerTechnique());
		dc.setPlayerAptitude(ccf.getPlayerAptitude());
		dc.setPlayerReasoning(ccf.getPlayerReasoning());
		dc.setPlayerGrammar(ccf.getPlayerGrammar());
		dc.setPlayerLevel(ccf.getPlayerLevel());
		bdp.getPlayerName(ccf.getPlayerName());
		dc.createProductArrays();
		dc.startPointControlThread();
		Thread mainWindowFrameThread = new Thread(mwf);
		mainWindowFrameThread.start();

		mainWindowFrameOpen();
		mwf.createGUI();
		dc.createPlayer();
	}

	public void resumeGame(){
		gamePaused = false;
	}

	public void pauseGame(){
		gamePaused = true; // pause game
	}

	public void mainWindowFrameOpen(){
		//continues the mainWindowFrameThread.
		//Required for settingsFrame
		mwf.startGamePanel();
	}

	public boolean getMainWindowFrameOpened(){
		return mwf.getMainWindowFrame();
	}

	public boolean gamePaused(){
		return gamePaused;
	}

	public void setDataController(DataController dc){
		this.dc = dc;
	}

	public MainWindowFrame getMainWindowFrame(){
		//used to position new production frame
		return mwf;
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.
		menuGraphics.begin();
		menuGraphics.setColor(0,0,0, delta);
		createButtons();
		menuGraphics.end();
		SoundStore.get().poll(0);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		ttf = bookGame.getMenuFont();
		buttonXPos = (int) Math.round(svs.getResWidth()/2.5); // centers buttons on the x axis
		
				
		button_selected = new Texture(Gdx.files.internal("res/images/main_menu/button_base_selected.png"));
		button_image = new Texture(Gdx.files.internal("res/images/main_menu/button_base.png"));

		//load music and sounds
		try {
			main_menu_music = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("res/sound/music/main_menu_music.ogg"));

			main_menu_button_hover = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("res/sound/effects/main_menu_button_hover.ogg"));
			main_menu_music.playAsMusic(1.0f, 1.0f, true); // start playing background music
		} catch (IOException e) {
		
			e.printStackTrace();
		}
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
