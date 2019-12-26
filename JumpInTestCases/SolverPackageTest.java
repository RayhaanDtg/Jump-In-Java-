//LAST MODIFIED: 2019.12.01

package JumpInTestCases;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jumpin.Board;
import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.command.BoardTurnCommand;
import jumpin.command.Command;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;
import solver.SolverStep;


public class SolverPackageTest {
	SolverStep s;
	JumpInModel game;
	Board board;
	private static final Level level = Level.builder()
			.add(new Bunny(), 0, 3)
			.add(new Bunny(), 2, 4)
			.add(new Bunny(), 4, 1)
			.add(new Fox(Orientation.VERTICAL), 1, 1)
			.add(new Fox(Orientation.HORIZONTAL), 3, 4)
			.add(new Mushroom(), 1, 3)
			.add(new Mushroom(), 4, 2)
			.build();
	BoardTurnCommand btc;
	List<Command> commands;
	static Bunny b = new Bunny();

	
	@Before
	public void setUp() throws Exception {
		this.btc = new BoardTurnCommand(b, new Position (0,3), new Position(2,3));
		commands = new ArrayList<Command>();
		commands.add(btc);
		s = new SolverStep(level, btc, commands);
	}
	
	//SolverTest
	@Test
	public void testSolverStep() {
		assertTrue("The Solver Step wasn't initialized.", s.equals(s));
	}
}
