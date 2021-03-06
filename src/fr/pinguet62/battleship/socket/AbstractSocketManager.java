package fr.pinguet62.battleship.socket;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** Interacts with {@link AbstractThreadSocket}. */
public abstract class AbstractSocketManager {

    /** The {@link Game}. */
    protected final Game game;

    /** The {@link InetAddress}. */
    protected InetAddress inetAddress;

    /** The port of {@link Socket}. */
    protected int port = -1;

    /** The {@link AbstractThreadSocket}. */
    protected AbstractThreadSocket threadSocket;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public AbstractSocketManager(final Game game) {
	this.game = game;
    }

    /**
     * Method of connection to host or waiting for guest.
     * 
     * @param method
     *            The {@link Runnable} to execute after connection.
     */
    public abstract void connect(final Runnable method);

    /**
     * Send message to client.
     * 
     * @param object
     *            The message to send.
     */
    public void send(final Object object) {
	threadSocket.send(object);
    }

    /**
     * 
     * Gets the {@link InetAddress}.
     * 
     * @param inetAddress
     *            The {@link InetAddress}.
     */
    public void setInetAddress(final InetAddress inetAddress) {
	this.inetAddress = inetAddress;
    }

    /**
     * Sets the {@link Consumer} to execute after {@link AttackDto} reception.
     * 
     * @param onAttackReceived
     *            The {@link Consumer} to execute.
     */
    public void setOnAttackReceivedListener(
	    final Consumer<AttackDto> onAttackReceived) {
	threadSocket.setOnAttackReceivedListener(onAttackReceived);
    }

    /**
     * Sets the {@link Consumer} to execute after {@link ParametersDto}
     * reception.
     * 
     * @param onParametersReceived
     *            The {@link Consumer} to execute.
     */
    public void setOnParametersReceivedListener(
	    final Consumer<ParametersDto> onParametersReceived) {
	threadSocket.setOnParametersReceivedListener(onParametersReceived);
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
	threadSocket.setOnPositionsReceivedListener(onPositionsReceived);
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

    /** Close the {@link Socket} or {@link ServerSocket} and stop the thread. */
    public void stop() {
	threadSocket.closeAndStop();
    }

}
