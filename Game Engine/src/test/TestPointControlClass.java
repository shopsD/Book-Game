package test;

import static org.junit.Assert.*;
import gameController.PointControlClass;

import org.junit.Test;

public class TestPointControlClass {

	@Test
	public void testGetCreativity() {
		fail("Not yet implemented");
	}

	@Test
	public void testResetPoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCreativity() {
		PointControlClass pcc = new PointControlClass(null, null);
		pcc.addCreativity(5);
		pcc.addCreativity(3);
		assertEquals(9, pcc.getCreativity());
	}

}
