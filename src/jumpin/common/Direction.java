package jumpin.common;

/**
*  Direction
*  The Direction class is an Enum class that is meant to give the spaces around the element
*  being looked at. Example being for Bunny1, we have the up, down, left and right directions for
*  the bunny. Each of these correspond to a particular space on the board. This will be used by
*  the Orientation class and will be used in order to move to a particular space on the board.
*/

public enum Direction {
	UP,
	DOWN,
	RIGHT,
	LEFT;
}
