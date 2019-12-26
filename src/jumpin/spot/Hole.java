package jumpin.spot;

import jumpin.common.Position;
import jumpin.element.ElementType;
import jumpin.element.GameElement;
/**
*  Hole
*  The Hole class creates a Spot on the board that is a hole. This is where the bunnies jump in
*  to win the game. This class extends spot.
*/
public class Hole extends Spot {

	/**
	*  This creates a hole.
	*  @param position
	*/
	public Hole(final Position position) {
		super(position);
	}

	/**
	*  This method checks if spot can hold specific element.
	*  @param element
	*  @return boolean
	*/
	@Override
	public boolean canHold(final GameElement element) {
		final ElementType elementType = element.getType();

		final boolean typeAllowed = elementType != ElementType.FOX;

		return typeAllowed && (isHoldingElement(element) || isEmpty());
	}
}
