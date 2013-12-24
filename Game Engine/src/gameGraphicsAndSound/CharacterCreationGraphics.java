package gameGraphicsAndSound;


import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import gameGUI.CreateCharacterFrame;



public class CharacterCreationGraphics extends AWTGLCanvas implements Runnable{
	private CreateCharacterFrame ccf;
	private JPanel imagePanel;
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;

	private Texture backgroundImage; 
	public CharacterCreationGraphics(CreateCharacterFrame createCharacterFrame) throws LWJGLException {
		ccf = createCharacterFrame;
	}

	public void setImagePanel(JPanel imagePanel, int imagePanelWidth, int imagePanelHeight){
		this.imagePanel = imagePanel;
		CANVAS_WIDTH = imagePanelWidth - 10;
		CANVAS_HEIGHT = imagePanelHeight + 5;
	}
	protected void initializeOpenGL(){
		imagePanel.add(this);
		imagePanel.setVisible(true);
		try {
			initGL();

			Display.setDisplayMode(new DisplayMode(CANVAS_WIDTH, CANVAS_HEIGHT));
			//Display.setParent(this);
			Display.create();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, CANVAS_WIDTH, CANVAS_HEIGHT, 0, 1,-1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setLocation(5, 5);
		this.setSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.setVisible(true);


	}


	private void drawBackground(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		try {
			backgroundImage = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("BG_Character.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		backgroundImage.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+backgroundImage.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+backgroundImage.getTextureWidth(),100+backgroundImage.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+backgroundImage.getTextureHeight());
		GL11.glEnd();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initializeOpenGL();
		//while(ccf.isVisible()){

			drawBackground();

			Display.update();
			Display.sync(60);
		//}
		Display.destroy();
	}

}
