package fr.pinguet62.battleship.socket;

import java.util.ArrayList;
import java.util.Collection;

import fr.pinguet62.battleship.model.grid.Coordinates;

public final class Positions {

	private Collection<Coordinates> coordinates = new ArrayList<Coordinates>();

	public Collection<Coordinates> getCoordinates() {
		return coordinates;
	}

}
