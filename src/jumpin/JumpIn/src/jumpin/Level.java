package jumpin;

import java.util.HashMap;
import java.util.Map;

import jumpin.common.Position;
import jumpin.element.ElementType;
import jumpin.element.GameElement;

/**
*  Level
*  The Level class contains all the information needed to play a game of JumpIn. By creating a
*  level, you will have all the necessary elements in order to play the game, including the game
*  elements and the board and the positioning of the bunnies, foxes and mushrooms.
*/
public class Level {
	final Map<Position, GameElement> bunnies;
	final Map<Position, GameElement> foxes;
	final Map<Position, GameElement> mushrooms;

	/**
	*  This creates a list of the positions of the bunnies, foxes and mushrooms. Each item position is
	*  stored to be used for that level. This will be the layout for the game to be played on.
	*/
	public Level(final Map<Position, GameElement> bunnies, final Map<Position, GameElement> foxes,
			final Map<Position, GameElement> mushrooms) {
		this.bunnies = bunnies;
		this.foxes = foxes;
		this.mushrooms = mushrooms;
	}

	/**
	*  Creates and returns a new builder. This is what is used to build the level for the game.
	*  @return Builder
	*/
	public static Builder builder() {
		return new Builder();
	}

	/**
	*  This gets and returns all elements used for the game. It takes the positions of the elements
	*  and returns them as a map of positions associated with game elements.
	*  @return Map<Position, GameElement>
	*/
	public Map<Position, GameElement> getGameElements() {
		final Map<Position, GameElement> allElements = new HashMap<>();

		allElements.putAll(bunnies);
		allElements.putAll(foxes);
		allElements.putAll(mushrooms);

		return allElements;
	}
	
	/**
	*  This gets and returns the number of bunnies.
	*  @return int
	*/
	public int getBunnyCount() {
		return bunnies.size();
	}

	/**
	*  Creates the levels to be used in the game. This contains its own methods which are used to
	*  add and bring in each element needed for the level being created. This is what sets the
	*  positions within the board, as well as the orientation of the foxes and the layout of the game to
	*  be played. 
	*/
	public static class Builder {
		final Map<Position, GameElement> bunnies = new HashMap<>();
		final Map<Position, GameElement> foxes = new HashMap<>();
		final Map<Position, GameElement> mushrooms = new HashMap<>();

		public Builder add(final GameElement element, final int row, final int column) {
			final Position position = new Position(row, column);
			return add(element, position);
		}

		public Builder add(final GameElement element, final Position position) {
			Map<Position, GameElement> elementPositionMap;
			final ElementType type = element.getType();

			switch (type) {
			case BUNNY:
				elementPositionMap = bunnies;
				break;
			case FOX:
				elementPositionMap = foxes;
				break;
			case MUSHROOM:
				elementPositionMap = mushrooms;
				break;
			default:
				throw new IllegalStateException("Unsupported element type in Level builder: " + type);
			}

			if (elementPositionMap.size() == type.getMaxCount()) {
				throw new IllegalArgumentException(String.format("Cannot add more than %s %s", type, type.getMaxCount()));
			}

			elementPositionMap.put(position, element);

			return this;
		}

		public Level build() {
			return new Level(bunnies, foxes, mushrooms);
		};
	}
}
