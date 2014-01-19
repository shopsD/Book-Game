package gameEntry;

public class SettingsVariablesStore {
	//class is for storing variables
	private static int resWidth;
	private static int resHeight;
	
	private static boolean fullScreen;
	private static boolean vSync;
	
	private static float soundVolume;
	private static float musicVolume;
	private static boolean soundOn;
	private static boolean musicOn;
	public void setResolution(int resWidth, int resHeight){
		SettingsVariablesStore.resWidth = resWidth;
		SettingsVariablesStore.resHeight = resHeight;
	}
	
	public void setSoundVolume(float soundVolume){
		SettingsVariablesStore.soundVolume = soundVolume;
	}
	
	public void setMusicVolume(float musicVolume){
		SettingsVariablesStore.musicVolume = musicVolume;
	}
	
	public void setVsync(boolean vSync){
		SettingsVariablesStore.vSync = vSync;
	}
	
	public void setFullScreen(boolean fullScreen){
		SettingsVariablesStore.fullScreen = fullScreen;
	}
	
	public void setSoundOn(boolean soundOn){
		SettingsVariablesStore.soundOn = soundOn;
	}
	
	public void setMusicOn(boolean musicOn){
		SettingsVariablesStore.musicOn = musicOn;
	}
	
	public int getResWidth() {
		return resWidth;
	}

	public int getResHeight() {
		return resHeight;
	}

	public float getSoundVolume(){
		return soundVolume;
	}
	
	public float getMusicVolume(){
		return musicVolume;
	}
	
	public boolean getFullScreen() {
		return fullScreen;
	}

	public boolean getVsync() {
		return vSync;
	}
	
	public boolean getMusicOn(){
		return musicOn;
	}
	
	public boolean getSoundOn(){
		return soundOn;
	}
}
