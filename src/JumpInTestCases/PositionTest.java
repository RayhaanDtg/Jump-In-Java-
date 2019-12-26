//LAST MODIFIED: 2019.11.04

package JumpInTestCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jumpin.common.Position;

public class PositionTest {

	Position pos1;
	Position pos2;
	Position pos3;
	Position pos4;

	
	@Before
	public void setUp() throws Exception {
		pos1 = new Position(0, 0);
		pos2 = new Position(0, 1);
		pos3 = new Position(1, 3);
		pos4 = new Position(5, 6);
	}

	@Test
	public void testPosition() {
		assertEquals("Position (0,0) has not been initialized.", pos1.getColumn(), 0);
		assertEquals("Position (0,1) has not been initialized.", pos2.getColumn(), 1);
		assertEquals("Position (1,3) has not been initialized.", pos3.getColumn(), 3);
		assertEquals("Position (5,6) has not been initialized.", pos4.getColumn(), 6);
	}

}
