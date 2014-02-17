package fr.pinguet62.battleship.view.positioning;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.boat.Boat;

/** A boat entry associate to a {@link Boat}. */
public final class BoatView extends JButton {

    /** Serial version UID. */
    private static final long serialVersionUID = -334380936888307359L;

    /** The {@link Boat} class. */
    private final Class<? extends Boat> boatClass;

    /** If the {@link Boat} is placed. */
    private boolean placed = false;

    /** The {@link Boat} size. */
    private final int boatSize;

    /**
     * Constructor.
     * 
     * @param boatClass
     *            The {@link Boat} class.
     */
    public BoatView(final Class<? extends Boat> boatClass) {
	this.boatClass = boatClass;
	Boat boat = Boat.getInstance(boatClass);
	setText(String.format("%s (x%d)", boat.getName(), boat.getSize()));
	boatSize = boat.getSize();
    }

    /**
     * Gets the {@link Boat} class.
     * 
     * @return The {@link Boat} class.
     */
    public Class<? extends Boat> getBoatClass() {
	return boatClass;
    }

    /**
     * Gets the {@link Boat} size.
     * 
     * @return The {@link Boat} size.
     */
    public int getBoatSize() {
	return boatSize;
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
    public void setPlaced(final boolean placed) {
	this.placed = placed;
    }

}
