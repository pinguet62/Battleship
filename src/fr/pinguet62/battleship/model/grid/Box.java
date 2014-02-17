package fr.pinguet62.battleship.model.grid;

import fr.pinguet62.battleship.model.boat.Boat;

/** A box consists {@link Fleet}. */
public final class Box {

    /** The result of an attack. */
    public enum AttackResult {
	/** No {@link Boat} touched. */
	FAILED, /**
	 * All {@link Box} of {@link Boat} have been touched and the
	 * {@link Boat} is sunk.
	 */
	SUNK, /** A {@link Box} of {@link Boat} are been touched. */
	TOUCHED;
    }

    /** If is attacked. */
    private boolean attacked = false;

    /** The {@link Boat}. */
    private final Boat boat;

    /** The {@link Coordinates}. */
    private final Coordinates coordinates;

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates}.
     */
    public Box(final Coordinates coordinates) {
	this(coordinates, null);
    }

    /**
     * Full constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates}.
     * @param boat
     *            The {@link Boat}.
     */
    public Box(final Coordinates coordinates, final Boat boat) {
	this.coordinates = coordinates;
	this.boat = boat;
    }

    /**
     * Attack this box.
     * 
     * @return The {@link AttackResult}.
     */
    public AttackResult attack() {
	attacked = true;
	if (boat == null)
	    return AttackResult.FAILED;
	else if (boat.isSunk())
	    return AttackResult.SUNK;
	else
	    return AttackResult.TOUCHED;
    }

    /**
     * Gets the {@link Boat}, <code>null</code> if no {@link Boat} above.
     * 
     * @return The {@link Boat} or <code>null</code>.
     */
    public Boat getBoat() {
	return boat;
    }

    /**
     * Gets the {@link Coordinates}.
     * 
     * @return The {@link Coordinates}.
     */
    public Coordinates getCoordinates() {
	return coordinates;
    }

    /**
     * Test if is attacked.
     * 
     * @return Result.
     */
    public boolean isAttacked() {
	return attacked;
    }

}
