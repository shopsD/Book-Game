package gameGUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;









import gameController.DataController;

public class MainMenuFrame extends AWTGLCanvas implements Runnable{

	//AppGameContainer container = new AppGameContainer(loader, 1024, 768, false);
	JPanel mainMenuPanel = new JPanel();
	SettingsFiles sf;
	MainWindowFrame mwf;
	SettingsFrame sfm;
	DataController dc;
	BottomDisplayPanel bdp;
	CreateCharacterFrame ccf;
	private Thread mainWindowFrameThread; // main window thread
	private volatile boolean gamePaused = false; // to be  used for pausing the game

	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 480;	
	private static final int BUTTON_WIDTH = 120;
	private static final int BUTTON_HEIGHT = 30;
	private static int buttonXPos = (int) Math.round(FRAME_WIDTH/2.5);
	private static int buttonYPos = 0;
	private static final int GRID_ROWS = 6;
	private static final int GRID_COLUMNS =1;
	private Graphics menuGraphics = new Graphics();
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

	public void openMainWindow(){
		/*	createButtons("Start Game");
		createButtons("Load Game");
		createButtons("Settings");
		createButtons("Help");
		createButtons("Credits");
		createButtons("Exit Game");*/
		// add main menu panel to this frame
		mainMenuPanel.setLayout(new GridLayout(GRID_ROWS,GRID_COLUMNS));
		//	this.add(mainMenuPanel);
		//	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT); // hard coded
		//	this.setLocationRelativeTo(null); // centers window
		//this.setResizable(false);

		this.setVisible(true); // shows window
	}

	/**
	 * Creates buttons for the main menu
	 */
	private void createButtons (){
		buttonYPos = 0;
		int x = Mouse.getX();
		int y = Mouse.getY();

		//Loop to create buttons
		for(int i =1; i <= 5; i++){
			buttonYPos += (BUTTON_HEIGHT + 50);
			if (x > buttonXPos && x < (buttonXPos + BUTTON_WIDTH) && y < buttonYPos && y > (buttonYPos -  BUTTON_HEIGHT)){
				menuGraphics.setColor(Color.orange);
				if(Mouse.isButtonDown(0)){
					switch(i){
					case 1:
						System.out.println("Start Game");
						break;

					case 2:
						System.out.println("Load Game");
						break;

					case 3:
						System.out.println("Settings");
						break;

					case 4:
						System.out.println("Credits");
						break;

					case 5:
						System.out.println("Quit Game");
						break;

					default:


					}
				}
			}
			else{
				menuGraphics.setColor(Color.white);
			}
			menuGraphics.fillRoundRect(buttonXPos, buttonYPos, BUTTON_WIDTH, BUTTON_HEIGHT, 5);
		}


	}

	private void checkButtonClicked(String buttonName){
		String bn = buttonName.toLowerCase(); //converts to lower case
		if (bn.equals("start game")){
			this.setVisible(false);
			//starts the game
			ccf = new CreateCharacterFrame(this);
		}

		if (bn.equals("settings")){
			sfm.setVisible(true); //shows the settings window
		}

		if (bn.equals("exit game")){
			System.exit(0); // quits the program

		}	
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
		this.mainWindowFrameThread = mainWindowFrameThread;
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
		mainWindowFrameThread = null;
	}


	protected void initializeOpenGL(){

		try {
			initGL();

			Display.setDisplayMode(new DisplayMode(FRAME_WIDTH, FRAME_HEIGHT));
			//Display.setParent(this);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setLocation(5, 5);
		this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setVisible(true);


	}


	private void drawBackground(Texture backgroundImage){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		createButtons();


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		initializeOpenGL();
		Texture backgroundImage = null;
		try {
			backgroundImage = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("BG_Character.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!Display.isCloseRequested()){

			drawBackground(backgroundImage);

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

}
