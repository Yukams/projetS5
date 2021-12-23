package server;

import client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    public final static int PORT = 5000;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{

            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Connexion d'un nouveau client");

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println("Thread End");

            }

        } catch (IOException e){

        }
    }

    public void closeServerSocket(){
        try{

            if(serverSocket != null){
                serverSocket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}
