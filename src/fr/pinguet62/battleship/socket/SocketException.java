package fr.pinguet62.battleship.socket;

public final class SocketException extends RuntimeException {

	private static final long serialVersionUID = -4705262888558385280L;

	public SocketException(String message, Throwable cause) {
		super(message, cause);
	}

}
