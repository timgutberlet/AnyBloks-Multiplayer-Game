package game.view;

import engine.handler.InputHandler;
import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.field.FieldSquare;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class BoardSquarePane extends GridPane implements BoardPane {

	private final List<Rectangle> squares;

	private BoardSquare boardSquare;

	private InputHandler inputHandler;

	public BoardSquarePane(BoardSquare boardSquare, InputHandler inputHandler) {
		this.inputHandler = inputHandler;
		this.boardSquare = boardSquare;
		squares = new ArrayList<>();
		setBoard();
		setStyle();
	}

	/**
	 * Method that draws a square at the coordinates {row,column}
	 *
	 * @param color
	 * @param row
	 * @param column
	 */
	public void setSquare(Color color, int row, int column) {
		Rectangle r = new Rectangle(30, 30);
		r.setFill(color);
		squares.add(r);
		this.add(r, column, row);
		//inputHandler.registerPolyPressed(r);
		inputHandler.registerBoardHovered(r);
		inputHandler.registerBoardLeft(r);
		inputHandler.registerBoardPressed(r);
	}

	@Override
	public void setBoard() {
		for (int row = 0; row < boardSquare.SIZE; row++) {
			for (int column = 0; column < boardSquare.SIZE; column++) {
				int[] pos = {row, column};
				setSquare(boardSquare.getJavaColor(pos), row, column);

			}
		}
	}

	@Override
	public void repaint(Board board) {
		BoardSquare help = (BoardSquare) board;
		for (FieldSquare fs : help.getBoard()) {
			this.setSquare(fs.getJavaColor(), fs.getPos()[0], fs.getPos()[1]);
		}
	}

	final void setStyle() {
		this.setGridLinesVisible(true);
	}
}
