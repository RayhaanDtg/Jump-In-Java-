//LAST MODIFIED: 2019.12.02

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jumpin.common.Position;

public class GameView {
	private final JPanel gamePanel;
	private final JButton mainMenuButton;
	private final JButton undoButton;
	private final JButton redoButton;
	private final JButton showSolutionButton;
	private final JLabel loadingLabel;
	private final BoardView boardView;

	/**
	 * The view for the game play page
	 */
	public GameView() {
		mainMenuButton = new JButton("Main menu");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		showSolutionButton = new JButton("ShowSolution");
		loadingLabel = new JLabel();

		boardView = new BoardView();

		gamePanel = buildGamePanel(boardView.getPanel());
	}

	/**
	 * Returns the panel associated with this view for the event handler to use when
	 * switching between pages
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return gamePanel;
	}

	/**
	 * changes the background color of squares that a given element can move to to a
	 * light gray
	 * 
	 * @param List<Positions>
	 */
	public void hilightHints(List<Position> positions) {
		for (Position p : positions) {
			boardView.getGridButton(p.getRow(), p.getColumn()).setOpaque(true);
			boardView.getGridButton(p.getRow(), p.getColumn()).setBackground(Color.LIGHT_GRAY);
		}
	}

	/**
	 * removes the background color of squares that a given element can move to to a
	 * light gray after the piece has moved
	 */
	public void unhilightHints() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boardView.getGridButton(i, j).setOpaque(false);
			}
		}
	}

	/**
	 * displays loading text whenever a solve board proccess is going on
	 */
	public void displayLoading() {
		loadingLabel.setText("Loading . . . ");
		showSolutionButton.setEnabled(false);
		boardView.removeGridButtonListeners();
	}

	/**
	 * removes the loading text whenever a solve board proccess is done
	 */
	public void hideLoading(final ActionListener al) {
		loadingLabel.setText("");
		showSolutionButton.setEnabled(true);
		boardView.setGridButtonListener(al);
	}

	/**
	 * This gets the undoButton.
	 * 
	 * @return undoButton
	 */
	public GameView setUndoButtonListener(final ActionListener al) {
		undoButton.addActionListener(al);
		return this;
	}

	/**
	 * This method gets the redoButton.
	 * 
	 * @return redoButton
	 */
	public GameView setRedoButtonListener(final ActionListener al) {
		redoButton.addActionListener(al);
		return this;
	}

	/**
	 * This method gets the solution button.
	 * 
	 * @return showSolutionButton
	 */
	public GameView setShowSolutionButtonListener(final ActionListener al) {
		showSolutionButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on main menu button to be
	 * detected.
	 * 
	 * @return view
	 */
	public GameView setMainMenuButtonListener(final ActionListener al) {
		mainMenuButton.addActionListener(al);
		return this;
	}

	/**
	 * changes the visibility of the undo button when the queue is empty
	 */
	public void setUndoButtonVisibility(final boolean isEnabled) {
		undoButton.setEnabled(isEnabled);
	}

	/**
	 * changes the visibility of the redo button when the queue is empty
	 */
	public void setRedoButtonVisibility(final boolean isEnabled) {
		redoButton.setEnabled(isEnabled);
	}

	/**
	 * Returns the button associated to the element position given.
	 *
	 * @param i row
	 * @param j column
	 * @return Button associated to the element position on the board
	 */
	public JButton getGridButton(final int i, final int j) {
		return boardView.getGridButton(i, j);
	}

	/**
	 * calls updateBoard() which checks if any new elements have been added on the
	 * view of the board
	 */
	public void updateBoard() {
		boardView.updateBoard();
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add grid button to be
	 * detected.
	 * 
	 * @return view
	 */
	public GameView setGridButtonListener(final ActionListener al) {
		boardView.setGridButtonListener(al);
		return this;
	}

	/**
	 * this method disables the game features when the solution is displayed
	 */
	public void disableGame() {
		boardView.removeGridButtonListeners();
		showSolutionButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
	}

	/**
	 * adds the game to the main panel and returns it
	 * 
	 * @return gamepanel
	 */
	private JPanel buildGamePanel(final JPanel boardPanel) {
		final JPanel gamePanel = new JPanel(new BorderLayout());

		final JPanel gameMenuPanel = buildGameMenuPanel();
		gamePanel.add(gameMenuPanel, BorderLayout.PAGE_START);

		gamePanel.add(boardPanel, BorderLayout.CENTER);

		return gamePanel;
	}

	private JPanel buildGameMenuPanel() {
		JPanel gameMenuPanel = new JPanel();
		loadingLabel.setText("");
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		showSolutionButton.setEnabled(true);

		gameMenuPanel.add(mainMenuButton);
		gameMenuPanel.add(undoButton);
		gameMenuPanel.add(redoButton);
		gameMenuPanel.add(showSolutionButton);
		gameMenuPanel.add(loadingLabel);

		return gameMenuPanel;
	}
}
