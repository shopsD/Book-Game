package gameGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gameController.DataController;

public class MainMenuFrame extends JFrame{
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
	
	MainMenuFrame(SettingsFiles sf, SettingsVariablesStore svs) {
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
		this.add(mainMenuPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT); // hard coded
		this.setLocationRelativeTo(null); // centers window
		this.setResizable(false);

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
	
}
