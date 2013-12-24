package gameGUI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import de.matthiasmann.TWLSlick.BasicTWLGameState;

public class GameIntroState extends BasicTWLGameState{
	private UnicodeFont ttf;
	private static float FONT_SIZE = 25f;

	private int loading = 0;
	Graphics loadingGraphics = new Graphics();

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)throws SlickException {
		ttf = new UnicodeFont(new Font("Verdana", Font.ITALIC, 20));// load default font
		try {
			// creates a new custom font
			Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/GCursive-mouser.ttf"));
			buttonFont = buttonFont.deriveFont(FONT_SIZE);
			ttf = new UnicodeFont(buttonFont);

			ttf.getEffects().add(new ColorEffect(java.awt.Color.white));
			ttf.addAsciiGlyphs();
			ttf.loadGlyphs(); 
		} catch (FontFormatException | IOException | SlickException e1) {

			e1.printStackTrace();
		}
		loading = 0;
	}

	@Override
	public void render(GameContainer gCont, StateBasedGame arg1, Graphics arg2)throws SlickException {
		//increase the length of the loading bar
		loadingGraphics.fillRoundRect(Math.round(gCont.getWidth()/10), Math.round(gCont.getHeight()/2), (loading * 3400)/(gCont.getWidth()/4), 40, 5);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int arg2)throws SlickException {
		if(loading == 100){
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		loading++;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public UnicodeFont getMenuFont(){
		return ttf;
	}
}
