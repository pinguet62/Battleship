package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** A cruiser. */
public final class Cruiser extends Boat {

    /** Default constructor. */
    public Cruiser() {
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public Cruiser(final Coordinates coordinates, final Alignment alignment) {
	super(coordinates, alignment);
    }

    @Override
    public String getName() {
	return "Cruiser";
    }

    @Override
    public int getSize() {
	return 4;
    }

}
