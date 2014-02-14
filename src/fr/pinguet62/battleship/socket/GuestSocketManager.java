package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;
import fr.pinguet62.utils.Consumer;

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
    public void connectToHost(final Consumer<ParametersDto> consumer) {
	guestThread = new GuestThread(port);
	guestThread.setOnConnectedListener(consumer);
	guestThread.start();
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

    /** The {@link Consumer} to execute after {@link AttackDto} reception. */
    private Consumer<AttackDto> onAttackReceivedListener;

    /** The {@link Runnable} to execute after guest connection. */
    private Consumer<ParametersDto> onConnectedListener;

    /** The {@link Consumer} to execute after {@link PositionsDto} reception. */
    private Consumer<PositionsDto> onPositionsReceivedListener;

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
	    socket = new Socket("localhost", port);
	    System.out.println("Connected to host.");
	} catch (IOException exception) {
	    throw new SocketException("Error during serveur socket creation.",
		    exception);
	}
    }

    /** Listening guest. */
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
	    if (onConnectedListener != null)
		onConnectedListener.accept(parametersDto);
	} catch (IOException | ClassNotFoundException exception) {
	    throw new SocketException(
		    "Error receiving boat positions from guest.", exception);
	}

	// Positioning
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

	// while (true) {
	// // Attack
	// try {
	// ObjectInputStream objectInputStream = new ObjectInputStream(
	// inputStream);
	// AttackDto attackDto = (AttackDto) objectInputStream
	// .readObject();
	// if (onAttackReceivedListener != null)
	// onAttackReceivedListener.accept(attackDto);
	// } catch (IOException | ClassNotFoundException exception) {
	// throw new SocketException("Error receiving attack from guest.",
	// exception);
	// }
	// }
    }

    /**
     * Send message to client.
     * 
     * @param object
     *            The message to send.
     */
    public void send(final Object object) {
	try {
	    System.out.println(String.format("Sending [%s] to host... (%s)",
		    object.getClass().getSimpleName(), object));
	    OutputStream outputStream = socket.getOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		    outputStream);
	    objectOutputStream.writeObject(object);
	    objectOutputStream.flush();
	} catch (IOException exception) {
	    throw new SocketException("Error serializing message.", exception);
	}
    }

    /**
     * Sets the {@link Consumer} to execute after {@link AttackDto} reception.
     * 
     * @param onAttackReceived
     *            The {@link Consumer} to execute.
     */
    public void setOnAttackReceivedListener(
	    final Consumer<AttackDto> onAttackReceived) {
	onAttackReceivedListener = onAttackReceived;
    }

    /**
     * Sets the {@link Runnable} to execute after connection.
     * 
     * @param onConnected
     *            The {@link Runnable} to execute.
     */
    public void setOnConnectedListener(final Consumer<ParametersDto> onConnected) {
	onConnectedListener = onConnected;
    }

    /**
     * Sets the {@link Consumer} to execute after {@link PositionsDto}
     * reception.
     * 
     * @param onPositionsReceived
     *            The {@link Consumer} to execute.
     */
    public void setOnPositionsReceivedListener(
	    final Consumer<PositionsDto> onPositionsReceived) {
	onPositionsReceivedListener = onPositionsReceived;
    }

}