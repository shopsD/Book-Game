package gameGUI;

import gameGraphicsAndSound.CharacterCreationGraphics;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.lwjgl.LWJGLException;

public class CreateCharacterFrame extends JFrame{
	//player skills
	private String playerName;
	private int technique = 5;
	private int aptitude = 5;
	private int reasoning = 5;
	private int grammar = 5;
	private int level = 1;

	private int levelSkillPoints = 20;
	private MainMenuFrame mmf;
	private JTextArea nameInput = new JTextArea();//where player name is entered
	private volatile JTextArea techniqueText;
	private volatile JTextArea aptitudeText;
	private volatile JTextArea reasoningText;
	private volatile JTextArea grammarText;
	private volatile JTextArea remainingPointsArea;


	private CharacterCreationGraphics ccg;

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
	CreateCharacterFrame(MainMenuFrame mainMenuFrame){
		mmf = mainMenuFrame;
		try {
			ccg = new CharacterCreationGraphics(this);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createGUI();
	}


	private void createGUI(){
		final JPanel imagePanel = new JPanel();
		JPanel dataPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		imagePanel.setVisible(true);
		dataPanel.setVisible(true);
		buttonPanel.setVisible(true);

		JTextArea instructions = new JTextArea("Type in your Name");
		instructions.setForeground(Color.black);
		instructions.setBackground(Color.gray);
		instructions.setEditable(false);
		instructions.setEnabled(false);
		instructions.setFocusable(false);
		instructions.setVisible(true);
		instructions.setPreferredSize(new Dimension (TEXT_AREA_WIDTH, INSTRUCTIONS_TEXT_AREA_HEIGHT));

		nameInput.setText("Player");
		nameInput.setSize(new Dimension (TEXT_AREA_WIDTH, NAME_INPUT_TEXT_AREA_HEIGHT));

		JScrollPane textScrollPane = new JScrollPane(nameInput, JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		textScrollPane.setPreferredSize(new Dimension (TEXT_AREA_WIDTH, TEXT_SCROLL_PANE_HEIGHT));

		dataPanel.add(instructions);
		dataPanel.add(textScrollPane);
		//Text Area controls
		JTextArea remainingPointsArea = new JTextArea(""+levelSkillPoints);

		remainingPointsArea.setEditable(false);
		remainingPointsArea.setFocusable(false);
		remainingPointsArea.setVisible(true);
		this.remainingPointsArea = remainingPointsArea;
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
		remainingPointsArea.setText(""+(levelSkillPoints-(Integer.valueOf(techniqueText.getText()) + Integer.valueOf(aptitudeText.getText())
				+ Integer.valueOf(reasoningText.getText()) + Integer.valueOf(grammarText.getText()))));

		dataPanel.setLayout(new GridLayout(GRID_ROWS,GRID_COLUMNS));

		buttonPanel.add(createButtons("Continue")); // adds two buttons
		buttonPanel.add(createButtons("Cancel"));

		//design image pane
		imagePanel.setPreferredSize(new Dimension(IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT));
		imagePanel.setBorder(BorderFactory.createLoweredBevelBorder());

		//OpenGL
		ccg.setImagePanel(imagePanel,IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT);
		Thread imagePanelThread = new Thread(ccg);
		imagePanelThread.start();

		//adds panel locations
		this.add(imagePanel, BorderLayout.WEST);
		this.add(dataPanel, BorderLayout.EAST);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setTitle("Character Editor");
		//this.setLocationRelativeTo(mmf); // places it center of main menu frame


		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				//mmf.setVisible(true);

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		nameInput.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10){//if press enter
					dispose();//closes this window
					playerName = nameInput.getText(); // gets player name
					technique = Integer.valueOf(techniqueText.getText());
					aptitude = Integer.valueOf(aptitudeText.getText());
					reasoning = Integer.valueOf(reasoningText.getText());
					grammar = Integer.valueOf(grammarText.getText());
					//mmf.startProgram(); // starts up game loop
					mmf.startGameThreads();
				}
				else if(e.getKeyCode() == 27){
					dispose(); //if press escape
					//mmf.setVisible(true);
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	private JButton createButtons (final String buttonName){
		JButton button = new JButton (buttonName); // creates new button with button name
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkButtonClicked(buttonName);//adds actionlistener to button

			}

		});
		return button; // returns newly created button
	}

	private void checkButtonClicked(String buttonName){
		String bn = buttonName.toLowerCase(); //converts to lower case
		if (bn.equals("continue")){
			this.dispose();//closes this window
			playerName = nameInput.getText(); // gets player name
			technique = Integer.valueOf(techniqueText.getText());
			aptitude = Integer.valueOf(aptitudeText.getText());
			reasoning = Integer.valueOf(reasoningText.getText());
			grammar = Integer.valueOf(grammarText.getText());
			//	mmf.startProgram(); // starts up game loop

			mmf.destroyRunningThread();
			mmf.startGameThreads();

		}

		if (bn.equals("cancel")){
			this.dispose();
		//	mmf.setVisible(true);
		}
	}	

	private void createScrollBarAndTextArea(final String barName, JPanel panelName){
		//creating scroll bars with text areas
		final JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		JTextArea staticTextArea = new JTextArea(barName);
		scrollBar.setValue((Integer.valueOf(remainingPointsArea.getText()))/4);
		final JTextArea dynamicTextArea = new JTextArea(""+scrollBar.getValue());//sets default value to scrollbar

		//assign main textareas correctly
		if(barName.equalsIgnoreCase("technique")){
			techniqueText = dynamicTextArea;
		}
		else if (barName.equalsIgnoreCase("aptitude")){
			aptitudeText = dynamicTextArea;
		}
		else if(barName.equalsIgnoreCase("reasoning")){
			reasoningText = dynamicTextArea;
		}
		else if(barName.equalsIgnoreCase("grammar")){
			grammarText = dynamicTextArea;
		}
		//set text area properties
		dynamicTextArea.setEditable(false);
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
		if (barName.equalsIgnoreCase("technique")){
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
		this.revalidate();
		this.repaint();
	}

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
}
