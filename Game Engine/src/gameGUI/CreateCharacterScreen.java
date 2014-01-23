package gameGUI;

import gameEntry.BookGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CreateCharacterScreen implements Screen{
	//player skills
	private String playerName;
	private int technique = 5;
	private int aptitude = 5;
	private int reasoning = 5;
	private int grammar = 5;
	private int level = 1;

	private int levelSkillPoints = 20;
	private MainMenuScreen mms;
	//private JTextArea nameInput = new JTextArea();//where player name is entered
	/*private volatile JTextArea techniqueText;
	private volatile JTextArea aptitudeText;
	private volatile JTextArea reasoningText;
	private volatile JTextArea grammarText;
	private volatile JTextArea remainingPointsArea;*/


	private static final int IMAGE_PANEL_WIDTH = 260;
	private static final int IMAGE_PANEL_HEIGHT = 340;

	private static final int TEXT_AREA_WIDTH = 105;

	private static final int INSTRUCTIONS_TEXT_AREA_HEIGHT  = 5;
	private static final int NAME_INPUT_TEXT_AREA_HEIGHT = 15 ;
	private static final int TEXT_SCROLL_PANE_HEIGHT  = 20;

	private static final int GRID_ROWS = 16; 
	private static final int GRID_COLUMNS = 2;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 420;
	
	private BookGame bookGame;
	private static Sprite buttonBaseSprite;
	private static Sprite buttonHoverSprite;
	private static Music mainMenuMusic;
	private static Sound mainMenuButtonHover;

	CreateCharacterScreen(MainMenuScreen mms, BookGame bookGame, Sound mainMenuButtonHover, Music mainMenuMusic, Sprite buttonHoverSprite, Sprite buttonBaseSprite){
		this.mms = mms;
		CreateCharacterScreen.mainMenuButtonHover  = mainMenuButtonHover;
		CreateCharacterScreen.mainMenuMusic = mainMenuMusic;
		this.bookGame = bookGame;
		CreateCharacterScreen.buttonBaseSprite = buttonBaseSprite;
		CreateCharacterScreen.buttonHoverSprite = buttonHoverSprite;
		//createGUI();
	}


	private void createGUI(){
	/*	final JPanel imagePanel = new JPanel();
		JPanel dataPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		imagePanel.setVisible(true);
		dataPanel.setVisible(true);
		buttonPanel.setVisible(true);
*/
		/*JTextArea instructions = new JTextArea("Type in your Name");
		instructions.setForeground(Color.black);
		instructions.setBackground(Color.gray);
		instructions.setEditable(false);
		instructions.setEnabled(false);
		instructions.setFocusable(false);
		instructions.setVisible(true);
		instructions.setPreferredSize(new Dimension (TEXT_AREA_WIDTH, INSTRUCTIONS_TEXT_AREA_HEIGHT));
*/
		//nameInput.setText("Player");
		//nameInput.setSize(new Dimension (TEXT_AREA_WIDTH, NAME_INPUT_TEXT_AREA_HEIGHT));

		//JScrollPane textScrollPane = new JScrollPane(nameInput, JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				//JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	//	textScrollPane.setPreferredSize(new Dimension (TEXT_AREA_WIDTH, TEXT_SCROLL_PANE_HEIGHT));

	//	dataPanel.add(instructions);
	//	dataPanel.add(textScrollPane);
		//Text Area controls
	//	JTextArea remainingPointsArea = new JTextArea(""+levelSkillPoints);
/*
		remainingPointsArea.setEditable(false);
		remainingPointsArea.setFocusable(false);
		remainingPointsArea.setVisible(true);
	//	this.remainingPointsArea = remainingPointsArea;
		//Text Area controls
		JTextArea remainingPointsText = new JTextArea("Remaining Skill Points");
		remainingPointsText.setForeground(Color.black);
		remainingPointsText.setBackground(Color.gray);
		remainingPointsText.setEditable(false);
		remainingPointsText.setEnabled(false);
		remainingPointsText.setFocusable(false);
		remainingPointsText.setVisible(true);

		dataPanel.add(remainingPointsText);
		dataPanel.add(remainingPointsArea);
		//creates skill scroll bars
		createScrollBarAndTextArea("Technique", dataPanel);
		createScrollBarAndTextArea("Aptitude", dataPanel);
		createScrollBarAndTextArea("Reasoning", dataPanel);
		createScrollBarAndTextArea("Grammar", dataPanel);
		//sets correct number of remaining skill points
		//remainingPointsArea.setText(""+(levelSkillPoints-(Integer.valueOf(techniqueText.getText()) + Integer.valueOf(aptitudeText.getText())
	//			+ Integer.valueOf(reasoningText.getText()) + Integer.valueOf(grammarText.getText()))));

		dataPanel.setLayout(new GridLayout(GRID_ROWS,GRID_COLUMNS));

		buttonPanel.add(createButtons("Continue")); // adds two buttons
		buttonPanel.add(createButtons("Cancel"));

*/

	}

	/*private JButton createButtons (final String buttonName){
		JButton button = new JButton (buttonName); // creates new button with button name
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkButtonClicked(buttonName);//adds actionlistener to button

			}

		});
		return button; // returns newly created button
	}*/

	private void checkButtonClicked(String buttonName){
		String bn = buttonName.toLowerCase(); //converts to lower case
		if (bn.equals("continue")){
			
			//playerName = nameInput.getText(); // gets player name
		/*	technique = Integer.valueOf(techniqueText.getText());
			aptitude = Integer.valueOf(aptitudeText.getText());
			reasoning = Integer.valueOf(reasoningText.getText());
			grammar = Integer.valueOf(grammarText.getText());*/
			//	mmf.startProgram(); // starts up game loop


			mms.startGameThreads();

		}

		if (bn.equals("cancel")){
			
		//	mmf.setVisible(true);
		}
	}	

	/*private void createScrollBarAndTextArea(final String barName, JPanel panelName){
		//creating scroll bars with text areas
		//final JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	//	JTextArea staticTextArea = new JTextArea(barName);
		//scrollBar.setValue((Integer.valueOf(remainingPointsArea.getText()))/4);
		//final JTextArea dynamicTextArea = new JTextArea(""+scrollBar.getValue());//sets default value to scrollbar

		//assign main textareas correctly
		if(barName.equalsIgnoreCase("technique")){
		//	techniqueText = dynamicTextArea;
		}
		else if (barName.equalsIgnoreCase("aptitude")){
		//	aptitudeText = dynamicTextArea;
		}
		else if(barName.equalsIgnoreCase("reasoning")){
		//	reasoningText = dynamicTextArea;
		}
		else if(barName.equalsIgnoreCase("grammar")){
		//	grammarText = dynamicTextArea;
		}
		//set text area properties
		/*dynamicTextArea.setEditable(false);
		dynamicTextArea.setFocusable(false);
		dynamicTextArea.setVisible(true);

		staticTextArea.setForeground(Color.black);
		staticTextArea.setBackground(Color.gray);
		staticTextArea.setEditable(false);
		staticTextArea.setEnabled(false);
		staticTextArea.setFocusable(false);
		staticTextArea.setVisible(true);

		scrollBar.setMaximum(levelSkillPoints);
		scrollBar.setMinimum(1);

		scrollBar.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				int x = Integer.valueOf(dynamicTextArea.getText());
				//prevents using extra points
				if(Integer.valueOf(remainingPointsArea.getText()) <0){

					scrollBar.setMaximum(Integer.valueOf(dynamicTextArea.getText())+10);
					scrollBar.setValue(x+(Integer.valueOf(remainingPointsArea.getText())));
				}else if(Integer.valueOf(remainingPointsArea.getText()) ==0){
					scrollBar.setMaximum(Integer.valueOf(dynamicTextArea.getText())+10);
					scrollBar.setValue(x-1);
				}
				else{
					scrollBar.setMaximum(levelSkillPoints);
				}
				checkBarAdjusted(barName, scrollBar);
			}

		});
		panelName.add(staticTextArea);
		panelName.add(dynamicTextArea);
		panelName.add(scrollBar);
	}

	private void checkBarAdjusted(String barName, JScrollBar scrollBar) {
	/*	if (barName.equalsIgnoreCase("technique")){
			techniqueText.setText(""+scrollBar.getValue());
		}
		else if(barName.equalsIgnoreCase("aptitude")){
			aptitudeText.setText(""+scrollBar.getValue());
		}
		else if(barName.equalsIgnoreCase("reasoning")){
			reasoningText.setText(""+scrollBar.getValue());
		}
		else if(barName.equalsIgnoreCase("grammar")){
			grammarText.setText(""+scrollBar.getValue());
		}
		remainingPointsArea.setText(""+(levelSkillPoints-(Integer.valueOf(techniqueText.getText()) + Integer.valueOf(aptitudeText.getText())
				+ Integer.valueOf(reasoningText.getText()) + Integer.valueOf(grammarText.getText()))));

	}*/

	public String getPlayerName(){
		return playerName;
	}

	public int getPlayerTechnique(){
		return technique;
	}

	public int getPlayerAptitude(){
		return aptitude;
	}

	public int getPlayerReasoning(){
		return reasoning;
	}

	public int getPlayerGrammar(){
		return grammar;
	}

	public int getPlayerLevel(){
		return level;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		mainMenuMusic.play();
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
