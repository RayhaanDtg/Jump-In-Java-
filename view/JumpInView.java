//LAST MODIFIED: 2019.12.02

package view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This sets up the view of the game with the physical board and element images.
 * This is the view portion of the MVC model, and this has all the components
 * for the buttons and graphics associated with the game.
 */

public class JumpInView {
	public static final String MENU_PANEL = "1";
	public static final String GAME_PANEL = "2";
	public static final String EDITOR_PANEL = "3";

	final JFrame frame = new JFrame();
	final CardLayout cl;
	private JPanel containerPanel;
	private MainMenuView mainMenuView;
	private GameView gameView;
	private EditorView editorView;

	/**
	 * This Constructor, constructs the view of the game.
	 */
	public JumpInView() {
		cl = new CardLayout();
		containerPanel = new JPanel();
		containerPanel.setLayout(cl);

		mainMenuView = new MainMenuView();
		gameView = new GameView();
		editorView = new EditorView();

		containerPanel.add(mainMenuView.getPanel(), MENU_PANEL);
		containerPanel.add(gameView.getPanel(), GAME_PANEL);
		containerPanel.add(editorView.getPanel(), EDITOR_PANEL);

		frame.add(containerPanel);
		frame.setLocation(500, 0);
		// Allows the user to resize the Frame by minimizing and maximizing.
		frame.setResizable(false);
		// Allows the user to exit out of the frame.
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// Packs Everything in to the GUI.
		frame.pack();
		// Sets the title name to JumIn
		frame.setTitle("JumpIn");
		frame.setMinimumSize(new Dimension(665, 710));
	}

	/**
	 * shows the frame that contains the card holder
	 */
	public void show(final String viewName) {
		cl.show(containerPanel, viewName);
		frame.setVisible(true);
	}

	/**
	 * gets the main menu view
	 * 
	 * @returns MainMenuView
	 */
	public MainMenuView getMainMenu() {
		return mainMenuView;
	}

	public GameView getGame() {
		return gameView;
	}

	/**
	 * gets the new game menu view for the game builder
	 * 
	 * @returns MainMenuView
	 */
	public GameView getNewGame() {
		containerPanel.remove(gameView.getPanel());
		gameView = new GameView();
		containerPanel.add(gameView.getPanel(), GAME_PANEL);

		return gameView;
	}

	/**
	 * gets the new editor view for the game builder
	 * 
	 * @returns EditorView
	 */
	public EditorView getNewEditor() {
		containerPanel.remove(editorView.getPanel());
		editorView = new EditorView();
		containerPanel.add(editorView.getPanel(), EDITOR_PANEL);

		return editorView;
	}
}