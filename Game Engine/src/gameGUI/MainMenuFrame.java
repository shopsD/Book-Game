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
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
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

	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 500;		
	private static final int GRID_ROWS = 6;
	private static final int GRID_COLUMNS =1;
	
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
		createButtons("Start Game");
		createButtons("Load Game");
		createButtons("Settings");
		createButtons("Help");
		createButtons("Credits");
		createButtons("Exit Game");
		// add main menu panel to this frame
		mainMenuPanel.setLayout(new GridLayout(GRID_ROWS,GRID_COLUMNS));
	//	this.add(mainMenuPanel);
	//	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT); // hard coded
	//	this.setLocationRelativeTo(null); // centers window
		//this.setResizable(false);

		this.setVisible(true); // shows window
	}

	private void createButtons (final String buttonName){
		JButton button = new JButton (buttonName);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkButtonClicked(buttonName);

			}

		});
		mainMenuPanel.add(button);
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


	private void drawBackground(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		//try {
		//	backgroundImage = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("BG_Character.png"));
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	
	//	backgroundImage.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1,0);
		//	GL11.glVertex2f(100+backgroundImage.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
		//	GL11.glVertex2f(100+backgroundImage.getTextureWidth(),100+backgroundImage.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			//GL11.glVertex2f(100,100+backgroundImage.getTextureHeight());
		GL11.glEnd();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initializeOpenGL();
		while(!Display.isCloseRequested()){

			drawBackground();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	
}
