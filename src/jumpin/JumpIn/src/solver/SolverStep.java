package solver;

import java.util.List;

import jumpin.Level;
import jumpin.command.Command;

public class SolverStep {
	private final Level level;
	private final Command command;
	private final List<Command> previousCommands;

	public SolverStep(final Level level, final Command command, final List<Command> previousCommands) {
		this.level = level;
		this.command = command;
		this.previousCommands = previousCommands;
	}

	public Level getLevel() {
		return level;
	}

	public Command getCommand() {
		return command;
	}

	public List<Command> getPreviousCommands() {
		return previousCommands;
	}
}
