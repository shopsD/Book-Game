package gameGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import gameController.DataController;
import gameController.MouseListen;
import gameController.Product;

public class MainGamePanel extends JPanel implements Runnable{
	// instantiated by MainWindowFrame
	private DataController dc;
	private MainMenuScreen mmf;
	private UpperDisplayPanel udp;
	private NewProductionFrame npf;
	private Product pdc;
	
	private boolean visible = false;
	private MouseEvent e;
	private JPopupMenu newMenu;
	
	private JMenuItem writeManuscript;
	private JMenuItem writeSequel;
	private JMenuItem findContract;
	private JMenuItem findPubDeal;
	private JMenuItem createPubDeal;
	private JMenuItem convertWork;
	private JMenuItem train;
	
	private static final int THREAD_SLEEP_TIME_PAUSE = 100;
	public MainGamePanel(DataController dc, MainMenuScreen mmf, UpperDisplayPanel udp) {
		this.dc = dc;
		this.mmf = mmf;
		this.udp = udp;
		dc.setUpperDisplayPanel(udp);
		npf = new NewProductionFrame(this, dc, udp);
		MouseListener ml = new MouseListen(this);//adds mouse listener to this
		this.addMouseListener(ml);
		// creates popup menu 
		JPopupMenu newMenu = new JPopupMenu();
		this.newMenu = newMenu;
		createGameOptionMenu(); 
		//-----------------------
		npf.createGUI();
	}

	public void checkButtonClicked(){
		if(e.getButton()==e.BUTTON1){
			//LEFT CLICKED
		}
		else if (e.getButton() == e.BUTTON3){
			//RIGHT CLICKED
			showGameOptionMenu();
		}
	}
	
	public void setMouseEvent (MouseEvent e){
		this.e = e;
	}
	
	private void showGameOptionMenu(){
		newMenu.show(e.getComponent(), e.getX(), e.getY()); // open menu at mouse coordinates
	}
	
	private void createGameOptionMenu(){
		
		//adds options to the popup menu
		createMenuItem("Write Manuscript", newMenu);
		createMenuItem("Write Sequel", newMenu);
		createMenuItem("Find Contract", newMenu);
		createMenuItem("Find Publishing Deal", newMenu);
		createMenuItem("Create Publishing Deal", newMenu);
		createMenuItem("Convert Past Work", newMenu);
		createMenuItem("Train", newMenu);
	
	}

	private JPopupMenu createMenuItem (final String buttonLabel, final JPopupMenu newMenu){
		final JMenuItem menuItemOption = new JMenuItem(buttonLabel); // creates new menu item
		
		menuItemOption.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
			
				//determine which menu is selected
				getSelectedMenu(buttonLabel);
			}	
		});
		setMenuItemVisibility(menuItemOption);
		newMenu.add(menuItemOption); // add option to menu
		//assign main variables correctly
		if(buttonLabel.equalsIgnoreCase("write manuscript")){
			writeManuscript = menuItemOption;
		}else if(buttonLabel.equalsIgnoreCase("write sequel")){
			writeSequel = menuItemOption;
		}
		else if(buttonLabel.equalsIgnoreCase("find contract")){
			findContract = menuItemOption;
		}
		else if(buttonLabel.equalsIgnoreCase("find publishing deal")){
			findPubDeal = menuItemOption;
		}
		else if(buttonLabel.equalsIgnoreCase("create publishing deal")){
			createPubDeal	= menuItemOption;
		}
		else if(buttonLabel.equalsIgnoreCase("convert past work")){
			convertWork= menuItemOption;
		}
		else if(buttonLabel.equalsIgnoreCase("train")){
			train = menuItemOption;
		}
			   
		return newMenu;
	}

	private void setMenuItemVisibility(JMenuItem menuItemOption){
		String mIO = menuItemOption.getText();
		String menuItemOptionString = mIO.toLowerCase();
		if(menuItemOptionString.equals("create publishing deal")){
			menuItemOption.setVisible(visible);
		}
	}
	
	private void getSelectedMenu(String buttonLabel){
		//used to determine which option was selected
		String bl = buttonLabel.toLowerCase(); // converts to lower case
		try{
			if (bl.equals("write manuscript")){
				npf.showWindow();
			}
			else if (bl.equals("write sequel")){
				npf.setIsSequel(true);
				
				npf.showWindow();
			}
			else if (bl.equals("find contract")){

			}
			else if (bl.equals("find publishing deal")){

			}
			else if (bl.equals("create publishing deal")){

			}
			else if (bl.equals("convert past work")){

			}
			else if (bl.equals("train")){

			}
		}catch(NullPointerException npe){

		}
	}

	private void createSubOption(String optionName, JMenu subMenuName, JMenu mainMenuName) {
		// TODO Auto-generated method stub

	}
	
	public void setOptionVisibility(boolean show){
		//hides menu options when manuscript being created
		writeManuscript.setVisible(show);
		writeSequel.setVisible(show);
		findContract.setVisible(show);
		findPubDeal.setVisible(show);
		convertWork.setVisible(show);
	}
	
	public MainMenuScreen getMainMenuFrame(){
		return mmf;
	}
		
	@Override
	public void run() {
		////-------PAUSE GAME
		while(mmf.gamePaused()){
			try {
				Thread.sleep(THREAD_SLEEP_TIME_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		////-------PAUSE GAME
	}



}
