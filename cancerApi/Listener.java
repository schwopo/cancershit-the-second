package cancerApi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Listener implements Runnable {
	ObjectInputStream ois;
	Socket socket;
	CancerClient client;
	
	public Listener(Socket s, CancerClient c) throws IOException {
		ois = new ObjectInputStream(s.getInputStream());
		socket = s;
		client = c;
	}

	public void run() {
		while (!socket.isInputShutdown()) {
			try {
				Message msg = (Message) ois.readObject();
				client.onMessageIncoming(msg);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				client.disconnect();
			}
		}
	}
	
	public void close() {
		try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
