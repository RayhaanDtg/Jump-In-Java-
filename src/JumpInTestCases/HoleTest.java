//LAST MODIFIED: 2019.11.04

package JumpInTestCases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jumpin.common.Position;
import jumpin.spot.Hole;

public class HoleTest {

		Position p0;
		Hole h0;

		@Before
		public void setUp() throws Exception {
			p0 = new Position(0,0);
			h0 = new Hole(p0);
		}

		@Test
		public void testHill() {
			assertEquals("Hole has not been initialized.", h0.getPosition(), p0);
		}
}
