package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.pinguet62.battleship.Consumer;
import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** Class who interacts with guest user. */
public final class SocketManager {

    /** The {@link Game}. */
    private final Game game;

    /** The {@link MyThread}. */
    private MyThread myThread;

    /** The port of {@link Socket}. */
    private int port = -1;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public SocketManager(final Game game) {
	this.game = game;
    }

    /**
     * Gets the port.
     * 
     * @return The port.
     */
    public int getPort() {
	return port;
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

    /**
     * Initialize the {@link Socket} and the {@link Thread} waiting guest:
     * <ol>
     * <li>Create the {@link Thread};</li>
     * <li>Run the {@link Thread};</li>
     * <li>Create the server {@link Socket};</li>
     * <li>Wait client connection;</li>
     * <li>Send {@link ParametersDto} to client;</li>
     * <li>Execute the method.</li>
     * </ol>
     * 
     * @param method
     *            The method to execute after connection and sending of
     *            {@link ParametersDto}.
     */
    public void waitClientConnection(final Runnable method) {
	// Thread
	myThread = new MyThread(port);
	myThread.setOnGuestConnectedListener(new Runnable() {
	    /** Send {@link ParametersDto} to guest. */
	    @Override
	    public void run() {
		ParametersDto parameters = new ParametersDto(game.getWidth(),
			game.getHeight(), game.getBoatEntries());
		myThread.send(parameters);
		method.run();
	    }
	});
	myThread.start();
    }

}

/** {@link Thread} who listen the client {@link Socket}. */
class MyThread extends Thread {

    /** Method to execute after {@link AttackDto} received. */
    private Consumer<AttackDto> onAttackReceivedListener;

    /** Method to execute after guest connection. */
    private Runnable onGuestConnectedListener;

    /** Method to execute after {@link PositionsDto} received. */
    private Consumer<PositionsDto> onPositionsReceivedListener;

    /** The client {@link Socket}. */
    private Socket socketClient;

    /** The server {@link Socket}. */
    private final ServerSocket socketServeur;

    /**
     * The server {@link Socket}.
     * 
     * @param port
     *            The port of {@link Socket}.
     */
    public MyThread(final int port) {
	// Server socket
	try {
	    socketServeur = new ServerSocket(port);
	} catch (IOException exception) {
	    throw new SocketException("Error during serveur socket creation.",
		    exception);
	}
    }

    /** Listening guest. */
    @Override
    public void run() {
	// Guest connexion
	try {
	    socketClient = socketServeur.accept();
	} catch (IOException exception) {
	    throw new SocketException("Error creating client socket.",
		    exception);
	}
	if (onGuestConnectedListener != null)
	    onGuestConnectedListener.run();

	// Positioning
	try {
	    InputStream inputStream = socketClient.getInputStream();
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    inputStream);
	    PositionsDto positionsDto = (PositionsDto) objectInputStream
		    .readObject();
	    if (onPositionsReceivedListener != null)
		onPositionsReceivedListener.accept(positionsDto);
	} catch (IOException | ClassNotFoundException exception) {
	    throw new SocketException(
		    "Error receiving boat positions from guest.", exception);
	}

	while (true) {
	    try {
		InputStream inputStream = socketClient.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(
			inputStream);
		AttackDto attackDto = (AttackDto) objectInputStream
			.readObject();
		if (onAttackReceivedListener != null)
		    onAttackReceivedListener.accept(attackDto);
	    } catch (IOException | ClassNotFoundException exception) {
		throw new SocketException("Error receiving attack from guest.",
			exception);
	    }
	}
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