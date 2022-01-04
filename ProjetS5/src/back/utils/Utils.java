package back.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import static java.lang.Math.abs;

public class Utils {

	public static int createRandomId() {
		Random r = new Random();
		return abs(r.nextInt());
	}

	// Avoids long nested try catch statements (Closes Client cx)
	public static void closeAll(Socket socket, BufferedReader in, PrintWriter out, int clientId){
		try {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			if(socket != null){
				socket.close();
			}
			System.out.println("[SERVER] Closing connection with client (" + clientId + ")");

		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
