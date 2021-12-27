package front.client;

import com.google.gson.Gson;
import front.main.mainFront;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class RootRequest {
    public final static String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private Socket socket;
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();


    public RootRequest(){
        try {
            this.socket = new Socket(HOST, PORT);

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);


        } catch (IOException e) {
            mainFront.utils.closeAll(socket, in, out);
        }
    }



}
