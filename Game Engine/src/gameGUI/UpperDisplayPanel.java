package gameGUI;

import gameController.DataController;
import gameController.ManuscriptCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UpperDisplayPanel extends JPanel{
	private JMenuBar mainMenuBar = new JMenuBar();
	private JTextPane manuscriptName = new JTextPane();
	
	private MainMenuFrame mmf;
	private SettingsFiles sf;
	private	SettingsFrame sfm ;
	private DataController dc;
	private MainWindowFrame mwf;
	private ManuscriptCreator mc;
	/////////////////////
	UpperDisplayPanel(SettingsFiles sf, SettingsFrame sfm, MainWindowFrame mwf, DataController dc, MainMenuFrame mmf){
		this.sf = sf;
		this.sfm = sfm;
		this.dc = dc;
		this.mwf = mwf;
		this.mmf = mmf;
		createMainMenuBar();
		manuscriptName.setEditable(false);
		manuscriptName.setFocusable(false);
		setMenuPaneText("No Production", "",true);
		this.add(manuscriptName);
		this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));// sets border colour
	}
	////////////////Create new menu bars above
	private void createMainMenuBar(){
		//main menu bars
		createMainMenu();
		createBuyMenu();

		this.add(mainMenuBar, BorderLayout.WEST);
	}
	////////////////Create new menu bars below

	////////////////Create new menu's below
	private void createMainMenu(){
		JMenu fileMenu = new JMenu("Game");
		JMenu editMenu = new JMenu("Edit");// sub menu
		setMenuActionListener(fileMenu);
		setMenuActionListener(editMenu);
		
		mainMenuBar.add(createMenuItem("New", fileMenu));
		mainMenuBar.add(createMenuItem("Open", fileMenu));
		mainMenuBar.add(createMenuItem("Save", fileMenu));
		//create a subMenu
		createSubOption("Settings", editMenu, fileMenu);
		mainMenuBar.add(createMenuItem("Exit", fileMenu));
	}

	private void createSubOption(String optionName, JMenu subMenuName, JMenu mainMenuName){
		subMenuName.add(createMenuItem(optionName, subMenuName));//creates new submenu
		mainMenuName.add(subMenuName);//adds submenu to existing menu
	}

	private void createBuyMenu(){
		JMenu buyMenu = new JMenu("Buy");
		setMenuActionListener(buyMenu);
		mainMenuBar.add(createMenuItem("Option One", buyMenu));
		mainMenuBar.add(createMenuItem("Option Two", buyMenu));
		mainMenuBar.add(createMenuItem("Option Three", buyMenu));
	}
	////////////////Create new menu's above

	////////////////Create new menus items above
	private JMenu createMenuItem (final String buttonLabel, final JMenu menuName){
		JMenuItem menuItemOption = new JMenuItem(buttonLabel); // creates new menu item
		menuItemOption.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuNameString = menuName.getText(); // get menu option
				//determine which menu is selected
				getSelectedMenu(menuNameString, buttonLabel);
			}	
		});

		menuName.add(menuItemOption); // add option to menu
		return menuName;
	}

	////////////////Determine Selected menu
	private void getSelectedMenu(String menuNameString, String buttonLabel){
		String bl = buttonLabel.toLowerCase(); // converts to lower case
		if (menuNameString.equals("Game")){
			getSelectedFileOption(bl); // for file menu
		}

		else if (menuNameString.equals("Buy")){

		}

		else if (menuNameString.equals("Edit")){
			getSelectedEditOption(bl); // for edit menu
		}
	}

	////////////////Create new button actions below above
	private void getSelectedFileOption(String bl){
		if (bl.equals("open")){
			//Do Something
		}
		else if(bl.equals("exit")){
			mwf.backToMainMenu();
		}
	}

	private void getSelectedEditOption(String bl){
		if (bl.equals("settings")){
			//sfm.setVisible(true); // shows settings window
		}
	}

	private void setMenuActionListener(JMenu theMenu){
		theMenu.addMenuListener(new MenuListener(){

			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				mmf.resumeGame();
			
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				mmf.pauseGame();
			}
			
		});
	}
	
	public void setMenuPaneText(String bookName, String type, boolean firstRun){
		if(manuscriptName.getText().equals("No Production") && firstRun == false){
			manuscriptName.setText(bookName + " (" +  type + ")");
		}else{
			manuscriptName.setText("No Production");
			
		}
	}
	
	
}
