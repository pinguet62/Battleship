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
     * Connect to host.
     * 
     * @param onConnected
     *            The method to execute after connection.
     */
    public void connectToHost(final Consumer<ParametersDto> onConnected) {
	threadSocket = new GuestThreadSocket(port);
	threadSocket.setOnParametersReceivedListener(onConnected);
	threadSocket.start();
    }

}