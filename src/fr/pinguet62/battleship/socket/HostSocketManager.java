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
     * Create the {@link ServerSocket} into {@link HostThreadSocket} and wait
     * guest connection.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute after guest connection.
     */
    public void connect(final Runnable onConnected) {
	// Thread
	HostThreadSocket hostThreadSocket = new HostThreadSocket(port);
	threadSocket = hostThreadSocket;
	hostThreadSocket.setOnGuestConnectedListener(onConnected);
	// hostThreadSocket.setOnGuestConnectedListener(new Runnable() {
	// /** Send {@link ParametersDto} to guest. */
	// @Override
	// public void run() {
	// ParametersDto parameters = new ParametersDto(game.getWidth(),
	// game.getHeight(), game.getBoatEntries());
	// threadSocket.send(parameters);
	// onParametersSent.run();
	// }
	// });
	threadSocket.start();
    }

}