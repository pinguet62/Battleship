package fr.pinguet62.battleship.model;

import fr.pinguet62.battleship.model.grid.Fleet;
import fr.pinguet62.battleship.model.socket.ThreadSocket;

/** General model of this game. */
public final class Game {

    /** The height. */
    private final int height;

    /** The current user's {@link Fleet}. */
    private final Fleet myFleet;

    /** The opponent user's {@link Fleet}. */
    private final Fleet opponentFleet;

    /** The {@link ThreadSocket}. */
    private final ThreadSocket threadSocket = new ThreadSocket();

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
    public Game(final int width, final int height) {
	this.width = width;
	this.height = height;

	myFleet = new Fleet(this);
	opponentFleet = new Fleet(this);
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
     * Gets the current user's {@link Fleet}.
     * 
     * @return The {@link Fleet}.
     */
    public Fleet getMyFleet() {
	return myFleet;
    }

    /**
     * Gets the opponent user's {@link Fleet}.
     * 
     * @return The {@link Fleet}.
     */
    public Fleet getOpponentFleet() {
	return opponentFleet;
    }

    /**
     * Gets the {@link ThreadSocket}.
     * 
     * @return The {@link ThreadSocket}.
     */
    public ThreadSocket getThreadSocket() {
	return threadSocket;
    }

    /**
     * Gets the width.
     * 
     * @return The width.
     */
    public int getWidth() {
	return width;
    }

}
