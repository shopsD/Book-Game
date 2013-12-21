package gameController;

import java.util.Random;

import gameGUI.MainMenuFrame;

public class Character implements Runnable{
	private String characterName;
	///-----Used for skill upgrades
	private volatile int technique; // modifies creativity/design
	private volatile int aptitude; // modifies suspense/ accuracy
	private volatile int reasoning; // modifies intrigue / relevancy
	private volatile int grammar; // modifies grammar
	private volatile int level;

	private volatile int restTime = 2000;

	private volatile boolean isEmployed = true;
	
	private static final int THREAD_SLEEP_TIME_PAUSE = 100;
	private static final int RANDPOINT_MAX_VALUE = 9;
	
	
	
	private int randPoint;
	private int randSwitch;
	private int []randomSkill;


	private DataController dc;
	private PointControlClass pcc;
	private MainMenuFrame mmf;
	private ManuscriptCreator mc;

	private Random randomOption = new Random();

	private Thread characterThread;
	
	Character(String characterName, int technique, int aptitude, int reasoning, int grammar, int level, PointControlClass pcc, MainMenuFrame mmf, DataController dataController){
		this.characterName = characterName;
		this.technique = technique;
		this.aptitude = aptitude;
		this.reasoning = reasoning;
		this.grammar = grammar;
		this.level = level;

		this.pcc = pcc;
		dc = dataController;
		this.mmf = mmf;

		int [] randomSkill = new int []{technique, aptitude, reasoning, grammar};
		this.randomSkill = randomSkill;
		characterThread = new Thread(this);
		characterThread.start();
	}

	private void selectRandomPoint(){
		randSwitch = (randomOption.nextInt(randomSkill.length))+1;
		randPoint = ((randomOption.nextInt(RANDPOINT_MAX_VALUE)) +1) * level;
		//adding random points to book based on particular character skill
		if (mc.getPurpose().equalsIgnoreCase("entertainment")){
			switch(randSwitch){
			case 1:
				pcc.addCreativity(randomOption.nextInt(randPoint * technique)+1);
				break;
			case 2:
				pcc.addSuspense(randomOption.nextInt(randPoint * aptitude)+1);
				break;
			case 3:
				pcc.addIntrigue(randomOption.nextInt(randPoint * reasoning)+1);
				break;
			case 4:
				pcc.addGrammar(randomOption.nextInt(randPoint * grammar)+1);
				break;
			default:
				selectRandomPoint();
			}
		}
		else if (mc.getPurpose().equalsIgnoreCase("education")){
			switch(randSwitch){
			case 1:
				pcc.addDesign(randomOption.nextInt(randPoint * technique)+1);
				break;
			case 2:
				pcc.addAccuracy(randomOption.nextInt(randPoint * aptitude)+1);
				break;
			case 3:
				pcc.addRelevancy(randomOption.nextInt(randPoint * reasoning)+1);
				break;
			case 4:
				pcc.addGrammar(randomOption.nextInt(randPoint * grammar)+1);
				break;
			default:
				selectRandomPoint();
			}
		}
	}
	
	@Override
	public void run() {
		while(mmf.getMainWindowFrameOpened()){
			if(isEmployed == false){
				//increase free position
				//remove from array of employees
				break;
			}
			////-------PAUSE GAME
			while(mmf.gamePaused()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			////-------PAUSE GAME
			while (dc.isCreatingBook()){
				if(isEmployed == false){
					break;
				}
				////-------PAUSE GAME
				while(mmf.gamePaused()){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				////-------PAUSE GAME
				if(mc == null){
					mc = dc.getManuscript(); // set manuscript
				}
				if( mmf.getMainWindowFrameOpened() == false){
					break; // destroy character if game is quit
				}
				synchronized (pcc) {
					selectRandomPoint();
					try {
						Thread.sleep(restTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			mc = null;//empty manuscript
			if(dc.isCreatingBook() == false){
				if(isEmployed == false){
					break;
				}
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
				setAction();

				try {
					Thread.sleep(restTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void fireEmployee(){
		isEmployed = false;
	}
	
	private void setAction(){

	}
	
	public void endThread(){
		characterThread = null;
	}
	
}
