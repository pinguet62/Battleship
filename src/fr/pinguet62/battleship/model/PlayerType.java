package fr.pinguet62.battleship.model;

/** Player type: host or guest. */
public enum PlayerType {

    /** Host. */
    HOST(true), /** Guest. */
    GUEST(false);

    /** If is host. */
    private final boolean host;

    /**
     * Constructor.
     * 
     * @param host
     *            If is host.
     */
    PlayerType(boolean host) {
	this.host = host;
    }

    /**
     * Tests if is host.
     * 
     * @return Result.
     */
    public boolean isHost() {
	return host;
    }

    /**
     * Tests if is guest.
     * 
     * @return Result.
     */
    public boolean isGuest() {
	return !host;
    }

}
