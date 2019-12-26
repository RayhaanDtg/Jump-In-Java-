package JumpInTestCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.command.BoardTurnCommand;
import jumpin.command.CommandManager;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;

public class BoardTurnCommandTest {
	BoardTurnCommand btc;
	JumpInModel game;
	static Bunny b = new Bunny();
	CommandManager cm;
	private static final Level level = Level.builder()
			.add(b, 0, 3)
			.add(new Bunny(), 2, 4)
			.add(new Bunny(), 4, 1)
			.add(new Fox(Orientation.VERTICAL), 1, 1)
			.add(new Fox(Orientation.HORIZONTAL), 3, 4)
			.add(new Mushroom(), 1, 3)
			.add(new Mushroom(), 4, 2)
			.build();
	
	@Before
	public void setUp() throws Exception {
		this.btc = new BoardTurnCommand(b, new Position (0,3), new Position(2,3));
		this.game = new JumpInModel(level);
	}	
	
	@Test
	public void testBoardTurnCommand() {
		assertTrue("Board Turn Command does not exist.", btc.equals(btc));
	}

	@Test
	public void testExecute() {
		btc.execute(game);
		assertEquals("The bunny did not move successfully", game.getElementPosition(1), new Position(2,3));
	}

	@Test
	public void testUndo() {
		btc.execute(game);
		btc.undo(game);
		assertEquals("The bunny did not move successfully", game.getElementPosition(1), new Position(0,3));
	}

	@Test
	public void testToString() {
		assertEquals("Strings do not match.", btc.toString(), String.format("{%s : %s -> %s}", b.getFriendlyId(), new Position(0,3), new Position(2,3)));
	}
	
}
