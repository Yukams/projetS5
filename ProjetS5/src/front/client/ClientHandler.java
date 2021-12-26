package front.client;

import front.affichage.FenetreConnexion;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandler = new ArrayList<>(); //Tous les clients /=> static: appartient à la classe (pas à chaque instance d'obj)
    private Socket socket;
    private DataOutputStream dataOutputStream; //writer
    private ObjectInputStream objectInputStream; //reader
    private FenetreConnexion connexionWindow;
    private String usernamePasswordToken;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());//writer
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());//reader


            clientHandler.add(this);
        } catch (IOException e){
            closeAll(socket, dataOutputStream, objectInputStream);
        }
    }

    @Override
    public void run() {
        // TODO
        launchConnexionWindow();
    }

    private void launchConnexionWindow() {
        System.out.println("Launching Window");
        this.connexionWindow = new FenetreConnexion();
        connexionWindow.setVisible(true);
    }

    public void closeAll(Socket socket, DataOutputStream dataOutputStream, ObjectInputStream objectInputStream){
        clientHandler.remove(this); //remove client from list

        try{
            if(objectInputStream != null){
                objectInputStream.close();
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


}
