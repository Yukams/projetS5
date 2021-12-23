package client;

import server.Server;

import java.io.IOException;

import java.net.Socket;




public class Client {
    public final static String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(HOST, Server.PORT);
        System.out.println("Client");
    }
}
