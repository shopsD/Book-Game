package gameController;

import gameGUI.MainMenuFrame;

public class ManuscriptCreator {
	//this class serves as individual manuscripts
	private String name; // book name
	private String type; // magazine/novel/text book e.t.c
	private String genre; // book genre
	private String genreTwo;
	private String audience; //target audience
	private String fictionType; // fiction or non fiction
	private String topic; // book topic
	private String topicTwo;
	private String topicThree;
	private String prequelName; // magazine/website name for articles or previous book name
	private String purpose; // education vs entertainment
	
	private ManuscriptCreator prequel;
	
	private boolean isPrequel;
	
	//points for each manuscript
	//entertainment only
	private int creativity;
	private int suspense;
	private int intrigue;
	//education only
	private int design;
	private int accuracy;
	private int relevancy;
	//all manuscripts
	private int grammar;
	//for release and sales
	private boolean isOnSale = false;
	private int fanCount =0;
	private int criticScore=0;
	private int fanScore =0;
	private int saleCount = 0;
	private int income = 0;
	
	private Thread postReleaseThread;
	private PostReleaseController prc;
	public ManuscriptCreator(String name, String type, String genre, String genreTwo, String audience, String fictionType, String topic, String topicTwo, String topicThree, String prequelName, String purpose, ManuscriptCreator prequel, MainMenuFrame mmf, DataController dc){
		this.name = name;
		this.type = type;
		this.genre = genre;
		this.genreTwo = genreTwo;
		this.audience = audience;
		this.fictionType = fictionType;
		this.topic = topic;
		this.topicTwo = topicTwo;
		this.topicThree = topicThree;
		this.prequelName = prequelName;
		this.purpose = purpose;
		this.prequel = prequel;
		
		PostReleaseController prc = new PostReleaseController(mmf, this, dc);
		Thread postReleaseThread = new Thread(prc);
		this.postReleaseThread = postReleaseThread;
		this.prc = prc;
	}

	public void startRelease(){
		postReleaseThread.start();
	}
	
	
	//setters
	public void setCreativity(int creativity){
		this.creativity = creativity;
	}

	public void setSuspense(int suspense){
		this.suspense = suspense;
	}

	public void setIntrigue(int intrigue){
		this.intrigue = intrigue;
	}

	public void setDesign(int design){
		this.design = design;
	}

	public void setAccuracy(int accuracy){
		this.accuracy = accuracy;
	}

	public void setRelevancy(int relevancy){
		this.relevancy = relevancy;
	}

	public void setGrammar(int grammar){
		this.grammar = grammar;
	}
	
	public void setIsOnSale(boolean isOnSale){
		this.isOnSale = isOnSale;
	}
	
	public void setFanCount(int fanCount){
		this.fanCount = fanCount;
	}
	
	public void setCriticScore(int criticScore){
		this.criticScore = criticScore;
	}
	
	public void setFanScore(int fanScore){
		this.fanScore = fanScore;
	}
	
	public void setSaleCount(int saleCount){
		this.saleCount = saleCount;
	}
	
	public void setIncome(int income){
		this.income = income;
	}

	public void setManuscriptPrequel(ManuscriptCreator prequel){
		this.prequel = prequel;
	}

	public void setIsPrequel(boolean isPrequel){
		this.isPrequel = true;
	}
	
	//getters
	public String getManuscriptName(){
		return name;
	}

	public String getType(){
		return type;
	}

	public String getGenre(){
		return genre;
	}
	
	public String getGenreTwo(){
		return genreTwo;
	}

	public String getAudience(){
		return audience;
	}

	public String getFiction(){
		return fictionType;
	}

	public String getTopic(){
		return topic;
	}
	
	public String getTopicTwo(){
		return topicTwo;
	}
	
	public String getTopicThree(){
		return topicThree;
	}

	public String getPrequelName(){
		return prequelName;
	}

	public String getPurpose(){
		return purpose;
	}

	public int getCreativity(){
		return creativity;
	}
	
	public int getSuspense(){
		return suspense;
	}
	
	public int getIntrigue(){
		return intrigue;
	}
	
	public int getDesign(){
		return design;
	}
	
	public int getAccuracy(){
		return accuracy;
	}
	
	public int getRelevancy(){
		return relevancy;
	}
	
	public int getGrammar(){
		return grammar;
	}

	public boolean getIsOnSale(){
		return isOnSale;
	}

	public int getFanCount(){
		return fanCount;
	}
	
	public int getCriticScore(){
		return criticScore;
	}
	
	public int getFanScore(){
		return fanScore;
	}
	
	public int getSaleCount(){
		return saleCount;
	}
	
	public int getIncome(){
		return income;
	}
	
	public ManuscriptCreator getPrequel(){
		return prequel;
	}
	
	public boolean getIsPrequel(){
		return isPrequel;
	}
	
	public ManuscriptCreator saveData(){
		return this;
	}
	
	public void removePostReleaseController(){
		prc = null;
		postReleaseThread = null;
	}

}
