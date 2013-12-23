package gameGUI;

import java.io.File;

import org.lwjgl.LWJGLException;

public class MainProgram {
	//class serves as link to main method and other classes
	public void startUp(){
		// Reads in a series of settings and configurations
		SettingsVariablesStore svs = new SettingsVariablesStore();
		SettingsFiles sf = new SettingsFiles(svs);
		readStartupFiles(sf);
		//creates main window
		MainMenuFrame mmf;
		try {
			mmf = new MainMenuFrame(sf, svs);
			Thread game = new Thread(mmf);
			game.start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void readStartupFiles(SettingsFiles sf){
		//Creates files to be read
		File mainWindowSettings = new File("config.ini");
		File settingsWindowSettings = new File("setconfig.ini");
		//reads the configuration files
		sf.readSettingsFile(mainWindowSettings);
		sf.readSettingsFile(settingsWindowSettings);
	}
	
	public void closeMainMenu(){
		
	}
}
