package jumpin;

import controller.JumpInController;
import view.JumpInView;

/**
*  This runs the full JumpIn game. Contains the main function
*  in order to run the steps for the game.
*/

public class JumpInClientGUI {

	/**
	*  Opens and runs through the game sequence.
	* @param args
	*/
	public static void main(String[] args) {
		final JumpInView view = new JumpInView();
		new JumpInController(view);
	}
}
