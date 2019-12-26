package jumpin.common;

/**
*  JumpInConstants
*  The JumpInConstants class creates all the values for the different elements in the game,
*  being the number of bunnies, foxes, and mushrooms. We also initialize the sizing of the board
*  as well as setting the ID’s for the elements.
*/


public final class JumpinConstants {
	public static final int MAX_FOX_COUNT = 2;
	public static final int MAX_BUNNY_COUNT = 3;
	public static final int MAX_MUSHROOM_COUNT = 3;

	public static final int BOARD_WIDTH = 5;
	public static final int BOARD_HEIGHT = 5;

	private static int elementCounter = 1;

	/**
	*  Method sets the values of the element counters, so for each element they receive and ID and
	*  counts through the elements being used for the specific game.
	*  Ex. counts through from Bunny1 to Bunny3
	*/

	public static int getNextElementId() {
		return elementCounter++;
	}

	// No initialization of utils class
	private JumpinConstants() {}
}
