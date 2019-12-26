//This class was created for milstone1 , it is not used neither in milstone 2 nor 3.
package jumpin;

import java.util.Scanner;

import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.Fox;
import jumpin.element.Mushroom;

/**
*  This runs the full JumpIn game. Contains the main function
*  in order to run the steps for the game.
*/
public class JumpInClient {
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
	*/
        /*
	public static void main(String[] args) {
		final Scanner userInput = new Scanner(System.in);
		final Game theOnlyGameForNow = new Game(ONLY_LEVEL_FOR_NOW);
		while(!theOnlyGameForNow.isFinished()) {
			System.out.println(theOnlyGameForNow);
			final int elementId = getElementId(userInput);
			final Position position = getPosition(userInput);
			try {
				theOnlyGameForNow.takeTurn(elementId, position);
			} catch (final IllegalMoveException e) {
				// On illegal moves, simply print and let the user try another one.
				System.out.println(e.getMessage());
				System.out.println("press 'y' and Enter to continute");
				userInput.next();
			}
			System.out.println("\n\n\n\n\n");
		}
		System.out.println(theOnlyGameForNow.getState());
		System.out.println("Press enter to close");
		userInput.next();
	}
        */
	/**
	*  Gets the element ID from the user, based on input.
	*  @param userInput
	*  @return int
	*/
	private static int getElementId(final Scanner userInput) {
		System.out.println("What element would you like to move?");
		return getUserInt("Element Id", userInput);
	}

	/**
	*  Get the position to move to, based on user input.
	*  @param userInput
	*  @return Position
	*/
	private static Position getPosition(final Scanner userInput) {
		System.out.println("Where do you want to place it?");
		final int row = getUserInt("row", userInput);
		final int column = getUserInt("column", userInput);
		return new Position(row, column);
	}

	/**
	*  Gets the user input.
	*  @param inputPromt
	*  @param userInput
	*  @return int
	*/
	private static int getUserInt(final String inputPromt, final Scanner userInput) {
		System.out.print(inputPromt + ": ");
		final String inputStr = userInput.next();
		final int inputInt = Integer.parseInt(inputStr);
		return inputInt;
	}
}