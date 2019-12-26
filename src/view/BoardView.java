//LAST MODIFIED: 2019.12.02

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import jumpin.common.JumpinConstants;

public class BoardView {
	private final JPanel boardPanel;
	private final JButton[][] gridButtons;

	// Setting the elements images
	final ImageIcon iconHole = new ImageIcon("images/Hole.png");
	final ImageIcon iconFoxHead = new ImageIcon("images/FoxHead.png");
	final ImageIcon iconFoxTail = new ImageIcon("images/FoxButt.png");
	final ImageIcon iconFoxHead_H = new ImageIcon("images/FoxHead_H.png");
	final ImageIcon iconFoxTail_H = new ImageIcon("images/FoxButt_H.png");
	final ImageIcon iconMushroom = new ImageIcon("images/mushroom.png");
	final ImageIcon iconBunny = new ImageIcon("images/BunnyWhite.png");
	final ImageIcon iconHill = new ImageIcon("images/HILL.png");
	final ImageIcon bunnyOnHill = new ImageIcon("images/BunnyHill.png");
	final ImageIcon bunnyInHole = new ImageIcon("images/BunnyHole.png");

	public BoardView() {
		boardPanel = new JPanel(new GridLayout(JumpinConstants.BOARD_HEIGHT, JumpinConstants.BOARD_WIDTH));
		gridButtons = new JButton[JumpinConstants.BOARD_HEIGHT][JumpinConstants.BOARD_WIDTH];

		buildBoardPanel();
	}

	public JPanel getPanel() {
		return boardPanel;
	}

	/**
	 * Returns the button associated to the element position given.
	 *
	 * @param i row
	 * @param j column
	 * @return Button associated to the element position on the board
	 */
	public JButton getGridButton(final int i, final int j) {
		return gridButtons[i][j];
	}

	public void setGridButtonListener(final ActionListener al) {
		for (int i = 0; i < gridButtons.length; i++) {
			for (int j = 0; j < gridButtons[i].length; j++) {
				gridButtons[i][j].addActionListener(al);
			}
		}
	}

	public void removeGridButtonListeners() {
		for (int i = 0; i < gridButtons.length; i++) {
			for (int j = 0; j < gridButtons[i].length; j++) {
				for (final ActionListener al : gridButtons[i][j].getActionListeners()) {
					gridButtons[i][j].removeActionListener(al);
				}
			}
		}
	}

	public void updateBoard() {
		for (int i = 0; i < gridButtons.length; i++) {
			for (int j = 0; j < gridButtons[i].length; j++) {
				setButtonImage(gridButtons[i][j]);
			}
		}
	}

	private JPanel buildBoardPanel() {
		boardPanel.setBackground(Color.green);

		// Adding the buttons in the 2-D Array
		for (int i = 0; i < JumpinConstants.BOARD_HEIGHT; i++) {
			for (int j = 0; j < JumpinConstants.BOARD_WIDTH; j++) {
				final JButton gridButton = new JButton();
				gridButton.setOpaque(false);
				gridButton.setContentAreaFilled(false);
				gridButton.setBorderPainted(true);
				gridButtons[i][j] = gridButton;
				boardPanel.add(gridButtons[i][j]);
			}
		}

		// Setting the minimum size of the game frame
		boardPanel.setMinimumSize(new Dimension(665, 710));
		boardPanel.setLocation(500, 0);
		boardPanel.setVisible(true);
		return boardPanel;
	}

	/**
	 * Sets the element and board graphics for the buttons.
	 * 
	 * @param button
	 * @param str
	 */
	public void setButtonImage(JButton button) {
		String str = button.getText();
		if (str == null) {
			button.setIcon(null);
		} else {
			str = str.trim();
			if (str.equals("^")) {
				button.setIcon(iconHill);
			} else if (str.equals("O")) {
				button.setIcon(iconHole);
			} else if (str.indexOf("FTV") >= 0) {
				button.setIcon(iconFoxTail);
			} else if (str.indexOf("FHV") >= 0) {
				button.setIcon(iconFoxHead);
			} else if (str.indexOf("FTH") >= 0) {
				button.setIcon(iconFoxTail_H);
			} else if (str.indexOf("FHH") >= 0) {
				button.setIcon(iconFoxHead_H);
			} else if (str.indexOf("M") >= 0) {
				button.setIcon(iconMushroom);
			} else if (str.indexOf("B") >= 0) {
				try {
					if (button.getIcon().equals(iconHill)) {
						button.setIcon(bunnyOnHill);
					} else if (button.getIcon().equals(iconHole)) {
						button.setIcon(bunnyInHole);
					}
				} catch (NullPointerException e) {
					button.setIcon(iconBunny);
				}
			}
		}
	}
}
