package fr.pinguet62.battleship.view.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Coordinates;

/** A box in grid. */
public final class BoxView extends JButton {

    /** The state of a {@link BoxView}. */
    public static enum State {

	/** Attack was out of {@link Boat}. */
	FAILED(Color.BLACK), /** The attack touched a {@link Boat}. */
	TOUCHED(Color.RED), /** Not attack in this {@link BoxView}. */
	WATER(Color.BLUE);

	/** The {@link Color}. */
	private final Color color;

	/**
	 * Constructor.
	 * 
	 * @param color
	 *            The {@link Color}.
	 */
	State(final Color color) {
	    this.color = color;
	}

	/**
	 * Gets the {@link Color}.
	 * 
	 * @return The {@link Color}.
	 */
	public Color getColor() {
	    return color;
	}

    }

    /** Serial version UID. */
    private static final long serialVersionUID = -4404473579692189048L;

    /** The {@link Coordinates}. */
    private final Coordinates coordinates;

    /** The {@link State}. */
    private State state = State.WATER;

    /**
     * Constructor.
     * 
     * @param coordinates
     *            The {@link Coordinates}.
     */
    public BoxView(final Coordinates coordinates) {
	super(String.format("(%d;%d)", coordinates.getX(), coordinates.getY()));
	this.coordinates = coordinates;
	setPreferredSize(new Dimension(30, 30));
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
     * Gets the {@link State}.
     * 
     * @return The {@link State}.
     */
    public State getState() {
	return state;
    }

    /**
     * Sets the {@link State}.
     * 
     * @param state
     *            The {@link State}.
     */
    public void setState(final State state) {
	setEnabled(state.equals(State.WATER));
	this.state = state;
	setBackground(state.getColor());
    }

}
