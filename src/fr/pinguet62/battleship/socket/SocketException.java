package fr.pinguet62.battleship.socket;

/** {@link Exception} threw when an error occurred in the {@link Socket}. */
public final class SocketException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = -4705262888558385280L;

    /**
     * Constructor.
     * 
     * @param message
     *            The message.
     * @param cause
     *            The cause.
     */
    public SocketException(String message, Throwable cause) {
	super(message, cause);
    }

}
