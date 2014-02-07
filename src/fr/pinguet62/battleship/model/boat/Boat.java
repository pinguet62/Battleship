package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** The abstract boat. */
public abstract class Boat {

    /** The {@link Alignment}. */
    private final Alignment alignment;

    /** The {@link Box} on which is placed the boat. */
    private final Box[] boxs = new Box[getSize()];

    /** Default constructor. */
    public Boat() {
	alignment = null;
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public Boat(final Coordinates coordinates, final Alignment alignment) {
	for (int i = 0; i < getSize(); i++) {
	    int sensX = Alignment.HORIZONTAL.equals(alignment) ? 1 : 0;
	    int sensY = Alignment.VERTICAL.equals(alignment) ? 1 : 0;
	    Coordinates coordinatesBox = new Coordinates(coordinates.getX()
		    + (sensX * i), coordinates.getY() + (sensY * i));
	    boxs[i] = new Box(coordinatesBox, this);
	}
	this.alignment = alignment;
    }

    /**
     * Gets the {@link Alignment}.
     * 
     * @return The {@link Alignment}.
     */
    public Alignment getAlignment() {
	return alignment;
    }

    /**
     * Gets the {@link Box} at an index.
     * 
     * @param i
     *            The index.
     * @return The {@link Box}.
     */
    public Box getBox(final int i) {
	return boxs[i];
    }

    /**
     * Gets the {@link Box}s.
     * 
     * @return The {@link Box}s.
     */
    public Box[] getBoxs() {
	return boxs;
    }

    /**
     * Gets the name.
     * 
     * @return The name.
     */
    public abstract String getName();

    /**
     * Gets the size.
     * 
     * @return The size.
     */
    public abstract int getSize();

    /**
     * Tests if is sunk : if all {@link Box} are {@link Box#attack()}.
     * 
     * @return Result.
     */
    public boolean isSunk() {
	for (Box box : boxs)
	    if (!box.isAttacked())
		return false;
	return true;
    }

}
