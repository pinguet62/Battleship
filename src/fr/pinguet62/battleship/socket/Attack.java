package fr.pinguet62.battleship.socket;

import java.io.Serializable;

import fr.pinguet62.battleship.model.grid.Coordinates;

public class Attack implements Serializable {

	private static final long serialVersionUID = 4311698328134751924L;

	private Coordinates coordinates;

	public Attack(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

}
