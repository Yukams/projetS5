package front.client;

import front.affichage.FenetreConnexion;
import server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Client {
    public final static String HOST = "127.0.0.1";
    private Socket socket;
    private String username;
    private String password;
    private DataInputStream objectInputStream; //Read
    private DataOutputStream dataOutputStream; //Write
    public static Map<Client, Integer> clients = new HashMap<>(); //Each validated connexion is added to the map //TODO

    public Client(String username, String password){
        try {
            this.socket = new Socket(HOST, Server.PORT);
            this.username = username;
            this.password = password;

            this.objectInputStream = new DataInputStream(this.socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            String authToken = this.serializeData();
            dataOutputStream.writeUTF(authToken);

            String responseToken = objectInputStream.readUTF(); // Wait for response

        } catch (IOException e) {
            closeAll(socket, dataOutputStream, objectInputStream);
        }
    }

    public String serializeData(){
            StringBuilder authSB = new StringBuilder();
            authSB.append("{username:"+username+",password:"+password+"}");
            return authSB.toString();
    }
    // Avoids long nested try catch statements
    public void closeAll(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream){
        try{
            if(dataInputStream != null){
                dataInputStream.close();
            }
            if(dataOutputStream != null){
                dataOutputStream.close();
            }
            if(socket != null){
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
/*-----------------------------------------------------------------------*/
    public static void main(String[] args){
        launchConnexionWindow();
    }
    // Lance l'application cote client
    private static void launchConnexionWindow() {
        FenetreConnexion fen = new FenetreConnexion();
        fen.setVisible(true);
    }
}
