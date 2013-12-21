package test;

import static org.junit.Assert.*;
import gameGUI.SettingsFiles;
import java.io.FileNotFoundException;
import org.junit.Test;

public class TestFiles {
	SettingsFiles sf = new SettingsFiles(null);
	@Test
	public void testFileCreated() {
		assertNotNull(sf.createSettingsFile("config.ini"));
	}
	@Test
	public void testWriteToFile() throws FileNotFoundException{
		assertTrue(sf.writeToFile("test file.txt", "new", "old"));
		assertTrue(sf.writeToFile("test file.txt", "old", "new"));
	}
	

}
