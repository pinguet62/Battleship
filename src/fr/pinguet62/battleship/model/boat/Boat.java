package fr.pinguet62.battleship.model.boat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import fr.pinguet62.battleship.model.Alignment;
import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** The abstract boat. */
public abstract class Boat {

    /** The {@link Logger}. */
    private static final Logger LOGGGER = Logger
	    .getLogger(Boat.class.getName());

    /**
     * Gets an instance of the {@link Boat} {@link Class}, <code>null</code> if
     * an error occurs.<br />
     * Use reflection to invoke default constructor.
     * 
     * @param boatClass
     *            The {@link Boat} {@link Class}.
     * @return The {@link Boat} instance.
     */
    public static Boat getInstance(final Class<? extends Boat> boatClass) {
	try {
	    Constructor<? extends Boat> defaultConstructor = boatClass
		    .getConstructor();
	    return defaultConstructor.newInstance();
	} catch (NoSuchMethodException | SecurityException
		| InstantiationException | IllegalAccessException
		| IllegalArgumentException | InvocationTargetException exception) {
	    Boat.LOGGGER.severe(exception.getMessage());
	    return null;
	}
    }

    /** The {@link Alignment}. */
    private final Alignment alignment;

    /** The {@link Box} on which is placed the boat. */
    private final Box[] boxs = new Box[getSize()];

    /** Default constructor. */
    public Boat() {
	alignment = null;
    }

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates} at top-left.
     * @param alignment
     *            The {@link Alignment}.
     */
    public Boat(final Coordinates coordinates, final Alignment alignment) {
	for (int i = 0; i < getSize(); i++) {
	    int sensX = Alignment.HORIZONTAL.equals(alignment) ? 1 : 0;
	    int sensY = Alignment.VERTICAL.equals(alignment) ? 1 : 0;
	    Coordinates coordinatesBox = new Coordinates(coordinates.getX()
		    + (sensX * i), coordinates.getY() + (sensY * i));
	    boxs[i] = new Box(coordinatesBox, this);
	}
	this.alignment = alignment;
    }

    /**
     * Gets the {@link Alignment}.
     * 
     * @return The {@link Alignment}.
     */
    public Alignment getAlignment() {
	return alignment;
    }

    /**
     * Gets the {@link Box}s.
     * 
     * @return The {@link Box}s.
     */
    public Box[] getBoxs() {
	return boxs;
    }

    /**
     * Gets the name.
     * 
     * @return The name.
     */
    public abstract String getName();

    /**
     * Gets the size.
     * 
     * @return The size.
     */
    public abstract int getSize();

    /**
     * Tests if is sunk : if all {@link Box} are {@link Box#attack()}.
     * 
     * @return Result.
     */
    public boolean isSunk() {
	for (Box box : boxs)
	    if (!box.isAttacked())
		return false;
	return true;
    }

}
