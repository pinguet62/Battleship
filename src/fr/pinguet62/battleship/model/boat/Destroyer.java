package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** A destroyer. */
public final class Destroyer extends Boat {

    /** Default constructor. */
    public Destroyer() {
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public Destroyer(final Coordinates coordinates, final Alignment alignment) {
	super(coordinates, alignment);
    }

    @Override
    public String getName() {
	return "Destroyer";
    }

    @Override
    public int getSize() {
	return 3;
    }

}
