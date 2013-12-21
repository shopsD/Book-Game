package gameController;

public enum AudienceList {
	YOUNG("Young"), TEEN("Teen"), ADULT("Adult"), ELDERLY("Elderly"), EVERYONE("Everyone");
	
	AudienceList(String audienceName){
		this.audienceName = audienceName;
	}
	
	private String audienceName;
	
	public String getAudienceName(){
		return audienceName;
	}
}
