package fr.pinguet62.battleship.socket;

import java.util.ArrayList;
import java.util.Collection;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Coordinates;

/**
 * Stores informations about the game:
 * <ul>
 * <li>the dimension of grid;</li>
 * <li>the {@link Boat}s (with their {@link Coordinates}.</li>
 * </ul>
 */
public final class Positions {

    private final Collection<Coordinates> coordinates = new ArrayList<Coordinates>();

    public Collection<Coordinates> getCoordinates() {
	return coordinates;
    }

}
