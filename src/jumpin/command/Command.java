package jumpin.command;

import jumpin.JumpInModel;
/**
 * Interface to implement a changeable type of action- either undo or redo.
 */
public interface Command {
	/**
	 * Executes the command.
	 */

	public void execute(final JumpInModel game);

	/**
	 * undoes the command.
	 */

	public void undo(final JumpInModel game);

}
