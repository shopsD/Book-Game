package gameController;

public enum GenreList {
	
	COMEDY("Comedy"), SCIFI("Sci Fi"), HORROR("Horror"), KIDS("Kids"), ACTION("Action"), DRAMA("Drama"),
	THRILLER("Thriller"), ROMANCE("Romance");
	
	GenreList(String genreName){
		this.genreName = genreName;
	}
	private String genreName;
	
	public String getGenreName(){
		return genreName;
	}
}
