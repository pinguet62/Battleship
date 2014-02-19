package fr.pinguet62.battleship.socket.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import fr.pinguet62.battleship.model.boat.Boat;

/** Store positions of {@link Boat}s. */
public final class PositionsDto implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -9107150373490492463L;

    /** The {@link BoatPosition}s. */
    private final Collection<BoatPosition> boatPositions;

    /**
     * Constructor.
     * 
     * @param boatPositions
     *            The {@link BoatPosition}s.
     */
    public PositionsDto(final Collection<BoatPosition> boatPositions) {
	this.boatPositions = boatPositions;
    }

    /**
     * Gets the {@link Boat}s.
     * 
     * @return The {@link Boat}s.
     */
    public Collection<BoatPosition> getBoatPositions() {
	return boatPositions;
    }

    @Override
    public String toString() {
	StringBuilder sBuilder = new StringBuilder("[");
	for (Iterator<BoatPosition> it = boatPositions.iterator(); it.hasNext();) {
	    BoatPosition boatPosition = it.next();
	    sBuilder.append(boatPosition.toString());
	    if (it.hasNext())
		sBuilder.append(", ");
	}
	sBuilder.append("]");
	return sBuilder.toString();
    }

}
