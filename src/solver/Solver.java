package solver;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jumpin.JumpInModel;
import jumpin.Level;
import jumpin.command.BoardTurnCommand;
import jumpin.command.Command;
import jumpin.common.Position;
import jumpin.exception.NoPossibleSolutionException;

/**
 * Game Solver, provides only one solution per game using breadth-first-search 
 * The solution is based on the fastest path to win the game
 */
public class Solver {
	private static final int MAX_SOLVER_ATTEMPTS = 300000;
	
	/**
	 * this method returns a list of available commands to solve the game
	 * @param level, the chosen level 
	 * @return return a solution for the game via a list of commands
	 */
	public static List<Command> getSolution(final Level level) {
		final JumpInModel initialgame = new JumpInModel(level);
		final Queue<SolverStep> stepsToProcess = new LinkedList<>();

		if (initialgame.isFinished()) {
			return new LinkedList<>();
		}

		queueAllPossibleSteps(initialgame, new LinkedList<>(), stepsToProcess);
		
		//while the queue is not empty, get all the possible positions and add them to the queue
		int solverCounter = 0;
		while(!stepsToProcess.isEmpty() && solverCounter < MAX_SOLVER_ATTEMPTS) {
			final SolverStep stepToTry = stepsToProcess.poll();
			
			//Every step has to have its own list of previous commands and its own game.
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

		throw new NoPossibleSolutionException("This level cannot be solved!");
	}

	/**
	 * Checks to see if a level has a solution.
	 *
	 * @param level Level to check
	 * @return True if has a solution
	 */
	public static boolean hasSolution(final Level level) {
		try {
			Solver.getSolution(level);
			return true;
		} catch (final NoPossibleSolutionException e) {
			return false;
		}
	}
	
	/**
	 * This method queue all possible steps
	 * @param game
	 * @param commandsExecuted
	 * @param stepsToProcess
	 */
	private static void queueAllPossibleSteps(final JumpInModel game, final List<Command> commandsExecuted, final Queue<SolverStep> stepsToProcess) {
		// Recreate the game at every step
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
