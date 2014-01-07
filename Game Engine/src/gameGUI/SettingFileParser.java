package gameGUI;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SettingFileParser extends DefaultHandler {
	private SettingsVariablesStore svs;
	private static String fileName;
	private static SAXParserFactory pFactory = SAXParserFactory.newInstance();
	private static  SAXParser parser;
	
	private static String elementValue;
	SettingFileParser(SettingsVariablesStore svs){
		this.svs = svs;
		try {
			parser = pFactory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
	
	public void parseSettings(){
		 
	        try {
	          
	           parser.parse(fileName, this);
	        } catch ( IOException | SAXException e) {
	           e.printStackTrace();
	        } 

	}
	
	@Override
	 public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException{
		if(elementName.equalsIgnoreCase("RESOLUTION")){
			svs.setResolution(Integer.parseInt(attributes.getValue("WIDTH")), Integer.parseInt(attributes.getValue("HEIGHT")));
		}
		if(elementName.equalsIgnoreCase("SOUND_ON")){
			svs.setMusicVolume(Float.parseFloat(attributes.getValue("VOLUME_SOUND")));
		}
		if(elementName.equalsIgnoreCase("MUSIC_ON")){
			svs.setMusicVolume(Float.parseFloat(attributes.getValue("VOLUME_MUSIC")));
		}
		
	}
	
	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		if(element.equalsIgnoreCase("RESOLUTION")){
			svs.setFullScreen(Boolean.parseBoolean(elementValue));
		}
		if(element.equalsIgnoreCase("VSYNC")){
			svs.setVsync(Boolean.parseBoolean(elementValue));
		}
		if(element.equalsIgnoreCase("MUSIC_ON")){
			svs.setMusicOn(Boolean.parseBoolean(elementValue));
		}
		if(element.equalsIgnoreCase("SOUND_ON")){
			svs.setSoundOn(Boolean.parseBoolean(elementValue));
		}
	}
	
	@Override
	public void characters(char[] ac, int i, int j) throws SAXException{
		elementValue = new String(ac, i, j);
	}
	public void setFileName(String fileName) {
		SettingFileParser.fileName = fileName;
		
	}
}
