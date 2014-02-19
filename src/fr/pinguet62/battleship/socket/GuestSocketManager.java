package fr.pinguet62.battleship.socket;

import java.net.Socket;

import fr.pinguet62.battleship.model.Game;

/** Class who interacts with host {@link Socket}. */
public final class GuestSocketManager extends AbstractSocketManager {

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public GuestSocketManager(final Game game) {
	super(game);
    }

    /**
     * Create the {@link GuestThreadSocket} and {@link Socket} to connect host.<br />
     * If successful connection, execute the method, otherwise throws
     * {@link SocketException}.<br />
     * Start {@link GuestThreadSocket}.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute after connection to host.
     * @throws SocketException
     *             Error during connection to host.
     */
    @Override
    public void connect(final Runnable onConnected) {
	threadSocket = new GuestThreadSocket(inetAddress, port);
	onConnected.run();
	threadSocket.start();
    }

}