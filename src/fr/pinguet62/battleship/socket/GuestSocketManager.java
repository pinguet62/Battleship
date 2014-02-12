package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.pinguet62.battleship.Consumer;
import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** Class who interacts with guest user. */
public final class GuestSocketManager {

    /** The {@link Game}. */
    private final Game game;

    /** The {@link GuestThread}. */
    private GuestThread guestThread;

    /** The port of {@link Socket}. */
    private int port = -1;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public GuestSocketManager(final Game game) {
	this.game = game;
    }

    /**
     * Connect to host.
     * 
     * @param method
     *            The method to execute after connection.
     */
    public void connectToHost(final Runnable method) {
	// Thread
	guestThread = new GuestThread(port);
    }

    /**
     * Sets the port.
     * 
     * @param port
     *            The port.
     */
    public void setPort(final int port) {
	this.port = port;
    }

}

/** {@link Thread} who listen the client {@link Socket}. */
class GuestThread extends Thread {

    /** Method to execute after {@link AttackDto} received. */
    private Consumer<AttackDto> onAttackReceivedListener;

    /** Method to execute after guest connection. */
    private Runnable onGuestConnectedListener;

    /** Method to execute after {@link PositionsDto} received. */
    private Consumer<PositionsDto> onPositionsReceivedListener;

    /** The client {@link Socket}. */
    private Socket socketClient;

    /** The server {@link Socket}. */
    private final Socket socket;

    /**
     * The client {@link Socket}.
     * 
     * @param port
     *            The port of {@link Socket}.
     */
    public GuestThread(final int port) {
	try {
	    socket = new Socket("localhost", 49152);
	} catch (IOException exception) {
	    throw new SocketException("Error during serveur socket creation.",
		    exception);
	}
    }

    /** Listening guest. */
    @Override
    public void run() {
    }

    /**
     * Send message to client.
     * 
     * @param object
     *            The message to send.
     */
    public void send(final Object object) {
	try {
	    OutputStream outputStream = socketClient.getOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		    outputStream);
	    objectOutputStream.writeObject(object);
	    objectOutputStream.flush();
	    objectOutputStream.close();
	} catch (IOException exception) {
	    throw new SocketException("Error serializing message.", exception);
	}
    }

    public void setOnAttackReceivedListener(final Consumer<AttackDto> consumer) {
	onAttackReceivedListener = consumer;
    }

    public void setOnGuestConnectedListener(final Runnable method) {
	onGuestConnectedListener = method;
    }

    public void setOnPositionsReceivedListener(
	    final Consumer<PositionsDto> consumer) {
	onPositionsReceivedListener = consumer;
    }

}