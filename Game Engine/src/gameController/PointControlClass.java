package gameController;

import java.util.Random;

import gameGUI.MainGamePanel;
import gameGUI.MainMenuFrame;

public class PointControlClass implements Runnable{
	//class adds variables which are then added to the book and saved;
	private volatile int creativity = 1;
	private volatile int suspense = 1;
	private volatile int intrigue = 1;
	private volatile int design = 1;
	private volatile int accuracy = 1;
	private volatile int relevancy = 1;
	private volatile int grammar = 1; //shared by type


	private volatile int percentage = 0;
	private volatile int progressSpeed = 3000; // time taken to complete work

	private Thread percComplete = new Thread();
	private DataController dc;
	private MainMenuFrame mmf;
	private MainGamePanel mgp;

	private static final int THREAD_SLEEP_TIME_PAUSE = 100;
	
	public PointControlClass(DataController dataController, MainMenuFrame mmf) {
		dc = dataController;
		this.mmf = mmf;
	}

	public int getCreativity() {
		return creativity;
	}

	public int getSuspense(){
		return suspense;
	}	

	public int getIntrigue() {
		return intrigue;
	}

	public int getDesign() {
		return design;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getRelevancy() {
		return relevancy;
	}

	public int getGrammar() {
		return grammar;
	}

	public void addCreativity(int creativityPoint){
		creativity = creativity + creativityPoint;
	}

	public void addSuspense(int suspensePoint){
		suspense = suspense + suspensePoint;
	}

	public void addIntrigue(int intriguePoint){
		intrigue = intrigue + intriguePoint;
	}

	public void addDesign(int designPoint) {
		design = design + designPoint;
	}

	public void addAccuracy(int accuracyPoint) {
		accuracy = accuracy + accuracyPoint;
	}


	public void addRelevancy(int relevancyPoint) {
		relevancy = relevancy + relevancyPoint;
	}


	public void addGrammar(int grammarPoint) {
		grammar = grammar + grammarPoint;
	}



	public void resetPoints(){
		//sets points to default values
//		creativity = 1;
//		suspense = 1;
//		intrigue = 1;
//		design = 1;
//		accuracy = 1;
//		relevancy = 1;
//		grammar = 1;
		progressSpeed = 3000;
		mgp.setOptionVisibility(true);
		dc.stopManuscript();
	}

	private void completionTimer(){
		percComplete.run();
		for (percentage =0; percentage < 20; percentage++){
			if(mmf.getMainWindowFrameOpened() == false){
				//stops manuscript when game quit
				resetPoints();
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
			System.out.println("creativity "+ creativity + " suspense " + suspense + 
					" ingtrigue "+ intrigue + " design " + design + 
					" accuracy "+ accuracy + " relevancy " + relevancy + 
					" grammar "+ grammar +". PointControlClass. completionTimer Method");
			
			try {
				percComplete.sleep(progressSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(mmf.getMainWindowFrameOpened()){
			dc.finishBook();
		}
	}

	public void setMainGamePanel(MainGamePanel mgp){
		this.mgp = mgp;
	}


	@Override
	public void run() {
		while (mmf.getMainWindowFrameOpened()){//keeps thread running
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
			if(dc.isCreatingBook()){
				if(mmf.getMainWindowFrameOpened() == false){
					break;
				}
				creativity = dc.getManuscript().getCreativity();
				suspense = dc.getManuscript().getSuspense();
				intrigue = dc.getManuscript().getIntrigue();
				design = dc.getManuscript().getDesign();
				accuracy = dc.getManuscript().getAccuracy();
				relevancy = dc.getManuscript().getRelevancy();
				grammar = dc.getManuscript().getGrammar();
				mgp.setOptionVisibility(false);
				completionTimer();//starts when manuscript is being written
			}
			//			if (dc.isCreatingBook() == false){
			//				//pauses thread until next manuscript is written
			//				try {
			//					percComplete.sleep(10);
			//				} catch (InterruptedException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}
		}

	}


}
