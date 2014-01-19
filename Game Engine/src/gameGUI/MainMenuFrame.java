package gameGUI;

import javax.swing.JPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gameController.DataController;
import gameEntry.BookGame;
import gameEntry.SettingsFiles;
import gameEntry.SettingsVariablesStore;

public class MainMenuFrame implements Screen {

	JPanel mainMenuPanel = new JPanel();

	private SettingsFiles sf;
	private MainWindowFrame mwf;
	private SettingsFrame sfm;
	private DataController dc;
	private BottomDisplayPanel bdp;
	private CreateCharacterFrame ccf;
	private GameIntroState gis;
	private SettingsVariablesStore svs;
	private BookGame bookGame;

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
	private static Color textColour = Color.BLACK;
	//private UnicodeFont ttf;

	private SpriteBatch menuGraphics = new SpriteBatch();
	private static Texture buttonTexture;
	private static Texture buttonHoverTexture;
	private static Sprite buttonBaseSprite;
	private static Sprite buttonHoverSprite;
	private Music mainMenuMusic;
	private Sound mainMenuButtonHover;



	public MainMenuFrame(SettingsFiles sf, SettingsVariablesStore svs, GameIntroState gis, BookGame bookGame){
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
		//new Stage(svs.getResWidth(),svs.getResHeight(),svs.getFullScreen());
	}

	/**
	 * Creates buttons for the main menu
	 */
	private void createButtons (){
		buttonYPos = Math.round(svs.getResHeight()/2.5f);
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();

		//Loop to create buttons
		//Buttons are created from the bottom to the top
		for(int i =1; i <= 6; i++){
			buttonYPos += (BUTTON_HEIGHT + BUTTON_SPACING);
			//Check if mouse is over a button
			if (x > buttonXPos && x < (buttonXPos + BUTTON_WIDTH) && y < buttonYPos && y > (buttonYPos -  BUTTON_HEIGHT)){
				textColour = Color.BLUE;
				
				mainMenuButtonHover.play();
				
				buttonHoverSprite.setPosition(buttonXPos, (svs.getResHeight() - buttonYPos));
				buttonHoverSprite.draw(menuGraphics);
				
				if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
					switch(i){
					case 1:
						ccf = new CreateCharacterFrame(this);	
						break;
					case 2:
						System.out.println("Load Game");

						break;
					case 3:
						System.out.println("Settings");
						break;

					case 4:
						System.out.println("Help");
						break;

					case 5:
						System.out.println("Credits");
						break;
					case 6:
						System.exit(0);
					}
				}
			}
			else{
				//Used to start displaying buttons from the bottom of the screen
				buttonBaseSprite.setPosition(buttonXPos, (svs.getResHeight() - buttonYPos));
				buttonBaseSprite.draw(menuGraphics);
				textColour = Color.BLACK;
			}
			//Draw text
			switch(i){
			case 1:
				createButtonText(buttonXPos,buttonYPos, "Start Game");
				break;

			case 2:
				createButtonText(buttonXPos,buttonYPos, "Load Game");
				break;

			case 3:
				createButtonText(buttonXPos,buttonYPos, "Settings");
				break;

			case 4:
				createButtonText(buttonXPos,buttonYPos, "Help");
				break;

			case 5:
				createButtonText(buttonXPos,buttonYPos, "Credits");
				break;

			case 6:
				createButtonText(buttonXPos,buttonYPos, "Quit Game");
				break;
			}
		}
	}

	public void createButtonText(int xpos, int ypos, String text){
		//ttf.drawString((xpos + BUTTON_TEXT_CENTER_X), ((svs.getResHeight() - ypos ) + BUTTON_TEXT_CENTER_Y), text, textColour);
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
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		menuGraphics.begin();


		createButtons();
		menuGraphics.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		//ttf = bookGame.getMenuFont();
		buttonXPos = (int) Math.round(svs.getResWidth()/2.5); // centers buttons on the x axis


		buttonHoverTexture = new Texture(Gdx.files.internal("res/images/main_menu/button_base_selected.png"));
		buttonTexture = new Texture(Gdx.files.internal("res/images/main_menu/button_base.png"));

		buttonBaseSprite = new Sprite(buttonTexture);
		buttonHoverSprite = new Sprite(buttonHoverTexture);

		buttonBaseSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		buttonHoverSprite.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		//load music
		mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("res/music/Luxian Voyage.ogg"));

		mainMenuMusic.setVolume(svs.getMusicVolume());
		mainMenuMusic.setLooping(true);
		mainMenuMusic.play();
		
		mainMenuButtonHover = Gdx.audio.newSound(Gdx.files.internal("res/sound/main_menu_button_hover.ogg"));
		mainMenuButtonHover.setVolume(0, svs.getSoundVolume());
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		mainMenuMusic.stop();
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
		mainMenuMusic.stop();
		mainMenuMusic.dispose();
	}

}
