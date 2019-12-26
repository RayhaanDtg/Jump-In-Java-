package controller;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import jumpin.Board;
import jumpin.Level;
import jumpin.LevelRepository;
import jumpin.common.JumpinConstants;
import jumpin.common.Orientation;
import jumpin.common.Position;
import jumpin.element.Bunny;
import jumpin.element.ElementType;
import jumpin.element.Fox;
import jumpin.element.GameElement;
import jumpin.element.Mushroom;
import jumpin.spot.Spot;
import solver.Solver;
import view.EditorView;
import view.JumpInView;

public class EditorController {
	private EditorView editorView;
	private JumpInView view;

	private Optional<GameElement> elementToPlace;
	private Board board;
	final private LevelRepository userLevelRepository;
	final private Map<ElementType, Integer> countOfElementAvailable;
	
	/**
	 * The editorView Constructor.
	 * @param view: Instance of JumpInView to get new editor
	 */
	public EditorController(final JumpInView view) {
		this.view = view;
		this.userLevelRepository = LevelRepository.getUserLevelRepository();
		this.editorView = view.getNewEditor()
				.setMainMenuListener(e -> view.show(JumpInView.MENU_PANEL))
				.setConfirmBuildListener(this::onConfirmBuildClick)
				.setResetButtonListener(e -> setupNewBoard())
				.setAddBunnyButtonListener(this:: onAddBunnyClick)
				.setAddFoxHorizontalButtonListener(this::onAddFoxHorizontalClick)
				.setAddFoxVerticalButtonListener(this::onAddFoxVerticalClick)
				.setAddMushroomButtonListener(this::onAddMushroomClick)
				.setGridButtonListener(this::onGridButtonClick);

		this.countOfElementAvailable = new HashMap<>();

		setupNewBoard();
	}
	
	/**
	 * This method set up new board and updates the board as well as element buttons
	 */
	private void setupNewBoard() {
		board = new Board(JumpinConstants.MAX_BUNNY_COUNT);
		elementToPlace = Optional.empty();
		countOfElementAvailable.put(ElementType.BUNNY, JumpinConstants.MAX_BUNNY_COUNT);
		countOfElementAvailable.put(ElementType.FOX, JumpinConstants.MAX_FOX_COUNT);
		countOfElementAvailable.put(ElementType.MUSHROOM, JumpinConstants.MAX_MUSHROOM_COUNT);
		updateBoard();
		updateElementButtons();
	}
	
	/**
	 * This event set the action of confirmBuild button in the editor view
	 * @param e
	 */
	private void onConfirmBuildClick(final ActionEvent e) {
		final Map<GameElement, Position> elementPositions = board.getElementPositions();

		final Level.Builder levelBuilder = Level.builder();
		elementPositions.forEach((element, position) -> levelBuilder.add(element, position));
		final Level level = levelBuilder.build();

		final boolean levelHasSolution = Solver.hasSolution(level);

		if (levelHasSolution) {
			final String levelName = JOptionPane.showInputDialog("Input level name", "");
			userLevelRepository.saveLevel(levelName, level);
			view.show(JumpInView.MENU_PANEL);
		} else {
			showUserMessage("Level built does not have a solution!");
		}
	}
	
	/**
	 * This method set up the action of addBunny button 
	 * @param e: the action event
	 */
	private void onAddBunnyClick(final ActionEvent e) {
		elementToPlace = Optional.of(new Bunny());
	}
	
	/**
	 * This method set up the action of add vertical fox button 
	 * @param e: the action event
	 */
	private void onAddFoxVerticalClick(final ActionEvent e) {
		elementToPlace = Optional.of(new Fox(Orientation.VERTICAL));
	}
	
	/**
	 * This method set up the action of add horizontal fox button 
	 * @param e: the action event
	 */
	private void onAddFoxHorizontalClick(final ActionEvent e) {
		elementToPlace = Optional.of(new Fox(Orientation.HORIZONTAL));
	}
	
	/**
	 * This method set up the action of add mushroom button 
	 * @param e: the action event
	 */
	private void onAddMushroomClick(final ActionEvent e) {
		elementToPlace = Optional.of(new Mushroom());
	}
	
	/**
	 * This method set up the action of the grid buttons
	 * @param e: the action event
	 */
	private void onGridButtonClick(final ActionEvent e) {
		if (!elementToPlace.isPresent()) {
			return;
		}

		for (int i = 0; i < JumpinConstants.BOARD_HEIGHT; i++) {
			for (int j = 0; j < JumpinConstants.BOARD_WIDTH; j++) {
				if (e.getSource() == editorView.getGridButton(i, j)) {
					final Position selectedPosition = new Position(i, j);
					if (canPlace(elementToPlace.get(), selectedPosition)) {
						board.putElement(elementToPlace.get(), selectedPosition);

						decrementCount(elementToPlace.get());

						elementToPlace = Optional.empty();
					} else {
						showUserMessage("Cannot place this here!");
					}
				}
			}
		}

		updateBoard();
	}
	
	/**
	 * This method check if an element can be placed on the board and did not exceed the allowed number of elements on the board
	 * @param element
	 * @param position
	 * @return returns true if the element can be placed
	 */
	private boolean canPlace(final GameElement element, final Position position) {
		final Spot spot = board.getSpot(position);
		boolean spotCanHold = spot.canHold(element);
		boolean haveEnoughOf = countOfElementAvailable.get(element.getType()) > 0;

		if (ElementType.FOX == element.getType()) {
			final Position tailPosition = ((Fox) element).getTailPosition(position);
			final Spot tailSpot = board.getSpot(tailPosition);
			spotCanHold = spotCanHold && tailSpot.canHold(element);
		}

		return spotCanHold && haveEnoughOf;
	}
	
	/**
	 * This method takes decrement a specific element count when placed on the grid.
	 * @param element: gameElement
	 */
	private void decrementCount(final GameElement element) {
		final ElementType type = element.getType();
		final int currentCount = countOfElementAvailable.get(type).intValue();
		countOfElementAvailable.put(type, currentCount - 1);
		updateElementButtons();
	}
	
	/**
	 * This method updates the board
	 */
	private void updateBoard() {
		final String[][] boardElementLabels = board.getBoardElements();
		for (int i = 0; i < boardElementLabels.length; i++) {
			for (int j = 0; j < boardElementLabels[i].length; j++) {
				final String elementLabel = boardElementLabels[i][j];
				final JButton button = editorView.getGridButton(i, j);
				button.setText(elementLabel);
			}
		}

		editorView.updateView();
	}
	
	/**
	 * This method updates elementButtons, it disable the elements buttons 
	 * when the maximum number allowed for each element has been reached
	 */
	private void updateElementButtons() {
		final int bunnyCount = countOfElementAvailable.get(ElementType.BUNNY);
		final int foxCount = countOfElementAvailable.get(ElementType.FOX);
		final int mushroomCount = countOfElementAvailable.get(ElementType.MUSHROOM);

		editorView.toggleAddBunnyButton(bunnyCount > 0);
		editorView.toggleAddFoxButton(foxCount > 0);
		editorView.toggleAddMushroomButton(mushroomCount > 0);
	}
	
	/**
	 * This message takes in a message to be displayed for the user
	 * @param message
	 */
	private void showUserMessage(final String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
