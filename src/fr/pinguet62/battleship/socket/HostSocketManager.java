package fr.pinguet62.battleship.socket;

import java.net.Socket;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.ParametersDto;

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
     * <ol>
     * <li>Create the {@link MyThread} and run this.</li>
     * <li>After guest connection, send {@link ParametersDto} to guest.</li>
     * <li>After sending, execute the {@link Runnable} on parameter.</li>
     * <ol>
     * 
     * @param onParametersSent
     *            The {@link Runnable} to execute at the end.
     */
    public void waitClientConnection(final Runnable onParametersSent) {
	// Thread
	HostThreadSocket hostThreadSocket = new HostThreadSocket(port);
	threadSocket = hostThreadSocket;
	hostThreadSocket.setOnGuestConnectedListener(new Runnable() {
	    /** Send {@link ParametersDto} to guest. */
	    @Override
	    public void run() {
		ParametersDto parameters = new ParametersDto(game.getWidth(),
			game.getHeight(), game.getBoatEntries());
		threadSocket.send(parameters);
		onParametersSent.run();
	    }
	});
	threadSocket.start();
    }

}