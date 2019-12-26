package jumpin.element;

import java.util.ArrayList;
import java.util.List;

import jumpin.common.Direction;
import jumpin.common.JumpinConstants;
import jumpin.spot.Spot;

/**
*  Bunny
*  The Bunny class is an element used in the game. Each bunny has an associated ID as well
*  as rules they are meant to adhere to when playing the game.
*/
public class Bunny implements GameElement {
	private final int id;

	/**
	*  Creates the bunnies for the game, using the element ID as the identifier.
	*/
	public Bunny() {
		this.id = JumpinConstants.getNextElementId();
	}

	/**
	*  Gets the type of the element being used, ie the Bunny.
	*  @return ElementType
	*/
	@Override
	public ElementType getType() {
		return ElementType.BUNNY;
	}
	
	/**
	*  This returns the ID of the bunny object.
	*  @return int
	*/
	@Override
	public int getId() {
		return id;
	}

	/**
	* has no implementation here, but is needed for completion (see fox)
	* @param direction
	* @return true
	*/
	@Override
	public boolean canMove(Direction direction) {
		return true;
	}

	/**
	*  This returns a list of available spots compared to the current spot. Based on the spots that are
	*  ahead of the current one and the direction to go in.
	* @param List<Spot> spotsAhead
	* @param Direction direction
	* @return List<Spot>
	*/
	@Override
	public List<Spot> getNextAvailableSpots(List<Spot> spotsAhead, Direction direction) {
		final List<Spot> nextAvailableSpots = new ArrayList<>();

		if (spotsAhead.size() > 1 && !spotsAhead.get(0).isEmpty()) {
			for (int i = 1; i < spotsAhead.size(); i++) {
				final Spot spotWeCanHopTo = spotsAhead.get(i);
				if (spotWeCanHopTo.isEmpty()) {
					nextAvailableSpots.add(spotWeCanHopTo);
					break;
				}
			}
		}

		return nextAvailableSpots;
	}
}
