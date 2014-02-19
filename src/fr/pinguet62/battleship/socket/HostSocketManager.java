package fr.pinguet62.battleship.socket;

import java.net.ServerSocket;
import java.net.Socket;

import fr.pinguet62.battleship.model.Game;

/** Class who interacts with guest {@link Socket}. */
public final class HostSocketManager extends AbstractSocketManager {

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public HostSocketManager(final Game game) {
	super(game);
    }

    /**
     * Create the {@link HostThreadSocket} and {@link ServerSocket}.<br />
     * Start the {@link HostThreadSocket}.<br />
     * When guest connected, execute the method.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute after guest connection.
     */
    @Override
    public void connect(final Runnable onConnected) {
	HostThreadSocket hostThreadSocket = new HostThreadSocket(inetAddress,
		port);
	hostThreadSocket.setOnConnectedListener(onConnected);
	hostThreadSocket.start();
	threadSocket = hostThreadSocket;
    }

}