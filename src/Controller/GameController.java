package controller;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import jumpin.JumpInModel;
import jumpin.command.BoardTurnCommand;
import jumpin.command.Command;
import jumpin.command.CommandManager;
import jumpin.common.Position;
import jumpin.element.GameElement;
import jumpin.exception.IllegalMoveException;
import jumpin.exception.NoPossibleSolutionException;
import solver.Solver;
import view.GameView;
import view.JumpInView;

public class GameController {
	private final GameView gameView;
	private final JumpInModel game;
	private final CommandManager commandManager;

	private Optional<String> solution;
	private Optional<Integer> selectedElementId;
	
	/**
	 * This is the constructor of game controller.
	 * @param view: instance of JumpInView
	 * @param game: instance of JumpInmodel
	 */
	public GameController(final JumpInView view, JumpInModel game) {
		this.gameView = view.getNewGame()
				.setUndoButtonListener(this::onUndoClick)
				.setRedoButtonListener(this::onRedoClick)
				.setShowSolutionButtonListener(this::onShowButtonClick)
				.setMainMenuButtonListener(e -> view.show(JumpInView.MENU_PANEL))
				.setGridButtonListener(this::onGridButtonClick);
		this.game = game;
		this.commandManager = new CommandManager(game);
		this.solution = Optional.empty();
		this.selectedElementId = Optional.empty();

		updateBoard();
	}
	
	/**
	 * This method set up the action of undo button
	 * @param e: actionEvent
	 */
	private void onUndoClick(final ActionEvent e) {
		commandManager.undo();
		solution = Optional.empty();
		updateBoard();
	}
	
	/**
	 * This method set up the action of redo button
	 * @param e: actionEvent
	 */
	private void onRedoClick(final ActionEvent e) {
		commandManager.redo();
		solution = Optional.empty();
		updateBoard();
	}
	
	/**
	 * This method set up the action of show solution button
	 * @param e: actionEvent
	 */
	private void onShowButtonClick(final ActionEvent e) {
		gameView.displayLoading();

		final Thread solutionThread = new Thread() {
			@Override
			public void run() {
				final String solutionStr = getSolution();
				JOptionPane.showMessageDialog(null, solutionStr);
				gameView.hideLoading(e -> onGridButtonClick(e));
			};
		};
		solutionThread.start();
	}
	
	/**
	 * This method set up the action of the grid buttons
	 * @param e: actionEvent
	 */
	private void onGridButtonClick(final ActionEvent e) {
		final String[][] elements = game.getBoardElements();
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[0].length; j++) {
				if (e.getSource() == gameView.getGridButton(i, j)) {
					if (!selectedElementId.isPresent()) {
						selectElement(elements, i, j);
					} else {
						moveElement(i, j);
						selectedElementId = Optional.empty();
						solution = Optional.empty();
					}

					updateBoard();
				}
			}
		}
	}

	/**
	 * This method selects the element
	 * @param elements
	 * @param i Row
	 * @param j Column
	 */
	private void selectElement(final String[][] elements, final int i, final int j) {
		if (elements[i][j] == null)
			return;

		String richElementId = elements[i][j].trim();
		// remove whitespaces
		richElementId = richElementId.replaceAll("\u00A0", "");

		String elementId = null;
		if (richElementId.indexOf("B") == 0 ) {
			elementId = richElementId.substring(1).trim();
		} else if (richElementId.indexOf("FH") == 0) {		//if Fox
			elementId = richElementId.substring(3).trim();
		}

		if (elementId != null) {
			selectedElementId = Optional.of(Integer.parseInt(elementId));
			Map<GameElement, List<Position>> elementMap = game.getElementNextAvailablePositions();
			List<Position> positions = elementMap.get(game.getElementById(selectedElementId.get()));
			gameView.hilightHints(positions);
		}
	}
	
	/**
	 * This method moves the selected element 
	 * @param i Row
	 * @param j Column
	 */
	private void moveElement(final int i, final int j) {
		gameView.unhilightHints();
		final Position position = new Position(i, j);
		try {
			final Position currentPosition = game.getElementPosition(selectedElementId.get());
			final GameElement element = game.getElementById(selectedElementId.get());

			final Command commandToExecute = new BoardTurnCommand(element, currentPosition, position);
			commandManager.executeCommand(commandToExecute);
		} catch (IllegalMoveException er) {
			// On illegal moves, simply print and let the user try another one.
			JOptionPane.showMessageDialog(null, er.getMessage());
		}
	}
	
	/**
	 * This method get the solution of a level
	 * @return solution string 
	 */
	private String getSolution() {
		if (!solution.isPresent()) {
			try {
				final List<Command> commandsToSolve = Solver.getSolution(game.getLevel());
				final StringBuilder sb = new StringBuilder();
				sb.append("Solution:");
				commandsToSolve.forEach(command -> {
					sb.append("\n").append(command);
				});
				solution = Optional.of(sb.toString());
			} catch (final NoPossibleSolutionException e) {
				solution = Optional.of(e.getMessage());
			}
		}
		return solution.get();
	}

	
	/**
	 * updates the board every time a button is pressed
	 */
	public void updateBoard() {
		gameView.setUndoButtonVisibility(commandManager.isUndoAvailable());
		gameView.setRedoButtonVisibility(commandManager.isRedoAvailable());
		
		String[][] elementLabels = game.getBoardElements();
		for (int i = 0; i < elementLabels.length; i++) {
			for (int j = 0; j < elementLabels[0].length; j++) {
				final String elementLabel = elementLabels[i][j];
				final JButton button = gameView.getGridButton(i, j);
				button.setText(elementLabel);
			}
		}

		gameView.updateBoard();

		if (game.isFinished()) {
			JOptionPane.showMessageDialog(null, "Well done!");
			gameView.disableGame();
		}	
	}
}
