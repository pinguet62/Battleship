package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import fr.pinguet62.battleship.controller.Controller;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class ThreadSocket extends Thread {

	public static Logger LOGGER = Logger
			.getLogger(ThreadSocket.class.getName());

	private static final int PORT = 9632;

	private final Controller controller;

	private Socket socketClient;

	private final ServerSocket socketServeur;

	public ThreadSocket(Controller controller) {
		this.controller = controller;
		try {
			socketServeur = new ServerSocket(ThreadSocket.PORT);
		} catch (IOException e) {
			throw new SocketException("Error creating server socket.", e);
		}
	}

	@Override
	public void run() {
		try {
			socketClient = socketServeur.accept();
			while (true) {
				InputStream inputStream = socketClient.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(
						inputStream);
				try {
					Attack message = (Attack) objectInputStream.readObject();
					Coordinates coordinates = message.getCoordinates();
					controller.receiveAttack(coordinates);
				} catch (ClassNotFoundException e) {
					LOGGER.info("Invalid message received by the client socket.");
				}
			}
		} catch (IOException e) {
			throw new SocketException("Error creating client socket.", e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (socketClient != null) {
			socketClient.close();
		}
		if (socketServeur != null) {
			socketServeur.close();
		}
		super.finalize();
	}

}
