package gameController;

import gameEntry.SettingsVariablesStore;
import gameGUI.MainGamePanel;
import gameGUI.MainMenuFrame;
import gameGUI.ManuscriptCompleteFrame;
import gameGUI.NewProductionFrame;
import gameGUI.UpperDisplayPanel;

public class DataController implements Runnable{

	private Character playerCharacter;
	private Character employee;
	

	private ManuscriptCreator mc;
	private MainMenuFrame mmf;
	private PointControlClass pcc;
	private UpperDisplayPanel udp;
	private Product product;
	private ManuscriptCompleteFrame mcf;
	//character data
	private String playerName;
	private int technique;
	private int aptitude;
	private int reasoning;
	private int grammar;
	private int charLevel;
	
	private int level =1;
	
	private Thread pointControlClassThread;

	private volatile boolean creatingManuscript = false; // needs to be set to true at some point
	private volatile boolean pccThreadInterrupted;
	
	private NewProductionFrame npf;
	private MainGamePanel mgp;
	
	private static final int THREAD_SLEEP_TIME = 100;
	
	public DataController(SettingsVariablesStore svs, MainMenuFrame mainMenuFrame) {
		//instantiated by MainMenuFrame
		mmf = mainMenuFrame;
		PointControlClass pcc = new PointControlClass(this, mmf);
		Product product = new Product();
		this.product = product;
		ManuscriptCompleteFrame mcf = new ManuscriptCompleteFrame(mmf, product, pcc);
		this.mcf = mcf;
		this.pcc = pcc;		
		
	}
	
	public void createProductArrays(){
		product.createNewArrays();
	}
	
	public void fireEmployee(Character employee){
		product.fireEmployee(employee);
	}
	
	public void startPointControlThread(){
		Thread pointControlClassThread = new Thread(pcc);
		this.pointControlClassThread = pointControlClassThread;
		pointControlClassThread.start();
	}

	public void setManuscript(ManuscriptCreator mc){
		this.mc = mc;
		mcf.setManuscript(mc);
		//prc.setManuscript(mc);
	}

	public ManuscriptCreator getManuscript(){
		return mc;
	}
	
	public void setUpperDisplayPanel (UpperDisplayPanel udp){
		this.udp = udp;
	}
	
	public void setPlayerName(String playerName){
		this.playerName = playerName;
	}

	public void setPlayerTechnique(int techniquePlayer){
		this.technique = techniquePlayer;
	}

	public void setPlayerAptitude(int aptitudePlayer){
		aptitude = aptitudePlayer;
	}

	public void setPlayerReasoning(int reasoningPlayer){
		reasoning = reasoningPlayer;
	}

	public void setPlayerGrammar(int grammarPlayer){
		grammar = grammarPlayer;
	}

	public void setPlayerLevel(int levelPlayer){
		charLevel = levelPlayer;
	}
	
	public void createPlayer(){
		playerCharacter = new Character(playerName, technique, aptitude,reasoning,grammar, charLevel, pcc, mmf, this);
//		playerCharacterThread = new Thread(playerCharacter);
//		playerCharacterThread.start();
	}

	private void generateCharacters(){
		employee = new Character(null, 0, 0, 0, 0,1,pcc, mmf, this);
//		genCharacterThread = new Thread(employee);
//		genCharacterThread.start();
	}

	public void finishBook(){
		//save book points
		mc.setCreativity(pcc.getCreativity());
		mc.setSuspense(pcc.getSuspense());
		mc.setIntrigue(pcc.getIntrigue());
		mc.setDesign(pcc.getDesign());
		mc.setAccuracy(pcc.getAccuracy());
		mc.setRelevancy(pcc.getRelevancy());
		mc.setGrammar(pcc.getGrammar());
		//reset book points
		//pcc.resetPoints();
		udp.setMenuPaneText("No Production", "", true);//update upper display panel text
		creatingManuscript = false; //manuscript finished
		mgp.setOptionVisibility(true);
		mcf.showWindow();//show final product window
		
		//done elsewhere
		//		while(creatingBook == false){
		//			//wait for next book to be created
		//			try {
		//				pointControlClassThread.sleep(100);
		//			} catch (InterruptedException e) {
		//
		//			}
		//		}
	}

	public void saveManuscript(){
		product.saveManuscript(mc);
	}

	public void runGame(){//Currently not used
		while(creatingManuscript){

		}
	}

	public void startmanuscript(){
		creatingManuscript = true;
		//pointControlClassThread.start();

	}

	public void stopManuscript(){
		creatingManuscript = false;
	}

	public boolean isCreatingBook(){
		return creatingManuscript;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void increaseLevel(){
		level++;
	}

	
//	public void exitGame(){
//		pcc.resetPoints();
//		//pcc.resetAndExitGame();//for quitting game/////deprecated
////		while (mmf.getProgramRunning()){//keeps thread running for restart
////			while (mmf.gameRunning() == false){//allows thread to be restarted
////				try {
////					pointControlClassThread.sleep(100);
////				} catch (InterruptedException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////			}
////		}
//	}
	
	public void setNewProductionFrame(NewProductionFrame npf){
		this.npf = npf;//passing window to this for dynamically hiding menu options
		npf.setProduct(product);
	}
	
	public void setMainGamePanel (MainGamePanel mgp){
		this.mgp = mgp;
		pcc.setMainGamePanel(mgp);
	}
	
	@Override
	public void run() {
		while (mmf.getMainWindowFrameOpened()){
			////-------PAUSE GAME
			while(mmf.gamePaused()){
				try {
					Thread.sleep(THREAD_SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			////-------PAUSE GAME
			synchronized(pcc){
				if(creatingManuscript == false){//while nothing is created pcc thread should sleep;
					try {

						pointControlClassThread.sleep(THREAD_SLEEP_TIME);
						pccThreadInterrupted = pointControlClassThread.isInterrupted();
						if(pccThreadInterrupted = true){
							//pointControlClassThread.join();
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			//			if(beginBook){//if book is being created pcc thread should start
			//				creatingBook = true;
			//			}

		}
		destroyCharacters();
	}
	
	private void destroyCharacters(){
		//for removing from memory
		for (int i = 0; i < product.getEmployeeVectorSize(); i++){
			employee = product.getEmployee(i);
			employee.fireEmployee();
			employee.endThread();
			employee = null;
		}
		playerCharacter.fireEmployee();
		playerCharacter.endThread();
		playerCharacter = null;
	}
}
