package gameGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SettingsFiles {
	private File file;
	//////list of variables
	//Read in resolutions
	private int windowHeight;
	private int windowWidth;
	//Settings Variables Store
	private SettingsVariablesStore svs;

	public SettingsFiles(SettingsVariablesStore svs) {
		this.svs = svs;
	}

	public void readSettingsFile(File file){
		this.file = file;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()==true){
				//read lines of file
				readData(scanner);
			}
		} catch (FileNotFoundException e) {
			createSettingsFile(file.getName());
			readSettingsFile(this.file);
		}
	}

	public File createSettingsFile(String fileNameUpper){
		//converts file name to lowercase
		String fileName = fileNameUpper.toLowerCase();
		//creates new file
		File setFile = new File (fileName);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(setFile, true));
			//Determines where to write the default values
			if (fileName.equals("config.ini")){
				//writes default value (width then size)
				bw.write("Size: 480 640\n");
			}
			else if(fileName.equals("setconfig.ini")){
				//writes default value
				bw.write("Size: 200 340\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return setFile;
	}

	private void readData(Scanner sc){
		//Reads the line
		String line = sc.nextLine();
		//uses whitespace as delimiter
		String[] resolution= line.split("\\s+");
		String height = resolution[1];
		String width = resolution[2];

		//converts strings to ints
		windowWidth = Integer.parseInt(width);
		windowHeight = Integer.parseInt(height);
		setConfigFile(windowHeight, windowWidth);
	}

	private void setConfigFile(int height, int width){
		//determine which configuration file to write to
		String fileNameUpper = file.getName();
		String fileName = fileNameUpper.toLowerCase();

		if(fileName.equals("config.ini")){
			svs.setMainDimension(width, height);
		}

		else if (fileName.equals("setconfig.ini")){
			svs.setSettingsDimension(width, height);
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
