package jumpin.command;

import jumpin.JumpInModel;
import jumpin.common.Position;
import jumpin.element.GameElement;

public class BoardTurnCommand implements Command {
	private final GameElement selectedElement;
	private final Position previousPosition;
	private final Position nextPosition;

	public BoardTurnCommand(final GameElement element, final Position previousPosition, final Position newPosition) {
		this.selectedElement = element;
		this.previousPosition = previousPosition;
		this.nextPosition = newPosition;
	}

	@Override
	public void execute(final JumpInModel game) {
		game.takeTurn(selectedElement.getId(), nextPosition);
	}

	@Override
	public void undo(final JumpInModel game) {
		game.takeTurn(selectedElement.getId(), previousPosition);
	}

	@Override
	public String toString() {
		return String.format("{%s : %s -> %s}", selectedElement.getFriendlyId(), previousPosition, nextPosition);
	}
}
