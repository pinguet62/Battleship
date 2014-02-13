package fr.pinguet62.battleship.socket.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Box;

/** Store positions of {@link Boat}s. */
public final class PositionsDto implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -9107150373490492463L;

    /** The {@link Boat}s. */
    private final Collection<Boat> boats;

    /**
     * Constructor.
     * 
     * @param boats
     *            The {@link Boat}s.
     */
    public PositionsDto(final Collection<Boat> boats) {
	this.boats = boats;
    }

    /**
     * Gets the {@link Boat}s.
     * 
     * @return The {@link Boat}s.
     */
    public Collection<Boat> getBoats() {
	return boats;
    }

    @Override
    public String toString() {
	String lineSeparator = System.getProperty("line.separator");
	StringBuilder sBuilder = new StringBuilder();
	for (Iterator<Boat> it = boats.iterator(); it.hasNext();) {
	    Boat boat = it.next();
	    Box[] boxs = boat.getBoxs();
	    if (boxs.length == 0)
		sBuilder.append(boat.getName() + " ?");
	    else
		sBuilder.append(String.format("%s - %s to %s", boat.getName(),
			boxs[0], boxs[boxs.length - 1]));
	    if (it.hasNext())
		sBuilder.append(lineSeparator);
	}
	return super.toString();
    }

}
