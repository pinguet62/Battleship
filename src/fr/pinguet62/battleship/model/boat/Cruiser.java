package fr.pinguet62.battleship.model.boat;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class Cruiser extends Boat {

	public Cruiser() {
	}
	
	public Cruiser(final Coordinates coordinates, final Alignment alignment) {
		super(coordinates, alignment);
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public String getName() {
		return "Cruiser";
	}

}