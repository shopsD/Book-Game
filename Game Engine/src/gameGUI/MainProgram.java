package gameGUI;

import java.io.File;





import java.net.URL;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import de.matthiasmann.TWLSlick.TWLStateBasedGame;

public class MainProgram extends TWLStateBasedGame{
	
	private static SettingsVariablesStore svs = new SettingsVariablesStore();
	private static SettingsFiles sf = new SettingsFiles(svs);
	
	public MainProgram(String title) {
		super(title);
	}
	/**
	 * Reads the files with the game settings
	 */
	public static void loadGameSettings(){
		//Creates files to be read
		File mainWindowSettings = new File("config.ini");
		File settingsWindowSettings = new File("setconfig.ini");
		//reads the configuration files
		sf.readSettingsFile(mainWindowSettings);
		sf.readSettingsFile(settingsWindowSettings);
		
	}
	
	public static void main(String[] args) {
		////-----Version 0.7.0.0
		////-----Added feature to fire employees. Currently non-functional.
		////-----Need to add frames to view past sequels. List built correctly(Double frame considered)
		////-----Need to add frame to hire and fire employees.
		////-----Need to add unlock system
		////-----Need to rework scoring system
		////-----Need to add Graphics to GUI (OpenGL???)
		loadGameSettings();
		try {
			AppGameContainer container = new AppGameContainer(new MainProgram("Writer Game"));
			container.setDisplayMode(container.getScreenWidth(),container.getScreenHeight(),false);
			container.setVSync(true);
			container.setFullscreen(true);
			container.start();	

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
	
		//creates main menu
		try {
			GameIntroState gis = new GameIntroState();
			addState(gis);
			addState(new MainMenuFrame(sf, svs, gis));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}
	@Override
	protected URL getThemeURL() {
		return MainProgram.class.getResource("/res/themes/Menu_Theme.xml");
	}
}
