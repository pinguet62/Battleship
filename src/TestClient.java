import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.omg.Dynamic.Parameter;

import fr.pinguet62.battleship.model.socket.dto.ParametersDto;
import fr.pinguet62.battleship.model.socket.dto.PositionsDto;

public final class TestClient {

    public static void main(String[] args) throws Exception {
	Socket clientSocket = new Socket("localhost", 49152);
	
	InputStream inputStream = clientSocket.getInputStream();
	ObjectInputStream objectInputStream = new ObjectInputStream(
		inputStream);
	ParametersDto parametersDto = (ParametersDto) objectInputStream
		.readObject();
	System.out.println("parametersDto = " + parametersDto.toString());
	
	clientSocket.close();
    }
    
}
