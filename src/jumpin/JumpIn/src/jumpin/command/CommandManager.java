package jumpin.command;

import java.util.Stack;

import jumpin.JumpInModel;

/**
 * The CommandManager class is responsible for tracking, executing, undoing, and
 * redoing Command implementations.
 *
 */
public class CommandManager {
	private JumpInModel game;
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	public CommandManager(final JumpInModel game) {
		this.game = game;
	}

	/**
	 * This method executes a command
	 * 
	 * @param command
	 */
	public void executeCommand(Command command) {
		command.execute(game);
		undoStack.push(command);
		redoStack.clear();
	}

	/**
	 * This method checks if a undo is available.
	 * 
	 * @return boolean
	 */
	public boolean isUndoAvailable() {
		return !undoStack.empty();
	}

	/**
	 * This method undoes a command
	 */
	public void undo() {
		if (!isUndoAvailable())
			return;

		Command command = undoStack.pop();
		command.undo(game);
		redoStack.push(command);
	}

	/**
	 * This method checks if a redo is available
	 * 
	 * @return boolean
	 */
	public boolean isRedoAvailable() {
		return !redoStack.empty();
	}

	public void redo() {
		if (!isRedoAvailable())
			return;

		Command command = redoStack.pop();
		command.execute(game);
		undoStack.push(command);
	}

}
