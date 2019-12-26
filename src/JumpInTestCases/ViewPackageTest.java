//LAST MODIFIED: 2019.12.01

package JumpInTestCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.*;

public class ViewPackageTest {
	BoardView bv;
	EditorView ev;
	GameView gv;
	JumpInView jv;
	MainMenuView mv;
	
	@Before
	public void setUp() throws Exception {
		bv = new BoardView();
		ev = new EditorView();
		gv = new GameView();
		jv = new JumpInView();
		mv = new MainMenuView();
	}

	@Test
	public void BoardViewTest() {
		assertTrue("BoardView is not initialized.", bv.equals(bv));
	}
	
	@Test
	public void EditorViewTest() {
		assertTrue("EditorView is not initialized.", ev.equals(ev));
	}	
	
	@Test
	public void GameViewTest() {
		assertTrue("GameView is not initialized.", gv.equals(gv));
	}	
	
	@Test
	public void JumpInViewTest() {
		assertTrue("BoardView is not initialized.", jv.equals(jv));
	}
	
	@Test
	public void MainMenuViewTest() {
		assertTrue("BoardView is not initialized.", mv.equals(mv));
	}
}
