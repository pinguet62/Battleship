package fr.pinguet62.battleship.view.positioning;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.boat.Boat;

/** A boat entry associate to a {@link Boat}. */
public final class BoatView extends JButton {

	/** Serial version UID. */
	private static final long serialVersionUID = -334380936888307359L;

	/** The {@link Boat}. */
	private final Boat boat;

	/** If the {@link Boat} is placed. */
	private boolean placed = false;

	/**
	 * Constructor.
	 * 
	 * @param boat
	 *            The {@link Boat}.
	 */
	public BoatView(final Boat boat) {
		this.boat = boat;
		setText(String.format("%s (x%d)", boat.getName(), boat.getSize()));
	}

	/**
	 * Gets the {@link Boat}.
	 * 
	 * @return The {@link Boat}.
	 */
	public Boat getBoat() {
		return boat;
	}

	/**
	 * Tests if the {@link Boat} is placed.
	 * 
	 * @return Result.
	 */
	public boolean isPlaced() {
		return placed;
	}

	/**
	 * Sets if the {@link Boat} is placed.
	 * 
	 * @param placed
	 *            If the {@link Boat} is placed.
	 */
	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

}
