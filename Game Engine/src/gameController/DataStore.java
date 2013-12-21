package gameController;

import java.util.ArrayList;

public class DataStore {
	
	ArrayList<ArrayList<String>> bookArray = new ArrayList<ArrayList<String>>();
	
	private int intrigue;
	private int creativity;
	private int suspense;
	
	public void setSuspense(int suspense){
		this.suspense = suspense;
	}
	
	public int getSuspense(){
		return suspense;
	}
	
	public void setIntrigue(int intrigue){
		this.intrigue = intrigue;
	}
	
	public int getIntrigue(){
		return intrigue;
	}
	
	public void setCreativity(int creativity){
		this.creativity = creativity;
	}
	
	public int getCreativity(){
		return creativity;
	}
}	
