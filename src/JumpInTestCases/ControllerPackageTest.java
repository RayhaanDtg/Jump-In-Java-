//LAST MODIFIED: 2019.12.01

package JumpInTestCases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.EditorController;
import controller.GameController;
import controller.JumpInController;
import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.common.Orientation;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;
import view.JumpInView;

public class ControllerPackageTest {
	EditorController ec;
	GameController gc;
	JumpInController jc;
	JumpInView view;
	JumpInModel game;
	private static final Level level = Level.builder()
			.add(new Bunny(), 0, 3)
			.add(new Bunny(), 2, 4)
			.add(new Bunny(), 4, 1)
			.add(new Fox(Orientation.VERTICAL), 1, 1)
			.add(new Fox(Orientation.HORIZONTAL), 3, 4)
			.add(new Mushroom(), 1, 3)
			.add(new Mushroom(), 4, 2)
			.build();
	
	@Before
	public void setUp() throws Exception {
		view = new JumpInView();
		game = new JumpInModel(level);
		ec = new EditorController(view);
		gc = new GameController(view, game);
		jc = new JumpInController(view);
	}

	//EditorController Test
	@Test
	public void testEditorController() {
		assertTrue("The element does not exist.", ec.equals(ec));
	}
	
	
	//GameController Test
	public void testGameController() {
		assertTrue("The element does not exist.", gc.equals(gc));
	}
	
	
	//JumpInController Test
	public void testJumpInController() {
		assertTrue("The element does not exist.", jc.equals(jc));
	}
}
