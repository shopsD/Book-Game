package gameGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SettingsFrame extends JFrame{
	JPanel settingsPanel = new JPanel();
	SettingsFiles sf;
	SettingsVariablesStore svs;
	private int height;
	private int width;
	private boolean changed = false;
	private volatile boolean inGameWindow = false;
	MainMenuFrame mmf;
	public SettingsFrame(SettingsFiles sf, final SettingsVariablesStore svs, MainMenuFrame mmf) {
		this.svs = svs;
		this.sf = sf;
		this.mmf = mmf;
		
		this.setTitle("Settings");
		this.setResizable(false);
		//detect window movement
		this.addComponentListener(new ComponentListener (){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				closeWindow();
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub	
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void displaySettingsWindow(){
		addContent();
		this.setSize(svs.getSettingsDimension());
		this.setVisible(false);
		//set window location to center of screen
		this.setLocationRelativeTo(null);
	}

	private void closeWindow(){
		if(changed == true){
			//OPEN CONFIRM DIALOGUE
		}else{

			//get window size
			Dimension settingsFrameSize = this.getBounds().getSize();
			//assign new variables
			int height = settingsFrameSize.height;
			int width = settingsFrameSize.width;


			//assign old variables
			int oldHeight = svs.getSettingsDimension().height;
			int oldWidth = svs.getSettingsDimension().width;
			//write to file
			sf.writeToFile("setconfig.ini", "Size: " + height + " " + width, "Size: " + oldHeight + " " + oldWidth);
			//update new values
			svs.setSettingsDimension(width, height);
		}
	}

	private void addContent(){
		//creates a text area
		JTextArea resolution = new JTextArea("Resolution");
		JTextArea difficulty = new JTextArea("Difficulty");
		createTextArea(resolution);//adds text area to panel
		//adds drop down list to panel
		createDropList("resolution", "1366 x 768, 1360 x 768, 1280 x 768, 1024 x 768, 640 x 480 (default)");

		createTextArea(difficulty);//adds text area to panel

		createButton("Save");//creates buttons
		createButton("Close");
		this.add(settingsPanel);
	}

	private void createDropList(final String dropListName, final String dropListItems){
		//splits string into values uses ", " as a delimiter
		final String[] item = dropListItems.split(", ");
		// creates combo box
		final JComboBox<String> dropList = new JComboBox<String>(item);

		//gives each option an action listener
		dropList.addActionListener((new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedValue = String.valueOf(dropList.getSelectedItem());
				//performs action when object is selected
				readSelectedItem(dropListName, selectedValue);

			}		
		}));

		settingsPanel.add(dropList);
	}

	private void readSelectedItem(String dropListName, String selectedValue){
		String dln = dropListName.toLowerCase();
		String sv = selectedValue.toLowerCase();

		if(dln.equals("resolution")){

			String[] res = sv.split(" ");
			width = Integer.valueOf(res[0]);
			height = Integer.valueOf(res[2]);
		}
	}

	private void saveData(){
		//overwrites old settings with new
		int oldHeight = svs.getMainDimension().height;
		int oldWidth = svs.getMainDimension().width;
		sf.writeToFile("config.ini", "Size: " + height + " " + width, "Size: " + oldHeight + " " + oldWidth);
		svs.setMainDimension(width, height);
	}

	private void createTextArea(JTextArea textArea){
		//sets text area parameters
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setEditable(false);
		settingsPanel.add(textArea);
	}

	private void createButton (final String buttonName){
		//adds button settings
		JButton button = new JButton (buttonName);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkButtonClicked(buttonName);

			}

		});
		settingsPanel.add(button); // adds button to panel
	}

	private void checkButtonClicked(String buttonName){
		inGameWindow = mmf.getMainWindowFrameOpened();
		String bn = buttonName.toLowerCase(); // converts to lower case
		if(bn.equals("save")){
			saveData(); // write to ini files
			closeWindow(); // write to ini files
			this.setVisible(false); // hide window
		}

		else if (bn.equals("close")){
			closeWindow(); // write to ini file
			this.setVisible(false); // hide window
		}
	}
}
