package fr.pinguet62.battleship.model;

import java.util.Collection;

import fr.pinguet62.battleship.model.grid.Fleet;
import fr.pinguet62.battleship.model.socket.SocketManager;
import fr.pinguet62.battleship.model.socket.dto.ParametersDto.BoatEntry;

/** General model of this game. */
public final class Game {

    /** The height. */
    private final int height;

    /** The current user's {@link Fleet}. */
    private final Fleet myFleet;

    /** The opponent user's {@link Fleet}. */
    private final Fleet opponentFleet;

    /** The {@link BoatEntry}s. */
    private Collection<BoatEntry> boatEntries;

    /** The {@link SocketManager}. */
    private final SocketManager socketManager = new SocketManager(this);

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
     * Gets the {@link SocketManager}.
     * 
     * @return The {@link SocketManager}.
     */
    public SocketManager getSocketManager() {
	return socketManager;
    }

    /**
     * Gets the width.
     * 
     * @return The width.
     */
    public int getWidth() {
	return width;
    }

    /**
     * Sets the {@link BoatEntry}s.
     * 
     * @param boatEntries
     *            The {@link BoatEntry}s.
     */
    public void setBoatEntries(final Collection<BoatEntry> boatEntries) {
	this.boatEntries = boatEntries;
    }

}
