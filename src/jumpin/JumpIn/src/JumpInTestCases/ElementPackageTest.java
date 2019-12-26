package JumpInTestCases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import jumpin.common.Orientation;
import jumpin.element.*;

public class ElementPackageTest {

	public Bunny bunny1;
	public Fox fox1;
	public Mushroom shroom1;

	
	@Before
	public void setUp() throws Exception {
		bunny1 = new Bunny();
		fox1 = new Fox(Orientation.HORIZONTAL);
		shroom1 = new Mushroom();
	}

	@Test
	public void testBunny() {
		assertTrue("Bunny does not exist.", bunny1.equals(bunny1));
	}
	
	@Test
	public void testFox() {
		assertTrue("Fox does not exist.", fox1.equals(fox1));
	}
	
	@Test
	public void testMushroom() {
		assertTrue("Mushroom does not exist.", shroom1.equals(shroom1));	

	}

	@Test
	public void testGetType() {
		assertEquals("Bunny is not of type BUNNY.", bunny1.getType(), ElementType.BUNNY);
		assertEquals("Fox is not of type FOX.", fox1.getType(), ElementType.FOX);
		assertEquals("Mushroom is not of type MUSHROOM.", shroom1.getType(), ElementType.MUSHROOM);
	}


}
