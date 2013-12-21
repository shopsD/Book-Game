package gameController;

import java.util.Random;

import gameGUI.MainMenuFrame;

public class PostReleaseController implements Runnable{
	//Instantiated by ManuscriptCreator
	//used to control sales 
	private MainMenuFrame mmf;
	private ManuscriptCreator mc;
	private DataController dc;

	private int criticOne = 0;
	private int criticTwo = 0;
	private int criticThree = 0;
	private int criticFour = 0;

	private int fanOne = 0;
	private int fanTwo = 0;
	private int fanThree = 0;
	private int fanFour = 0;
	private int fanFive = 0;
	private int fanSix = 0;

	private int scoreOne;
	private int scoreTwo;
	private int scoreThree;
	private int scoreFour;

	private int finalScore;

	private Random scoreRandom = new Random();

	private int saleTime; // time for a sale to run. Based on critic score
	private int saleModifier =1; // used to generate random sale amount. based on fan score
	private int eventModifier = 1; // for use with saleModifier only. Used to change sales from events

	private static final int SALE_TIME_MODIFIER = 20;
	private static final int SCORE_MODIFIER = 200;
	private static final int THREAD_SLEEP_TIME_PAUSE = 100;
	private static final int SCORE_THREAD_SLEEP_TIME = 1000;
	private static final int MIN_POINT_SCORE = 4;

	public PostReleaseController(MainMenuFrame mmf, ManuscriptCreator mc, DataController dc) {
		this.mc = mc;
		this.mmf = mmf;
		this.dc = dc;
	}

	@Override
	public void run() {
		while(mmf.getMainWindowFrameOpened()){
			if(mc == null){
				try {
					Thread.sleep(THREAD_SLEEP_TIME_PAUSE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				synchronized(mc){
					if(mc.getIsOnSale()){
						scoreManuscript();

						for (int i = 0; i < saleTime; i++){
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

							

						// sets sale time and rough number of sales and price based on reviews. 
							saleModifier = scoreRandom.nextInt((scoreRandom.nextInt(mc.getFanScore())+1)*(scoreRandom.nextInt(20)+1))+1;
		
							while(saleTime-saleModifier <= 0){
								//prevent negative randoms
								saleModifier = scoreRandom.nextInt((scoreRandom.nextInt(mc.getFanScore())+1)*(scoreRandom.nextInt(20)+1))+1;
							}
							//reduces sale amount over time
								saleModifier = (scoreRandom.nextInt(saleTime - saleModifier) + 1)*eventModifier;
							
								mc.setSaleCount((scoreRandom.nextInt(10) * saleModifier) +mc.getSaleCount());
								System.out.println(mc.getManuscriptName() + " "+mc.getSaleCount() + " sales. PostReleaseController. Run method");	
								
							if(mmf.getMainWindowFrameOpened() == false){
								// for exiting the game
								mc.setIsOnSale(false);
								break;
							}
							try {
								Thread.sleep(SCORE_THREAD_SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						//saves income and removes book from sale
						mc.setIsOnSale(false);
						mc.setIncome((scoreRandom.nextInt(mc.getCriticScore())+2)*mc.getSaleCount()); // sets price based on critic score
						System.out.println("Total income: £" + mc.getIncome());
						break;
					}

				}
			}
		}
		mc.removePostReleaseController();
	}

	private void scoreManuscript(){
		if(mc.getPurpose().equalsIgnoreCase("education")){
			educationScore();
			System.out.println("Scoring Education Manuscript. PostReleaseController. scoreManuscript Method");
		}
		else{
			entertainmentScore();
			System.out.println("Scoring Entertainment Manuscript. PostReleaseController. scoreManuscript Method");
		}
		mc.setCriticScore((criticOne + criticTwo+ criticThree + criticFour)/4);
		mc.setFanScore((fanOne + fanTwo + fanThree + fanFour + fanFive + fanSix)/6);
		System.out.println(mc.getCriticScore() + " " + mc.getFanScore());
		setSaleTime();

	}

	private void educationScore(){

		if(mc.getAccuracy() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreOne = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getRelevancy() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreTwo = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getGrammar() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreThree = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getDesign() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreFour = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}	
		finalScore = scoreOne + scoreTwo + scoreThree + scoreFour;

		criticOne = scoreRandom.nextInt(finalScore)+1;
		criticTwo = scoreRandom.nextInt(finalScore)+1;
		criticThree = scoreRandom.nextInt(finalScore)+1;
		criticFour = scoreRandom.nextInt(finalScore)+1;

		fanOne = scoreRandom.nextInt(finalScore)+1;
		fanTwo = scoreRandom.nextInt(finalScore)+1;
		fanThree = scoreRandom.nextInt(finalScore)+1;
		fanFour = scoreRandom.nextInt(finalScore)+1;
		fanFive = scoreRandom.nextInt(finalScore)+1;
		fanSix = scoreRandom.nextInt(finalScore)+1;
	}

	private void entertainmentScore(){
		//gets score by averaging score based on points 
		if(mc.getSuspense() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreOne = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getIntrigue() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreTwo = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getGrammar() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreThree = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}
		if(mc.getCreativity() <= (SCORE_MODIFIER * dc.getLevel())){
			scoreFour = scoreRandom.nextInt(MIN_POINT_SCORE)+1;
		}	
		finalScore = (scoreOne + scoreTwo + scoreThree + scoreFour)/4;
		
		//gets random critic scores
		criticOne = scoreRandom.nextInt(finalScore)+1;
		criticTwo = scoreRandom.nextInt(finalScore)+1;
		criticThree = scoreRandom.nextInt(finalScore)+1;
		criticFour = scoreRandom.nextInt(finalScore)+1;
		// gets random fan scores
		fanOne = scoreRandom.nextInt(finalScore)+1;
		fanTwo = scoreRandom.nextInt(finalScore)+1;
		fanThree = scoreRandom.nextInt(finalScore)+1;
		fanFour = scoreRandom.nextInt(finalScore)+1;
		fanFive = scoreRandom.nextInt(finalScore)+1;
		fanSix = scoreRandom.nextInt(finalScore)+1;
	}

	private void setSaleTime(){
		saleTime = scoreRandom.nextInt(mc.getCriticScore() * dc.getLevel() * SALE_TIME_MODIFIER)+1;
		if(mc.getFanScore() > 7){
			saleModifier = scoreRandom.nextInt(mc.getFanScore()) +7;
		}
		if(mc.getFanScore() < 3){
			saleModifier = scoreRandom.nextInt(mc.getFanScore()) -3;
		}
	}

}
