package JumpInTestCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.spot.Hill;
import jumpin.spot.Spot;

public class HillTest {

	Position p0;
	Hill hill0;
	
	@Before
	public void setUp() throws Exception {
		p0 = new Position(1,1);
		hill0 = new Hill(p0);
	}

	@Test
	public void testHill() {
		assertEquals("Type is not Hill.", hill0.getPosition(), p0);
	}
}

