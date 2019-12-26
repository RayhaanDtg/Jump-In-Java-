package jumpin.spot;

import jumpin.common.Position;
import jumpin.element.ElementType;
import jumpin.element.GameElement;

/**
*  The Hill class creates a Spot on the board that is a hill. This is where the bunnies jump on to
*  get to holes. Foxes cannot get onto holes. This class extends Spot.
*/
public class Hill extends Spot {
	/**
	*  This creates a hill.
	*  @param position
	*/
	public Hill(final Position position) {
		super(position);
	}

	/**
	*  This checks if spot can hold a specific element.
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
