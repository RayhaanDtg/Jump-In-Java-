//LAST MODIFIED: 2019.12.02

package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuView {
	private static final File BG_IMG = new File("images/homepage.png");

	private final JButton easyButton;
	private final JButton mediumButton;
	private final JButton hardButton;
	private final JButton buildLevelButton;
	private final JButton loadLevelButton;

	private JLabel background;
	private final JPanel mainMenuPanel;
	private final JPanel panwbuttons;

	/**
	 * constructs the view for the main menu, this is the view that you will see as
	 * soon as you open the project
	 */
	public MainMenuView() {
		panwbuttons = new JPanel(new BorderLayout());
		mainMenuPanel = new JPanel(new BorderLayout());
		try {
			background = new JLabel(new ImageIcon(ImageIO.read(BG_IMG)));
		} catch (IOException e) {
			throw new IllegalStateException("Error loading the background image.", e);
		}
		easyButton = new JButton("Easy");
		mediumButton = new JButton("Medium");
		hardButton = new JButton("Hard");
		buildLevelButton = new JButton("Build a level");
		loadLevelButton = new JButton("Load a level");

		resizeButtons();
		setupLayout();
		background.setLayout(new BorderLayout());
		background.add(panwbuttons, BorderLayout.PAGE_START);
		mainMenuPanel.add(background);
		mainMenuPanel.setMinimumSize(new Dimension(665, 710));
	}

	/**
	 * constructs the view for the main menu, this is the view that you will see as
	 * soon as you open the project
	 * 
	 * @return the panel that contains the full main menu panel
	 */
	public JPanel getPanel() {
		return mainMenuPanel;
	}

	/**
	 * adds an action listener to the easy level button so that something can happen
	 * when its clicked on
	 * 
	 * @return the view with the listener
	 */
	public MainMenuView setupEasyButton(final ActionListener al) {
		easyButton.addActionListener(al);
		return this;
	}

	/**
	 * adds an action listener to the medium level button so that something can
	 * happen when its clicked on
	 * 
	 * @return the view with the listener
	 */
	public MainMenuView setupMediumButton(final ActionListener al) {
		mediumButton.addActionListener(al);
		return this;
	}

	/**
	 * adds an action listener to the hard level button so that something can happen
	 * when its clicked on
	 * 
	 * @return the view with the listener
	 */
	public MainMenuView setupHardButton(final ActionListener al) {
		hardButton.addActionListener(al);
		return this;
	}

	/**
	 * adds an action listener to the build level button so that something can
	 * happen when its clicked on
	 * 
	 * @return the view with the listener
	 */
	public MainMenuView setupBuildLevelButton(final ActionListener al) {
		buildLevelButton.addActionListener(al);
		return this;
	}

	/**
	 * adds an action listener to the load level button so that something can happen
	 * when its clicked on
	 * 
	 * @return the view with the listener
	 */
	public MainMenuView setupLoadButton(final ActionListener al) {
		loadLevelButton.addActionListener(al);
		return this;
	}

	/**
	 * formats the buttons to be opaque and sized appropriately
	 */
	private void resizeButtons() {
		easyButton.setSize(50, 80);
		easyButton.setOpaque(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setBorderPainted(false);
		mediumButton.setSize(50, 80);
		mediumButton.setOpaque(false);
		mediumButton.setContentAreaFilled(false);
		mediumButton.setBorderPainted(false);
		hardButton.setSize(50, 80);
		hardButton.setOpaque(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setBorderPainted(false);
		buildLevelButton.setSize(100, 100);
		buildLevelButton.setOpaque(false);
		buildLevelButton.setContentAreaFilled(false);
		buildLevelButton.setBorderPainted(false);
		loadLevelButton.setSize(100, 100);
		loadLevelButton.setOpaque(false);
		loadLevelButton.setContentAreaFilled(false);
		loadLevelButton.setBorderPainted(false);
	}

	/**
	 * formats all the buttons on the grid to then be added to the main panel
	 */
	private void setupLayout() {
		final Container cont = new Container();
		final GridBagConstraints gbc = new GridBagConstraints();

		cont.setLayout(new GridBagLayout());

		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.weightx = 0; // c.weightx=0.0;
		gbc.weighty = 0; // c.weighty=0.5;

		JLabel title = new JLabel(" ");
		title.setFont(new Font("Arial BOLD", Font.ITALIC, 255));
		gbc.gridx = 3;
		gbc.gridy = 1;
		cont.add(title, gbc);

		// gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.ipadx = 8;
		cont.add(easyButton, gbc);

		JLabel title2 = new JLabel("    ");
		title2.setFont(new Font("Arial BOLD", Font.ITALIC, 10));
		gbc.gridx = 2;
		gbc.gridy = 2;
		cont.add(title2, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		cont.add(mediumButton, gbc);

		JLabel title3 = new JLabel("     ");
		title3.setFont(new Font("Arial BOLD", Font.ITALIC, 10));
		gbc.gridx = 4;
		gbc.gridy = 2;
		cont.add(title3, gbc);

		gbc.gridx = 5;
		gbc.gridy = 2;
		cont.add(hardButton, gbc);

		JLabel title4 = new JLabel(" ");
		title4.setFont(new Font("Arial BOLD", Font.ITALIC, 140));
		gbc.gridx = 3;
		gbc.gridy = 3;
		cont.add(title4, gbc);

		gbc.gridx = 3;
		gbc.gridy = 3;
		cont.add(buildLevelButton, gbc);

		gbc.gridx = 3;
		gbc.gridy = 4;
		cont.add(loadLevelButton, gbc);

		panwbuttons.setOpaque(false);
		panwbuttons.add(cont, BorderLayout.CENTER);
	}

}
