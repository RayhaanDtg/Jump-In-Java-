package jumpin.common;

import java.util.Set;

import com.google.common.collect.Sets;

/**
*  Orientation
*  The Orientation class is an Enum class that uses the Directions Enum class. This takes 
*  the directions and creates the orientation (Vertical and Horizontal) for the foxes to use. Each
*  fox has an orientation that cannot be changed.
*/


public enum Orientation {
	HORIZONTAL(Direction.RIGHT, Direction.LEFT),
	VERTICAL(Direction.UP, Direction.DOWN);

	private final Set<Direction> directions;

	/**
	*  Creates the set of directions to be used for each FOX element.
	*  @param directions
	*/
	private Orientation(final Direction... directions) {
		this.directions = Sets.newHashSet(directions);
	}

	/**
	*  Returns if the map of directions contains the specified direction.
	*  @param direction
	*/
	public boolean contains(final Direction direction) {
		return directions.contains(direction);
	}
}
