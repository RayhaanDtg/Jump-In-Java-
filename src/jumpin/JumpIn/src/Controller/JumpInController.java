package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JOptionPane;
import jumpin.JumpInModel;
import jumpin.command.BoardTurnCommand;
import jumpin.command.Command;
import jumpin.command.CommandManager;
import jumpin.common.Position;
import jumpin.element.GameElement;
import jumpin.exception.IllegalMoveException;
import solver.Solver;
import view.JumpInView;

public class JumpInController implements ActionListener {
	private final JumpInView view;
	private final JumpInModel game;
	private final CommandManager commandManager;

	private Optional<String> solution;
	private Optional<Integer> selectedElementId;

	public JumpInController(JumpInView view, JumpInModel game) {
		this.game = game;
		this.view = view;
		this.commandManager = new CommandManager(game);
		this.solution = Optional.empty();

		view.getUndoButton().addActionListener(this);
		view.getRedoButton().addActionListener(this);
		view.getShowSolutionButton().addActionListener(this);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				view.getElementButton(i, j).addActionListener(this);
			}
		}
		selectedElementId = Optional.empty();

		updateBoard();
	}
	
	/*
	 * updates the board every time a button is pressed
	 */
	public void updateBoard() {
		view.getUndoButton().setEnabled(commandManager.isUndoAvailable());
		view.getRedoButton().setEnabled(commandManager.isRedoAvailable());
		
		String[][] elementLabels = game.getBoardElements();
		for (int i = 0; i < elementLabels.length; i++) {
			for (int j = 0; j < elementLabels[0].length; j++) {
				final String elementLabel = elementLabels[i][j];
				view.getElementButton(i, j).setText(elementLabel);
				view.setButtonImage(view.getElementButton(i, j), elementLabel);
			}
		}

		if (game.isFinished()) {
			JOptionPane.showMessageDialog(null, "Well done!");
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getUndoButton()) {
			commandManager.undo();
		} else if (e.getSource() == view.getRedoButton()) {
			commandManager.redo();
		} else if (e.getSource() == view.getShowSolutionButton()) {
			// TODO Start loading screen
			JOptionPane.showMessageDialog(null, getSolution());
			// Stop loading screen
		} else {
			processElementButtonClick(e);
		}

		updateBoard();
	}

	private void processElementButtonClick(ActionEvent e) {
		final String[][] elements = game.getBoardElements();
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[0].length; j++) {
				if (e.getSource() == view.getElementButton(i, j)) {
					if (!selectedElementId.isPresent()) {
						selectElement(elements, i, j);
					} else {
						moveElement(i, j);
						selectedElementId = Optional.empty();
					}
				}
			}
		}
	}

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
		}
		Map<GameElement, List<Position>> elementMap = game.getElementNextAvailablePositions();
		List<Position> positions = elementMap.get(game.getElementById(selectedElementId.get()));
		view.hilightHints(positions);
	}

	private void moveElement(final int i, final int j) {
		view.unhilightHints();
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
			} catch (final IllegalArgumentException e) {
				solution = Optional.of(e.getMessage());
			}
		}
		return solution.get();
	}
}