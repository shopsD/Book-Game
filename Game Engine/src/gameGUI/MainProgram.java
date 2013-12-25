package gameGUI;

import java.net.URL;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import de.matthiasmann.TWLSlick.TWLStateBasedGame;

public class MainProgram extends TWLStateBasedGame{
	
	private static SettingsVariablesStore svs = new SettingsVariablesStore();
	private static SettingFileParser sfp = new SettingFileParser(svs);
	private static SettingsFiles sf = new SettingsFiles(svs, sfp);
	
	public MainProgram(String title) {
		super(title);
	}
	/**
	 * Reads the files with the game settings
	 */
	private static void loadGameSettings(){
		//Creates files to be read
		//reads the configuration files
		sf.readSettingsFile("bin/res/config/config.xml");
		
	}
	
	public static void main(String[] args) {
		////-----Version 0.7.0.0
		////-----Added feature to fire employees. Currently non-functional.
		////-----Need to add frames to view past sequels. List built correctly(Double frame considered)
		////-----Need to add frame to hire and fire employees.
		////-----Need to add unlock system
		////-----Need to rework scoring system
		////-----Need to add Graphics to GUI
		loadGameSettings();
		try {
			AppGameContainer container = new AppGameContainer(new MainProgram("Writer Game"));
			//Load display settings
			container.setDisplayMode(svs.getResWidth(),svs.getResHeight(),svs.getFullScreen());
			container.setVSync(svs.getVsync());
			//Load Audio settings
			container.setMusicOn(svs.getMusicOn());
			container.setSoundOn(svs.getSoundOn());
			container.setMusicVolume(svs.getMusicVolume());
			container.setSoundVolume(svs.getSoundVolume());
			container.start();	

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		//Statees
		//0 - Start Screen
		//1 - Main Menu
		//2 - Create Character Screen
		//3 - Settings Screen
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
