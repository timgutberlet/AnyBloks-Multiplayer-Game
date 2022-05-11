package game.view;

import game.model.polygon.PolySquare;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class SquarePolyPane extends GridPane {

	private PolySquare poly;

	public SquarePolyPane(PolySquare poly) {
		this.poly = poly;
		setPoly();
	}

	/**
	 * Method that draws a square with a specific color at the coordinates i,j
	 *
	 * @param i
	 * @param j
	 * @param color
	 */
	public void setSquare(int i, int j, Color color) {
		Rectangle r = new Rectangle(7, 7);
		r.setFill(color);
		this.add(r, i, j);
	}

	/**
	 * Method that draws a poly This is done by a double for loop, which covers the maximum height as
	 * well as the maximum width of a square poly
	 */
	public void setPoly() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (poly.containsField(i, j)) {
					setSquare(i, j, poly.getJavaColor());
				} else {
					setSquare(i, j, Color.TRANSPARENT);
				}

			}
		}
	}
}
