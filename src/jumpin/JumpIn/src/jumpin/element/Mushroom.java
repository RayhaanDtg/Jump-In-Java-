package jumpin.element;

import java.util.ArrayList;
import java.util.List;

import jumpin.common.Direction;
import jumpin.common.JumpinConstants;
import jumpin.spot.Spot;

/**
*  Mushroom
*  The Mushroom class is an element used in the game. Each mushroom has an associated ID,
*  but the mushroom position cannot be changed once it is instantiated.
*/
public class Mushroom implements GameElement{
	private final int id;
	
	/**
	*  This creates a mushroom game element based on how many foxes were previously created
	*  and sets the orientation of the fox. This orientation cannot be changed throughout the game.
	*/
	public Mushroom () {
		this.id = JumpinConstants.getNextElementId();
	}

	/**
	*  This returns the type of element being used, in this case the Mushroom.
	*  @return ElementType
	*/
	@Override
	public ElementType getType() {
		return ElementType.MUSHROOM;
	}

	/**
	*  This returns the ID of the fox object being used.
	*  @return String
	*/
	@Override
	public int getId() {
		return id;
	}

	/**
	*  This returns a list of available spots compared to the current spot. Based on the spots that are
	*  ahead of the current one and the direction to go in.
	*  @param List<Spot> spotsAhead
	*  @param Direction direction
	*  @return List<Spot>
	*/
	@Override
	public List<Spot> getNextAvailableSpots(List<Spot> spotsAhead, Direction direction) {
		return new ArrayList<>();
	}

	/**
	* has no implementation here, but is needed for completion (see fox)
	* @param direction
	* @return true
	*/
	@Override
	public boolean canMove(Direction direction) {
		return false;
	}
}
