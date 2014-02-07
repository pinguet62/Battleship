package fr.pinguet62.battleship.view.parameters;

import fr.pinguet62.battleship.model.boat.Boat;

public final class BoatEntry {

	private final Class<? extends Boat> boatClass;

	private int number;

	public BoatEntry(final Class<? extends Boat> boatClass) {
		this.boatClass = boatClass;
	}

	public Class<? extends Boat> getBoatClass() {
		return boatClass;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

}
