package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** {@link Thread} who listen the client {@link Socket}. */
final class HostThreadSocket extends AbstractThreadSocket {

    /** The {@link Runnable} to execute after guest connection. */
    private Runnable onConnectedListener;

    /** The server {@link Socket}. */
    private final ServerSocket socketServeur;

    /**
     * The server {@link Socket}.<br />
     * Initialize the server {@link Socket}.
     * 
     * @param inetAddress
     *            The server {@link InetAddress}.
     * @param port
     *            The port of {@link Socket}.
     */
    public HostThreadSocket(final InetAddress inetAddress, final int port) {
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
	// Guest connection
	try {
	    System.out.println("Waiting guest connection...");
	    socket = socketServeur.accept();
	    System.out.println("Guest connected.");
	    if (onConnectedListener != null)
		onConnectedListener.run();
	} catch (IOException exception) {
	    throw new SocketException("Error creating client socket.",
		    exception);
	}

	InputStream inputStream = null;
	try {
	    inputStream = socket.getInputStream();
	} catch (IOException exception) {
	    throw new SocketException("Error during getting input stream.",
		    exception);
	}

	// Position
	try {
	    System.out.println("Waiting guest positions...");
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    inputStream);
	    PositionsDto positionsDto = (PositionsDto) objectInputStream
		    .readObject();
	    System.out.println("Boat positions received: " + positionsDto);
	    if (onPositionsReceivedListener != null)
		onPositionsReceivedListener.accept(positionsDto);
	} catch (IOException | ClassNotFoundException exception) {
	    throw new SocketException(
		    "Error receiving boat positions from guest.", exception);
	}

	while (true) {
	    // Attack
	    try {
		System.out.println("Waiting guest attack...");
		ObjectInputStream objectInputStream = new ObjectInputStream(
			inputStream);
		AttackDto attackDto = (AttackDto) objectInputStream
			.readObject();
		System.out.println("Attack received: " + attackDto);
		if (onAttackReceivedListener != null)
		    onAttackReceivedListener.accept(attackDto);
	    } catch (IOException | ClassNotFoundException exception) {
		throw new SocketException("Error receiving attack from guest.",
			exception);
	    }
	}
    }

    /**
     * Sets the {@link Runnable} to execute after guest connection.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute.
     */
    public void setOnConnectedListener(final Runnable onConnected) {
	onConnectedListener = onConnected;
    }

}