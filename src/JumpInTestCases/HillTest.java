//LAST MODIFIED: 2019.11.04

package JumpInTestCases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jumpin.common.Position;
import jumpin.spot.Hill;

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

