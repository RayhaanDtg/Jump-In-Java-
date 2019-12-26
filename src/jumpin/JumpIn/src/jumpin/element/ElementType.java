package jumpin.element;

import jumpin.common.JumpinConstants;
/**
*  Element Type
*  The ElementType enum class is used to create the different types of board
*  elementsRepresent, being the fox, bunny and mushroom. Each element has 3 enumerable
*  types with associated max values.
*/
public enum ElementType {
	BUNNY(JumpinConstants.MAX_BUNNY_COUNT),
	FOX(JumpinConstants.MAX_FOX_COUNT),
	MUSHROOM(JumpinConstants.MAX_MUSHROOM_COUNT);

	final int maxElementCount;
	
	/**
	*  Sets the maximum number of active board elements to be used in the game.
	*/
	private ElementType(final int maxElementCount) {
		this.maxElementCount = maxElementCount;
	}
	
	/**
	*  This returns the maximum number of active board elements being used for the game.
	*  @return int
	*/
	public int getMaxCount() {
		return this.maxElementCount;
	}
}
