package JumpInTestCases;


import junit.framework.TestCase;
import view.JumpInView;

public class JumpInViewTest extends TestCase{
	
	private JumpInView view;
	
	protected void setUp() {
		view = new JumpInView();
	}
	
	public void testView() {
		assertNotNull(view.getRedoButton());
		assertNotNull(view.getUndoButton());
		assertNotNull(view.getShowSolutionButton());	
	}
}
