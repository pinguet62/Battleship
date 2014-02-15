package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;
import fr.pinguet62.utils.Consumer;

/** Class who interacts with guest user. */
public final class HostSocketManager {

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
    public HostSocketManager(final Game game) {
	this.game = game;
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
	myThread = new MyThread(port);
	myThread.setOnGuestConnectedListener(new Runnable() {
	    /** Send {@link ParametersDto} to guest. */
	    @Override
	    public void run() {
		ParametersDto parameters = new ParametersDto(game.getWidth(),
			game.getHeight(), game.getBoatEntries());
		myThread.send(parameters);
		onParametersSent.run();
	    }
	});
	myThread.start();
    }

}

/** {@link Thread} who listen the client {@link Socket}. */
class MyThread extends Thread {

    /** The {@link Consumer} to execute after {@link AttackDto} reception. */
    private Consumer<AttackDto> onAttackReceivedListener;

    /** The {@link Runnable} to execute after guest connection. */
    private Runnable onGuestConnectedListener;

    /** The {@link Consumer} to execute after {@link PositionsDto} reception. */
    private Consumer<PositionsDto> onPositionsReceivedListener;

    /** The client {@link Socket}. */
    private Socket socketClient;

    /** The server {@link Socket}. */
    private final ServerSocket socketServeur;

    /**
     * The server {@link Socket}.<br />
     * Initialize the server {@link Socket}.
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
	// Guest connection
	try {
	    System.out.println("Waiting guest connection...");
	    socketClient = socketServeur.accept();
	    System.out.println("Guest connected.");
	    if (onGuestConnectedListener != null)
		onGuestConnectedListener.run();
	} catch (IOException exception) {
	    throw new SocketException("Error creating client socket.",
		    exception);
	}

	InputStream inputStream = null;
	try {
	    inputStream = socketClient.getInputStream();
	} catch (IOException exception) {
	    throw new SocketException("Error during getting input stream.",
		    exception);
	}

	// Positioning
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
	    System.out.println(String.format("Sending [%s] to guest... (%s)",
		    object.getClass().getSimpleName(), object));
	    OutputStream outputStream = socketClient.getOutputStream();
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
     * Sets the {@link Runnable} to execute after guest connection.
     * 
     * @param onGuestConnected
     *            The {@link Runnable} to execute.
     */
    public void setOnGuestConnectedListener(final Runnable onGuestConnected) {
	onGuestConnectedListener = onGuestConnected;
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