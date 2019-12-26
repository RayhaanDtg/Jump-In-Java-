package jumpin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import jumpin.common.Direction;
import jumpin.common.JumpinConstants;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.ElementType;
import jumpin.element.Fox;
import jumpin.element.GameElement;
import jumpin.spot.Hill;
import jumpin.spot.Hole;
import jumpin.spot.Spot;

/**
 * The Board class represents the board of spots for the elements to exist. This
 * is where we instantiate the holes and hills on the board. This also where we
 * use the game elements in the board.
 */

public class Board {
	private final static List<Position> HOLES = Lists.newArrayList(new Position(0, 0), // Top left
			new Position(2, 2), // Middle
			new Position(0, 4), // Top right
			new Position(4, 0), // Bottom left
			new Position(4, 4));// Bottom right
	private final static List<Position> HILLS = Lists.newArrayList(new Position(0, 2), // Top middle
			new Position(2, 0), // Middle left
			new Position(2, 4), // Middle right
			new Position(4, 2));// Bottom middle

	private final Table<Integer, Integer, Spot> board;
	private final Map<GameElement, Position> elementPositions;
	private final List<Hole> holes;
	private final int bunnyCount;

	/**
	 * The board constructor takes in the number of bunnies that are being used in
	 * the game. A map of the element positions are created, as well as the holes
	 * for the board. From here all the spots are instantiated will hills and holes,
	 * and the board is built to be used in the game.
	 * 
	 * @param bunnyCount
	 */

	public Board(final int bunnyCount) {
		this.bunnyCount = bunnyCount;
		this.elementPositions = Maps.newHashMap();
		this.holes = new ArrayList<>();

		final ImmutableTable.Builder<Integer, Integer, Spot> boardBuilder = ImmutableTable.builder();

		for (int column = 0; column < JumpinConstants.BOARD_WIDTH; column++) {
			for (int row = 0; row < JumpinConstants.BOARD_HEIGHT; row++) {
				final Position position = new Position(row, column);

				Spot spot = new Spot(position);
				if (HOLES.contains(position)) {
					final Hole hole = new Hole(position);
					this.holes.add(hole);
					spot = hole;
				} else if (HILLS.contains(position)) {
					spot = new Hill(position);
				}
				boardBuilder.put(position.getRow(), position.getColumn(), spot);
			}
		}

		this.board = boardBuilder.build();
	}

	/**
	 * This returns the spot object for a given position.
	 * 
	 * @param position
	 * @return Spot
	 */

	public Spot getSpot(final Position position) {
		return board.get(position.getRow(), position.getColumn());
	}

	/**
	 * Return the position of a given element. Returns null if not found.
	 *
	 * @param element Element to find position for
	 * @return Position of the element on the board
	 */
	public Position getElementPosition(final GameElement element) {
		return elementPositions.get(element);
	}

	/**
	 * This returns if all the spots marked as holes have bunnies there. If this is
	 * true this means the game has been completed.
	 * 
	 * @return Boolean
	 */
	public boolean allBunniesInHoles() {
		final long filledHoleCount = holes.stream().filter(hole -> !hole.isEmpty()).collect(Collectors.toList()).size();

		return filledHoleCount == bunnyCount;
	}

	/**
	 * This method moves the element from its current position to a new position if
	 * it is a valid move.
	 * 
	 * @param element
	 * @param position
	 */
	public void putElement(final GameElement element, final Position position) {
		final Spot spot = getSpot(position);

		final ElementType elementType = element.getType();
		if (elementType == ElementType.FOX) {
			final Spot tailSpot = getFoxTailSpot(element, position);
			tailSpot.putElement(element);
		}

		spot.putElement(element);
		elementPositions.put(element, position);
	}

	/**
	 * This removes the element from its current position.
	 * 
	 * @param element
	 */

	public void removeElement(final GameElement element) {
		final Position position = elementPositions.get(element);
		final Spot spot = getSpot(position);

		final ElementType elementType = element.getType();
		if (elementType == ElementType.FOX) {
			final Spot tailSpot = getFoxTailSpot(element, position);
			tailSpot.removeElement();
		}

		spot.removeElement();
		elementPositions.remove(element);
	}

	public final Map<GameElement, Position> getElementPositions() {
		return elementPositions;
	}

	/**
	 * This method gets all the surrounding squares in range around the square that
	 * an element is currently on, based on the element being used and the direction
	 * to get the spot.
	 * 
	 * @param element
	 * @param direction
	 * @return List<Spot>
	 */

	public List<Spot> getSpotsAheadOf(final GameElement element, final Direction direction) {
		final List<Spot> spotAheadList = new ArrayList<Spot>();
		final Position position = elementPositions.get(element);

		boolean insideTheBoundaries = true;
		int row = position.getRow();
		int column = position.getColumn();

		while (insideTheBoundaries) {
			switch (direction) {
			case UP:
				row--;
				break;
			case DOWN:
				row++;
				break;
			case RIGHT:
				column++;
				break;
			case LEFT:
				column--;
				break;
			}

			if (row >= 0 && row < JumpinConstants.BOARD_HEIGHT && column >= 0 && column < JumpinConstants.BOARD_WIDTH) {
				final Spot spot = this.getSpot(new Position(row, column));
				spotAheadList.add(spot);
			} else {
				insideTheBoundaries = false;
			}
		}

		return spotAheadList;
	}

	/**
	 * This returns a list of all the elements in terms of its ID and associated
	 * coordinate.
	 * 
	 * @return String
	 */

	@Override
	public String toString() {
		final String lines = "=========================================\n";
		final StringBuilder sb = new StringBuilder(lines);
		sb.append("Element\t\t\tPosition\n");
		elementPositions.forEach((element, position) -> {
			sb.append(String.format("%s\t\t%s\n", element.getFriendlyId(), position));
		});
		sb.append(lines);
		sb.append(printBoard()).append(lines);

		return sb.toString();
	}

	/**
	 * This returns a spot from the position from getFoxTail given the element and
	 * its head position.
	 * 
	 * @param element
	 * @param headPosition
	 * @return Spot
	 */

	private Spot getFoxTailSpot(final GameElement element, final Position headPosition) {
		final Fox fox = (Fox) element;

		final Position tailPosition = fox.getTailPosition(headPosition);
		final Spot tailSpot = getSpot(tailPosition);

		return tailSpot;
	}

	/**
	 * prints the board
	 * 
	 * @return String
	 */
	private String printBoard() {
		final StringBuilder sb = new StringBuilder();

		final String[][] boardie = new String[5][5];
		HOLES.forEach(position -> {
			boardie[position.getRow()][position.getColumn()] = " O ";
		});
		HILLS.forEach(position -> {
			boardie[position.getRow()][position.getColumn()] = " ^ ";
		});
		elementPositions.forEach((element, position) -> {
			if (element.getType() == ElementType.FOX) {
				boardie[position.getRow()][position.getColumn()] = String.format("%s%s", "FH", element.getId());
				Spot spot = getFoxTailSpot(element, position);
				boardie[spot.getPosition().getRow()][spot.getPosition().getColumn()] = String.format("%s%s", "FT",
						element.getId());
			} else {
				boardie[position.getRow()][position.getColumn()] = String.format("%s%s ",
						element.getFriendlyId().charAt(0), element.getId());
			}
		});
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (boardie[i][j] == null) {
					sb.append("[   ]");
				} else {
					sb.append("[" + boardie[i][j] + "]");
				}
			}
			sb.append("").append('\n');
		}

		return sb.toString();
	}

	/**
	 * This gets all the elements on the board and gives them a Letter identifier
	 * for the controller to identify elements.
	 * 
	 * @return String [][]
	 */
	public String[][] getBoardElements() {
		final String[][] boardie = new String[5][5];
		HOLES.forEach(position -> {
			boardie[position.getRow()][position.getColumn()] = " O ";
		});
		HILLS.forEach(position -> {
			boardie[position.getRow()][position.getColumn()] = " ^ ";
		});
		elementPositions.forEach((element, position) -> {
			if (element.getType() == ElementType.FOX) {
				Fox fox = (Fox) (element);
				if (fox.getOrientation() == Orientation.HORIZONTAL) {
					boardie[position.getRow()][position.getColumn()] = String.format("%s%s", "FHH", element.getId());
					Spot spot = getFoxTailSpot(element, position);
					boardie[spot.getPosition().getRow()][spot.getPosition().getColumn()] = String.format("%s%s", "FTH",
							element.getId());
				} else {
					boardie[position.getRow()][position.getColumn()] = String.format("%s%s", "FHV", element.getId());
					Spot spot = getFoxTailSpot(element, position);
					boardie[spot.getPosition().getRow()][spot.getPosition().getColumn()] = String.format("%s%s", "FTV",
							element.getId());
				}
			} else {
				boardie[position.getRow()][position.getColumn()] = String.format("%s%s ",
						element.getFriendlyId().charAt(0), element.getId());
			}
		});

		return boardie;
	}
}
