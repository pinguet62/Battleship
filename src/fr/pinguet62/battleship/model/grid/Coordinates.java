package fr.pinguet62.battleship.model.grid;

import java.io.Serializable;

/** Coordinates in grid. */
public final class Coordinates implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -799359244428776811L;

    /** The horizontal index. */
    private int x;

    /** The vertical index. */
    private int y;

    /**
     * Constructor.
     * 
     * @param x
     *            The horizontal index.
     * @param y
     *            The vertical index.
     */
    public Coordinates(final int x, final int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Gets the horizontal index.
     * 
     * @return The horizontal index.
     */
    public int getX() {
	return x;
    }

    /**
     * Gets the vertical index.
     * 
     * @return The vertical index.
     */
    public int getY() {
	return y;
    }

    /**
     * Sets the horizontal index.
     * 
     * @param x
     *            The horizontal index.
     */
    public void setX(final int x) {
	this.x = x;
    }

    /**
     * Sets the vertical index.
     * 
     * @param y
     *            The vertical index.
     */
    public void setY(final int y) {
	this.y = y;
    }

    @Override
    public String toString() {
	return String.format("x=%d ; y=%d", x, y);
    }

}
