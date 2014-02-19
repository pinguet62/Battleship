package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** {@link Thread} who listen the client {@link Socket}. */
final class GuestThreadSocket extends AbstractThreadSocket {

    /**
     * The client {@link Socket}.
     * 
     * @param inetAddress
     *            The server {@link InetAddress}.
     * @param port
     *            The port of {@link Socket}.
     * @throws SocketException
     *             Error during {@link ServerSocket} creation.
     */
    public GuestThreadSocket(final InetAddress inetAddress, final int port) {
	try {
	    socket = new Socket(inetAddress, port);
	    System.out.println("Connected to host.");
	} catch (IOException exception) {
	    throw new SocketException("Error during server socket creation.",
		    exception);
	}
    }

    /** Listening host. */
    @Override
    public void run() {
	InputStream inputStream = null;
	try {
	    inputStream = socket.getInputStream();
	} catch (IOException exception) {
	    throw new SocketException("Error during getting input stream.",
		    exception);
	}

	// Parameters
	try {
	    System.out.println("Waiting host parameters...");
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    inputStream);
	    ParametersDto parametersDto = (ParametersDto) objectInputStream
		    .readObject();
	    System.out.println("Parameters received: " + parametersDto);
	    if (onParametersReceivedListener != null)
		onParametersReceivedListener.accept(parametersDto);
	} catch (IOException | ClassNotFoundException exception) {
	    throw new SocketException(
		    "Error receiving boat positions from guest.", exception);
	}

	// Positions
	try {
	    System.out.println("Waiting host positions...");
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
		System.out.println("Waiting host attack...");
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

}
