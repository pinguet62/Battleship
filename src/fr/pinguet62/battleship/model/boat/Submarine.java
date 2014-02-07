package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class Submarine extends Boat {

	/** Default constructor. */
	public Submarine() {
	}

	public Submarine(final Coordinates coordinates, final Alignment alignment) {
		super(coordinates, alignment);
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public String getName() {
		return "Submarine";
	}

}
