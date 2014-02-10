package fr.pinguet62.battleship.model.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import fr.pinguet62.battleship.Consumer;
import fr.pinguet62.battleship.socket.Positions;
import fr.pinguet62.battleship.socket.SocketException;

public final class ThreadSocket extends Thread {

    public static Logger LOGGER = Logger
	    .getLogger(ThreadSocket.class.getName());

    private int port = -1;

    private Socket socketClient;

    private final ServerSocket socketServeur;

    public ThreadSocket() {
	// try {
	socketServeur = null; // new ServerSocket(port);
	// } catch (IOException e) {
	// throw new SocketException("Error creating server socket.", e);
	// }
    }

    @Override
    protected void finalize() throws Throwable {
	if (socketClient != null)
	    socketClient.close();
	if (socketServeur != null)
	    socketServeur.close();
	super.finalize();
    }

    /**
     * Gets the port.
     * 
     * @return The port.
     */
    public int getPort() {
	return port;
    }

    @Override
    public void run() {
	// try {
	// socketClient = socketServeur.accept();
	// while (true) {
	// InputStream inputStream = socketClient.getInputStream();
	// ObjectInputStream objectInputStream = new ObjectInputStream(
	// inputStream);
	// try {
	// Attack message = (Attack) objectInputStream.readObject();
	// message.getCoordinates();
	// } catch (ClassNotFoundException e) {
	// ThreadSocket.LOGGER
	// .info("Invalid message received by the client socket.");
	// }
	// }
	// } catch (IOException e) {
	// throw new SocketException("Error creating client socket.", e);
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
	    OutputStream outputStream = socketClient.getOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		    outputStream);
	    objectOutputStream.writeObject(object);
	    objectOutputStream.flush();
	    objectOutputStream.close();
	} catch (IOException e) {
	    throw new SocketException("Error serializing message.", e);
	}
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
     * Wait the connection of the client.
     * 
     * @param method
     *            The method to execute after connection.
     */
    public void waitClientConnection(final Runnable method) {
	if (socketClient == null)
	    try {
		socketClient = socketServeur.accept();
	    } catch (IOException exception) {
		ThreadSocket.LOGGER.severe("Error creating client socket: "
			+ exception.getMessage());
		throw new SocketException("Error creating client socket.",
			exception);
	    }
	if (method != null)
	    method.run();
    }

    /**
     * Wait the positioning of the client.
     * 
     * @param method
     *            The method to execute after connection.
     */
    public void waitClientPositioning(final Consumer<Positions> method) {
	InputStream inputStream;
	try {
	    inputStream = socketClient.getInputStream();
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    inputStream);
	    Positions positions = (Positions) objectInputStream.readObject();
	    method.accept(positions);
	} catch (ClassNotFoundException exception) {
	    throw new SocketException(
		    "Error deserializing the client message.", exception);
	} catch (IOException exception) {
	    throw new SocketException("Error receiving the client message.",
		    exception);
	}
    }

}
