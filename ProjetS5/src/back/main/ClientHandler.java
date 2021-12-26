package back.main;

import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import back.backobjects.users.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();

    public ClientHandler(Socket clientSocket) throws IOException {
        client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        String request = null;
        try {
            while (request == null) {
                request = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert request != null;

            System.out.println("[SERVER] Received request from client => " + request);
            ClientRequest serverPayload = gson.fromJson(request, ClientRequest.class);
            String response = treatRequest(serverPayload);
            System.out.println("[SERVER] Sending response to client => " + response);
            out.println(response);

            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String treatRequest(ClientRequest request) {
        String address = request.address;
        Map<String,String> payload = request.payload;
        String toClient = "\"null\"";

        if (address.equals("/user/getUserById")) {
            toClient = IUser.getUserById(payload);
        } else if (address.equals("/thread/getThreadsByUserId")) {
            toClient = IThread.getAllThreadForUser(payload);
        }

        return "{ \"payload\": " + gson.toJson(toClient) + "}";
    }
}
