package fr.pinguet62.battleship.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class BoxView extends JButton {

	public static Color FAILED = Color.BLACK;

	private static final long serialVersionUID = -4404473579692189048L;

	public static Color TOUCHED = Color.RED;

	public static Color WATER = Color.BLUE;

	public static Color getColor(Box box) {
		if (!box.isAttacked()) {
			return WATER;
		} else if (box.getBoat() != null) {
			return TOUCHED;
		} else {
			return FAILED;
		}
	}

	private final Coordinates coordinates;

	public BoxView(Coordinates coordinates) {
		super(String.format("(%d;%d)", coordinates.getX(), coordinates.getY()));
		this.coordinates = coordinates;
		this.setPreferredSize(new Dimension(30, 30));
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

}
