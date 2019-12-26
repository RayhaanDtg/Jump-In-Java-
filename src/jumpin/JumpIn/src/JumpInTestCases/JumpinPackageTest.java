package JumpInTestCases;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import jumpin.Board;
import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.common.Direction;
import jumpin.common.GameState;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;

public class JumpinPackageTest {
	private static final Level level = Level.builder()
			.add(new Bunny(), 0, 3)
			.add(new Bunny(), 2, 4)
			.add(new Bunny(), 4, 1)
			.add(new Fox(Orientation.VERTICAL), 1, 1)
			.add(new Fox(Orientation.HORIZONTAL), 3, 4)
			.add(new Mushroom(), 1, 3)
			.add(new Mushroom(), 4, 2)
			.build();
	JumpInModel game;
	Board board;
	
	//Game Test
		@Before
		public void setUp() throws Exception {
			game = new JumpInModel(level);
			board = game.getBoard();
		}
	
		@Test
		public void testGame() {
			assertTrue("No game exists.", game.equals(game));
		}
	
		@Test
		public void testGetState() {
			assertEquals("Game is not in progress.", game.getState(), GameState.IN_PROGRESS);
		}
	
		@Test
		public void testIsFinished() {
			assertTrue("Game is not finished.", game.getState() == GameState.IN_PROGRESS);
		}
		
		@Test
		public void testTakeTurn() {
			Position p = new Position(2,3);
			game.takeTurn(1, p);
			assertFalse("Turn did not occur.",  game.getBoard().getSpot(p).isEmpty());
		}
	
	
	// BoardTest 
		@Test
		public void testAllBunniesInHoles() 
		{
			/*
			 * How to solve this level
			 * Bunny 1 	-> [2, 3]
			 * Bunny 2 	-> [2, 2]
			 * Bunny 1 	-> [4, 3]
			 * Bunny 3 	-> [4, 4]
			 * Fox 4 	-> [4, 1]
			 * Bunny 1 	-> [4, 0]
			 */
			Position p = new Position(2,3);
			game.takeTurn(1, p);
			assertFalse("All bunnies are not in their holes.",  board.allBunniesInHoles());
			game.takeTurn(2, new Position(2,2));
			game.takeTurn(1, new Position(4,3));
			game.takeTurn(3, new Position(4,4));
			game.takeTurn(4, new Position(4,1));
			game.takeTurn(1, new Position(4,0));
			assertTrue("All bunnies are not in their holes.",  board.allBunniesInHoles());
		}

		@Test
		public void testPutElement() 
		{
			Position p = new Position(3,2);
			board.putElement(new Bunny(), p);
			assertFalse("Element was not put here.", board.getSpot(p).isEmpty());
		}

		@Test
		public void testRemoveElement() 
		{
			Position p = new Position(0,3);
			board.removeElement(board.getSpot(p).getGameElement());
			assertTrue("Element was not removed.", board.getSpot(p).isEmpty());
		}

		@Test
		public void testGetSpotsAheadOf() 
		{
			Position p = new Position(0,3);
			assertFalse("No spots are ahead of it.", board.getSpotsAheadOf(board.getSpot(p).getGameElement(), Direction.DOWN).isEmpty());
		}

	
	// LevelTest 


		@Test
		public void testGetGameElements() {
			assertFalse(level.getGameElements().isEmpty());
			
		}

		@Test
		public void testGetBunnyCount() {
			assertEquals("Not all bunnies were counted.", level.getBunnyCount(), 3);
		}
	

}
