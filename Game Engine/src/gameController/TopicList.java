package gameController;

public enum TopicList {
	//TOPIC(name,fiction/nonfiction)
	
	
	ESPIONAGE("Espionage"), SPACE("Space"), NOIRE("Noire"), MYSTERY("Mystery"), MURDER("Murder"), MUSICAL("Musical"),
	HEIST("Heist"), ADVENTURE("Adventure"), FANTASY("Fantasy"), MEDIEVAL("Medieval"), HISTORY("History"), NEARFUTURE("Near Future"),
	CYBERPUNK("Cyberpunk"), MILITARY("Military"), CARS("Cars"), RACING("Racing"), AIRCRAFT("Aircraft"), TRAINS("Trains"),
	HIJACK("Hijack"), WAR("War"), VIDEOGAMES("Video Games"), MONSTERS("Monsters"),  DINOSAURS("Dinosaurs"), FAIRIES("Fairies"),
	DANCING("Dancing"), RELIGION("Religion"), TECHNOLOGY("Technology"), NUMBERS("Numbers"), SCHOOL("School"), APOCALYPSE("Apocalypse"),
	POSTAPOCALYPSE("Post Apocalypse"), FARFUTURE("Far Future");
	
	
	private TopicList(String topic){
		this.topic = topic;
	//	this.type = type;
	}
	
	private String topic;
	//private String type;
	
	public String getTopic(){
		return topic;
	}
}
