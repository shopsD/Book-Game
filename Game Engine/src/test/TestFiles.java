package test;

import static org.junit.Assert.*;
import gameEntry.SettingsFiles;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TestFiles {
	SettingsFiles sf = new SettingsFiles(null, null);

	@Test
	public void testWriteToFile() throws FileNotFoundException{
		assertTrue(sf.writeToFile("test file.txt", "new", "old"));
		assertTrue(sf.writeToFile("test file.txt", "old", "new"));
	}
	

}
