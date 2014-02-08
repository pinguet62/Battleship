package fr.pinguet62.battleship.view.parameters;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.pinguet62.battleship.model.boat.Boat;

/** {@link JSpinner} where user can choose the number of a {@link Boat} type. */
public final class BoatClassSpinner extends JSpinner {

    /** Serial version UID. */
    private static final long serialVersionUID = 692931075920605484L;

    /** The {@link Boat} class. */
    private final Class<? extends Boat> boatClass;

    /**
     * Constructor.
     * 
     * @param boatClass
     *            The {@link Boat} class.
     */
    public BoatClassSpinner(final Class<? extends Boat> boatClass) {
	super(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
	this.boatClass = boatClass;
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
     * Gets the integer value.
     * 
     * @return The integer value.
     */
    public int getIntValue() {
	return (Integer) getValue();
    }

}
