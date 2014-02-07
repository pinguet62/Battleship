package fr.pinguet62.battleship.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class BoxView extends JButton {

    /** The {@link Color} when the attack is out of {@link Boat}. */
    public static Color FAILED = Color.BLACK;

    /** Serial version UID. */
    private static final long serialVersionUID = -4404473579692189048L;

    /** The {@link Color} when the attack touches a {@link Boat}. */
    public static Color TOUCHED = Color.RED;

    /** The {@link Color} when {@link Box} doesn't be attacked. */
    public static Color WATER = Color.BLUE;

    /**
     * Gets the {@link Color} of the {@link Box}.
     * 
     * @param box
     *            The {@link Box}.
     * @return The {@link Color}
     */
    public static Color getColor(final Box box) {
	if (!box.isAttacked())
	    return BoxView.WATER;
	else if (box.getBoat() != null)
	    return BoxView.TOUCHED;
	else
	    return BoxView.FAILED;
    }

    /** The {@link Coordinates}. */
    private final Coordinates coordinates;

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates}.
     */
    public BoxView(final Coordinates coordinates) {
	super(String.format("(%d;%d)", coordinates.getX(), coordinates.getY()));
	this.coordinates = coordinates;
	setPreferredSize(new Dimension(30, 30));
    }

    /**
     * Gets the {@link Coordinates}.
     * 
     * @return The {@link Coordinates}.
     */
    public Coordinates getCoordinates() {
	return coordinates;
    }

}
