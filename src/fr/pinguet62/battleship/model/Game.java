package fr.pinguet62.battleship.model;

import java.util.Collection;

import fr.pinguet62.battleship.model.grid.Fleet;
import fr.pinguet62.battleship.socket.GuestSocketManager;
import fr.pinguet62.battleship.socket.HostSocketManager;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto.BoatEntry;

/** General model of this game. */
public final class Game {

    /** The {@link BoatEntry}s. */
    private Collection<BoatEntry> boatEntries;

    private final GuestSocketManager guestSocketManager = new GuestSocketManager(
	    this);

    /** The height. */
    private int height = -1;

    /** The {@link HostSocketManager}. */
    private final HostSocketManager hostSocketManager = new HostSocketManager(
	    this);

    /** The current user's {@link Fleet}. */
    private Fleet myFleet;

    /** The opponent user's {@link Fleet}. */
    private Fleet opponentFleet;
    
    /** Player type. */
    public final PlayerType playerType;

    /** The width. */
    private int width = -1;

    /**
     * Constructor.
     * 
     * @param playerType
     *            The {@link PlayerType}.
     */
    public Game(final PlayerType playerType) {
	this.playerType = playerType;
    }

    /**
     * Gets the {@link BoatEntry}s.
     * 
     * @return The {@link BoatEntry}s.
     */
    public Collection<BoatEntry> getBoatEntries() {
	return boatEntries;
    }

    public GuestSocketManager getGuestSocketManager() {
	return guestSocketManager;
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
     * Gets the {@link HostSocketManager}.
     * 
     * @return The {@link HostSocketManager}.
     */
    public HostSocketManager getHostSocketManager() {
	return hostSocketManager;
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
     * Gets the {@link PlayerType}.
     * 
     * @return The {@link PlayerType}.
     */
    public PlayerType getPlayerType() {
	return playerType;
    }

    /**
     * Gets the width.
     * 
     * @return The width.
     */
    public int getWidth() {
	return width;
    }

    /***
     * Initialize the game with the {@link ParametersDto}.
     * 
     * @param parametersDto
     *            The {@link ParametersDto}.
     */
    public void init(final ParametersDto parametersDto) {
	width = parametersDto.getWidth();
	height = parametersDto.getHeight();
	boatEntries = parametersDto.getBoatEntries();

	myFleet = new Fleet(this);
	opponentFleet = new Fleet(this);
    }

}
