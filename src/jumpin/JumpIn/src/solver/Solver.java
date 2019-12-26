package solver;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.command.BoardTurnCommand;
import jumpin.command.Command;
import jumpin.common.Position;

public class Solver {
	private static final int MAX_SOLVER_ATTEMPTS = 10000000;

	public static List<Command> getSolution(final Level level) {
		final JumpInModel initialgame = new JumpInModel(level);
		final Queue<SolverStep> stepsToProcess = new LinkedList<>();

		if (initialgame.isFinished()) {
			return new LinkedList<>();
		}

		queueAllPossibleSteps(initialgame, new LinkedList<>(), stepsToProcess);

		int solverCounter = 0;
		while(!stepsToProcess.isEmpty() && solverCounter < MAX_SOLVER_ATTEMPTS) {
			final SolverStep stepToTry = stepsToProcess.poll();

			final JumpInModel game = new JumpInModel(stepToTry.getLevel());
			final Command commandToExecute = stepToTry.getCommand();
			final List<Command> commandsExecuted = new LinkedList<>();
			commandsExecuted.addAll(stepToTry.getPreviousCommands());

			commandToExecute.execute(game);
			commandsExecuted.add(commandToExecute);

			if (game.isFinished()) {
				return commandsExecuted;
			}

			queueAllPossibleSteps(game, commandsExecuted, stepsToProcess);

			solverCounter++;
		}

		throw new IllegalArgumentException("This level cannot be solved!");
	}

	private static void queueAllPossibleSteps(final JumpInModel game, final List<Command> commandsExecuted, final Queue<SolverStep> stepsToProcess) {
		final Level level = game.getLevel();
		game.getElementNextAvailablePositions().forEach((element, nextPositions) -> {
			nextPositions.forEach(nextPosition -> {
				final Position currentPosition = game.getElementPosition(element.getId());
				final Command command = new BoardTurnCommand(element, currentPosition, nextPosition);
				final SolverStep step = new SolverStep(level, command, commandsExecuted);
				stepsToProcess.add(step);
			});
		});
	}
}
