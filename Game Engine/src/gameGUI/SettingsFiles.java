package gameGUI;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SettingsFiles {
	private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	private static File setFile;
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static Document doc;
	private static Transformer tr = null; 

	private static Element childElem;
	private static Element rootElem;
	private static Element parentElem;


	//Settings Variables Store
	private SettingsVariablesStore svs;
	private SettingFileParser sfp;

	public SettingsFiles(SettingsVariablesStore svs, SettingFileParser sfp) {
		this.svs = svs;
		this.sfp = sfp;
		initializeWriters();
	}

	/**
	 * Loads document readers
	 * @return True if writers loaded successfully
	 */
	private boolean initializeWriters(){
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		}
		return (doc !=null);
	}
	
	/**
	 * Creates a new XML Element
	 * @param elementName The name of the element
	 * @param data The data contained in the element
	 */
	private void addToXML(Element parElement, String elementName, String data){
		childElem = doc.createElement(elementName);
		childElem.appendChild(doc.createTextNode(data));
		parElement.appendChild(childElem);

	}
	
	/**
	 * Attempts to read a file. If the file does not exist a default file is created.
	 * If the file does exist it is parsed
	 * @param fileName The name of the file. Path directory may be included
	 */
	public void readSettingsFile(String fileName){
		//converts file name to lowercase
		fileName = fileName.toLowerCase();
		if(setFile == null){
			setFile = new File (fileName);
			sfp.setFileName(fileName);
		}
		//check if file exists
		try {//If file doesn't exist create it
			if(setFile.createNewFile()){
				
				createSettingsFile();
				readSettingsFile(fileName);
			}
			
			else{
				sfp.parseSettings();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createSettingsFile(){

		rootElem = doc.createElement("SETTINGS");
		rootElem.setAttribute("VERSION", "0.7.0.0");
		
		parentElem = doc.createElement("DISPLAY");
		childElem = doc.createElement("RESOLUTION");
		//Sets default resolution to be screen size
		childElem.setAttribute("WIDTH", String.valueOf(gd.getDisplayMode().getWidth()));
		childElem.setAttribute("HEIGHT", String.valueOf(gd.getDisplayMode().getHeight()));
		childElem.appendChild(doc.createTextNode("TRUE"));
		parentElem.appendChild(childElem);

		addToXML(parentElem, "VSYNC", "FALSE");
		rootElem.appendChild(parentElem);
		//Sound settings
		parentElem = doc.createElement("SOUND");
			childElem = doc.createElement("SOUND_ON");
				childElem.setAttribute("VOLUME_SOUND", "50");
				childElem.appendChild(doc.createTextNode("TRUE"));
			parentElem.appendChild(childElem);
		//Music Settings
			childElem = doc.createElement("MUSIC_ON");
				childElem.setAttribute("VOLUME_MUSIC", "50");
				childElem.appendChild(doc.createTextNode("TRUE"));
			parentElem.appendChild(childElem);
		
		rootElem.appendChild(parentElem);
		
		//write to file
		doc.appendChild(rootElem);
		try {
			tr = TransformerFactory.newInstance().newTransformer();

			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			// send DOM to file
			tr.transform(new DOMSource(doc),  new StreamResult(setFile));
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}

	public boolean writeToFile(String fileName, String newInputData, String oldInputData){
		//overwrites specific data with new data
		try {
			Path path = Paths.get(fileName);
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(oldInputData, newInputData);
			Files.write(path, content.getBytes(charset));

			return true;//for JUnit Only
		} catch (IOException e) {
			//e.printStackTrace();
			return false;//for JUnit only
		}
	}

}
