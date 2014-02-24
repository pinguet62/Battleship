package fr.pinguet62.battleship.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.PositionsDto;

/** {@link Thread} who listen and send messages to {@link Socket}. */
public abstract class AbstractThreadSocket extends Thread {

    /** The {@link Consumer} to execute after {@link AttackDto} reception. */
    protected Consumer<AttackDto> onAttackReceivedListener;

    /** The {@link Runnable} to execute after guest connection. */
    protected Consumer<ParametersDto> onParametersReceivedListener;

    /** The {@link Consumer} to execute after {@link PositionsDto} reception. */
    protected Consumer<PositionsDto> onPositionsReceivedListener;

    /** The {@link Socket}. */
    protected Socket socket;

    /** Close the {@link Socket} or {@link ServerSocket} and stop the thread. */
    public void closeAndStop() {
	try {
	    socket.close();
	} catch (IOException e) {
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
	    System.out.println(String.format("Sending [%s] to guest... (%s)",
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
     * Sets the {@link Runnable} to execute after {@link ParametersDto}
     * reception.
     * 
     * @param onParametersReceived
     *            The {@link Runnable} to execute.
     */
    public void setOnParametersReceivedListener(
	    final Consumer<ParametersDto> onParametersReceived) {
	onParametersReceivedListener = onParametersReceived;
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
