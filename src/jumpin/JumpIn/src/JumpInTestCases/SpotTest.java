package JumpInTestCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;
import jumpin.spot.Hill;
import jumpin.spot.Hole;
import jumpin.spot.Spot;

public class SpotTest {
	Position p0;
	Spot s0;
	Hill h0;

	Position p1;
	Spot s1;
	Bunny b1;

	Position p2;
	Spot s2;

	@Before
	public void setUp() throws Exception {
		p0 = new Position(0,0);
		s0 = new Spot(p0);
		h0 = new Hill(p0);
		
		p1 = new Position(1,1);
		s1 = new Spot(p1);
		b1 = new Bunny();

		p2 = new Position(2,2);
		s2 = new Spot(p2);
	}

	@Test
	public void testPutElement() throws Exception{
		s0.putElement(b1);
		assertEquals("There is no bunny on this spot.", s0.getGameElement(), b1);
	}

	
	@Test
	public void testRemoveElement() {
		s0.putElement(b1);
		assertEquals("There is no bunny on this spot.", s0.getGameElement(), b1);	
	}

	
	@Test
	public void testIsEmpty() {
		s0.putElement(b1);
		assertEquals("There is nothing on this spot.", s0.isEmpty(), false);
		assertEquals("There is nothing on this spot.", s2.isEmpty(), true);	
	}
	

}
