package gameGUI;

import java.io.File;

public class MainProgram {
	//class serves as link to main method and other classes
	public void startUp(){
		// Reads in a series of settings and configurations
		SettingsVariablesStore svs = new SettingsVariablesStore();
		SettingsFiles sf = new SettingsFiles(svs);
		readStartupFiles(sf);
		//creates main window
		MainMenuFrame mmf = new MainMenuFrame(sf, svs);
		mmf.openMainWindow();
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
