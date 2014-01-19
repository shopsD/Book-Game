package gameGUI;

import gameController.DataController;
import gameEntry.SettingsFiles;
import gameEntry.SettingsVariablesStore;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class MainWindowFrame extends JFrame implements Runnable{

	private static final long serialVersionUID = 3257305288182239L;
	//instantiated by MainMenuFrame
	private UpperDisplayPanel udp;
	private BottomDisplayPanel bbp;
	private MainGamePanel mgp;
	private SettingsFiles sf;
	private MainMenuFrame mmf;
	private SettingsVariablesStore svs;
	private DataController dc;
	private Thread mainGamePanelThread; // game display

	private volatile Dimension mainDimension; // main game window size
	private volatile boolean mainWindowFrameOpen; // used to determine if main game window is open
	MainWindowFrame(SettingsFiles sf, SettingsFrame sfm, MainMenuFrame mmf, SettingsVariablesStore svs, /*DataController dc,*/ CreateCharacterFrame ccf){
		mainWindowFrameOpen = false;//window closed on startup
		DataController dc = new DataController(svs, mmf);
		mmf.setDataController(dc);
		this.sf = sf;
		this.mmf = mmf;
		this.svs = svs;
		this.dc = dc;
		udp = new UpperDisplayPanel(sf, sfm, this, dc, mmf);
		bbp = new BottomDisplayPanel(dc);
		mgp = new MainGamePanel(dc, mmf, udp);
		dc.setMainGamePanel(mgp);
		
	}

	public void startGamePanel(){
		mainWindowFrameOpen = true; // main game window is opened
		Thread dataControllerThread = new Thread(dc);
		dataControllerThread.start();
		
		Thread mainGamePanelThread = new Thread(mgp);
		this.mainGamePanelThread = mainGamePanelThread; // starts the game thread
		mainGamePanelThread.start();
	}

	public void stopGamePanel(){
		mainWindowFrameOpen = false; // main game window is closed
	}

	public void backToMainMenu(){
		//closes this window and opens main menu
		udp.setMenuPaneText("No Production", "", true);
		this.dispose();
		//mmf.setVisible(true);
		stopGamePanel();
	}

	public boolean getMainWindowFrame(){
		return mainWindowFrameOpen;
	}

	public void createGUI(){
		//mainDimension = svs.getMainDimension();
		this.setSize(mainDimension);
		this.add(udp, BorderLayout.NORTH);
		this.add(bbp, BorderLayout.SOUTH);
		this.add(mgp, BorderLayout.CENTER);
		this.setSize(mainDimension);
		//this.setLocationRelativeTo(mmf);
		this.setResizable(false);
		this.setVisible(true);
		//gameWindow.start();
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				backToMainMenu();
				//gameWindow.stop();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}	

	
	public BottomDisplayPanel getBottomDisplayPanel(){
		return bbp;
	}
	
	@Override
	public void run() {
		//while (mmf.getProgramRunning()){
		while(mainWindowFrameOpen){
			//for resizing window while game is running
			//mainDimension = svs.getMainDimension();
			this.setSize(mainDimension);//resizes window
			this.setLocationRelativeTo(null);
			if (mainWindowFrameOpen == false){
				try {
					//mainGamePanelThread.sleep(100);
					mainGamePanelThread.join();
					
					break;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
			//break;
		}		
	}

}
