//LAST MODIFIED: 2019.12.02

package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditorView {

	private final JPanel editorPanel;
	private final BoardView boardView;

	private final JButton confirmBuildButton;
	private final JButton mainMenuButton;
	private final JButton resetButton;

	private final JButton addBunnyButton;
	private final JButton addFoxVerticalButton;
	private final JButton addFoxHorizontalButton;
	private final JButton addMushroomButton;

	/**
	 * The view for the editor/build your own board page
	 */
	public EditorView() {
		editorPanel = new JPanel(new BorderLayout());

		confirmBuildButton = new JButton("Confirm build");
		mainMenuButton = new JButton("Main menu");
		resetButton = new JButton("Reset");

		addBunnyButton = new JButton("Add Bunny");
		addFoxVerticalButton = new JButton("Add Vertical Fox");
		addFoxHorizontalButton = new JButton("Add Horizontal Fox");
		addMushroomButton = new JButton("Add Mushroom");

		final JPanel menuPanel = buildMenuPanel();
		editorPanel.add(menuPanel, BorderLayout.PAGE_START);

		final JPanel elementsPanel = buildElementsPanel();
		editorPanel.add(elementsPanel, BorderLayout.PAGE_END);

		boardView = new BoardView();
		editorPanel.add(boardView.getPanel(), BorderLayout.CENTER);

	}

	/**
	 * Returns the panel associated with this view for the event handler to use when
	 * switching between pages
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return editorPanel;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add grid button to be
	 * detected.
	 * 
	 * @return view
	 */
	public EditorView setGridButtonListener(final ActionListener al) {
		boardView.setGridButtonListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add bunny button to be
	 * detected.
	 * 
	 * @return view
	 */
	public EditorView setAddBunnyButtonListener(final ActionListener al) {
		addBunnyButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add vertical fox button
	 * to be detected.
	 * 
	 * @return view
	 */
	public EditorView setAddFoxVerticalButtonListener(final ActionListener al) {
		addFoxVerticalButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add horizontal fox
	 * button to be detected.
	 * 
	 * @return view
	 */
	public EditorView setAddFoxHorizontalButtonListener(final ActionListener al) {
		addFoxHorizontalButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on add mushroom button to
	 * be detected.
	 * 
	 * @return view
	 */
	public EditorView setAddMushroomButtonListener(final ActionListener al) {
		addMushroomButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on main menu button to be
	 * detected.
	 * 
	 * @return view
	 */
	public EditorView setMainMenuListener(final ActionListener al) {
		mainMenuButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on confirm build button to
	 * be detected.
	 * 
	 * @return view
	 */
	public EditorView setConfirmBuildListener(final ActionListener al) {
		confirmBuildButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the view with a listener associated with this view for the event
	 * handler is created the listener waits for a click on reset button to be
	 * detected.
	 * 
	 * @return view
	 */
	public EditorView setResetButtonListener(final ActionListener al) {
		resetButton.addActionListener(al);
		return this;
	}

	/**
	 * Returns the specific square button on a grid that is clicked.
	 * 
	 * @return JButton
	 */
	public JButton getGridButton(final int i, final int j) {
		return boardView.getGridButton(i, j);
	}

	/**
	 * calls updateBoard() which checks if any new elements have been added
	 */
	public void updateView() {
		boardView.updateBoard();
	}

	/**
	 * Returns the view with where a listener associated with this view for the
	 * event handler is created the listener waits for a click on add bunny button
	 * to be detected.
	 * 
	 * @return view
	 */
	public void toggleAddBunnyButton(final boolean isEnabled) {
		addBunnyButton.setEnabled(isEnabled);
	}

	/**
	 * Disables the addfox button if the max number of foxes has been reached
	 */
	public void toggleAddFoxButton(final boolean isEnabled) {
		addFoxVerticalButton.setEnabled(isEnabled);
		addFoxHorizontalButton.setEnabled(isEnabled);
	}

	/**
	 * Disables the addmushroom button if the max number of mushrooms has been
	 * reached
	 */
	public void toggleAddMushroomButton(final boolean isEnabled) {
		addMushroomButton.setEnabled(isEnabled);
	}

	/**
	 * adds the menu buttons to the menu element, returns that panel container
	 * 
	 * @return JPanel
	 */
	private JPanel buildMenuPanel() {
		final JPanel editorMenu = new JPanel();

		editorMenu.add(mainMenuButton);
		editorMenu.add(confirmBuildButton);
		editorMenu.add(resetButton);

		return editorMenu;
	}

	/**
	 * adds the builder buttons to the menu element, returns that panel container
	 * 
	 * @return JPanel
	 */
	private JPanel buildElementsPanel() {
		final JPanel elementsPanel = new JPanel();

		elementsPanel.add(addBunnyButton);
		elementsPanel.add(addFoxVerticalButton);
		elementsPanel.add(addFoxHorizontalButton);
		elementsPanel.add(addMushroomButton);

		return elementsPanel;
	}
}
