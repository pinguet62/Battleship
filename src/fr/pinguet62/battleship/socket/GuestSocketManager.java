package fr.pinguet62.battleship.socket;

import java.net.Socket;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.utils.Consumer;

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
     * Connection to host.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute after connection to host, and
     *            before {@link AbstractSocketManager} starting.
     */
    @Override
    public void connect(final Runnable onConnected) {
	threadSocket = new GuestThreadSocket(port);
	onConnected.run();
	threadSocket.start();
    }

}