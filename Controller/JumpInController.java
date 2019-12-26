package controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.LevelRepository;
import view.JumpInView;
import view.MainMenuView;

public class JumpInController {
	private final JumpInView view;
	private final LevelRepository premadeLevelRepository;
	private final LevelRepository userLevelRepository;
	
	/**
	 * This is the JumpInController constructor
	 * @param view: JumpInView
	 */
	public JumpInController(JumpInView view) {
		this.view = view;
		this.premadeLevelRepository = LevelRepository.getPremadeLevelRepository();
		this.userLevelRepository = LevelRepository.getUserLevelRepository();

		setupMainMenuButtons(view.getMainMenu());

		view.show(JumpInView.MENU_PANEL);
	}

	private void setupMainMenuButtons(final MainMenuView mainMenu) {
		mainMenu.setupEasyButton(e -> startGame(premadeLevelRepository, LevelRepository.EASY_LEVEL_NAME))
				.setupMediumButton(e -> startGame(premadeLevelRepository, LevelRepository.MEDIUM_LEVEL_NAME))
				.setupHardButton(e -> startGame(premadeLevelRepository, LevelRepository.HARD_LEVEL_NAME))
				.setupBuildLevelButton(this::onBuildLevelClick)
				.setupLoadButton(this::onLoadLevelClick);
	}
	
	/**
	 * This method starts the game
	 * @param levelRepository
	 * @param levelName
	 */
	private void startGame(final LevelRepository levelRepository, final String levelName) {
		final Level level = levelRepository.loadLevel(levelName);
		final JumpInModel game = new JumpInModel(level);
		new GameController(view, game);
		view.show(JumpInView.GAME_PANEL);
	}
	
	/**
	 * This method sets up the action of build Level button
	 * @param e: action event
	 */
	private void onBuildLevelClick(final ActionEvent e) {
		new EditorController(view);
		view.show(JumpInView.EDITOR_PANEL);
	}
	
	/**
	 * This method sets up the action of load a level button
	 * @param e: action event
	 */
	private void onLoadLevelClick(final ActionEvent e) {
		final List<String> levelNames = userLevelRepository.getLevelNames();
		final String[] levelNameArray = levelNames.toArray(new String[levelNames.size()]);
		final JComboBox<String> levelSelector = new JComboBox<String>(levelNameArray);
		final String[] options = {"Ok", "Cancel"};

		final int selectedOption = JOptionPane.showOptionDialog(null,
				levelSelector,
				"Select a Level!",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[1]);

		if (selectedOption == 0) {
			final String levelName = (String) levelSelector.getSelectedItem();
			startGame(userLevelRepository, levelName);
		}
	}
}