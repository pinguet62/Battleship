import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import fr.pinguet62.battleship.socket.dto.ParametersDto;

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
