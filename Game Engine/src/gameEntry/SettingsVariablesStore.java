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
	
	/**
	 * Stores the current resolution of the game
	 * @param resWidth the width of pixels on screen
	 * @param resHeight the height of the pixels on screen
	 */
	public void setResolution(int resWidth, int resHeight){
		SettingsVariablesStore.resWidth = resWidth;
		SettingsVariablesStore.resHeight = resHeight;
	}
	
	/**
	 * Sets the volume of game sounds between 0 and 1
	 * @param soundVolume The volume of in game sounds
	 */
	public void setSoundVolume(float soundVolume){
		SettingsVariablesStore.soundVolume = soundVolume;
	}
	
	/**
	 * Sets the volume of music in the game between 0 and 1
	 * @param musicVolume The volume of music in the game
	 */
	public void setMusicVolume(float musicVolume){
		SettingsVariablesStore.musicVolume = musicVolume;
	}
	
	/**
	 * Turns vsync on and off
	 * @param vSync Set to True to enable vsync. Set to False to disable vsync
	 */
	public void setVsync(boolean vSync){
		SettingsVariablesStore.vSync = vSync;
	}
	
	/**
	 * Turns full screen on and off
	 * @param fullScreen Set to True to enable full screen. False to disable full screen
	 */
	public void setFullScreen(boolean fullScreen){
		SettingsVariablesStore.fullScreen = fullScreen;
	}
	
	/**
	 * Turns in game sound on and off
	 * @param soundOn Set to True to enable in game sound. False to disable in game sound
	 */
	public void setSoundOn(boolean soundOn){
		SettingsVariablesStore.soundOn = soundOn;
	}
	
	/**
	 * Turns in game music on and off
	 * @param musicOn Set to True to enable music. False to disable music
	 */
	public void setMusicOn(boolean musicOn){
		SettingsVariablesStore.musicOn = musicOn;
	}
	
	/**
	 * Gets the width of pixels on the screen
	 * @return The width of pixels on the screen
	 */
	public int getResWidth() {
		return resWidth;
	}
	/**
	 * Gets the height of pixels on the screen
	 * @return The height of pixels on the screen
	 */
	public int getResHeight() {
		return resHeight;
	}
	
	/**
	 * Gets the current volume of in game sounds
	 * @return The volume of sound in game
	 */
	public float getSoundVolume(){
		return soundVolume;
	}
	
	/**
	 * Gets the current volume of music
	 * @return The volume of music
	 */
	public float getMusicVolume(){
		return musicVolume;
	}
	
	/**
	 * Gets full screen enabled
	 * @return True if full screen is enabled, otherwise False
	 */
	public boolean getFullScreen() {
		return fullScreen;
	}
	/**
	 * Gets vsync enabled
	 * @return True if vsync is enabled, otherwise False
	 */
	public boolean getVsync() {
		return vSync;
	}
	
	/**
	 * Gets music enabled
	 * @return True if music is enabled, otherwise False
	 */
	public boolean getMusicOn(){
		return musicOn;
	}
	
	/**
	 * Gets in game sound enabled
	 * @return True if in game sound is enabled, otherwise False
	 */
	public boolean getSoundOn(){
		return soundOn;
	}
}
