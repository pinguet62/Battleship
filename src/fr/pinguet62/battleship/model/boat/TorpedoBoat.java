package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class TorpedoBoat extends Boat {

	/** Default constructor. */
	public TorpedoBoat() {
	}
	
	public TorpedoBoat(final Coordinates coordinates, final Alignment alignment) {
		super(coordinates, alignment);
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public String getName() {
		return "Torpedo boat";
	}

}
