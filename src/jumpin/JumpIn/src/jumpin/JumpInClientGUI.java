package jumpin;

import Controller.JumpInController;
import jumpin.common.Orientation;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;
import view.JumpInView;

/**
*  This runs the full JumpIn game. Contains the main function
*  in order to run the steps for the game.
*/

public class JumpInClientGUI {
     /*
	 * How to solve this level
	 * Bunny 1 	-> [2, 3]
	 * Bunny 2 	-> [2, 2]
	 * Bunny 1 	-> [4, 3]
	 * Bunny 3 	-> [4, 4]
	 * Fox 4 	-> [4, 1]
	 * Bunny 1 	-> [4, 0]
	 */

	/**
	*  Creates the game and adds in all the elements to be used
	*  for the game. Builds the game board. This is our testing level.
	*/
	private static final Level ONLY_LEVEL_FOR_NOW = Level.builder()
			.add(new Bunny(), 0, 3)
			.add(new Bunny(), 2, 4)
			.add(new Bunny(), 4, 1)
			.add(new Fox(Orientation.VERTICAL), 1, 1)
			.add(new Fox(Orientation.HORIZONTAL), 3, 4)
			.add(new Mushroom(), 1, 3)
			.add(new Mushroom(), 4, 2)
			.build();

	/**
	*  Opens and runs through the game sequence.
	* @param args
	*/
	public static void main(String[] args) {
		final JumpInView view = new JumpInView();
		final JumpInModel gameState = new JumpInModel(ONLY_LEVEL_FOR_NOW);
		new JumpInController(view, gameState);
	}
}
