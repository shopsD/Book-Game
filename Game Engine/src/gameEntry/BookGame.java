package gameEntry;

import gameGUI.GameIntroState;
import gameGUI.MainMenuFrame;

import org.lwjgl.LWJGLException;




import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class BookGame extends Game{

	//private UnicodeFont ttf;
	private static float FONT_SIZE = 25f;

	private int loading = 0;
	private MainMenuFrame mmf;
	private static SettingsVariablesStore svs = new SettingsVariablesStore();
	private static SettingFileParser sfp = new SettingFileParser(svs);
	private static SettingsFiles sf = new SettingsFiles(svs, sfp);

	private OrthographicCamera camera;
	/*public UnicodeFont getMenuFont(){
		return ttf;
	}*/
	
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
		GameIntroState gis = new GameIntroState(sf, svs, this);

		setScreen(gis);
	}


}
