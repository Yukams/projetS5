package main;

import affichage.ConnexionWindow;

import static java.lang.Thread.sleep;

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
        sleep(1500);
        connexionWindow.setVisible(true);
        connexionWindow.reconnect();
    }
}
