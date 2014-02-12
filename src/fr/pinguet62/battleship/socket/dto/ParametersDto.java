package fr.pinguet62.battleship.socket.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import fr.pinguet62.battleship.model.boat.Boat;

/** Parameters of the game, sent to guest player. */
public final class ParametersDto implements Serializable {

    /** A {@link Boat} class with its number. */
    public static class BoatEntry implements Serializable {

	/** Serial version UID. */
	private static final long serialVersionUID = -2669863508918008318L;

	/** The {@link Boat} class. */
	private final Class<? extends Boat> boatClass;

	/** The number. */
	private final int number;

	/**
	 * Constructor.
	 * 
	 * @param boatClass
	 *            The {@link Boat} class.
	 * @param number
	 *            The number.
	 */
	public BoatEntry(final Class<? extends Boat> boatClass, final int number) {
	    if (number < 0)
		throw new IllegalArgumentException("Number can't be negative.");
	    this.boatClass = boatClass;
	    this.number = number;
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
	 * Gets the number.
	 * 
	 * @return The number.
	 */
	public int getNumber() {
	    return number;
	}

	/**
	 * Gets the {@link String} representation.
	 * 
	 * @return The {@link String} representation.
	 */
	@Override
	public String toString() {
	    return String.format("%d %s", number, Boat.getInstance(boatClass)
		    .getName());
	}

    }

    /** Serial version UID. */
    private static final long serialVersionUID = -6954668071575915013L;

    /** The {@link BoatEntry}s. */
    private final Collection<BoatEntry> boatEntries;

    /** The height. */
    private final int height;

    /** The width. */
    private final int width;

    /**
     * Constructor.
     * 
     * @param width
     *            The width.
     * @param height
     *            The height.
     */
    public ParametersDto(final int width, final int height,
	    final Collection<BoatEntry> boatEntries) {
	this.width = width;
	this.height = height;
	this.boatEntries = boatEntries;
    }

    /**
     * Gets the {@link BoatEntry}s.
     * 
     * @return The {@link BoatEntry}s.
     */
    public Collection<BoatEntry> getBoatEntries() {
	return boatEntries;
    }

    /**
     * Gets the height.
     * 
     * @return The height.
     */
    public int getHeight() {
	return height;
    }

    /**
     * Gets the width.
     * 
     * @return The width.
     */
    public int getWidth() {
	return width;
    }

    @Override
    public String toString() {
	StringBuilder sBuilder = new StringBuilder("[");
	for (Iterator<BoatEntry> it = boatEntries.iterator(); it.hasNext();) {
	    BoatEntry boatEntry = it.next();
	    sBuilder.append(boatEntry.toString());
	    if (it.hasNext())
		sBuilder.append(", ");
	}
	return String.format("width=%d, height=%d, boatEntries=%s", width,
		height);
    }
}
