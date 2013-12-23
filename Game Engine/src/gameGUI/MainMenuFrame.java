package gameGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;








import gameController.DataController;

public class MainMenuFrame extends AWTGLCanvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -974434834665374671L;

	JPanel mainMenuPanel = new JPanel();
	SettingsFiles sf;
	MainWindowFrame mwf;
	SettingsFrame sfm;
	DataController dc;
	BottomDisplayPanel bdp;
	CreateCharacterFrame ccf;
	private volatile boolean gamePaused = false; // to be  used for pausing the game

	private static final int FRAME_WIDTH = 1366;
	private static final int FRAME_HEIGHT = 768;	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 45;
	private static int buttonXPos = (int) Math.round(FRAME_WIDTH/2.5);
	private static int buttonYPos = 0;

	private static final int BUTTON_SPACING = 20;
	private static final int BUTTON_TEXT_CENTER_X = 40;
	private static final int BUTTON_TEXT_CENTER_Y = 5;
	private static Color textColour = Color.black;
	private static TrueTypeFont ttf;

	private Graphics menuGraphics = new Graphics();
	private static Image button_image = null;
	private static Image button_selected = null;

	private Audio main_menu_music;
	private Audio main_menu_button_hover;

	MainMenuFrame(SettingsFiles sf, SettingsVariablesStore svs) throws LWJGLException{
		//first line of GUI to run
		this.sf = sf;


		//passes info to the settings frame
		SettingsFrame sfm = new SettingsFrame(sf, svs, this);
		sfm.displaySettingsWindow(); // displays the settings window
		MainWindowFrame mwf = new MainWindowFrame(sf, sfm, this, svs,/* dc,*/ ccf);
		//updates variables
		this.sfm = sfm;
		this.mwf = mwf;

		bdp = mwf.getBottomDisplayPanel();

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
				button_selected.draw(buttonXPos,(FRAME_HEIGHT - buttonYPos), BUTTON_WIDTH, BUTTON_HEIGHT);
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

					default:


					}
				}
			}
			else{
				//Used to start displaying buttons from the bottom of the screen
				button_image.draw(buttonXPos,(FRAME_HEIGHT - buttonYPos), BUTTON_WIDTH, BUTTON_HEIGHT);
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
		ttf.drawString((xpos + BUTTON_TEXT_CENTER_X), ((FRAME_HEIGHT - ypos ) + BUTTON_TEXT_CENTER_Y), text, textColour);
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

	public void destroyRunningThread(){
	}

	/**
	 * Loads the assets and creates the display for rendering
	 */
	protected void initializeOpenGL(){

		try {
			initGL();
			//create the display
			Display.setDisplayMode(new DisplayMode(FRAME_WIDTH, FRAME_HEIGHT));
			Display.setFullscreen(true);
			Display.create();


			GL11.glEnable(GL11.GL_TEXTURE_2D);               

			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          

			// enable alpha blending

			GL11.glEnable(GL11.GL_BLEND);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glViewport(0,0,FRAME_WIDTH,FRAME_HEIGHT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, FRAME_WIDTH, FRAME_HEIGHT, 0, 1,-1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		//Load Fonts
		ttf = new TrueTypeFont(new Font("Verdana", Font.ITALIC, 20), true);
		try {
			Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("fonts/GCursive-mouser.ttf"));
			buttonFont.deriveFont(20);
			ttf = new TrueTypeFont(buttonFont, false);
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Load images for the buttons
		try {
			button_selected = new Image("images/main_menu/button_base_selected.png");
			button_image = new Image("images/main_menu/button_base.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			main_menu_music = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sound/music/main_menu_music.ogg"));

			main_menu_button_hover = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sound/effects/main_menu_button_hover.ogg"));
			main_menu_music.playAsMusic(1.0f, 1.0f, true); // start playing background music
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	/**
	 * Draws the background and buttons for the main menu
	 */
	private void drawBackground(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		menuGraphics.setBackground(Color.white);
		createButtons();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		initializeOpenGL();
		while(!Display.isCloseRequested()){

			drawBackground();
			SoundStore.get().poll(0);
			Display.update();
			Display.sync(60);
		}
		AL.destroy();
		Display.destroy();

	}

}
