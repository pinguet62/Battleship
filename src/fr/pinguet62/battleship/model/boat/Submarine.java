package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** A submarine. */
public final class Submarine extends Boat {

    /** Default constructor. */
    public Submarine() {
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public Submarine(final Coordinates coordinates, final Alignment alignment) {
	super(coordinates, alignment);
    }

    @Override
    public String getName() {
	return "Submarine";
    }

    @Override
    public int getSize() {
	return 3;
    }

}
