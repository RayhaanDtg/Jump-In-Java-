package jumpin.spot;

import java.util.Optional;

import jumpin.common.Position;
import jumpin.element.GameElement;

/**
*  Spot
*  The Spot class creates a space on the game board that can be used by elements. All
*  elements can be added to Spots.
*/
public class Spot {
	private Optional<GameElement> gameElement;
	private final Position position;

	/**
	*  This takes a position on the board and creates a space to be used.
	*  @param position
	*/
	public Spot(final Position position) {
		this.gameElement = Optional.empty();
		this.position = position;
	}

	/**
	*  This gets the position of the spot on the board.
	*  @return Position
	*/
	public Position getPosition() {
		return this.position;
	}

	/**
	*  Gets the gameElement of the spot on the board.
	*  @return GameElement
	*/
	public GameElement getGameElement() {
		return this.gameElement.get();
	}
	
	/**
	*  This checks if the spot can hold an element and then adds the element.
	*  @param element
	*/
	public void putElement(final GameElement element) {
		if (!canHold(element)) {
			throw new IllegalStateException("Trying to place an element on a spot that does not allow it");
		}

		this.gameElement = Optional.of(element);
	}
	
	/**
	*  This takes the element off the spot and makes the spot empty.
	*/
	public void removeElement() {
		this.gameElement = Optional.empty();
	}

	/**
	*  This checks if the spot is empty, returns if there is an element.
	*  @return boolean
	*/
	public boolean isEmpty() {
		return !gameElement.isPresent();
	}

	/**
	*  This checks if a spot can hold a specific element and checks if the spot is empty or is not
	*  holding and element.
	*  @param element
	*  @return boolean
	*/
	public boolean canHold(final GameElement element) {
		return isEmpty() || isHoldingElement(element);
	}

	/**
	*  This checks if space is holding a specific element.
	*  @param element
	*  @return boolean
	*/
	protected boolean isHoldingElement(final GameElement element) {
		return !isEmpty() && gameElement.get() == element;
	}
}
