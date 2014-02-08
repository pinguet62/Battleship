package fr.pinguet62.battleship.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Fleet;
import fr.pinguet62.battleship.view.parameters.BoatClassSpinner;

public final class Game {

	private final Collection<BoatClassSpinner> boatEntries;

	private List<Boat> boats = new ArrayList<Boat>();

	private Fleet fleet = new Fleet(this);

	private final int height;

	private Fleet opponent;

	private final int width;

	public Game(final int width, final int height,
			final Collection<BoatClassSpinner> boatEntries) {
		this.width = width;
		this.height = height;
		this.boatEntries = boatEntries;
	}

	public List<Boat> getBoats() {
		return boats;
	}

	public Fleet getFleet() {
		return fleet;
	}

	public int getHeight() {
		return height;
	}

	public Fleet getOpponent() {
		return opponent;
	}

	public int getWidth() {
		return width;
	}

}
