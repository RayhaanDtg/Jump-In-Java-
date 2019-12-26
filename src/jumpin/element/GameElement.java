package jumpin.element;
import java.util.List;

import jumpin.common.Direction;
import jumpin.spot.Spot;

/**
*  GameElement is the interface that is implemented by active elements on a board,
*  active meaning that they can’t be overlapped by another element.
*/
public interface GameElement {

	/**
	 * Returns the type of the element
	 * @return ElementType
	 */
	public ElementType getType();

	/**
	 * Returns the unique identifier of the game element.
	 * @return int
	 */
	public int getId();

	/**
	 * Returns the a nicely printed version of the element and its position
	 * @return String
	 */
	default public String getFriendlyId() {
		return String.format("%s (Id = %s)", getType().name(), getId());
	}

	/**
	 * Returns the list of available moves in a specific direction
	 * @param spotsAhead List of the ordered spots in a direction 
	 * @param direction
	 * @return List of possible spots to move to
	 */
	public List<Spot> getNextAvailableSpots(List<Spot> spotsAhead, Direction direction);

	/**
	 * Returns whether the game element is allowed to make a move in the direction.
	 *
	 * @param direction Direction
	 * @return True if the element is allowed to move in that direction
	 */
	public boolean canMove(Direction direction);
}
