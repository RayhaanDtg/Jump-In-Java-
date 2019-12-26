package jumpin.element;

import java.util.ArrayList;
import java.util.List;


import jumpin.common.Direction;
import jumpin.common.JumpinConstants;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.spot.Spot;


/**
*  Fox
*  The Fox class is an element used in the game. Each fox has an associated ID as well
*  as rules they are meant to adhere to when playing the game.
*/
public class Fox implements GameElement {

	private final int id;
	private final Orientation orientation;

	/**
	*  This creates a fox game element based on how many foxes were previously created and sets
	*  the orientation of the fox. This orientation cannot be changed throughout the game.
	*  @param: orientation
	*/
	public Fox(final Orientation orientation) {
		this.orientation = orientation;
		this.id = JumpinConstants.getNextElementId();
	}

	/**
	*  This returns the type of element being used, in this case the Fox.
	*  @return ElementType
	*/
	@Override
	public ElementType getType() {
		return ElementType.FOX;
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
	* Returns if the the fox is moving in the direction of its orientation
	* @param direction
	* @return boolean
	*/
	@Override
	public boolean canMove(Direction direction) {
		return orientation.contains(direction);
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
		final List<Spot> nextAvailableSpots = new ArrayList<>();

		// If we're not facing ahead, we need to look another spot ahead to jump over the tail.
		if (isFacingHead(direction)) {
			for (int i = 0; i < spotsAhead.size(); i++) {
				final Spot spot = spotsAhead.get(i);
				if (spot.canHold(this)) {
					nextAvailableSpots.add(spot);
				} else {
					break;
				}
			}
		} else {
			// Need to stop 1 spot ahead of final spot to ensure space for the head and tail.
			for (int i = 0; i < spotsAhead.size() - 1; i++) {
				final Spot spotForHead = spotsAhead.get(i);
				final Spot spotForTail = spotsAhead.get(i + 1);
				if (spotForHead.canHold(this) && spotForTail.canHold(this)) {
					nextAvailableSpots.add(spotForHead);
				} else {
					break;
				}
			}
		}
		return nextAvailableSpots;
	}



	/**
	*  Represents the position of the tail of the fox given the orientation and headPosition of the fox.
	*  @param headPosition
	*  @return Position
	*/
	public Position getTailPosition(final Position headPosition) {
		int tailRow = headPosition.getRow();
		int tailColumn = headPosition.getColumn();

		if (orientation == Orientation.HORIZONTAL) {
			tailColumn--;
		} else {
			tailRow--;
		}

		return new Position(tailRow, tailColumn);
	}
        
        public Orientation getOrientation(){
            return orientation;
        }

	/**
	* Represents if the direction is facing or away from the head
	* @param direction
	* @return boolean
	*/
	private boolean isFacingHead(final Direction direction) {
		return (direction == Direction.UP || direction == Direction.LEFT) ? false : true;
	}
}
