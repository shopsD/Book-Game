package gameEntry;

import gameGUI.GameIntroScreen;
import gameGUI.MainMenuScreen;

import org.lwjgl.LWJGLException;





import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
////-----Version 0.7.0.1
	////-----Added feature to fire employees. Currently non-functional.
	////-----Need to add frames to view past sequels. List built correctly
	////-----Need to add frame to hire and fire employees.
	////-----Need to add unlock system
	////-----Need to rework scoring system
	////-----Complete addition of opengl
public class BookGame extends Game{

	//private UnicodeFont ttf;
	private static float FONT_SIZE = 25f;

	private int loading = 0;

	private static SettingsVariablesStore svs = new SettingsVariablesStore();
	private static SettingFileParser sfp = new SettingFileParser(svs);
	private static SettingsFiles sf = new SettingsFiles(svs, sfp);

	private OrthographicCamera camera;
	/*public UnicodeFont getMenuFont(){
		return ttf;
	}*/
	
	/**
	 * Gets the SettingsVariableStore object
	 * @return SettingsVariablesStore class
	 */
	public SettingsVariablesStore getSettingsVariablesStore(){
		return svs;
	}
	/**
     * Reads the files with the game settings
     */
	public void loadGameSettings(String filePath){
		//Creates files to be read
		//reads the configuration files
		sf.initializeWriters();
		sf.readSettingsFile(filePath);
	}

	@Override
	public void create() {
		if(svs.getResHeight() == 0|| svs.getResWidth() == 0){
			svs.setResolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	camera = new OrthographicCamera(1, svs.getResHeight()/svs.getResWidth());
		//ttf = new UnicodeFont(new Font("Verdana", Font.ITALIC, 20));// load default font
		//try {
			// creates a new custom font
			//Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/GCursive-mouser.ttf"));
			//buttonFont = buttonFont.deriveFont(FONT_SIZE);
			/*ttf = new UnicodeFont(buttonFont);

			ttf.getEffects().add(new ColorEffect(java.awt.Color.white));
			ttf.addAsciiGlyphs();
			ttf.loadGlyphs(); */
		//} catch (FontFormatException | IOException | SlickException e1) {

		//	e1.printStackTrace();
		//}
		loading = 0;
		GameIntroScreen gis = new GameIntroScreen(sf, svs, this);

		setScreen(gis);
	}


}
