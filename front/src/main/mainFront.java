package main;

import affichage.ConnexionWindow;

public class mainFront {
    public static ConnexionWindow connexionWindow;
    public static void main(String[] args){
        connexionWindow = launchConnexionWindow();
    }
    // Launch connexion window
    private static ConnexionWindow launchConnexionWindow() {
        ConnexionWindow connexionWindow = new ConnexionWindow();
        connexionWindow.setVisible(true);
        return connexionWindow;
    }
    //Reconnection
    public static void reconnect() throws InterruptedException {
        connexionWindow.setVisible(true);
        connexionWindow.reconnect();
    }
}
