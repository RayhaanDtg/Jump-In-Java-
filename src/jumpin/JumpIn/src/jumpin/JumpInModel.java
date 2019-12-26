package jumpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jumpin.common.Direction;
import jumpin.common.GameState;
import jumpin.common.Position;
import jumpin.element.GameElement;
import jumpin.exception.IllegalMoveException;
import jumpin.spot.Spot;

/**
* Game
* The Game class simulates the game,this includes all the methods to play the game from start
*  to completion. This includes the take turn and manages the states, as well as check if the
*  game has been won. This also controls the moves made for the game.
*/

public class JumpInModel {
	private final Board board;
	private final List<GameElement> elements;
	private final Map<GameElement, List<Position>> elementNextAvailablePositions;

	private GameState state;
	
	/**
	*  This sets up the board to be played. It takes in the elements and the positions and adds
	*  everything to the board to be used in the game. The game uses the level that was set up in
	*  order to make the layout. It also sets the state and confirms the base positions that are being
	*  used for the elements.
	*  @param level
	*/

	public JumpInModel(final Level level) {
                
		this.board = new Board(level.getBunnyCount());
		this.elements = new ArrayList<>();
		this.elementNextAvailablePositions = new HashMap<>();

		level.getGameElements().forEach((position, element) -> {
			this.elements.add(element);
			this.board.putElement(element, position);
		});

		calcualteNextAvailablePositions();
                
		this.state = GameState.IN_PROGRESS;
	}
	
	/**
	*  This gets and returns state of the game.
	*  @return GameState
	*/

	public GameState getState() {
		return this.state;
	}

	/**
	*  This checks and returns if the game is finished. Checks the state.
	*  @return boolean
	*/

	public boolean isFinished() {
		return this.state != GameState.IN_PROGRESS;
	}

	/**
	*  This runs through the sequence to take a turn, starts by checking if the element can move,
	*  and checks through its available positions. If the position that is chosen to move to is within
	*  the available positions to move, the element will be moved to that position. This is based on
	*  the validity of the move and the rule of the element in question. If the move is not valid, the
	*  element will not move and a list of possible moves will be returned and the turn will finish.
	*  @param elementId
	*  @param position
	*/

	public void takeTurn(final int elementId, final Position position) {
		final GameElement element = getElementById(elementId);

		assertAllowedMove(element, position);

		board.removeElement(element);
		board.putElement(element, position);

		calcualteNextAvailablePositions();

		this.state = board.allBunniesInHoles() ? GameState.SUCCESS : GameState.IN_PROGRESS;
	}


	/**
	 * Return the position of a given element. Returns null if not found.
	 *
	 * @param elementId Element Id to find position for
	 * @return Position of the element on the board
	 */
	public Position getElementPosition(final int elementId) {
		final GameElement element = getElementById(elementId);

		return board.getElementPosition(element);
	}

	public Map<GameElement, List<Position>> getElementNextAvailablePositions() {
		return elementNextAvailablePositions;
	}

	/**
	*  Prints out the game for the user.
	*  @return String
	*/
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("Game State: " + this.state).append('\n');
		sb.append("Board:").append('\n').append(board).append('\n');

		return sb.toString();
	}
	
	/*
	 * returns elements on board
	 */
	public String[][] getBoardElements() {
		return board.getBoardElements();
	}

	/**
	 * Return the current state of the game described as a level.
	 *
	 * @return Level description of current state of the game.
	 */
	public Level getLevel() {
		final Level.Builder levelBuilder = Level.builder();

		elements.forEach(element -> {
			final Position position = getElementPosition(element.getId());
			levelBuilder.add(element, position);
		});

		return levelBuilder.build();
	}

	/**
	 * Returns the element associated to the given Id.
	 *
	 * @param elementId Id of the element we are trying to find
	 * @return GameElement
	 */
	public GameElement getElementById(final int elementId) {
		final GameElement element = elements.stream()
				.filter(e -> e.getId() == elementId)
				.findAny()
				.get();

		return element;
	}

	/**
	*  Based on the element and its position, calculates the spots available to that element to be
	*  moved to, based on the rules of the element.
	*/

	private void calcualteNextAvailablePositions() {
		for (final GameElement element : elements) {
			final List<Position> nextAvailablePositions = new ArrayList<>();

			for (final Direction direction : Direction.values()) {
				if (element.canMove(direction)) {
					final List<Spot> spotsAhead = board.getSpotsAheadOf(element, direction);

					final List<Position> nextAvailablePositionsInDirection = element.getNextAvailableSpots(spotsAhead, direction).stream()
							.map(Spot::getPosition)
							.collect(Collectors.toList());

					nextAvailablePositions.addAll(nextAvailablePositionsInDirection);
				}
			}

			elementNextAvailablePositions.put(element, nextAvailablePositions);
		}
	}

	/**
	*  Checks if the move is allowed, if not suggests other moves.
	*  @param element
	*  @param position
	*/
	private void assertAllowedMove(final GameElement element, final Position position) {
		final List<Position> availablePositions = elementNextAvailablePositions.get(element);

		if (!availablePositions.contains(position)) {
			throw new IllegalMoveException(element, position, availablePositions);
		}
	}
	

	/**
	*  Gets and returns the board of the game.
	*  @return Board
	*/
	public Board getBoard() {
		return board;
	}

}
