package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** A torpedo boat. */
public final class TorpedoBoat extends Boat {

    /** Default constructor. */
    public TorpedoBoat() {
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public TorpedoBoat(final Coordinates coordinates, final Alignment alignment) {
	super(coordinates, alignment);
    }

    @Override
    public String getName() {
	return "Torpedo boat";
    }

    @Override
    public int getSize() {
	return 2;
    }

}
