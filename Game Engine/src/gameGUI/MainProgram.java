package gameGUI;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainProgram extends StateBasedGame{

	public MainProgram(String title) {
		super(title);
	}

	private void readStartupFiles(SettingsFiles sf){

	}

	public static void main(String[] args) {
		////-----Version 0.7.0.0
		////-----Added feature to fire employees. Currently non-functional.
		////-----Need to add frames to view past sequels. List built correctly(Double frame considered)
		////-----Need to add frame to hire and fire employees.
		////-----Need to add unlock system
		////-----Need to rework scoring system
		////-----Need to add Graphics to GUI (OpenGL???)



		try {
			AppGameContainer container = new AppGameContainer(new MainProgram("Writer Game"));
			container.setDisplayMode(1366,768,false);
			container.setVSync(true);
			container.setFullscreen(true);
			container.start();	

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// Reads in a series of settings and configurations
		SettingsVariablesStore svs = new SettingsVariablesStore();
		SettingsFiles sf = new SettingsFiles(svs);

		//Creates files to be read
		File mainWindowSettings = new File("config.ini");
		File settingsWindowSettings = new File("setconfig.ini");
		//reads the configuration files
		sf.readSettingsFile(mainWindowSettings);
		sf.readSettingsFile(settingsWindowSettings);
		//creates main menu
		try {
			addState(new MainMenuFrame(sf, svs));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}
}
