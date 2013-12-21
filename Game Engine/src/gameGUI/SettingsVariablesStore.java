package gameGUI;

import java.awt.Dimension;

public class SettingsVariablesStore {
	//class is for storing variables
	
	private volatile Dimension mainDimension; // main window size
	private volatile Dimension settingsDimension; // settings window size
	
	
	public void setMainDimension(int width, int height){
		Dimension mainDimension = new Dimension();
		mainDimension.setSize(width, height); // sets main window size
		this.mainDimension = mainDimension;
	}
	
	public void setSettingsDimension(int width, int height){
		Dimension settingsDimension = new Dimension();
		settingsDimension.setSize(width, height); // sets settings window size
		this.settingsDimension = settingsDimension;
		
	}
	
	public Dimension getSettingsDimension(){
		return settingsDimension;
	}
	
	public Dimension getMainDimension(){
		return mainDimension;
	}
}
