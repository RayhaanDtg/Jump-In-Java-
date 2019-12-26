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

public class CommandManagerTest {
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
		this.cm = new CommandManager(game);
	}

	@Test
	public void testCommandManager() {
		assertTrue("The command manager does not exist", cm.equals(cm));
	}

	@Test
	public void testExecuteCommand() {
		cm.executeCommand(btc);
		assertTrue("Undo stack is not empty.", cm.getRedoStack().isEmpty());
		assertFalse("Undo stack is not empty.", cm.getUndoStack().isEmpty());
	}

	@Test
	public void testIsUndoAvailable() {
		cm.executeCommand(btc);
		assertEquals("There are no moves to undo.", cm.isUndoAvailable(), !cm.getUndoStack().isEmpty());		
	}

	@Test
	public void testUndo() {
		cm.executeCommand(btc);
		assertTrue("The move exists", cm.getUndoStack().pop().equals(btc));
		cm.undo();
		assertTrue("The move was undone", cm.getUndoStack().isEmpty());		
	}

	@Test
	public void testIsRedoAvailable() {
		cm.executeCommand(btc);
		assertEquals("There are no moves to redo.", cm.isRedoAvailable(), !cm.getRedoStack().isEmpty());
		cm.undo();
		assertEquals("There are moves to redo.", cm.isRedoAvailable(), !cm.getRedoStack().isEmpty());
	}

	@Test
	public void testRedo() {
		cm.executeCommand(btc);
		assertTrue("The move exists", cm.getUndoStack().pop().equals(btc));
		cm.undo();
		assertTrue("The move was undone", cm.getUndoStack().isEmpty());	
		assertTrue("The move was undone", cm.getRedoStack().isEmpty());
		cm.redo();
		assertTrue("The move was redone", cm.getRedoStack().isEmpty());
		assertTrue("The move was undone", cm.getUndoStack().isEmpty());	
	}
}
