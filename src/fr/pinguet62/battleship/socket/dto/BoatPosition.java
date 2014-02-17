package fr.pinguet62.battleship.socket.dto;

import java.io.Serializable;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** Store first and last {@link Coordinates} of a {@link Boat} class. */
public final class BoatPosition implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -4451370187604640291L;

    /** The {@link Boat} class. */
    private final Class<? extends Boat> boatClass;

    /** The first {@link Coordinates} of {@link Boat}. */
    private final Coordinates firstCoordinate;

    /** The last {@link Coordinates} of {@link Boat}. */
    private final Coordinates lastCoordinate;

    /**
     * Constructor.
     * 
     * @param boatClass
     *            The {@link Boat} class.
     * @param firstCoordinate
     *            The first {@link Coordinates} of {@link Boat}.
     * @param lastCoordinate
     */
    public BoatPosition(final Class<? extends Boat> boatClass,
	    final Coordinates firstCoordinate, final Coordinates lastCoordinate) {
	this.boatClass = boatClass;
	this.firstCoordinate = firstCoordinate;
	this.lastCoordinate = lastCoordinate;
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
     * Gets the first {@link Coordinates} of {@link Boat}.
     * 
     * @return The first {@link Coordinates} of {@link Boat}.
     */
    public Coordinates getFirstCoordinate() {
	return firstCoordinate;
    }

    /**
     * Gets the last {@link Coordinates} of {@link Boat}.
     * 
     * @return The last {@link Coordinates} of {@link Boat}.
     */
    public Coordinates getLastCoordinate() {
	return lastCoordinate;
    }

    @Override
    public String toString() {
	Boat boat = Boat.getInstance(boatClass);
	return String.format("%s - %s to %s", boat.getName(), firstCoordinate,
		lastCoordinate);
    }

}
